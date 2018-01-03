package org.loader.labelview;

import java.util.Calendar; 

import com.example.DataBase.InaccountDAO;
import com.example.DataBase.OutaccountDAO;
import com.example.accountantmoney.R;
import com.example.qingdan.Tb_inaccount;
import com.example.qingdan.Tb_outaccount;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InfoManage extends Activity {
	protected static final int DATE_DIALOG_ID = 0; 
	TextView tvtitle, textView; 
	EditText txtMoney, txtTime, txtHA, txtMark; 
	Spinner spType; 
	Button btnEdit, btnDel; 
	String[] strInfos;// 定义字符串数组
	String strid, strType;// 定义两个字符串变量，分别用来记录信息编号和管理类型
	private ImageButton fanhui;//返回
	private int mYear; 
	private int mMonth; 
	private int mDay; 

	OutaccountDAO outaccountDAO = new OutaccountDAO(InfoManage.this);// 创建OutaccountDAO对象
	InaccountDAO inaccountDAO = new InaccountDAO(InfoManage.this);// 创建InaccountDAO对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.infomanage);// 设置布局文件
		tvtitle = (TextView) findViewById(R.id.inouttitle); 
		textView = (TextView) findViewById(R.id.tvInOut); 
		txtMoney = (EditText) findViewById(R.id.txtInOutMoney); 
		txtTime = (EditText) findViewById(R.id.txtInOutTime); 
		spType = (Spinner) findViewById(R.id.spInOutType); 
		txtHA = (EditText) findViewById(R.id.txtInOut); 
		txtMark = (EditText) findViewById(R.id.txtInOutMark); 
		btnEdit = (Button) findViewById(R.id.btnInOutEdit); 
		btnDel = (Button) findViewById(R.id.btnInOutDelete); 

		Intent intent = getIntent();// 创建Intent对象
		Bundle bundle = intent.getExtras();// 获取传入的数据，并使用Bundle记录
		strInfos = bundle.getStringArray(Showinfo.FLAG);// 获取Bundle中记录的信息
		strid = strInfos[0];// 记录id
		strType = strInfos[1];// 记录类型
		
		fanhui = (ImageButton)findViewById(R.id.fanhui);
		fanhui.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(InfoManage.this, MainActivity.class);
				startActivity(intent);
			}
			
		});
		
		if (strType.equals("btnoutinfo")) 
		{
			tvtitle.setText("支出管理"); 
			textView.setText("支 付："); 
			// 根据编号查找支出信息，并存储到Tb_outaccount对象中
			Tb_outaccount tb_outaccount = outaccountDAO.find(Integer
					.parseInt(strid));
			txtMoney.setText(String.valueOf(tb_outaccount.getMoney()));// 显示金额
			txtTime.setText(tb_outaccount.getTime());// 显示时间
			spType.setPrompt(tb_outaccount.getType());// 显示类别
			txtHA.setText(tb_outaccount.getAddress());// 显示地点
			txtMark.setText(tb_outaccount.getMark());// 显示备注
		} else if (strType.equals("btnininfo"))// 如果类型是btnininfo
		{
			tvtitle.setText("收入管理"); 
			textView.setText("付款方："); 
			Tb_inaccount tb_inaccount = inaccountDAO.find(Integer
					.parseInt(strid));
			txtMoney.setText(String.valueOf(tb_inaccount.getMoney()));// 显示金额
			txtTime.setText(tb_inaccount.getTime());// 显示时间
			spType.setPrompt(tb_inaccount.getType());// 显示类别
			txtHA.setText(tb_inaccount.getHandler());// 显示付款方
			txtMark.setText(tb_inaccount.getMark());// 显示备注
		}

		txtTime.setOnClickListener(new OnClickListener() {// 为时间文本框设置单击监听事件
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);// 显示日期选择对话框
			}
		});

		btnEdit.setOnClickListener(new OnClickListener() {// 为修改按钮设置监听事件
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (strType.equals("btnoutinfo"))// 判断类型如果是btnoutinfo
				{
					Tb_outaccount tb_outaccount = new Tb_outaccount();// 创建Tb_outaccount对象
					tb_outaccount.setid(Integer.parseInt(strid));// 设置编号
					tb_outaccount.setMoney(Double.parseDouble(txtMoney
							.getText().toString()));// 设置金额
					tb_outaccount.setTime(txtTime.getText().toString());// 设置时间
					tb_outaccount.setType(spType.getSelectedItem().toString());// 设置类别
					tb_outaccount.setAddress(txtHA.getText().toString());// 设置地点
					tb_outaccount.setMark(txtMark.getText().toString());// 设置备注
					outaccountDAO.update(tb_outaccount); 
					Toast.makeText(InfoManage.this, "〖数据〗修改成功！", Toast.LENGTH_SHORT)
							.show();
					Intent intent = new Intent();
					intent.setClass(InfoManage.this, Outaccountinfo.class);
					startActivity(intent);
				} else if (strType.equals("btnininfo"))// 判断类型如果是btnininfo
				{
					Tb_inaccount tb_inaccount = new Tb_inaccount();// 创建Tb_inaccount对象
					tb_inaccount.setid(Integer.parseInt(strid));// 设置编号
					tb_inaccount.setMoney(Double.parseDouble(txtMoney.getText()
							.toString()));// 设置金额
					tb_inaccount.setTime(txtTime.getText().toString());// 设置时间
					tb_inaccount.setType(spType.getSelectedItem().toString());// 设置类别
					tb_inaccount.setHandler(txtHA.getText().toString());// 设置付款方
					tb_inaccount.setMark(txtMark.getText().toString());// 设置备注
					inaccountDAO.update(tb_inaccount); 
					Toast.makeText(InfoManage.this, "〖数据〗修改成功！", Toast.LENGTH_SHORT)
							.show();
					Intent intent = new Intent();
					intent.setClass(InfoManage.this, Inaccountinfo.class);
					startActivity(intent);
				} 
			}
		});

		btnDel.setOnClickListener(new OnClickListener() {// 为删除按钮设置监听事件
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (strType.equals("btnoutinfo")) 
				{
					outaccountDAO.detele(Integer.parseInt(strid));// 根据编号删除支出信息
					
					AlertDialog.Builder builder = new Builder(InfoManage.this);
					builder.setMessage("确定要删除吗?");
					builder.setTitle("提示");
					builder.setPositiveButton("确认",
					new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.dismiss();
					Toast.makeText(InfoManage.this, "〖数据〗删除成功！", Toast.LENGTH_SHORT)
					.show(); 
					Intent intent = new Intent();
					intent.setClass(InfoManage.this, Outaccountinfo.class);
					startActivity(intent);
					}
					});
					builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					}
					});
					builder.create().show();
					
				} else if (strType.equals("btnininfo"))// 判断类型如果是btnininfo
				{
					inaccountDAO.detele(Integer.parseInt(strid));// 根据编号删除收入信息
					
					AlertDialog.Builder builder = new Builder(InfoManage.this);
					builder.setMessage("确定要删除吗?");
					builder.setTitle("提示");
					builder.setPositiveButton("确认",
					new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.dismiss();
					Toast.makeText(InfoManage.this, "〖数据〗删除成功！", Toast.LENGTH_SHORT)
					.show(); 
					Intent intent = new Intent();
					intent.setClass(InfoManage.this, Inaccountinfo.class);
					startActivity(intent);
					}
					});
					builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					}
					});
					builder.create().show();
					
				} 
			}
		});

		final Calendar c = Calendar.getInstance();// 获取当前系统日期
		mYear = c.get(Calendar.YEAR);// 获取年份
		mMonth = c.get(Calendar.MONTH);// 获取月份
		mDay = c.get(Calendar.DAY_OF_MONTH);// 获取天数
		updateDisplay();// 显示当前系统时间
	}

	@Override
	protected Dialog onCreateDialog(int id)// 重写onCreateDialog方法
	{
		switch (id) {
		case DATE_DIALOG_ID:// 弹出日期选择对话框
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;// 为年份赋值
			mMonth = monthOfYear;// 为月份赋值
			mDay = dayOfMonth;// 为天赋值
			updateDisplay();// 显示设置的日期
		}
	};

	private void updateDisplay() {
		// 显示设置的时间
		txtTime.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1).append("-").append(mDay));
	}
}
