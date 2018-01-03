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
		//�����ޱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//����ȫ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.showinfo);
		// ���ò����ļ�
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
						ShowInfo(R.id.btnoutinfo);// ��ʾ֧����Ϣ
					}
				});

		btnininfo.setOnClickListener(new OnClickListener() { 
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ShowInfo(R.id.btnininfo);// ��ʾ������Ϣ
					}
				});
		btnflaginfo.setOnClickListener(new OnClickListener() { 
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ShowInfo(R.id.btnflaginfo);// ��ʾ��ǩ��Ϣ
					}
				});

		lvinfo.setOnItemClickListener(new OnItemClickListener()// ΪListView�������¼�
		{
			// ��дonItemClick����
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());// ��¼����������Ϣ
				String strid = strInfo.substring(0, strInfo.indexOf("   "));// ������Ϣ�н�ȡ���
				Intent intent = null;// ����Intent����
				if (strType == "btnoutinfo" | strType == "btnininfo") {// �ж������֧������������Ϣ
					intent = new Intent(Showinfo.this, InfoManage.class); 
					intent.putExtra(FLAG, new String[] { strid, strType });// ����Ҫ���ݵ�����
				} else if (strType == "btnflaginfo") {// �ж�����Ǳ�ǩ��Ϣ
					intent = new Intent(Showinfo.this, FlagManage.class);// ʹ��FlagManage���ڳ�ʼ��Intent����
					intent.putExtra(FLAG, strid);// ����Ҫ���ݵ�����
				}
				startActivity(intent);// ִ��Intent������Ӧ��Activity
			}
		});
	}

	private void ShowInfo(int intType) {// �������ݴ���Ĺ������ͣ���ʾ��Ӧ����Ϣ
		String[] strInfos = null;// �����ַ������飬�����洢������Ϣ
		ArrayAdapter<String> arrayAdapter = null;// ����ArrayAdapter����
		switch (intType) {// ��intTypeΪ���������ж�
		case R.id.btnoutinfo:// �����btnoutinfo��ť
			strType = "btnoutinfo";// ΪstrType������ֵ
			OutaccountDAO outaccountinfo = new OutaccountDAO(Showinfo.this); 
			List<Tb_outaccount> listoutinfos = outaccountinfo.getScrollData(0,
					(int) outaccountinfo.getCount());
			strInfos = new String[listoutinfos.size()];// �����ַ�������ĳ���
			int i = 0;// ����һ����ʼ��ʶ
			for (Tb_outaccount tb_outaccount : listoutinfos) { 
				strInfos[i] = tb_outaccount.getid() + "   "
						+ tb_outaccount.getType() + "    "
						+ String.valueOf(tb_outaccount.getMoney()) + "Ԫ     "
						+ tb_outaccount.getTime();
				i++;// ��ʶ��1
			}
			break;
		case R.id.btnininfo: 
			strType = "btnininfo"; 
			InaccountDAO inaccountinfo = new InaccountDAO(Showinfo.this); 
			// ��ȡ����������Ϣ�����洢��List���ͼ�����
			List<Tb_inaccount> listinfos = inaccountinfo.getScrollData(0,
					(int) inaccountinfo.getCount());
			strInfos = new String[listinfos.size()];// �����ַ�������ĳ���
			int m = 0;// ����һ����ʼ��ʶ
			for (Tb_inaccount tb_inaccount : listinfos) {// ����List���ͼ���
				// �����������Ϣ��ϳ�һ���ַ������洢���ַ����������Ӧλ��
				strInfos[m] = tb_inaccount.getid() + "   "
						+ tb_inaccount.getType() + " "
						+ String.valueOf(tb_inaccount.getMoney()) + "Ԫ     "
						+ tb_inaccount.getTime();
				m++;// ��ʶ��1
			}
			break;
		case R.id.btnflaginfo: 
			strType = "btnflaginfo"; 
			FlagDAO flaginfo = new FlagDAO(Showinfo.this); 
			// ��ȡ���б�ǩ��Ϣ�����洢��List���ͼ�����
			List<Tb_flag> listFlags = flaginfo.getScrollData(0,
					(int) flaginfo.getCount());
			strInfos = new String[listFlags.size()];// �����ַ�������ĳ���
			int n = 0;// ����һ����ʼ��ʶ
			for (Tb_flag tb_flag : listFlags) { 
				strInfos[n] = tb_flag.getid() + "   " + tb_flag.getFlag();
				if (strInfos[n].length() > 15)// �жϱ�ǩ��Ϣ�ĳ����Ƿ����15
					strInfos[n] = strInfos[n].substring(0, 15) + "����"; 
				n++;// ��ʶ��1
			}
			break;
		} 
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strInfos);
		lvinfo.setAdapter(arrayAdapter);// ΪListView�б���������Դ
	} 
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();// ʵ�ֻ����еķ���
		ShowInfo(R.id.btnoutinfo);// ��ʾ֧����Ϣ
	}
}
