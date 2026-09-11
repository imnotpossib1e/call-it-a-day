package com.callitaday.monsterhunter.dto;

public class DefendDto {
    private int damage;
    private boolean result;

    public DefendDto() {

    }

    public DefendDto(int damage, boolean result) {
        this.damage = damage;
        this.result = result;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
