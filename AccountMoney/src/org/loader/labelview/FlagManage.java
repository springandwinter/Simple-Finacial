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
	private ImageButton fanhui;//����
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//�����ޱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//����ȫ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.flagmanage);// ���ò����ļ�
		txtFlag = (EditText) findViewById(R.id.txtFlagManage); 
		btnEdit = (Button) findViewById(R.id.btnFlagManageEdit);// ��ȡ�޸İ�ť
		btnDel = (Button) findViewById(R.id.btnFlagManageDelete);// ��ȡɾ����ť

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
		btnEdit.setOnClickListener(new OnClickListener() {// Ϊ�޸İ�ť���ü����¼�
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Tb_flag tb_flag = new Tb_flag();// ����Tb_flag����
				tb_flag.setid(Integer.parseInt(strid));// ���ñ�ǩid
				tb_flag.setFlag(txtFlag.getText().toString());// ���ñ�ǩֵ
				flagDAO.update(tb_flag);// �޸ı�ǩ��Ϣ
				// ������Ϣ��ʾ
				Toast.makeText(FlagManage.this, "����ǩ���ݡ��޸ĳɹ���",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(FlagManage.this, Showinfo.class);
				startActivity(intent);
			}
		});   

		btnDel.setOnClickListener(new OnClickListener() {// Ϊɾ����ť���ü����¼�
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new Builder(FlagManage.this);
				builder.setMessage("ȷ��Ҫ��ת��?");
				builder.setTitle("��ʾ");
				builder.setPositiveButton("ȷ��",
				new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
					flagDAO.detele(Integer.parseInt(strid)); 
					Toast.makeText(FlagManage.this, "����ǩ���ݡ�ɾ���ɹ���",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(FlagManage.this, Showinfo.class);
					startActivity(intent);
					arg0.dismiss();
				 
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
		});
	}

}
