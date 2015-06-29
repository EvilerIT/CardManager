package com.ril.cardmanager.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ril.cardmanager.model.Card;
import com.ril.cardmanager.model.TeamMember;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
	private CardDBHelper cardhelper;
	private TeamDBHelper teamhelper;
	private SQLiteDatabase carddb;
	private SQLiteDatabase teamdb;
	private static String TAG = "DBManager";

	public DBManager(Context mContext) {
		// super();
		cardhelper = new CardDBHelper(mContext);
		teamhelper = new TeamDBHelper(mContext);
		carddb = cardhelper.getWritableDatabase();
		teamdb = teamhelper.getWritableDatabase();
		// initTeamDatabase();//加入home
	}

	public SQLiteDatabase getCarddb() {
		return carddb;
	}

	public SQLiteDatabase getTeamdb() {
		return teamdb;
	}

	private long initTeamDatabase() {
		Log.i(TAG, "initTeamDatabase");
		String sql = "SELECT * FROM team where name = 'home'";
		Cursor cursor = teamdb.rawQuery(sql, null);
		Log.i(TAG, "initTeamDatabase1");
		SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		String updateTime = df.format(new Date());// new Date()为获取当前系统时间
		Log.i(TAG, "initTeamDatabase2");
		if (cursor.getCount() <= 0) {
			Log.i(TAG, "initTeamDatabase cursor is null");
			ContentValues contentValue = new ContentValues();
			contentValue.put("singleId", 0);
			contentValue.put("name", "home");
			contentValue.put("phonenumber", "");
			contentValue.put("updatetime", updateTime);
			return teamdb.insert("team", null, contentValue);

		}
		return 1;
	}

	// _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, number TEXT, operator
	// INTEGER, updatetime TEXT
	public List<Card> query() {
		Log.i(TAG, "query");
		List<Card> cards = new ArrayList<Card>();
		String sql = "SELECT * FROM card";
		Cursor cursor = carddb.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String number = cursor.getString(cursor.getColumnIndex("number"));
			String team = cursor.getString(cursor.getColumnIndex("team"));
			String owner = cursor.getString(cursor.getColumnIndex("owner"));
			if (owner == "" || owner == null) {
				owner = "home";
			}
			int operator = cursor.getInt(cursor.getColumnIndex("operator"));
			String updatetime = cursor.getString(cursor
					.getColumnIndex("updatetime"));
			Card card = new Card(id, name, number, owner,team, operator, updatetime);
			cards.add(card);
		}
		return cards;
	}

	public List<TeamMember> queryTeamMember() {// 查询团队成员
		Log.i(TAG, "queryTeamMember");
		List<TeamMember> teamMembers = new ArrayList<TeamMember>();
		String sql = "SELECT * FROM team";
		Cursor cursor = teamdb.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String number = cursor.getString(cursor
					.getColumnIndex("phonenumber"));
			String singleId = cursor.getString(cursor
					.getColumnIndex("singleId"));
			String updatetime = cursor.getString(cursor
					.getColumnIndex("updatetime"));
			TeamMember teamMember = new TeamMember(id, singleId, name, number,
					updatetime);
			teamMembers.add(teamMember);
		}
		return teamMembers;
	}

	public ArrayList<String> queryTeamMemberNames() {// 查询团队成员名字
		Log.i(TAG, "queryTeamMember");
		String sql = "SELECT * FROM team";
		Cursor cursor = teamdb.rawQuery(sql, null);
		ArrayList<String> items = new ArrayList<String>();
		items.add("home");
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			Log.i(TAG, "queryTeamMember" + name);
			items.add(name);
		}
		return items;
	}

	public int update(Card card) {
		Log.i(TAG, "update" + card.getCardName());
		ContentValues contentValue = new ContentValues();
		contentValue.put("owner", card.getOwner());
		contentValue.put("team", card.getTeam());
		contentValue.put("updatetime", card.getUpdateTime());
		String[] args = { String.valueOf(card.getCardId()) };
		return carddb.update("card", contentValue, "_id=?", args);
	}

	public long insert(Card card) {
		Log.i(TAG, "insert" + card.getCardName());
		ContentValues contentValue = new ContentValues();
		contentValue.put("name", card.getCardName());
		contentValue.put("number", card.getCardNumber());
		contentValue.put("owner", card.getOwner());
		contentValue.put("team", card.getTeam());
		contentValue.put("operator", card.getOperatorTag());
		contentValue.put("updatetime", card.getUpdateTime());
		return carddb.insert("card", null, contentValue);
	}

	public int delete(Card card) {
		Log.i(TAG, "delete" + card.getCardName());
		return carddb.delete("card", "_id=?",
				new String[] { String.valueOf(card.getCardId()) });
	}

	public long insertTeamMember(TeamMember tMember) {
		// TODO Auto-generated method stub
		Log.i(TAG, "insert" + tMember.getTmname());
		ContentValues contentValue = new ContentValues();
		contentValue.put("singleId", tMember.getsingleId());
		contentValue.put("name", tMember.getTmname());
		contentValue.put("phonenumber", tMember.getPhoneNumber());
		contentValue.put("updatetime", tMember.getUpdateTime());
		return teamdb.insert("team", null, contentValue);
	}

	public void deleteCarddb() {
		carddb.delete("card", null, null);
	}

	public void deleteTeamdb() {
		// TODO Auto-generated method stub
		teamdb.delete("team", null, null);
	}

	public int delete(TeamMember tMember) {
		Log.d(TAG,
				"delete" + tMember.getTmname() + "	" + tMember.gettMemberId());
		return teamdb.delete("team", "_id=?",
				new String[] { String.valueOf(tMember.gettMemberId()) });
	}

	public int update(TeamMember tMember) {

		ContentValues contentValue = new ContentValues();
		contentValue.put("phonenumber", tMember.getPhoneNumber());
		contentValue.put("updatetime", tMember.getUpdateTime());
		String[] args = { String.valueOf(tMember.gettMemberId()) };
		return teamdb.update("team", contentValue, "_id=?", args);
	}

	public int queryCardOwner(String tempowner) {

		String sql = "SELECT * FROM team Where name = '" + tempowner + "'";
		Cursor cursor = teamdb.rawQuery(sql, null);
		Log.d(TAG, "cursor.getCount() " + cursor.getCount() + "	" + tempowner);

		if (cursor.getCount() > 0)
			return 1;
		else
			return -1;
	}

	public void closeDB() {
		carddb.close();
		teamdb.close();
		teamhelper.close();
		cardhelper.close();
	}

}
