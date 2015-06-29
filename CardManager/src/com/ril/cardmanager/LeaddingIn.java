package com.ril.cardmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.ril.cardmanager.Leading.CardXmlLeaddingIn;
import com.ril.cardmanager.Leading.TeamMemberXmlLeaddingIn;
import com.ril.cardmanager.db.DBManager;
import com.ril.cardmanager.model.Card;
import com.ril.cardmanager.model.TeamMember;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class LeaddingIn extends Activity {
	private DBManager dbmanager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dbmanager = new DBManager(LeaddingIn.this);

		File cardxmlFile = new File("/sdcard/carddb.xml");
		File teamxmlFile = new File("/sdcard/teamdb.xml");
		FileInputStream inStream = null;
		try {
			if (cardxmlFile.isFile()) {
				inStream = new FileInputStream(cardxmlFile);
				List<Card> cards = CardXmlLeaddingIn.readCardXML(inStream);
				dbmanager.deleteCarddb();//
				// dbmanager.createCarddb();
				// 下面我要把他们（cards）插入到数据库中了~~~
				for (int index = 0; index < cards.size() - 1; index++) {// </table>导致额外的空card被加入到cards中
					Card card = cards.get(index);
					dbmanager.insert(card);
				}
			}
			if (teamxmlFile.isFile()) {
				inStream = new FileInputStream(teamxmlFile);

				List<TeamMember> teamMembers = TeamMemberXmlLeaddingIn
						.readTeamXML(inStream);
				dbmanager.deleteTeamdb();//
				// dbmanager.createCarddb();
				// 下面我要把他们（cards）插入到数据库中了~~~
				for (int index = 0; index < teamMembers.size() - 1; index++) {// </table>导致额外的空card被加入到cards中
					TeamMember tMember = teamMembers.get(index);
					dbmanager.insertTeamMember(tMember);
				}
			}
			if (inStream != null)
				inStream.close();
			dbmanager.closeDB();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Toast.makeText(getApplicationContext(), "SIM卡信息和Team信息已经导入！",
				Toast.LENGTH_SHORT).show();
		this.finish();
	}
}
