package org.loader.labelview;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.DataBase.OutaccountDAO;
import com.example.accountantmoney.R;
import com.example.qingdan.Tb_outaccount;

public class Outaccountinfo extends Activity {
	public static final String FLAG = "id"; 
	ListView lvinfo; 
	String strType = ""; 
	AutoCompleteTextView autoCompleteTextView = null; 
	private ImageButton fanhui;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.outaccountinfo);// 设置布局文件
		fanhui = (ImageButton)findViewById(R.id.fanhui);
		fanhui.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Outaccountinfo.this, MainActivity.class);
				startActivity(intent);
			} 
		});
		lvinfo = (ListView) findViewById(R.id.lvoutaccountinfo);// 获取布局文件中的ListView组件

		ShowInfo();// 调用自定义方法显示支出信息

		lvinfo.setOnItemClickListener(new OnItemClickListener()// 为ListView添加项单击事件
		{
			// 覆写onItemClick方法
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());// 记录支出信息
				String strid = strInfo.substring(0, strInfo.indexOf("   "));// 从支出信息中截取支出编号 
				Intent intent = new Intent(Outaccountinfo.this,
						InfoManage.class);// 创建Intent对象
				intent.putExtra(FLAG, new String[] { strid, strType });// 设置传递数据
				startActivity(intent);// 执行Intent操作
			}
		});
		lvinfo.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
//				 if(arg2 == 1){
//					 Toast.makeText(Outaccountinfo.this, arg2, Toast.LENGTH_SHORT).show();
//				 }
				return false;
			}
			
		});
	} 
	private void ShowInfo() { 
		String[] strInfos = null; 
		ArrayAdapter<String> arrayAdapter = null; 
		strType = "btnoutinfo";// 为strType变量赋值
		OutaccountDAO outaccountinfo = new OutaccountDAO(Outaccountinfo.this); 
		List<Tb_outaccount> listoutinfos = outaccountinfo.getScrollData(0,
				(int) outaccountinfo.getCount());
		strInfos = new String[listoutinfos.size()]; 
		int i = 0;// 定义一个开始标识
		for (Tb_outaccount tb_outaccount : listoutinfos) {  
			strInfos[i] = tb_outaccount.getid() + "   " + tb_outaccount.getType()
					+ " " + String.valueOf(tb_outaccount.getMoney()) + "元     "
					+ tb_outaccount.getTime();
			i++;// 标识加1
		} 
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strInfos);
		lvinfo.setAdapter(arrayAdapter);// 为ListView列表设置数据源
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart(); 
		ShowInfo(); 
	}
}