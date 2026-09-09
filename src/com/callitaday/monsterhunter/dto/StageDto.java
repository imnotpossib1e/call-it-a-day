package com.callitaday.monsterhunter.dto;

public class StageDto {
	private int stageId;
	private int enemyHp;
	private int enemyAtk;
	private int enemyDef;
	private int rewardCoin;
	
	private ItemDto itemdto;
	
	public StageDto() {}

	public StageDto(int stageId, int enemyHp, int enemyAtk, int enemyDef, int rewardCoin) {
		super();
		this.stageId = stageId;
		this.enemyHp = enemyHp;
		this.enemyAtk = enemyAtk;
		this.enemyDef = enemyDef;
		this.rewardCoin = rewardCoin;

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

	public ItemDto getItemdto() {
		return itemdto;
	}

	public void setItemdto(ItemDto itemdto) {
		this.itemdto = itemdto;
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
		builder.append(", rewardItem=");
		builder.append(itemdto.getItemName());
		builder.append("]");
		return builder.toString();
	}

}
