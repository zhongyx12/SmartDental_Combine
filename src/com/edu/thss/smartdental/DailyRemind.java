package com.edu.thss.smartdental;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.edu.thss.smartdental.model.LocalDB;
import com.edu.thss.smartdental.model.Model;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class DailyRemind extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(now);
        
        LocalDB ldb = new LocalDB(getApplicationContext());
        Model[] mdl = ldb.findList(date);
        ldb.close();
        
        DailyNotificationBusiness dnb = new DailyNotificationBusiness();
	    dnb.addNotification(DailyRemind.this, mdl.length);
	    DailyRemind.this.finish();
	}
}
