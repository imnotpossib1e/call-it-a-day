package com.callitaday.monsterhunter.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.callitaday.monsterhunter.dao.CharacterInfoDao;
import com.callitaday.monsterhunter.dao.CharacterInfoDaoImpl;
import com.callitaday.monsterhunter.dao.ItemDao;
import com.callitaday.monsterhunter.dao.ItemDaoImpl;
import com.callitaday.monsterhunter.dao.StageDao;
import com.callitaday.monsterhunter.dao.StageDaoImpl;

import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.dto.StageDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.dto.CharacterInfoDto;

public class BattleServiceImpl implements BattleService{
	private static final int MAX_HP = 100;
	private static final int MAX_MP = 100;
	
	private final StageDao stageDao = new StageDaoImpl();
	private final CharacterInfoDao characterInfoDao = new CharacterInfoDaoImpl();
	private final ItemDao itemDao = new ItemDaoImpl();
	
	private final Map<Integer, CharacterInfoDto> users = new HashMap<>();
	
	private static final BattleService instance = new BattleServiceImpl();
	
    public static BattleService getInstance(){
        return instance;
    }
	
	/**
	 * 현재 stage_id로 전투 시작
	 */
	@Override
	public StageDto startBattle(int userId) throws SQLException {
		// TODO Auto-generated method stub
		CharacterInfoDto user = loadUser(userId);
		
		return prepareBattle(user, user.getStage_id());
	}

	/**
	 * 지정한 스테이지로 전투 시작
	 */
	@Override
	public StageDto startBattle(int userId, int stageId) throws SQLException {
		CharacterInfoDto user = loadUser(userId);
		
		return prepareBattle(user, stageId);
	}
	
	private CharacterInfoDto loadUser(int userId) throws SQLException {
		CharacterInfoDto user = characterInfoDao.getCharacterByUserId(userId);
		if (user == null) throw new SQLException("캐릭터를 찾을 수 없습니다.");
		
		return user;
	}
	
	/**
	 * 적 로딩하기
	 */
	private StageDto prepareBattle(CharacterInfoDto user, int stageId) throws SQLException {
		
		if (user.getHp() <= 0) {
            throw new SQLException("체력이 0이므로 입장할 수 없습니다.");
        }
		
		if (stageDao.isStageCleared(user.getUserId(), stageId)) {
            throw new SQLException("이미 클리어한 스테이지입니다.");
        }
		
		StageDto enemy = stageDao.enemyInfoForFight(stageId);
		
		if (enemy == null) {
            throw new SQLException("해당 스테이지를 찾을 수 없습니다.");
        }
		
		if (enemy.getEnemyHp() <= 0) {
			throw new SQLException("적의 체력이 비정상적 수치입니다.");
		}
		
		user.setStage_id(stageId);
        user.setStageDto(enemy);
        
        users.put(user.getUserId(), user);
        
        return enemy;
	}
	
	/**
	 * 전투 중인 user 객체 반환
	 */
	@Override
    public CharacterInfoDto getBattleUser(int userId) throws SQLException {
		CharacterInfoDto user = users.get(userId);
		
		if (user == null) {
            throw new SQLException("먼저 전투를 시작하세요.");
        }
		
		return user;
	}
	
	/**
	 * 랜덤 다이스 생성
	 */
	@Override
	public int randomDice() {
		// TODO Auto-generated method stub
		int num = (int)(Math.random() * 11);
		return num;
	}

	/**
	 * 유저의 공격
	 */
	@Override
	public int userAttack(int userId) throws SQLException {
		// TODO Auto-generated method stub
		CharacterInfoDto user = getBattleUser(userId);
        StageDto enemy = user.getStageDto();
        
        if (user.getMp() <= 0) {
            throw new SQLException("공격을 위한 마나가 부족합니다.");
        }
		
        int userDice = randomDice();
        int enemyDice = randomDice();
        int manaCost = userDice;
        int previousMp = user.getMp();
        
        if (userDice <= enemyDice) {
            return 0;
        }
        
        int damage = Math.max(0, user.getAtk() - enemy.getEnemyDef());
        int actualDamage = Math.min(damage, enemy.getEnemyHp());
        
        enemy.setEnemyHp(enemy.getEnemyHp() - actualDamage);
        
        int missingMana = Math.max(0, manaCost - previousMp);
        
        user.setHp(Math.max(0,  user.getHp() - missingMana * 10));

		return actualDamage;
	}

	/**
	 * 적의 공격
	 */
	@Override
	public int enemyAttack(int userId) throws SQLException {
		// TODO Auto-generated method stub
		CharacterInfoDto user = getBattleUser(userId);
		StageDto enemy = user.getStageDto();
		
		int userDice = randomDice();
        int enemyDice = randomDice();
		
        if (enemyDice <= userDice) {
            return 0;
        }
        
        int damage = Math.max(0, enemy.getEnemyAtk() - user.getDef());
        
        int actualDamage = Math.min(damage, user.getHp());

        user.setHp(user.getHp() - actualDamage);

        return actualDamage;
	}
	
	/**
	 * 유저의 방어
	 */
	@Override
	public int userDefend(int userId) throws SQLException {
		// TODO Auto-generated method stub
		CharacterInfoDto user = getBattleUser(userId);
	    StageDto enemy = user.getStageDto();
	    
	    int userDice = randomDice();
	    int enemyDice = randomDice();
	    
	    if (userDice >= enemyDice) {
	    	if(userDice == 10) {
	    		int counterDamage = (userDice - enemyDice) * 10;
	    		
	    		enemy.setEnemyHp(Math.max(0, enemy.getEnemyHp() - counterDamage));
	    	}
	    	
	    	return 0;
	    }
	    	    
	    int damage = Math.max(0, enemy.getEnemyAtk() - user.getDef());
	    int actualDamage = Math.min(damage, user.getHp());

	    user.setHp(user.getHp() - actualDamage);

	    return actualDamage;
	}

	/**
	 * 포션 아이템 사용
	 */
	@Override
	public CharacterInfoDto useItem(int userId, int itemId) throws SQLException {
		// TODO Auto-generated method stub
		CharacterInfoDto user = getBattleUser(userId);		
		ItemDto item = itemDao.getItemByItemId(itemId);
		
		if (item == null) {
            throw new SQLException("아이템을 찾을 수 없습니다.");
        }
		
		boolean hpPotion = "회복포션".equals(item.getItemType());		
		boolean mpPotion = "마나포션".equals(item.getItemType());
		
		if (!hpPotion && !mpPotion) {
            throw new SQLException("포션만 사용할 수 있습니다.");
        }
		
		if (item.getItemIncrease() <= 0) {
            throw new SQLException("포션 회복량이 올바르지 않습니다.");
        }
		
		int current = hpPotion ? user.getHp() : user.getMp();
        int maximum = hpPotion ? MAX_HP : MAX_MP;
        
        int recovery = Math.min(item.getItemIncrease(), maximum - current);
        
        int result = stageDao.useItem(userId, itemId);
        
        if (result != 1) {
            throw new SQLException("보유한 포션이 없습니다.");
        }
        
        if (current >= maximum) {
            throw new SQLException("수치가 가득 차있어 회복할 수 없습니다.");
        }
		
        if (hpPotion) {
            user.setHp(user.getHp() + recovery);
        } else {
            user.setMp(user.getMp() + recovery);
        }

        return user;
	}
	
	/**
	 * 스테이지 클리어 저장
	 */
	@Override
	public int saveBattle(int userId) throws SQLException, NotFoundException, AddException, ModifyException {
		CharacterInfoDto user = getBattleUser(userId);
        StageDto enemy = user.getStageDto();
        
        boolean victory = user.getHp() > 0 && enemy.getEnemyHp() <= 0;
        int result = stageDao.saveBattle(user);
        
        if (result > 1) {
            throw new ModifyException("user 정보가 중복됩니다.");
        }
        
        if (result == 0) {
            CharacterInfoDto stored = characterInfoDao.getCharacterByUserId(userId);

            if (stored == null
                    || stored.getHp() != user.getHp()
                    || stored.getMp() != user.getMp()
                    || stored.getStage_id() != user.getStage_id()) {

                throw new ModifyException("저장에 실패했습니다.");
            }
        }
        
        if(victory) {
        	int rewardResult = stageDao.addRewardItem(userId, enemy.getStageId());
        	
        	if (rewardResult != 1) {
                throw new AddException("보상 지급에 실패했습니다.");
            }
        }
        
        users.remove(userId);
        
        return result;
	}
	
}
