package com.ril.cardmanager.Leading;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;

import com.ril.cardmanager.model.Card;
import com.ril.cardmanager.model.TeamMember;

public class TeamMemberXmlLeaddingIn {
	final static String TAG = "XmlLeaddingInTeamMember";

	// 读取XML
	public static List<TeamMember> readTeamXML(InputStream inStream) {

		XmlPullParser parser = Xml.newPullParser();
		Log.d(TAG, "readXML");

		try {
			parser.setInput(inStream, "UTF-8");
			int eventType = parser.getEventType();

			TeamMember currentTeamMember = null;
			List<TeamMember> tMembers = new ArrayList<TeamMember>();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
					currentTeamMember = new TeamMember();
					break;

				case XmlPullParser.START_TAG:// 开始元素事件

					String name = parser.getName();

					if (currentTeamMember.dbConsistsName(name)) {// 检查数据库中是否有该属性值

						String nameValue = parser.nextText();

						Log.d(TAG, "getName&NameValue :" + name + "	"
								+ nameValue);

						currentTeamMember.setNameValue(name, nameValue);// 根据名字设置card值

					}

					break;

				case XmlPullParser.END_TAG:// 结束元素事件
				//	Log.d(TAG + "insert",currentTeamMember.getTmname());
					tMembers.add(currentTeamMember);
					currentTeamMember = new TeamMember();
					Log.d(TAG, "TeamXmlPullParser.END_TAG");

					break;
				}

				eventType = parser.next();
			}

			inStream.close();
			return tMembers;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
