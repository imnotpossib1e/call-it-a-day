package com.callitaday.monsterhunter.dto;

public class ItemDto {
    private int itemId;
    private String itemName;
    private int itemPrice;
    private int itemIncrease;
    private int itemType;

    private ItemTypeDto itemTypeDto;


    public ItemDto() {
    }

    public ItemDto(int itemId, String itemName, int itemPrice, int itemIncrease, int itemType) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
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

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
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

    public ItemTypeDto getItemTypeDto() {
        return itemTypeDto;
    }

    public void setItemTypeDto(ItemTypeDto itemTypeDto) {
        this.itemTypeDto = itemTypeDto;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ItemDto{");
        sb.append("itemId=").append(itemId);
        sb.append(", itemName='").append(itemName).append('\'');
        sb.append(", itemPrice=").append(itemPrice);
        sb.append(", itemIncrease=").append(itemIncrease);
        sb.append(", itemType=").append(itemType);
        sb.append('}');
        return sb.toString();
    }
}
