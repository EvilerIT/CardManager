package com.ril.cardmanager;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.ril.cardmanager.adapter.TmListAdapter;
import com.ril.cardmanager.db.DBManager;
import com.ril.cardmanager.model.TeamMember;

public class ListTeamManager extends Activity {

	private ListView mListView;
	private TmListAdapter mAdapter;
	private DBManager dbmanager;
	private List<TeamMember> data;
	private final String TAG = "ListTeamManager";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		window.setAttributes(lp);

		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.card_list);
		mListView.setEmptyView(findViewById(R.id.empty));
		data = getData();
		dbmanager.closeDB();
		mAdapter = new TmListAdapter(this, data, R.layout.list_tmitem);
		mListView.setAdapter(mAdapter);
		setListener();

	}

	private void setListener() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(ListTeamManager.this,
						EditTeamManager.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("teaminfor", data.get(arg2));
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
	}

	private List<TeamMember> getData() {
		dbmanager = new DBManager(ListTeamManager.this);
		return dbmanager.queryTeamMember();
	}

	@Override
	protected void onResume() {
		super.onResume();
		data.clear();
		data.addAll(getData());
		mAdapter.notifyDataSetChanged();
	}

}
