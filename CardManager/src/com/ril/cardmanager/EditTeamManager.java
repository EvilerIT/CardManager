package com.ril.cardmanager;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ril.cardmanager.db.DBManager;
import com.ril.cardmanager.model.TeamMember;

public class EditTeamManager extends Activity {

	private String TAG = "EditTeamManager";
	private TextView mName;
	private EditText mNumber;
	private TextView mSingleId;
	private Button mUpdate;
	private Button mDelete;
	private DBManager dbmanager;
	private TeamMember tMember = new TeamMember();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// full screen.
		Window window = getWindow();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		window.setAttributes(lp);
		setContentView(R.layout.activity_teamedit);

		mName = (TextView) findViewById(R.id.edit_teammember_name);
		mNumber = (EditText) findViewById(R.id.edit_teammember_number);
		mSingleId = (TextView) findViewById(R.id.teammember_singleId);
		mUpdate = (Button) findViewById(R.id.update_teammember);
		mDelete = (Button) findViewById(R.id.delete_teammember);
		tMember = (TeamMember) getIntent().getSerializableExtra("teaminfor");

		mName.setText(tMember.getTmname());
		mNumber.setText(tMember.getPhoneNumber());
		mSingleId.setText(tMember.getsingleId());

		mUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						EditTeamManager.this);
				builder.setTitle("确定更新此人手机信息？");
				builder.setPositiveButton("嗯~", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						final View view = LayoutInflater.from(
								EditTeamManager.this).inflate(
								R.layout.dialog_pwd, null);
						AlertDialog d = new AlertDialog.Builder(
								EditTeamManager.this)
								.setTitle("Root User Login").setView(view)
								.setPositiveButton("确定", new OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										EditText e = (EditText) view
												.findViewById(R.id.edit_pwd);
										String pwd = e.getText().toString()
												.trim();
										if (pwd.equals("woshizhu")) {
											if (updateTeamMember() > 0) {

												Toast.makeText(
														getApplicationContext(),
														"更新完成",
														Toast.LENGTH_SHORT)
														.show();
												finish();
											} else
												Toast.makeText(
														getApplicationContext(),
														"失败了。。。",
														Toast.LENGTH_SHORT)
														.show();
										} else {
											Toast.makeText(
													EditTeamManager.this,
													"密码不对~~~~不能删除哦~~~~~~",
													Toast.LENGTH_SHORT).show();
										}
									}
								}).create();
						d.show();

					}

				});

				builder.setNegativeButton("算了~", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(), "。。。。。。。",
								Toast.LENGTH_SHORT).show();
					}
				});
				builder.create().show();

			}
		});

		mDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						EditTeamManager.this);
				builder.setTitle("删除此人？");
				builder.setPositiveButton("恩~", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						final View view = LayoutInflater.from(
								EditTeamManager.this).inflate(
								R.layout.dialog_pwd, null);
						new AlertDialog.Builder(EditTeamManager.this)
								.setTitle("Root User Login").setView(view)
								.setPositiveButton("确定", new OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										EditText e = (EditText) view
												.findViewById(R.id.edit_pwd);
										String pwd = e.getText().toString()
												.trim();
										if (pwd.equals("woshizhu")) {
											delete(tMember);
											finish();
											Toast.makeText(
													getApplicationContext(),
													"已删除", Toast.LENGTH_SHORT)
													.show();
										} else {
											Toast.makeText(
													EditTeamManager.this,
													"密码不对~~~~不能删除哦~~~~~~",
													Toast.LENGTH_SHORT).show();
										}
									}
								}).create().show();

					}

				});

				builder.setNegativeButton("算了~", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(), "没有删除。。。。",
								Toast.LENGTH_SHORT).show();
					}
				});
				builder.create().show();
			}
		});

	}

	protected int delete(TeamMember tMember) {
		dbmanager = new DBManager(EditTeamManager.this);
		int value = dbmanager.delete(tMember);
		dbmanager.closeDB();
		return value;
	}

	private int updateTeamMember() {
		dbmanager = new DBManager(EditTeamManager.this);
		SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		String updateTime = df.format(new Date());// new Date()为获取当前系统时间
		tMember.setUpdateTime(updateTime);

		String nowphonenumber = mNumber.getText().toString().trim();
		if (!nowphonenumber.equalsIgnoreCase(tMember.getPhoneNumber())) {
			tMember.setPhoneNumber(nowphonenumber);
			int value = dbmanager.update(tMember);
			dbmanager.closeDB();
			return value;
		} else
			return -1;
	}

}
