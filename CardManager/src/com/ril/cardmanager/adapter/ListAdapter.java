package com.ril.cardmanager.adapter;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ril.cardmanager.R;
import com.ril.cardmanager.db.DBManager;
import com.ril.cardmanager.model.Card;

public class ListAdapter extends BaseAdapter {
	private final String TAG = "ListAdapter";
	private Context mcontext;// 运行上下文
	private List<Card> data;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源

	private class ListItemView { // 自定义控件集合
		public TextView name;
		public TextView number;
		public TextView updatetime;
		public TextView owner;
		public ImageView logo;
	}

	private DBManager dbmanager;

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ListAdapter(Context context, List<Card> data, int resource) {
		this.mcontext = context;
		this.listContainer = LayoutInflater.from(mcontext); // 创建视图容器并设置上下文
		this.itemViewResource = resource;
		this.data = data;
	}

	public int getCount() {

		return data.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		// Log.d("method", "getView");

		// 自定义视图
		ListItemView listItemView = null;
		convertView = listContainer.inflate(this.itemViewResource, null);
		listItemView = new ListItemView();
		// 获取控件对象
		listItemView.name = (TextView) convertView.findViewById(R.id.textView1);
		listItemView.number = (TextView) convertView
				.findViewById(R.id.textView2);
		listItemView.owner = (TextView) convertView
				.findViewById(R.id.textView3);
		listItemView.updatetime = (TextView) convertView
				.findViewById(R.id.textView4);
		listItemView.logo = (ImageView) convertView
				.findViewById(R.id.operator_logo);
		// if(data.size() > 0)
		Card card = data.get(position);

		listItemView.name.setText(card.getCardName());
		listItemView.number.setText(card.getCardNumber());

		String tempowner = card.getOwner();
		String team = card.getTeam();

		Log.d(TAG, "tempowner	" + tempowner);
		dbmanager = new DBManager(mcontext);
		if (team.equalsIgnoreCase("1")) {
			if (!tempowner.equalsIgnoreCase("home")) {// 防止组成员已经被删除情况

				if (dbmanager.queryCardOwner(tempowner) < 0) {// 并且不是组内成员
					// 更新为home
					card.setOwner("home");
					Log.d(TAG, "set 	" + tempowner);
					dbmanager.update(card);
				}
			}
		}
		dbmanager.closeDB();
		listItemView.owner.setText(card.getOwner());
		listItemView.updatetime.setText(card.getUpdateTime());
		Bitmap bitmap;
		if (card.getOperatorTag() == 1) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.cmcc);
			listItemView.logo.setImageBitmap(bitmap);
		} else if (card.getOperatorTag() == 2) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.cu);
			listItemView.logo.setImageBitmap(bitmap);
		} else if (card.getOperatorTag() == 3) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.ctc);
			listItemView.logo.setImageBitmap(bitmap);
		}
		if (card.getOwner().equals("home")) {
			convertView.setBackgroundColor(convertView.getResources().getColor(
					R.color.light_yellow));
		}
		return convertView;
	}
}