# Anam145
지원자 김태규 Anam145 사전 과제 'Web3 Service 개발자' 제출용입니다

전체적인 구조는 데이터 재사용을 통한 자원 낭비 방지입니다. 지갑 주소마다 잔액 객체를 매핑하여 캐싱해두고 클라이언트 요청 시 캐시의 잔액 객체가 10초가 지나지 않았다면 기존의 잔액값을 반환하는 구조입니다. 이를 통해 서로 다른 사용자들에게도 동일한 기존 데이터를 반환하며 서버 자원을 효율적으로 활용하게됩니다.
<br><br><br><br>
<img width="1561" height="481" alt="0" src="https://github.com/user-attachments/assets/3bb8080f-3bbf-4187-a605-4468abdb65ea" />
<br>프론트 코드는 최소한으로 제작했습니다.
<br><br><br><br>
<img width="254" height="37" alt="1" src="https://github.com/user-attachments/assets/6ad6720c-bebd-4cb1-bdf4-03f11bf919eb" />
<br>리액트 프론트에서 이더리움 주소 작성 후 잔액 조회시 스프링 백엔드로 요청이 갑니다.
<br><br><br><br>
<img width="1137" height="188" alt="cache" src="https://github.com/user-attachments/assets/c3155282-27da-47a3-9c1d-4459e778d08f" />
<br>스프링 백엔드에서는 Map형태로 캐시 객체를 유지합니다<지갑주소 : VO>.
<br><br><br><br>
<img width="465" height="412" alt="VO" src="https://github.com/user-attachments/assets/20e61efc-2204-4ee3-8ab9-ac14aabfc8f0" />
<br>잔액과 저장시간을 담을 VO객체
<br><br><br><br>
<img width="1110" height="211" alt="3" src="https://github.com/user-attachments/assets/235d8338-005e-4063-8c9d-aa29ddad51ea" />
<br>프론트에서 보낸 주소를 캐시에서 검색하고 VO객체로 반환받습니다. 이 때 VO객체가 존재하고 time필드가 현재시간과 10초도 차이가 나지않는다면 기존의 balance필드를 그대로 반환합니다.
<br><br><br><br>
<img width="1606" height="608" alt="4" src="https://github.com/user-attachments/assets/00e3ccf4-34b1-41cc-bf9f-e7a51ed77f3f" />
<br>그 외의 경우엔 직접 RPC노드에 요청을 날립니다. 헤드 객체와 바디 객체를 포함한 HttpEntity객체를 직접 제작하고 restTemplate를 이용해 전송합니다.
<br>이후 문자열 형태로 받은 json데이터를 ObjectMapper객체를 이용해 자바 객체(이 경우엔 Map)화 시킵니다.
<br>마지막으로 VO객체를 직접 제작하거나 필드를 덮어 쓴 후 다시 캐시에 저장하고 프론트에게 잔액만 전달합니다.
<br><br><br><br>

<img width="449" height="33" alt="2" src="https://github.com/user-attachments/assets/655cf482-3e5a-414e-ae1c-26a65fb05d68" />
<br>잔액은 ETH 단위로 나타납니다.
<br><br><br><br>


<br>※ 프론트에서 env파일의 REACT_APP_URL 환경변수에 백엔드 URI를 작성해야합니다.
<br>※ 백엔드 Anam145BApplication에서 프론트의 Origin을 허용해줘야 합니다.
<br>※ 백엔드 RPCcontrollerAlchemy에서 Alchemy 네트워크의 API키를 작성해야합니다(공개 저장소라 비워뒀습니다 양해바랍니다).
