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
	String[] strInfos;// �����ַ�������
	String strid, strType;// ���������ַ����������ֱ�������¼��Ϣ��ź͹�������
	private ImageButton fanhui;//����
	private int mYear; 
	private int mMonth; 
	private int mDay; 

	OutaccountDAO outaccountDAO = new OutaccountDAO(InfoManage.this);// ����OutaccountDAO����
	InaccountDAO inaccountDAO = new InaccountDAO(InfoManage.this);// ����InaccountDAO����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//�����ޱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//����ȫ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.infomanage);// ���ò����ļ�
		tvtitle = (TextView) findViewById(R.id.inouttitle); 
		textView = (TextView) findViewById(R.id.tvInOut); 
		txtMoney = (EditText) findViewById(R.id.txtInOutMoney); 
		txtTime = (EditText) findViewById(R.id.txtInOutTime); 
		spType = (Spinner) findViewById(R.id.spInOutType); 
		txtHA = (EditText) findViewById(R.id.txtInOut); 
		txtMark = (EditText) findViewById(R.id.txtInOutMark); 
		btnEdit = (Button) findViewById(R.id.btnInOutEdit); 
		btnDel = (Button) findViewById(R.id.btnInOutDelete); 

		Intent intent = getIntent();// ����Intent����
		Bundle bundle = intent.getExtras();// ��ȡ��������ݣ���ʹ��Bundle��¼
		strInfos = bundle.getStringArray(Showinfo.FLAG);// ��ȡBundle�м�¼����Ϣ
		strid = strInfos[0];// ��¼id
		strType = strInfos[1];// ��¼����
		
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
			tvtitle.setText("֧������"); 
			textView.setText("֧ ����"); 
			// ���ݱ�Ų���֧����Ϣ�����洢��Tb_outaccount������
			Tb_outaccount tb_outaccount = outaccountDAO.find(Integer
					.parseInt(strid));
			txtMoney.setText(String.valueOf(tb_outaccount.getMoney()));// ��ʾ���
			txtTime.setText(tb_outaccount.getTime());// ��ʾʱ��
			spType.setPrompt(tb_outaccount.getType());// ��ʾ���
			txtHA.setText(tb_outaccount.getAddress());// ��ʾ�ص�
			txtMark.setText(tb_outaccount.getMark());// ��ʾ��ע
		} else if (strType.equals("btnininfo"))// ���������btnininfo
		{
			tvtitle.setText("�������"); 
			textView.setText("�����"); 
			Tb_inaccount tb_inaccount = inaccountDAO.find(Integer
					.parseInt(strid));
			txtMoney.setText(String.valueOf(tb_inaccount.getMoney()));// ��ʾ���
			txtTime.setText(tb_inaccount.getTime());// ��ʾʱ��
			spType.setPrompt(tb_inaccount.getType());// ��ʾ���
			txtHA.setText(tb_inaccount.getHandler());// ��ʾ���
			txtMark.setText(tb_inaccount.getMark());// ��ʾ��ע
		}

		txtTime.setOnClickListener(new OnClickListener() {// Ϊʱ���ı������õ��������¼�
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);// ��ʾ����ѡ��Ի���
			}
		});

		btnEdit.setOnClickListener(new OnClickListener() {// Ϊ�޸İ�ť���ü����¼�
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (strType.equals("btnoutinfo"))// �ж����������btnoutinfo
				{
					Tb_outaccount tb_outaccount = new Tb_outaccount();// ����Tb_outaccount����
					tb_outaccount.setid(Integer.parseInt(strid));// ���ñ��
					tb_outaccount.setMoney(Double.parseDouble(txtMoney
							.getText().toString()));// ���ý��
					tb_outaccount.setTime(txtTime.getText().toString());// ����ʱ��
					tb_outaccount.setType(spType.getSelectedItem().toString());// �������
					tb_outaccount.setAddress(txtHA.getText().toString());// ���õص�
					tb_outaccount.setMark(txtMark.getText().toString());// ���ñ�ע
					outaccountDAO.update(tb_outaccount); 
					Toast.makeText(InfoManage.this, "�����ݡ��޸ĳɹ���", Toast.LENGTH_SHORT)
							.show();
					Intent intent = new Intent();
					intent.setClass(InfoManage.this, Outaccountinfo.class);
					startActivity(intent);
				} else if (strType.equals("btnininfo"))// �ж����������btnininfo
				{
					Tb_inaccount tb_inaccount = new Tb_inaccount();// ����Tb_inaccount����
					tb_inaccount.setid(Integer.parseInt(strid));// ���ñ��
					tb_inaccount.setMoney(Double.parseDouble(txtMoney.getText()
							.toString()));// ���ý��
					tb_inaccount.setTime(txtTime.getText().toString());// ����ʱ��
					tb_inaccount.setType(spType.getSelectedItem().toString());// �������
					tb_inaccount.setHandler(txtHA.getText().toString());// ���ø��
					tb_inaccount.setMark(txtMark.getText().toString());// ���ñ�ע
					inaccountDAO.update(tb_inaccount); 
					Toast.makeText(InfoManage.this, "�����ݡ��޸ĳɹ���", Toast.LENGTH_SHORT)
							.show();
					Intent intent = new Intent();
					intent.setClass(InfoManage.this, Inaccountinfo.class);
					startActivity(intent);
				} 
			}
		});

		btnDel.setOnClickListener(new OnClickListener() {// Ϊɾ����ť���ü����¼�
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (strType.equals("btnoutinfo")) 
				{
					outaccountDAO.detele(Integer.parseInt(strid));// ���ݱ��ɾ��֧����Ϣ
					
					AlertDialog.Builder builder = new Builder(InfoManage.this);
					builder.setMessage("ȷ��Ҫɾ����?");
					builder.setTitle("��ʾ");
					builder.setPositiveButton("ȷ��",
					new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.dismiss();
					Toast.makeText(InfoManage.this, "�����ݡ�ɾ���ɹ���", Toast.LENGTH_SHORT)
					.show(); 
					Intent intent = new Intent();
					intent.setClass(InfoManage.this, Outaccountinfo.class);
					startActivity(intent);
					}
					});
					builder.setNegativeButton("ȡ��",
					new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					}
					});
					builder.create().show();
					
				} else if (strType.equals("btnininfo"))// �ж����������btnininfo
				{
					inaccountDAO.detele(Integer.parseInt(strid));// ���ݱ��ɾ��������Ϣ
					
					AlertDialog.Builder builder = new Builder(InfoManage.this);
					builder.setMessage("ȷ��Ҫɾ����?");
					builder.setTitle("��ʾ");
					builder.setPositiveButton("ȷ��",
					new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.dismiss();
					Toast.makeText(InfoManage.this, "�����ݡ�ɾ���ɹ���", Toast.LENGTH_SHORT)
					.show(); 
					Intent intent = new Intent();
					intent.setClass(InfoManage.this, Inaccountinfo.class);
					startActivity(intent);
					}
					});
					builder.setNegativeButton("ȡ��",
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

		final Calendar c = Calendar.getInstance();// ��ȡ��ǰϵͳ����
		mYear = c.get(Calendar.YEAR);// ��ȡ���
		mMonth = c.get(Calendar.MONTH);// ��ȡ�·�
		mDay = c.get(Calendar.DAY_OF_MONTH);// ��ȡ����
		updateDisplay();// ��ʾ��ǰϵͳʱ��
	}

	@Override
	protected Dialog onCreateDialog(int id)// ��дonCreateDialog����
	{
		switch (id) {
		case DATE_DIALOG_ID:// ��������ѡ��Ի���
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;// Ϊ��ݸ�ֵ
			mMonth = monthOfYear;// Ϊ�·ݸ�ֵ
			mDay = dayOfMonth;// Ϊ�츳ֵ
			updateDisplay();// ��ʾ���õ�����
		}
	};

	private void updateDisplay() {
		// ��ʾ���õ�ʱ��
		txtTime.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1).append("-").append(mDay));
	}
}
