package com.callitaday.monsterhunter.dto;

public class InventoryDto {
	private int userId;
	private int quantity;
	private boolean isEquipped;
	private int itemId;
	
	private ItemDto itemDto;
	
	public InventoryDto() {
		
	}

	public InventoryDto(int userId, int quantity, boolean isEquipped, int itemId) {
		this.userId = userId;
		this.quantity = quantity;
		this.isEquipped = isEquipped;
		this.itemId = itemId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isEquipped() {
		return isEquipped;
	}

	public void setEquipped(boolean isEquipped) {
		this.isEquipped = isEquipped;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public ItemDto getItemDto() {
		return itemDto;
	}

	public void setItemDto(ItemDto itemDto) {
		this.itemDto = itemDto;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryDto [userId=");
		builder.append(userId);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", isEquipped=");
		builder.append(isEquipped);
		builder.append(", itemDto=");
		builder.append(itemDto);
		builder.append("]");
		return builder.toString();
	}
	
	
}
