package com.edu.thss.smartdental;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;

import com.edu.thss.smartdental.AlarmDialogue;
import com.edu.thss.smartdental.OnedayAlarmClockActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 淙毅 on 12/18/2014.
 */
public class DailyNotificationBusiness {
	public void addNotification(Context cnt, int numberOfEvents)
    {
        Intent resultIntent = new Intent(cnt, OnedayAlarmClockActivity.class);
        SimpleDateFormat todayFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date(System.currentTimeMillis());
        String todayString = todayFormatter.format(todayDate);
        resultIntent.putExtra("date", todayString);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(cnt, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification;
        String contentText;
        if(numberOfEvents == 0)
        {
            contentText = "你今天没有日程，记得好好刷牙哦~";
        }
        else if(numberOfEvents == 1)
        {
            contentText = "你今天有1个日程，点击查看详情";
        }
        else
        {
            contentText = "你今天有" + numberOfEvents + "个日程，点击查看详情";
        }
        Notification.Builder notificationBuilder = new Notification.Builder(cnt)
                .setContentTitle("Smart Dental Alarm")
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            notification = notificationBuilder.build();
        }
        else
        {
            notification = notificationBuilder.getNotification();
        }
        int notificationID = 0x1234;
        //final Vibrator vibrator = (Vibrator) cnt.getSystemService(Service.VIBRATOR_SERVICE);
	    //vibrator.vibrate(new long[]{1000,3000}, -1);
        long[] vibrator= new long[]{1000,2000};
	    notification.vibrate = vibrator;
	    notification.ledARGB = Color.GREEN;
	    notification.ledOffMS= 0;
	    notification.ledOnMS = 1;
	    notification.flags = notification.flags | Notification.FLAG_SHOW_LIGHTS;
        NotificationManager notificationManager = (NotificationManager) cnt.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notification);

        /*
        // Create a Notification Builder
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(cnt);
        notificationBuilder.setSmallIcon(1);//need modification
        notificationBuilder.setContentTitle("MyNotification");
        notificationBuilder.setContentText("NotificationContentText");
        notificationBuilder.setAutoCancel(true);// correct ?

        // Define the Notification's Action (Need to send today's date in as argument here)
        Intent resultIntent = new Intent(cnt, OnedayAlarmClockActivity.class);
        SimpleDateFormat todayFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date(System.currentTimeMillis());
        String todayString = todayFormatter.format(todayDate);
        resultIntent.putExtra("date", todayString);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(cnt, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the Notification's Click Behavior
        notificationBuilder.setContentIntent(resultPendingIntent);

        // Issue the Notification
        int notificationID = 0x1234;
        NotificationManager notificationManager = (NotificationManager) cnt.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notificationBuilder.build());
        */
    }

    public void setNotificationTriggerAlarm(Context cnt, int hourToSet, int minuteToSet)
    {
        Intent intent = new Intent(cnt, DailyRemind.class);
        PendingIntent pi = PendingIntent.getActivity(cnt, 3650000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar c = Calendar.getInstance();
        AlarmManager aManager;
        aManager = (AlarmManager) cnt.getSystemService(Service.ALARM_SERVICE);
        aManager.cancel(pi);
        Log.v("debug", "1111");
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        if(hourToSet <= hour && minuteToSet <= minute)
        {
            long timeToSet = Calendar.getInstance().getTimeInMillis() + 86400000;
            long timeDelta = (((hour - hourToSet) * 60 + minute - minuteToSet) * 60 + second) * 1000;
            timeToSet -= timeDelta;
            c.setTimeInMillis(timeToSet);
            Log.v("debug", c.toString());
            //aManager.set(AlarmManager.RTC, timeToSet, pi);
            aManager.setRepeating(AlarmManager.RTC, timeToSet, 86400000, pi);
        }
        else
        {
            c.set(year, month, day, hourToSet, minuteToSet, 0);
            //aManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
            aManager.setRepeating(AlarmManager.RTC, c.getTimeInMillis(), 86400000, pi);
        }
    }
}
