package com.ril.cardmanager;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ril.cardmanager.db.DBManager;
import com.ril.cardmanager.model.Card;
import com.ril.cardmanager.model.TeamMember;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TeamManager extends Activity {
	final private String TAG= "TeamManager";
	private EditText singleId;
	private EditText tname;
	private EditText pnumber;
	private Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		window.setAttributes(lp);
		setContentView(R.layout.activity_teammanager);
		initView();
	}

	private void initView() {
		singleId = (EditText) findViewById(R.id.singleId_context);
		tname = (EditText) findViewById(R.id.membername_context);
		pnumber = (EditText) findViewById(R.id.phonenumber_context);		
		mButton = (Button)findViewById(R.id.make_sure_team);
		
		mButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if (addRecord() > 0) {
					Toast.makeText(getApplicationContext(), "add teammember success!!",
							Toast.LENGTH_LONG).show();
					singleId.setText("");
					tname.setText("");
					pnumber.setText("");
					// finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"add failed!!Format Error....", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
	}
	private long addRecord() {
		int Id=0;
		String single = singleId.getText().toString().trim();
		String name = tname.getText().toString().trim();
		String number = pnumber.getText().toString().trim();
		if (name.equals("") || number.equals("")) {
			Log.d(TAG, name+""+number);
			return -1;
		}
		
		SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		String updateTime = df.format(new Date());// new Date()涓鸿幏鍙栧綋鍓嶇郴缁熸椂闂�

		TeamMember tMember = new TeamMember(Id,single,name,number,updateTime);

		DBManager manager = new DBManager(this);
		return manager.insertTeamMember(tMember);
	}

}
