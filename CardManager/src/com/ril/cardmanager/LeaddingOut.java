package com.ril.cardmanager;

import java.io.IOException;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.ril.cardmanager.Leading.DatabaseDump;
import com.ril.cardmanager.db.DBManager;

public class LeaddingOut extends Activity {
	private DBManager dbmanager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dbmanager = new DBManager(LeaddingOut.this);

		SQLiteDatabase carddb = dbmanager.getCarddb();
		String carddbXML = "/sdcard/carddb.xml";
		DatabaseDump carddbDump = new DatabaseDump(carddb, carddbXML);

		SQLiteDatabase teamdb = dbmanager.getTeamdb();
	
		String teamdbXML = "/sdcard/teamdb.xml";
		DatabaseDump teamdbDump = new DatabaseDump(teamdb, teamdbXML);

		try {
			carddbDump.exportTable("card");
			teamdbDump.exportTable("team");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(getApplicationContext(),
				"SIM卡信息和Team信息已经导出到SD卡根目录下面了！", Toast.LENGTH_SHORT)
				.show();
		dbmanager.closeDB();
		this.finish();
	}
}
