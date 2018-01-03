package org.loader.labelview;

import java.util.List; 

import com.example.DataBase.FlagDAO;
import com.example.DataBase.InaccountDAO;
import com.example.DataBase.OutaccountDAO;
import com.example.accountantmoney.R;
import com.example.qingdan.Tb_flag;
import com.example.qingdan.Tb_inaccount;
import com.example.qingdan.Tb_outaccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Showinfo extends Activity {
	public static final String FLAG = "id"; 
	Button btnoutinfo, btnininfo, btnflaginfo; 
	ListView lvinfo; 
	String strType = ""; 
	private ImageButton fanhui;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.showinfo);
		// 设置布局文件
		fanhui = (ImageButton)findViewById(R.id.fanhui);
		fanhui.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Showinfo.this, MainActivity.class);
				startActivity(intent);
			}
			
		}); 
		lvinfo = (ListView) findViewById(R.id.lvinfo); 
		btnoutinfo = (Button) findViewById(R.id.btnoutinfo); 
		btnininfo = (Button) findViewById(R.id.btnininfo); 
		btnflaginfo = (Button) findViewById(R.id.btnflaginfo); 

		ShowInfo(R.id.btnoutinfo); 

		btnoutinfo.setOnClickListener(new OnClickListener() { 
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ShowInfo(R.id.btnoutinfo);// 显示支出信息
					}
				});

		btnininfo.setOnClickListener(new OnClickListener() { 
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ShowInfo(R.id.btnininfo);// 显示收入信息
					}
				});
		btnflaginfo.setOnClickListener(new OnClickListener() { 
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ShowInfo(R.id.btnflaginfo);// 显示便签信息
					}
				});

		lvinfo.setOnItemClickListener(new OnItemClickListener()// 为ListView添加项单击事件
		{
			// 覆写onItemClick方法
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());// 记录单击的项信息
				String strid = strInfo.substring(0, strInfo.indexOf("   "));// 从项信息中截取编号
				Intent intent = null;// 创建Intent对象
				if (strType == "btnoutinfo" | strType == "btnininfo") {// 判断如果是支出或者收入信息
					intent = new Intent(Showinfo.this, InfoManage.class); 
					intent.putExtra(FLAG, new String[] { strid, strType });// 设置要传递的数据
				} else if (strType == "btnflaginfo") {// 判断如果是便签信息
					intent = new Intent(Showinfo.this, FlagManage.class);// 使用FlagManage窗口初始化Intent对象
					intent.putExtra(FLAG, strid);// 设置要传递的数据
				}
				startActivity(intent);// 执行Intent，打开相应的Activity
			}
		});
	}

	private void ShowInfo(int intType) {// 用来根据传入的管理类型，显示相应的信息
		String[] strInfos = null;// 定义字符串数组，用来存储收入信息
		ArrayAdapter<String> arrayAdapter = null;// 创建ArrayAdapter对象
		switch (intType) {// 以intType为条件进行判断
		case R.id.btnoutinfo:// 如果是btnoutinfo按钮
			strType = "btnoutinfo";// 为strType变量赋值
			OutaccountDAO outaccountinfo = new OutaccountDAO(Showinfo.this); 
			List<Tb_outaccount> listoutinfos = outaccountinfo.getScrollData(0,
					(int) outaccountinfo.getCount());
			strInfos = new String[listoutinfos.size()];// 设置字符串数组的长度
			int i = 0;// 定义一个开始标识
			for (Tb_outaccount tb_outaccount : listoutinfos) { 
				strInfos[i] = tb_outaccount.getid() + "   "
						+ tb_outaccount.getType() + "    "
						+ String.valueOf(tb_outaccount.getMoney()) + "元     "
						+ tb_outaccount.getTime();
				i++;// 标识加1
			}
			break;
		case R.id.btnininfo: 
			strType = "btnininfo"; 
			InaccountDAO inaccountinfo = new InaccountDAO(Showinfo.this); 
			// 获取所有收入信息，并存储到List泛型集合中
			List<Tb_inaccount> listinfos = inaccountinfo.getScrollData(0,
					(int) inaccountinfo.getCount());
			strInfos = new String[listinfos.size()];// 设置字符串数组的长度
			int m = 0;// 定义一个开始标识
			for (Tb_inaccount tb_inaccount : listinfos) {// 遍历List泛型集合
				// 将收入相关信息组合成一个字符串，存储到字符串数组的相应位置
				strInfos[m] = tb_inaccount.getid() + "   "
						+ tb_inaccount.getType() + " "
						+ String.valueOf(tb_inaccount.getMoney()) + "元     "
						+ tb_inaccount.getTime();
				m++;// 标识加1
			}
			break;
		case R.id.btnflaginfo: 
			strType = "btnflaginfo"; 
			FlagDAO flaginfo = new FlagDAO(Showinfo.this); 
			// 获取所有便签信息，并存储到List泛型集合中
			List<Tb_flag> listFlags = flaginfo.getScrollData(0,
					(int) flaginfo.getCount());
			strInfos = new String[listFlags.size()];// 设置字符串数组的长度
			int n = 0;// 定义一个开始标识
			for (Tb_flag tb_flag : listFlags) { 
				strInfos[n] = tb_flag.getid() + "   " + tb_flag.getFlag();
				if (strInfos[n].length() > 15)// 判断便签信息的长度是否大于15
					strInfos[n] = strInfos[n].substring(0, 15) + "……"; 
				n++;// 标识加1
			}
			break;
		} 
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strInfos);
		lvinfo.setAdapter(arrayAdapter);// 为ListView列表设置数据源
	} 
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();// 实现基类中的方法
		ShowInfo(R.id.btnoutinfo);// 显示支出信息
	}
}
