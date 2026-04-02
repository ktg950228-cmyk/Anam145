package kr.co.ictedu.Anam.RPC;

public class BalanceVO {			// 잔액 객체
	
	private String balance;			// 잔액 값
	private long time;				// 불러온 시간
	
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
}
