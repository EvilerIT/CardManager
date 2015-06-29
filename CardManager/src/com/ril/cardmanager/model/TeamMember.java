package com.ril.cardmanager.model;

import java.io.Serializable;

public class TeamMember implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1879184850590374884L;
	private int tMemberId;
	private String singleId;
	private String tmname;// Team member name
	private String phoneNumber;
	private String updateTime;

	public TeamMember(int id, String singleId, String tmname,
			String phoneNumber, String updateTime) {
		super();
		this.tMemberId = id;
		this.singleId = singleId;
		this.tmname = tmname;
		this.phoneNumber = phoneNumber;
		this.updateTime = updateTime;
	}

	public TeamMember() {
	}

	public int gettMemberId() {
		return tMemberId;
	}

	public void settMemberId(int tMemberId) {
		this.tMemberId = tMemberId;
	}

	public String getsingleId() {
		return singleId;
	}

	public void setsingleId(String singleId) {
		this.singleId = singleId;
	}

	public String getTmname() {
		return tmname;
	}

	public void setTmname(String tmname) {
		this.tmname = tmname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public boolean dbConsistsName(String name) {
		// 判断是否是数据库中的属性值
		if (name.equalsIgnoreCase("_id") || name.equalsIgnoreCase("name")
				|| name.equalsIgnoreCase("singleId")
				|| name.equalsIgnoreCase("phoneNumber")
				|| name.equalsIgnoreCase("updatetime"))
			return true;
		return false;
	}

	public void setNameValue(String name, String nameValue) {
		// 根据名字设置card值
		if (name.equalsIgnoreCase("name"))
			tmname = nameValue;
		else if (name.equalsIgnoreCase("singleId"))
			singleId = nameValue;
		else if (name.equalsIgnoreCase("phoneNumber"))
			phoneNumber = nameValue;
		else if (name.equalsIgnoreCase("updatetime"))
			updateTime = nameValue;
	}
}
