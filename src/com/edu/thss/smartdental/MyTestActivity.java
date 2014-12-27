package com.edu.thss.smartdental;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MyTestActivity extends Activity {
	Button test;  //�����ؼ�
	
	public static final int RESULT_CODE = 1; //������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_test);  //��XML�ļ�ӳ��
		test = (Button)findViewById(R.id.test_btn); //id�ź�XML�ж����ID��Ӧ
		//test.setText("����һ��button"); //���ÿؼ�
		
		
		String data = getIntent().getExtras().getString("data");
	    test.setText(data);
	    
		test.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("result-data","test");
				MyTestActivity.this.setResult(RESULT_CODE, intent);
			}});
		
	}

}
