package com.callitaday.monsterhunter.dto;

public class CharactorInfoDto {
	private int userId;
	private int hp = 100;
	private int mp = 100;
	private int atk = 20;
	private int def = 20;
	private int job;
	private int coin = 100;
	private int stage_id;
	
	private StageDto stageDto;
	
	public CharactorInfoDto() {
	}

	public CharactorInfoDto(int userId, int hp, int mp, int atk, int def, int job, int coin, int stage_id) {

		super();
		this.userId = userId;
		this.hp = hp;
		this.mp = mp;
		this.atk = atk;
		this.def = def;
		this.job = job;
		this.coin = coin;

		this.stage_id = stage_id;

	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getJob() {
		return job;
	}

	public void setJob(int job) {
		this.job = job;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public StageDto getStageDto() {
		return stageDto;
	}

	public void setStageDto(StageDto stageDto) {
		this.stageDto = stageDto;
	}

	public int getStage_id() {
		return stage_id;
	}

	public void setStage_id(int stage_id) {
		this.stage_id = stage_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CharactorInfoDto [userId=");
		builder.append(userId);
		builder.append(", hp=");
		builder.append(hp);
		builder.append(", mp=");
		builder.append(mp);
		builder.append(", atk=");
		builder.append(atk);
		builder.append(", def=");
		builder.append(def);
		builder.append(", job=");
		builder.append(job);
		builder.append(", coin=");
		builder.append(coin);
		builder.append(", stage_id=");
		builder.append(stage_id);
		return builder.toString();
	}
}
