package com.callitaday.monsterhunter.dto;

public class ItemTypeDto {
    private int itemType;
    private String typeName;

    public ItemTypeDto(){

    }
    public ItemTypeDto(int itemType, String typeName) {
        this.itemType = itemType;
        this.typeName = typeName;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ItemTypeDto{");
        sb.append("itemType=").append(itemType);
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
