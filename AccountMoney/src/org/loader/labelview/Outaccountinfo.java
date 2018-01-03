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
		//�����ޱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//����ȫ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.outaccountinfo);// ���ò����ļ�
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
		lvinfo = (ListView) findViewById(R.id.lvoutaccountinfo);// ��ȡ�����ļ��е�ListView���

		ShowInfo();// �����Զ��巽����ʾ֧����Ϣ

		lvinfo.setOnItemClickListener(new OnItemClickListener()// ΪListView�������¼�
		{
			// ��дonItemClick����
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());// ��¼֧����Ϣ
				String strid = strInfo.substring(0, strInfo.indexOf("   "));// ��֧����Ϣ�н�ȡ֧����� 
				Intent intent = new Intent(Outaccountinfo.this,
						InfoManage.class);// ����Intent����
				intent.putExtra(FLAG, new String[] { strid, strType });// ���ô�������
				startActivity(intent);// ִ��Intent����
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
		strType = "btnoutinfo";// ΪstrType������ֵ
		OutaccountDAO outaccountinfo = new OutaccountDAO(Outaccountinfo.this); 
		List<Tb_outaccount> listoutinfos = outaccountinfo.getScrollData(0,
				(int) outaccountinfo.getCount());
		strInfos = new String[listoutinfos.size()]; 
		int i = 0;// ����һ����ʼ��ʶ
		for (Tb_outaccount tb_outaccount : listoutinfos) {  
			strInfos[i] = tb_outaccount.getid() + "   " + tb_outaccount.getType()
					+ " " + String.valueOf(tb_outaccount.getMoney()) + "Ԫ     "
					+ tb_outaccount.getTime();
			i++;// ��ʶ��1
		} 
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strInfos);
		lvinfo.setAdapter(arrayAdapter);// ΪListView�б���������Դ
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart(); 
		ShowInfo(); 
	}
}