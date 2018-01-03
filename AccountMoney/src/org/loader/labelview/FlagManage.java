package org.loader.labelview;
 
import com.example.DataBase.FlagDAO;
import com.example.accountantmoney.R;
import com.example.qingdan.Tb_flag;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class FlagManage extends Activity {
	EditText txtFlag; 
	Button btnEdit, btnDel; 
	String strid; 
	private ImageButton fanhui;//返回
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.flagmanage);// 设置布局文件
		txtFlag = (EditText) findViewById(R.id.txtFlagManage); 
		btnEdit = (Button) findViewById(R.id.btnFlagManageEdit);// 获取修改按钮
		btnDel = (Button) findViewById(R.id.btnFlagManageDelete);// 获取删除按钮

		Intent intent = getIntent(); 
		Bundle bundle = intent.getExtras(); 
		strid = bundle.getString(Showinfo.FLAG); 
		final FlagDAO flagDAO = new FlagDAO(FlagManage.this); 
		txtFlag.setText(flagDAO.find(Integer.parseInt(strid)).getFlag()); 
		fanhui = (ImageButton)findViewById(R.id.fanhui);
		fanhui.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FlagManage.this, MainActivity.class);
				startActivity(intent);
			}
			
		});
		btnEdit.setOnClickListener(new OnClickListener() {// 为修改按钮设置监听事件
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Tb_flag tb_flag = new Tb_flag();// 创建Tb_flag对象
				tb_flag.setid(Integer.parseInt(strid));// 设置便签id
				tb_flag.setFlag(txtFlag.getText().toString());// 设置便签值
				flagDAO.update(tb_flag);// 修改便签信息
				// 弹出信息提示
				Toast.makeText(FlagManage.this, "〖便签数据〗修改成功！",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(FlagManage.this, Showinfo.class);
				startActivity(intent);
			}
		});   

		btnDel.setOnClickListener(new OnClickListener() {// 为删除按钮设置监听事件
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new Builder(FlagManage.this);
				builder.setMessage("确定要跳转吗?");
				builder.setTitle("提示");
				builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
					flagDAO.detele(Integer.parseInt(strid)); 
					Toast.makeText(FlagManage.this, "〖便签数据〗删除成功！",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(FlagManage.this, Showinfo.class);
					startActivity(intent);
					arg0.dismiss();
				 
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
		});
	}

}
