//.env파일에 백엔드 서버 URI 적어야됩니다

import axios from "axios";
import { useState } from "react";

function App() {
	const [address, setAddress] = useState<string>('');				// 유저가 입력하는 주소 문자열
	const [balance, setBalance] = useState<number | null>(null);	// 받은 잔액 데이터

	return (
		<div className="App">

			{/* 입력태그 */}
			<input type="text" placeholder="이더리움 주소 입력" onChange={(e) => { setAddress(e.target.value) }}></input>

			{/* 조회버튼 */}
			<button onClick={() => {
				axios.get(`${process.env.REACT_APP_URL}/anam145/rpc/getbalance?address=${address}`).then(response => setBalance(parseInt(response.data, 16)))	// 16진법 문자열이라 parseInt로 10진법 정수화
			}}>잔액조회</button>

			{/* 출력태그 */}
			<span>{balance != null ? ` ${balance / 10 ** 18} ETH` : ''}</span>	 {/* 1 ETH = 10**18 Wei */}
			
		</div>
	);
}
export default App;
