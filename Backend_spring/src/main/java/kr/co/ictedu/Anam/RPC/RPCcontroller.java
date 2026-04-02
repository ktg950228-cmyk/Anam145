package kr.co.ictedu.Anam.RPC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/rpc")
public class RPCcontroller {
	private String url = "https://eth-sepolia.g.alchemy.com/v2/";						// 이더리움 테스트넷
	private String API_KEY = "";														// API키(Alchemy 네트워크) 작성요망
	private Map<String, BalanceVO> cache = new HashMap<>();								// 잔액들 저장할 캐시 객체
	private RestTemplate restTemplate = new RestTemplate();								// HttpEntity 전송 객체
	
	@GetMapping("/getbalance")
	public String getBalance(@RequestParam Map<String, Object> req) throws Exception {	// req = {address:"0x~~~"}형태로 날아옴
		BalanceVO bVO = (BalanceVO) cache.get((String) req.get("address"));				// 필드 두 개 VO(잔액, 저장시간)
		
		// 캐시에 있고 10초도 안됐으면
		if (bVO != null && System.currentTimeMillis() - bVO.getTime() < 10000) {
			return bVO.getBalance();
		}
		// 아니면
		else {		// HttpEntity객체(Headers와 Body로 구성) 만들고 RestTemplate의 exchange로 전송
			HttpHeaders headers = new HttpHeaders();									// head 제작
			headers.setContentType(MediaType.APPLICATION_JSON);							// json이라 명시
			
			Map<String, Object> body = new HashMap<>();									// body 제작(Alchemy API대로)
			body.put("id", 1);
			body.put("jsonrpc", "2.0");
			body.put("method", "eth_getBalance");
			List<String> params = new ArrayList<>();
			String address = (String) req.get("address");								// 요청받은 주소
			params.add(address);
			params.add("latest");
			body.put("params",params);
			
			HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);	// HttpEntity 완성
			
			ResponseEntity<String> response = restTemplate.exchange(url + API_KEY, HttpMethod.POST, request, String.class);	// 주소(문자열), 매서드객체, Http엔티티 객체, 받는 데이터 변환할 클래스
			ObjectMapper mapper = new ObjectMapper();									// 문자열 상태인 json을 변환해줄 ObjectMapper
			Map<String, Object> responseMap = mapper.readValue(response.getBody(), Map.class);	// Map으로 변환 {id:~, jsonrpc:~, result:~}
			
			// 캐시에 VO하나 새로넣음(잔액과 시간도 초기화)
			if (bVO == null) {			// 캐시에 VO없었다면 첫 생성
			    bVO = new BalanceVO();
			}
			String balance = (String) responseMap.get("result");
			bVO.setBalance(balance);
			bVO.setTime(System.currentTimeMillis());
			cache.put(address, bVO);
			return balance;																// 잔액만 반환
		}
	}
}