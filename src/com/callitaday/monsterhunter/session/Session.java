package com.callitaday.monsterhunter.session;

//로그인한 사용자 한 명을 나타내는 세션 정보 클래스
public class Session {
	private String sessionId;

	public Session() {
	}

	public Session(String sessionId) {
		this.sessionId = sessionId; // 로그인한 유저를 식별하는 값 (아이디)
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() { // 세션 정보를 사람이 보기 좋은 문자열로 출력할 때 사용
		return "Session [sessionId=" + sessionId + "님이 접속중입니다.]";
	}

	@Override
	public int hashCode() {// HashSet에 저장할 때 sessionId 기준으로 같은 객체인지 판단하기 위한 해시값
		return sessionId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {// 같은 sessionId를 가진 세션인지 비교 (중복 로그인 방지용)
		Session other = (Session) obj;
		if (sessionId.equals(other.sessionId)) {
			return true;
		} else {
			return false;
		}
	}
}
