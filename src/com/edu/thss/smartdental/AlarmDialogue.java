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

public class AlarmDialogue extends Activity{
	private MediaPlayer alarmMusic;
	private long id;
	private String time;
	private String title;
	private String music;
	private int sound;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		id = getIntent().getExtras().getLong("id");
		time = getIntent().getExtras().getString("time");
		title = getIntent().getExtras().getString("title");
		music = getIntent().getExtras().getString("music");
		sound = getIntent().getExtras().getInt("sound");
		
		final boolean soundFlag = ((sound & 2) == 2);
		final boolean vibrateFlag = ((sound & 1) == 1);
		
		alarmMusic = new MediaPlayer();
		if (soundFlag)
		{
			try{
				alarmMusic.setDataSource(music);
				alarmMusic.setLooping(true);
				alarmMusic.prepare();
				alarmMusic.start();
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
		final Vibrator vibrator = (Vibrator) AlarmDialogue.this.getSystemService(Service.VIBRATOR_SERVICE);
		if (vibrateFlag)
			vibrator.vibrate(new long[]{3000,1500}, 0);
		
		
		new AlertDialog.Builder(AlarmDialogue.this)
			.setCancelable(false)
			.setTitle("SmartDental日程提醒")
			.setMessage(time + " 到了，是时候[" + title + "]")
			.setPositiveButton("查看详情", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					if (soundFlag)
						alarmMusic.stop();
					if (vibrateFlag)
						vibrator.cancel();
					Intent intent = new Intent(AlarmDialogue.this, SetClockActivity.class);
				    intent.putExtra("id", (long)id);
				    startActivityForResult(intent,3);
					AlarmDialogue.this.finish();
				}
			})
			.setNegativeButton("知道了", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					if (soundFlag)
						alarmMusic.stop();
					if (vibrateFlag)
						vibrator.cancel();
					AlarmDialogue.this.finish();
				}
			})
			.show();
	}

}
