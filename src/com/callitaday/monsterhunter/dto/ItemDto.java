package com.callitaday.monsterhunter.dto;

public class ItemDto {
    private int itemId;
    private String itemName;
    private int itmePrice;
    private int itemIncrease;
    private int itemType;

    public ItemDto() {
    }

    public ItemDto(int itemId, String itemName, int itmePrice, int itemIncrease, int itemType) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itmePrice = itmePrice;
        this.itemIncrease = itemIncrease;
        this.itemType = itemType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItmePrice() {
        return itmePrice;
    }

    public void setItmePrice(int itmePrice) {
        this.itmePrice = itmePrice;
    }

    public int getItemIncrease() {
        return itemIncrease;
    }

    public void setItemIncrease(int itemIncrease) {
        this.itemIncrease = itemIncrease;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ItemDto{");
        sb.append("itemId=").append(itemId);
        sb.append(", itemName='").append(itemName).append('\'');
        sb.append(", itmePrice=").append(itmePrice);
        sb.append(", itemIncrease=").append(itemIncrease);
        sb.append(", itemType=").append(itemType);
        sb.append('}');
        return sb.toString();
    }
}
