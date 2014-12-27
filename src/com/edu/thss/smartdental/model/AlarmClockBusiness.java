package com.edu.thss.smartdental.model;

import java.util.Calendar;

import com.edu.thss.smartdental.AlarmDialogue;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

public class AlarmClockBusiness {
	public boolean addAlarmClock(Model md, Context cnt)
	{
		Intent intent = new Intent(cnt, AlarmDialogue.class);
		intent.putExtra("id", md._id);
		intent.putExtra("time", md.time);
		intent.putExtra("title", md.title);
		intent.putExtra("music", md.music);
		intent.putExtra("sound", md.sound);
		PendingIntent pi = PendingIntent.getActivity(cnt, Long.valueOf(md._id).intValue(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
		Calendar c = Calendar.getInstance();
		int year = Integer.parseInt(md.date.subSequence(0, 4).toString());
        int month = Integer.parseInt(md.date.subSequence(5, 7).toString())-1;
        int day = Integer.parseInt(md.date.subSequence(8, 10).toString());
        int hour = Integer.parseInt(md.time.subSequence(0, 2).toString());
        int minute = Integer.parseInt(md.time.subSequence(3, 5).toString());
        c.set(year, month, day, hour, minute, 0);
        Calendar current = Calendar.getInstance();
        if (current.getTimeInMillis() < c.getTimeInMillis())
        {
	        AlarmManager aManager;
	        aManager = (AlarmManager) cnt.getSystemService(Service.ALARM_SERVICE);
	        aManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
			return true;
        }
        else
        	return false;
	}
	
	public boolean updateAlarmClock(Model md, Context cnt)
	{
		boolean flag;
		deleteAlarmClock(md._id, cnt);
		flag = addAlarmClock(md, cnt);
		return flag;
	}
	
	public boolean deleteAlarmClock(long id, Context cnt)
	{
		Intent intent;
		PendingIntent pi;
		AlarmManager aManager;
		
		intent = new Intent(cnt, AlarmDialogue.class);
		pi = PendingIntent.getActivity(cnt, Long.valueOf(id).intValue(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
		aManager = (AlarmManager) cnt.getSystemService(Service.ALARM_SERVICE);
		aManager.cancel(pi);
		return true;
	}
}
