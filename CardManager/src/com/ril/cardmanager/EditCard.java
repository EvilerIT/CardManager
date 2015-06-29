package com.ril.cardmanager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ril.cardmanager.adapter.ListAdapter;
import com.ril.cardmanager.adapter.TmListAdapter;
import com.ril.cardmanager.db.DBManager;
import com.ril.cardmanager.model.Card;

public class EditCard extends Activity {

	private Spinner mSpinner;
	private TextView mName;
	private TextView mNumber;
	private TextView mOperator;
	private Button mChange;
	private Button mReturn;
	private Button mDelete;
	private EditText mEdit;
	private RadioGroup mOwner;
	private RadioButton mInner;
	private RadioButton mOuter;
	// private TmListAdapter tmadapter;//Team member listadapter
	private ArrayAdapter<String> tmadapter;// Team member listadapter
	private DBManager dbmanager;
	/**
	 * 0-->组内 1-->组外
	 */
	private int ownerGroup = 0;
	private Card card = new Card();
	private int index = 0;
	final private String TAG = "EditCard";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// full screen.
		Window window = getWindow();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		window.setAttributes(lp);
		setContentView(R.layout.activity_edit);
		dbmanager = new DBManager(this);
		mSpinner = (Spinner) findViewById(R.id.card_owner);

		List<String> items = dbmanager.queryTeamMemberNames();
		dbmanager.closeDB();

		tmadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, items);
		tmadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// tmadapter = new TmListAdapter(this, items,
		// R.layout.list_team_member);
		mSpinner.setAdapter(tmadapter);
		mSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				index = arg2;
				card.setOwner(tmadapter.getItem(arg2));
				Log.d(TAG,
						"tmadapter.getItem(arg2) = " + tmadapter.getItem(arg2));
				arg0.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				arg0.setVisibility(View.VISIBLE);
			}
		});

		mName = (TextView) findViewById(R.id.edit_card_name_i);
		mNumber = (TextView) findViewById(R.id.edit_card_number_i);
		mOperator = (TextView) findViewById(R.id.card_operator_i);
		mChange = (Button) findViewById(R.id.make_sure);
		mReturn = (Button) findViewById(R.id.return_card);
		mDelete = (Button) findViewById(R.id.delete_card);
		mEdit = (EditText) findViewById(R.id.owner_other);
		mOwner = (RadioGroup) findViewById(R.id.owner_group);
		mInner = (RadioButton) findViewById(R.id.radio_inner);
		mOuter = (RadioButton) findViewById(R.id.radio_outter);
		card = (Card) getIntent().getSerializableExtra("cardinfor");
		mName.setText(card.getCardName());
		mNumber.setText(card.getCardNumber());
		if (card.getOperatorTag() == 1) {
			mOperator.setText("CMCC");
			mOperator.setBackgroundResource(R.color.green_normal);
		} else if (card.getOperatorTag() == 2) {
			mOperator.setText("CU");
			mOperator.setBackgroundResource(R.color.yellow_normal);
		} else if (card.getOperatorTag() == 3) {
			mOperator.setText("CTC");
			mOperator.setBackgroundResource(R.color.blue_normal);
		}
		Log.i("edit", card.getOwner());

		String team = card.getTeam();
		if (team.equalsIgnoreCase("2")) {// 组外
			index = 0;
			ownerGroup = 1;
			mEdit.setText(card.getOwner());
			mSpinner.setVisibility(View.GONE);
			mEdit.setVisibility(View.VISIBLE);
			mOuter.setChecked(true);

		} else {// 组内
			for (int i = 0; i < items.size(); i++)
				if (!card.getOwner().equalsIgnoreCase(items.get(i)))
					index++;
				else
					break;
			
			ownerGroup = 0;
			mSpinner.setVisibility(View.VISIBLE);
			mEdit.setVisibility(View.GONE);
		}
		mSpinner.setSelection(index);
		mChange.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (updateCard() > 0) {
					Toast.makeText(getApplicationContext(), "change success!!",
							Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "change failed!!",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		mReturn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (returnCard() > 0) {
					Toast.makeText(getApplicationContext(), "return success!!",
							Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "return failed!!",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		mDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						EditCard.this);
				builder.setTitle("销卡虽易，申卡不易，且用且珍惜");
				builder.setPositiveButton("不珍惜", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						final View view = LayoutInflater.from(EditCard.this)
								.inflate(R.layout.dialog_pwd, null);
						AlertDialog d = new AlertDialog.Builder(EditCard.this)
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
											delete(card);
											finish();
											Toast.makeText(
													getApplicationContext(),
													"唉~~删掉了~~~",
													Toast.LENGTH_SHORT).show();
										} else {
											Toast.makeText(EditCard.this,
													"密码不对~~~~不能删除哦~~~~~~",
													Toast.LENGTH_SHORT).show();
										}

									}
								}).create();
						d.show();

					}

				});

				builder.setNegativeButton("且珍惜", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "谢大侠不杀之恩!!",
								Toast.LENGTH_SHORT).show();
					}

				});
				builder.create().show();

				// if(returnCard() > 0) {
				// Toast.makeText(getApplicationContext(), "return success!!",
				// Toast.LENGTH_SHORT).show();
				// finish();
				// }else{
				//
				// }

			}
		});

		mOwner.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				if (checkedId == mInner.getId()) {
					mEdit.setText(card.getOwner());
					mSpinner.setVisibility(View.VISIBLE);
					mEdit.setVisibility(View.GONE);
					mInner.setChecked(true);
					ownerGroup = 1;
				} else if (checkedId == mOuter.getId()) {
					mEdit.setText(card.getOwner());
					mSpinner.setVisibility(View.GONE);
					mEdit.setVisibility(View.VISIBLE);
					mOuter.setChecked(true);
					ownerGroup = 2;
				}
			}
		});
	}

	protected int delete(Card card) {
		dbmanager = new DBManager(this);
		int value = dbmanager.delete(card);
		dbmanager.closeDB();
		return value;
	}

	private int returnCard() {

		SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		String updateTime = df.format(new Date());// new Date()为获取当前系统时间
		card.setUpdateTime(updateTime);
		card.setOwner("home");
		card.setTeam("1");
		dbmanager = new DBManager(this);
		int value = dbmanager.update(card);
		dbmanager.closeDB();
		return value;
	}

	private int updateCard() {
		dbmanager = new DBManager(this);
		SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		String updateTime = df.format(new Date());// new Date()为获取当前系统时间
		card.setUpdateTime(updateTime);
		if (ownerGroup == 1) {// 组内
			//
			card.setTeam("1");
		} else if (ownerGroup == 2) {// 组外
			String owner = mEdit.getText().toString().trim();
			card.setOwner(owner);
			card.setTeam("2");
		}
		int value = dbmanager.update(card);
		dbmanager.closeDB();
		return value;
	}

}
