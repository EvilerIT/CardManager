package com.ril.cardmanager;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ril.cardmanager.db.DBManager;
import com.ril.cardmanager.model.Card;

public class AddCard extends Activity {

	private EditText eName;
	private EditText eNumber;
	private RadioGroup rOperator;
	private Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// full screen.
		Window window = getWindow();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		window.setAttributes(lp);
		setContentView(R.layout.activity_add);
		initView();
	}

	private void initView() {
		eName = (EditText) findViewById(R.id.card_name_i);
		eNumber = (EditText) findViewById(R.id.card_number_i);
		rOperator = (RadioGroup) findViewById(R.id.radioOper);
		mButton = (Button) findViewById(R.id.make_sure);
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (addRecord() > 0) {
					Toast.makeText(getApplicationContext(), "add success!!",
							Toast.LENGTH_LONG).show();
					eName.setText("");
					eNumber.setText("");
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
		String name = eName.getText().toString().trim();
		String number = eNumber.getText().toString().trim();
		if (name.equals("") || number.equals("")) {
			return -1;
		}
		String owner = "home";
		int operFlag = 0;
		if (rOperator.getCheckedRadioButtonId() == R.id.radio_cmcc) {
			operFlag = 1;
		} else if (rOperator.getCheckedRadioButtonId() == R.id.radio_cu) {
			operFlag = 2;
		}else if (rOperator.getCheckedRadioButtonId() == R.id.radio_ctc) {
			operFlag = 3;
		}
		
		SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		String updateTime = df.format(new Date());// new Date()涓鸿幏鍙栧綋鍓嶇郴缁熸椂闂�

		Card c = new Card();
		c.setCardName(name);
		c.setCardNumber(number);
		c.setOwner(owner);
		c.setTeam("1");//默认组内
		c.setOperatorTag(operFlag);
		c.setUpdateTime(updateTime);
		DBManager manager = new DBManager(this);
		return manager.insert(c);
	}
}
