package com.ril.cardmanager.model;

import java.io.Serializable;

public class Card implements Serializable {

	private static final long serialVersionUID = -7060210544600464481L;
	private int cardId;
	private String cardName;
	private String cardNumber;
	private String owner;
	/**
	 * 1 组内  2 组外
	 */
	private String team;//组内还是组外
	/**
	 * 1 CMCC 2 CU 3 CTC
	 */
	private int operatorTag;
	private String updateTime;

	public Card() {

	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Card(int cardId, String cardName, String cardNumber, String owner,String team,
			int operatorTag, String updateTime) {
		this.cardId = cardId;
		this.cardName = cardName;
		this.cardNumber = cardNumber;
		this.owner = owner;
		this.team = team;
		this.operatorTag = operatorTag;
		this.updateTime = updateTime;
	}

	public boolean dbConsistsName(String name) {
		// 判断是否是数据库中的属性值
		if (name.equalsIgnoreCase("_id") || name.equalsIgnoreCase("name")
				|| name.equalsIgnoreCase("number")
				|| name.equalsIgnoreCase("owner")
								|| name.equalsIgnoreCase("team")
				|| name.equalsIgnoreCase("operator")
				|| name.equalsIgnoreCase("updatetime"))
			return true;

		return false;
	}

	public void setNameValue(String name, String nameValue) {
		// 根据名字设置card值
		if (name.equalsIgnoreCase("name"))
			cardName = nameValue;
		else if (name.equalsIgnoreCase("number"))
			cardNumber = nameValue;
		else if (name.equalsIgnoreCase("owner"))
			owner = nameValue;
		else if (name.equalsIgnoreCase("team"))
			team = nameValue;
		else if (name.equalsIgnoreCase("operator"))
			operatorTag = Integer.parseInt(nameValue);
		else if (name.equalsIgnoreCase("updatetime"))
			updateTime = nameValue;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getOperatorTag() {
		return operatorTag;
	}

	public void setOperatorTag(int operatorTag) {
		this.operatorTag = operatorTag;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
