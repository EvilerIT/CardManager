package com.ril.cardmanager;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ril.cardmanager.adapter.ListAdapter;
import com.ril.cardmanager.db.DBManager;
import com.ril.cardmanager.model.Card;

public class MainActivity extends Activity {

	private ListView mListView;
	// private Button mButton;
	private List<Card> data;
	private ListAdapter mAdapter;
	private DBManager dbmanager;
	private String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// full screen.
		Window window = getWindow();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		window.setAttributes(lp);

		setContentView(R.layout.activity_main);

		// LayoutInflater flater = LayoutInflater.from(this);
		// View view = flater.inflate(R.layout.example, null);

		mListView = (ListView) findViewById(R.id.card_list);
		// mListView.addFooterView(view);
		mListView.setEmptyView(findViewById(R.id.empty));
		dbmanager = new DBManager(MainActivity.this);
		data = getData();
		mAdapter = new ListAdapter(this, data, R.layout.list_item);
		mListView.setAdapter(mAdapter);
		setListener();
		// dbmanager.closeDB();
	}

	@Override
	protected void onResume() {
		super.onResume();
		data.clear();
		// data = getData();
		data.addAll(getData());
		Log.i("resume", data.toString());
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onRestart() {
		super.onResume();
		data.clear();
		// data = getData();
		data.addAll(getData());
		Log.i("resume", data.toString());
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(final MenuItem item) {
		super.onOptionsItemSelected(item);
		final View view = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.dialog_pwd, null);
		switch (item.getItemId()) {
		case R.id.action_add:

			new AlertDialog.Builder(MainActivity.this)
					.setTitle("Root User Login").setView(view)
					.setPositiveButton("确定", new OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							EditText e = (EditText) view
									.findViewById(R.id.edit_pwd);
							String pwd = e.getText().toString().trim();
							if (pwd.equals("woshizhu")) {
								Intent intent = new Intent(MainActivity.this,
										AddCard.class);
								startActivity(intent);
							} else {
								Toast.makeText(MainActivity.this, "密码不对~~~~",
										Toast.LENGTH_SHORT).show();
							}

						}
					}).create().show();
			return true;
		case R.id.action_LeadingIn:// Waiting to be implemented
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("Root User Login").setView(view)
					.setPositiveButton("确定", new OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							EditText e = (EditText) view
									.findViewById(R.id.edit_pwd);
							String pwd = e.getText().toString().trim();
							if (pwd.equals("woshizhu")) {
								Intent leaddingInintent = new Intent(
										MainActivity.this, LeaddingIn.class);
								startActivity(leaddingInintent);
							} else {
								Toast.makeText(MainActivity.this, "密码不对~~~~",
										Toast.LENGTH_SHORT).show();
							}

						}
					}).create().show();

			return true;
		case R.id.action_LeadingOut:// Waiting to be implemented
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("Root User Login").setView(view)
					.setPositiveButton("确定", new OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							EditText e = (EditText) view
									.findViewById(R.id.edit_pwd);
							String pwd = e.getText().toString().trim();
							if (pwd.equals("woshizhu")) {
								Intent leaddingOutintent = new Intent(
										MainActivity.this, LeaddingOut.class);
								startActivity(leaddingOutintent);
							} else {
								Toast.makeText(MainActivity.this, "密码不对~~~~",
										Toast.LENGTH_SHORT).show();
							}

						}
					}).create().show();

			return true;
		case R.id.action_addteammember:// 编辑组员
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("Root User Login").setView(view)
					.setPositiveButton("确定", new OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							EditText e = (EditText) view
									.findViewById(R.id.edit_pwd);
							String pwd = e.getText().toString().trim();
							if (pwd.equals("woshizhu")) {
								Intent intent = new Intent(MainActivity.this,
										TeamManager.class);
								startActivity(intent);
							} else {
								Toast.makeText(MainActivity.this, "密码不对~~~~",
										Toast.LENGTH_SHORT).show();
							}

						}
					}).create().show();

			return true;
		case R.id.action_listteammember:// 编辑组员
			Intent listintent = new Intent(MainActivity.this,
					ListTeamManager.class);
			startActivity(listintent);
			return true;
		default:
			return false;
		}
	}

	protected List<Card> getData() {
		return dbmanager.query();
	}

	protected int delete(Card card) {
		dbmanager = new DBManager(MainActivity.this);
		int value = dbmanager.delete(card);
		dbmanager.closeDB();
		return value;
	}

	protected void setListener() {

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(MainActivity.this, EditCard.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("cardinfor", data.get(arg2));
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i("KeyEvent", keyCode + "");
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键
			// return true;
		} else if (keyCode == KeyEvent.ACTION_DOWN) {
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_HOME) {
			// 由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
