package com.ril.cardmanager.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ril.cardmanager.R;
import com.ril.cardmanager.model.TeamMember;

public class TmListAdapter extends BaseAdapter {

	private Context mcontext;// 运行上下文
	private List<TeamMember> items;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源

	private class ListItemView { // 自定义控件集合
		public TextView name;
		public TextView phonenumber;
		// public TextView updatetime;
		public TextView singleId;
		public ImageView pic;
	}

	public TmListAdapter(Context context, List<TeamMember> data, int resource) {
		// TODO Auto-generated constructor stub
		this.mcontext = context;
		this.listContainer = LayoutInflater.from(mcontext); // 创建视图容器并设置上下文
		this.itemViewResource = resource;
		this.items = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public TeamMember getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 自定义视图
		ListItemView listItemView = null;
		convertView = listContainer.inflate(this.itemViewResource, null);
		listItemView = new ListItemView();

		// 获取控件对象
		listItemView.name = (TextView) convertView.findViewById(R.id.tmname1);
		listItemView.phonenumber = (TextView) convertView
				.findViewById(R.id.tmphonenumber1);
		listItemView.singleId = (TextView) convertView
				.findViewById(R.id.tmsingleId1);
		// listItemView.updatetime = (TextView) convertView
		// .findViewById(R.id.tmupdatetime1);
		listItemView.pic = (ImageView) convertView.findViewById(R.id.tmpic);

		TeamMember tMember = items.get(position);

		listItemView.name.setText(tMember.getTmname());
		listItemView.phonenumber.setText(tMember.getPhoneNumber());
		listItemView.singleId.setText(tMember.getsingleId());
		// listItemView.updatetime.setText(tMember.getUpdateTime());

		Bitmap bitmap = null;
		if (tMember.getTmname().equalsIgnoreCase("柳涛")) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.lt);
		} else if (tMember.getTmname().equalsIgnoreCase("王辉")) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.wh);
		} else if (tMember.getTmname().equalsIgnoreCase("李占占")) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.lzz);
		} else if (tMember.getTmname().equalsIgnoreCase("孟庆标")) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.mqb);
		} else if (tMember.getTmname().equalsIgnoreCase("荣芳芳")) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.rff);
		} else if (tMember.getTmname().equalsIgnoreCase("季振生")) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.jzs);
		} else if (tMember.getTmname().equalsIgnoreCase("路伟垚")) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.lwy);
		} else if (tMember.getTmname().equalsIgnoreCase("韩亮")) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.hl);
		} else if (tMember.getTmname().equalsIgnoreCase("陈敏海")) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.cmh);
		} else if (tMember.getTmname().equalsIgnoreCase("郭敬一")) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.gjy);
		} else if (tMember.getTmname().equalsIgnoreCase("王冲")) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.wc);
		} else if (tMember.getTmname().equalsIgnoreCase("李龙")) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.ll);
		} else if (tMember.getTmname().equalsIgnoreCase("刘传奇")) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.lcq);
		}

		listItemView.pic.setImageBitmap(bitmap);
		return convertView;
	}

}
