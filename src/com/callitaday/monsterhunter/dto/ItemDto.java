package com.callitaday.monsterhunter.dto;

public class ItemDto {
    private int itemId;
    private String itemName;
    private int itemPrice;
    private int itemIncrease;
    private String itemExplanation;
    private String itemType;

    public ItemDto() {
    }

    public ItemDto(int itemId, String itemName) {
    	this.itemId = itemId;
        this.itemName = itemName;
    }
    
    public ItemDto(int itemId, String itemName, int itemPrice, int itemIncrease, String itemExplanation, String itemType) {
        this(itemId, itemName);
        this.itemPrice = itemPrice;
        this.itemIncrease = itemIncrease;
        this.itemExplanation = itemExplanation;
        this.itemType = itemType;
    }

    public String getItemExplanation() {
		return itemExplanation;
	}

	public void setItemExplanation(String itemExplanation) {
		this.itemExplanation = itemExplanation;
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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
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
