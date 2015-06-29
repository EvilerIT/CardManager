package com.ril.cardmanager.Leading;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;

import com.ril.cardmanager.model.Card;

public class CardXmlLeaddingIn {
	final static String TAG = "XmlLeaddingInCard";

	// 读取XML
	public static List<Card> readCardXML(InputStream inStream) {

		XmlPullParser parser = Xml.newPullParser();
		Log.d(TAG, "readXML");

		try {
			parser.setInput(inStream, "UTF-8");
			int eventType = parser.getEventType();

			Card currentCard = null;
			List<Card> cards = new ArrayList<Card>();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
					currentCard = new Card();
					break;

				case XmlPullParser.START_TAG:// 开始元素事件

					String name = parser.getName();

					if (currentCard.dbConsistsName(name)) {// 检查数据库中是否有该属性值

						String nameValue = parser.nextText();

						Log.d(TAG, "getName&NameValue :" + name + "	"
								+ nameValue);

						currentCard.setNameValue(name, nameValue);// 根据名字设置card值

					}

					break;

				case XmlPullParser.END_TAG:// 结束元素事件
					Log.d(TAG + "insert",
							currentCard.getCardName() + "	"
									+ currentCard.getCardId() + " "
									+ currentCard.getCardNumber());
					cards.add(currentCard);
					currentCard = new Card();
					Log.d(TAG, "XmlPullParser.END_TAG");

					break;
				}

				eventType = parser.next();
			}

			inStream.close();
			return cards;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
