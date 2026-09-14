package com.callitaday.monsterhunter.dto;

import java.util.List;

public class CharacterInfoDto {
	private int userId;
	private int hp = 100;
	private int mp = 100;
	private int atk = 20;
	private int def = 20;
	private int coin = 100;
	private int stage_id;
	
	private List<ItemDto> equiplist;
	private List<InventoryDto> invenlist;
	private StageDto stageDto;
	
	public CharacterInfoDto() {
	}

	public CharacterInfoDto(int userId, int hp, int mp, int atk, int def, int coin, int stage_id) {
		super();
		this.userId = userId;
		this.hp = hp;
		this.mp = mp;
		this.atk = atk;
		this.def = def;
		this.coin = coin;
		this.stage_id = stage_id;
	}
	
	public List<ItemDto> getEquiplist() {
		return equiplist;
	}

	public void setEquiplist(List<ItemDto> equiplist) {
		this.equiplist = equiplist;
	}
	
	public List<InventoryDto> getInvenlist() {
		return invenlist;
	}

	public void setInvenlist(List<InventoryDto> invenlist) {
		this.invenlist = invenlist;
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
		builder.append("CharacterInfoDto [userId=");
		builder.append(userId);
		builder.append(", hp=");
		builder.append(hp);
		builder.append(", mp=");
		builder.append(mp);
		builder.append(", atk=");
		builder.append(atk);
		builder.append(", def=");
		builder.append(def);
		builder.append(", coin=");
		builder.append(coin);
		builder.append(", stage_id=");
		builder.append(stage_id);
		return builder.toString();
	}
}
