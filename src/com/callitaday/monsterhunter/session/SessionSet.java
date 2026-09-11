package com.callitaday.monsterhunter.session;

import com.callitaday.monsterhunter.dto.UserDto;

public class SessionSet {
	private static SessionSet ss = new SessionSet();
	private UserDto list; // 바로 옆 소문자 set 의 특성 => 중복안되고 순서없다!

	private SessionSet() {
		list = null;
	}

	public static SessionSet getInstance() {// SessionSet.getInstance() 호출해서 SessionSet 리턴받는다.
		return ss;
	}

	public UserDto getList() {
		return list;
	}

	public void setList(UserDto list) {
		this.list = list;
	}

}
