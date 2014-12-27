package com.edu.thss.smartdental;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;

public class AlarmBill extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		double rate = getIntent().getExtras().getDouble("rate");
		double current = getIntent().getExtras().getDouble("current");
		int threshold = getIntent().getExtras().getInt("threshold");
		
		
		final Vibrator vibrator = (Vibrator) AlarmBill.this.getSystemService(Service.VIBRATOR_SERVICE);
		String msg = "";
		msg += "������֧���Ѵﵽ" + current + "Ԫ\r\n(�����޶�Ϊ" + threshold + "Ԫ)\r\n";
		if (rate < 1)
		{
			msg += "�ѵ��޶��" + String.format("%.0f", rate * 100) + "%.\r\n�Ϳ쳬������ʡ�ŵ��ã�";
			vibrator.vibrate(new long[]{0,1000}, -1);
		}
		else if (rate <= 1.2)
		{
			msg += "�ѳ����޶��" + String.format("%.0f", rate * 100) + "%.\r\n�Ѿ���ȳ�������ע�⣡";
			vibrator.vibrate(new long[]{0,1000,500,1000}, -1);
		}
		else if (rate <= 1.5)
		{
			msg += "�ѳ����޶��" + String.format("%.0f", rate * 100) + "%.\r\n�Ѿ��жȳ�������Ǯ���ܳԽ���Ӵ��";
			vibrator.vibrate(new long[]{0,1000,500,1000,500,1000}, -1);
		}
		else
		{
			msg += "�ѳ����޶��" + String.format("%.0f", rate * 100) + "%��\r\n�Ѿ����س�������������û�����ˣ�";
			vibrator.vibrate(new long[]{0,1000,500,1000,500,1000,500,1000}, -1);
		}
		
		
		new AlertDialog.Builder(AlarmBill.this)
			.setCancelable(false)
			.setTitle("SmartDental�˵���������")
			.setMessage(msg)
			.setNegativeButton("֪����", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					vibrator.cancel();
					AlarmBill.this.finish();
				}
			})
			.show();
	}
}
