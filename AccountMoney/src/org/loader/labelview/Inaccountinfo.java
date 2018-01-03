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
	String strType = "";// �����ַ�������¼��������
	private ImageButton fanhui;//����
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//�����ޱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//����ȫ��
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

		ShowInfo(R.id.btnininfo);// �����Զ��巽����ʾ������Ϣ

		lvinfo.setOnItemClickListener(new OnItemClickListener()// ΪListView�������¼�
		{
			// ��дonItemClick����
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());// ��¼������Ϣ
				String strid = strInfo.substring(0, strInfo.indexOf("   "));// ��������Ϣ�н�ȡ������
				Intent intent = new Intent(Inaccountinfo.this, InfoManage.class);// ����Intent����
				intent.putExtra(FLAG, new String[] { strid, strType });// ���ô�������
				startActivity(intent);// ִ��Intent����
			}
		});
	}

	private void ShowInfo(int intType) { 
		String[] strInfos = null;  
		strType = "btnininfo";// ΪstrType������ֵ
		array = new ArrayList<String>();   
		InaccountDAO inaccountinfo = new InaccountDAO(Inaccountinfo.this); 
		List<Tb_inaccount> listinfos = inaccountinfo.getScrollData(0,
				(int) inaccountinfo.getCount());
		strInfos = new String[listinfos.size()];// �����ַ�������ĳ���
		int m = 0;// ����һ����ʼ��ʶ
		for (Tb_inaccount tb_inaccount : listinfos) {// ����List���ͼ���
			// �����������Ϣ��ϳ�һ���ַ������洢���ַ����������Ӧλ��
			strInfos[m] = tb_inaccount.getid() + "   " + tb_inaccount.getType()
					+ "   " + String.valueOf(tb_inaccount.getMoney()) + "Ԫ     "
					+ tb_inaccount.getTime();
			m++;// ��ʶ��1 
		}  
		for (int i = 0; i < strInfos.length; i++) {
			array.add(strInfos[i]);  

		}
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, array);
		lvinfo.setAdapter(arrayAdapter);// ΪListView�б���������Դ
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();// ʵ�ֻ����еķ���
		ShowInfo(R.id.btnininfo);// ��ʾ������Ϣ
	}
}
