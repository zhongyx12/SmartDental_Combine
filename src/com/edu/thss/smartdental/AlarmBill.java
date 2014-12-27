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
		msg += "本月总支出已达到" + current + "元\r\n(设置限额为" + threshold + "元)\r\n";
		if (rate < 1)
		{
			msg += "已到限额的" + String.format("%.0f", rate * 100) + "%.\r\n就快超额啦，省着点用！";
			vibrator.vibrate(new long[]{0,1000}, -1);
		}
		else if (rate <= 1.2)
		{
			msg += "已超过限额的" + String.format("%.0f", rate * 100) + "%.\r\n已经轻度超额啦，注意！";
			vibrator.vibrate(new long[]{0,1000,500,1000}, -1);
		}
		else if (rate <= 1.5)
		{
			msg += "已超过限额的" + String.format("%.0f", rate * 100) + "%.\r\n已经中度超额啦，钱包很吃紧了哟！";
			vibrator.vibrate(new long[]{0,1000,500,1000,500,1000}, -1);
		}
		else
		{
			msg += "已超过限额的" + String.format("%.0f", rate * 100) + "%！\r\n已经严重超额啦，这日子没法过了！";
			vibrator.vibrate(new long[]{0,1000,500,1000,500,1000,500,1000}, -1);
		}
		
		
		new AlertDialog.Builder(AlarmBill.this)
			.setCancelable(false)
			.setTitle("SmartDental账单超额提醒")
			.setMessage(msg)
			.setNegativeButton("知道了", new OnClickListener(){
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
