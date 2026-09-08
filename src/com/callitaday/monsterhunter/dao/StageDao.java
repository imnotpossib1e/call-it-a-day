package com.callitaday.monsterhunter.dao;

public interface StageDao {

	/**
	 * 공격하기(유저)
	 */
	public int userAttack ();
	
	/**
	 * 방어하기(유저)
	 */
	public int userDefend ();
	
	/**
	 * 아이템 사용
	 */
	public int useItem ();
	
	/**
	 * 스테이지 클리어 여부 체크
	 */
	public boolean stageCleared ();
	
	/**
	 * 스테이지 선택
	 */
	public int chooseStage ();
	
	/**
	 * 전투 종료 후 획득 재화 조정
	 */
	public int randomGetCoin ();
	
	/**
	 * 공격하기(상대)
	 */
	public int enemyAttack ();
	
	/**
	 * 방어하기(상대)
	 */
	public int enemyDefend ();

	/**
	 * 메인 전투
	 */
	public boolean mainfight();
}
