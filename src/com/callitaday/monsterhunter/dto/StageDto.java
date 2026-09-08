package com.callitaday.monsterhunter.dto;

public class StageDto {
	private int stageId;
	private int enemyHp;
	private int enemyAtk;
	private int enemyDef;
	private int rewardCoin;
	private int itemId;
	
	public StageDto() {}

	public StageDto(int stageId, int enemyHp, int enemyAtk, int enemyDef, int rewardCoin, int itemId) {
		super();
		this.stageId = stageId;
		this.enemyHp = enemyHp;
		this.enemyAtk = enemyAtk;
		this.enemyDef = enemyDef;
		this.rewardCoin = rewardCoin;
		this.itemId = itemId;
	}

	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public int getEnemyHp() {
		return enemyHp;
	}

	public void setEnemyHp(int enemyHp) {
		this.enemyHp = enemyHp;
	}

	public int getEnemyAtk() {
		return enemyAtk;
	}

	public void setEnemyAtk(int enemyAtk) {
		this.enemyAtk = enemyAtk;
	}

	public int getEnemyDef() {
		return enemyDef;
	}

	public void setEnemyDef(int enemyDef) {
		this.enemyDef = enemyDef;
	}

	public int getRewardCoin() {
		return rewardCoin;
	}

	public void setRewardCoin(int rewardCoin) {
		this.rewardCoin = rewardCoin;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StageDto [stageId=");
		builder.append(stageId);
		builder.append(", enemyHp=");
		builder.append(enemyHp);
		builder.append(", enemyAtk=");
		builder.append(enemyAtk);
		builder.append(", enemyDef=");
		builder.append(enemyDef);
		builder.append(", rewardCoin=");
		builder.append(rewardCoin);
		builder.append(", itemId=");
		builder.append(itemId);
		builder.append("]");
		return builder.toString();
	}

}
