package org.loader.labelview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.DataBase.InaccountDAO;
import com.example.accountantmoney.R;
import com.example.qingdan.Tb_inaccount;

public class Inaccountinfo extends Activity {
	private ArrayList<String>array;
	ArrayAdapter<String> arrayAdapter = null; 
	public static final String FLAG = "id"; 
	ListView lvinfo; 
	String strType = "";// 创建字符串，记录管理类型
	private ImageButton fanhui;//返回
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.inaccountinfo); 
		fanhui = (ImageButton)findViewById(R.id.fanhui);
		fanhui.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Inaccountinfo.this, MainActivity.class);
				startActivity(intent);
			}
			
		});
		lvinfo = (ListView) findViewById(R.id.lvinaccountinfo); 

		ShowInfo(R.id.btnininfo);// 调用自定义方法显示收入信息

		lvinfo.setOnItemClickListener(new OnItemClickListener()// 为ListView添加项单击事件
		{
			// 覆写onItemClick方法
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());// 记录收入信息
				String strid = strInfo.substring(0, strInfo.indexOf("   "));// 从收入信息中截取收入编号
				Intent intent = new Intent(Inaccountinfo.this, InfoManage.class);// 创建Intent对象
				intent.putExtra(FLAG, new String[] { strid, strType });// 设置传递数据
				startActivity(intent);// 执行Intent操作
			}
		});
	}

	private void ShowInfo(int intType) { 
		String[] strInfos = null;  
		strType = "btnininfo";// 为strType变量赋值
		array = new ArrayList<String>();   
		InaccountDAO inaccountinfo = new InaccountDAO(Inaccountinfo.this); 
		List<Tb_inaccount> listinfos = inaccountinfo.getScrollData(0,
				(int) inaccountinfo.getCount());
		strInfos = new String[listinfos.size()];// 设置字符串数组的长度
		int m = 0;// 定义一个开始标识
		for (Tb_inaccount tb_inaccount : listinfos) {// 遍历List泛型集合
			// 将收入相关信息组合成一个字符串，存储到字符串数组的相应位置
			strInfos[m] = tb_inaccount.getid() + "   " + tb_inaccount.getType()
					+ "   " + String.valueOf(tb_inaccount.getMoney()) + "元     "
					+ tb_inaccount.getTime();
			m++;// 标识加1 
		}  
		for (int i = 0; i < strInfos.length; i++) {
			array.add(strInfos[i]);  

		}
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, array);
		lvinfo.setAdapter(arrayAdapter);// 为ListView列表设置数据源
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();// 实现基类中的方法
		ShowInfo(R.id.btnininfo);// 显示收入信息
	}
}
