package com.edu.thss.smartdental;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.edu.thss.smartdental.model.AlarmClockBusiness;
import com.edu.thss.smartdental.model.LocalDB;
import com.edu.thss.smartdental.model.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class SetClockActivity extends Activity {

    long eventId;
    String eventDate;
    
    TextView tvdate;
    int year;
    int month;
    int day;
    CharSequence date;
    
    TextView tvtime;
    int hour;
    int minute;
    CharSequence time;
    
    EditText ettitle;
    CharSequence title;
    
    EditText etcontent;
    CharSequence content;
    
    CheckBox checksound;
    CheckBox checkvibrate;
    int check;
    Spinner ssound;
    CharSequence sound;
    
    Button save;
    Button delete;
    
    private static final int DATE_DIALOG_ID = 0;
    public static final int RESULT_CODE = 10;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_clock);
        setEventId();
        initialViews();
        
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_bg));
        
    }

    /**
     * 初始化控件和UI视图
     * */
    private void setEventId(){
        eventId = getIntent().getExtras().getLong("id");
        eventDate = getIntent().getExtras().getString("date");
    }
    
    private void initialViews(){
        if (eventId == -1)
        {
            title = "";
            content = "";
            check = 1;
            sound = "";
            
            //------- date------------
            date = eventDate;
            tvdate = (TextView)findViewById(R.id.setdate_c);
            tvdate.setText(date);
            year = Integer.parseInt(date.subSequence(0, 4).toString());
            month = Integer.parseInt(date.subSequence(5, 7).toString())-1;
            day = Integer.parseInt(date.subSequence(8, 10).toString());
            tvdate.setOnClickListener(datelistener);
            //------- time------------
            tvtime = (TextView)findViewById(R.id.settime_c);
            setTime();
            tvtime.setOnClickListener(timelistener);
            //-------------title------------
            ettitle = (EditText)findViewById(R.id.settitle_c);
            ettitle.setText(title);
            //-------------content------------
            etcontent = (EditText)findViewById(R.id.setcontent_c);
            etcontent.setText(content);
            //-------sound-----------
            checksound = (CheckBox)findViewById(R.id.setsound_r);
            checkvibrate = (CheckBox)findViewById(R.id.setsound_v);
            checksound.setChecked((check&2)==2?true:false);
            checkvibrate.setChecked((check&1)==1?true:false);
            checksound.setOnCheckedChangeListener(checklistener);
            checkvibrate.setOnCheckedChangeListener(checklistener);
            
            ssound = (Spinner)findViewById(R.id.setsound_s);
            ArrayAdapter<String> soundadapter = new ArrayAdapter<String>(SetClockActivity.this,
                    R.layout.spinner_select_music,
                    getSoundData());
            ssound.setAdapter(soundadapter);
            ssound.setPrompt("请选择铃声:");
            ssound.setOnItemSelectedListener(soundlistener);
            //----------button------------
            save = (Button)findViewById(R.id.setsave);
            save.setOnClickListener(saveListener);
            
            delete = (Button)findViewById(R.id.setdelete);
            delete.setVisibility(View.GONE);
            delete.setOnClickListener(deleteListener);
        }
        else
        {
            Model md = new Model();
            LocalDB ldb = new LocalDB(getApplication());
            md = ldb.find(eventId);
            //------- date------------
            date = md.date;
            tvdate = (TextView)findViewById(R.id.setdate_c);
            tvdate.setText(date);
            year = Integer.parseInt(date.subSequence(0, 4).toString());
            month = Integer.parseInt(date.subSequence(5, 7).toString())-1;
            day = Integer.parseInt(date.subSequence(8, 10).toString());
            tvdate.setOnClickListener(datelistener);
            //------- time------------
            time = md.time;
            tvtime = (TextView)findViewById(R.id.settime_c);
            tvtime.setText(time);
            hour = Integer.parseInt(time.subSequence(0, 2).toString());
            minute = Integer.parseInt(time.subSequence(3, 5).toString());
            tvtime.setOnClickListener(timelistener);
            //-------------title------------
            title = md.title;
            ettitle = (EditText)findViewById(R.id.settitle_c);
            ettitle.setText(title);
            //-------------content------------
            content = md.content;
            etcontent = (EditText)findViewById(R.id.setcontent_c);
            etcontent.setText(content);
            //-------sound-----------
            check = md.sound;
            checksound = (CheckBox)findViewById(R.id.setsound_r);
            checkvibrate = (CheckBox)findViewById(R.id.setsound_v);
            checksound.setChecked((check&2)==2?true:false);
            checkvibrate.setChecked((check&1)==1?true:false);
            checksound.setOnCheckedChangeListener(checklistener);
            checkvibrate.setOnCheckedChangeListener(checklistener);
            
            sound = md.music;
            ssound = (Spinner)findViewById(R.id.setsound_s);
            ArrayAdapter<String> soundadapter = new ArrayAdapter<String>(SetClockActivity.this,
                    R.layout.spinner_select_music,
                    getSoundData());
            ssound.setAdapter(soundadapter);
            ssound.setPrompt("请选择铃声:");
            scansdcard();
            if(soundadapter.getPosition(sound.toString())>=0)
            	ssound.setSelection(soundadapter.getPosition(sound.toString()), true);
            ssound.setOnItemSelectedListener(soundlistener);
            //----------button------------
            save = (Button)findViewById(R.id.setsave);
            save.setOnClickListener(saveListener);
            
            delete = (Button)findViewById(R.id.setdelete);
            delete.setOnClickListener(deleteListener);
        }
        
    }
    
    private void setDate(){
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        updateDateDisplay();
    }
    
    private void setTime(){
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        updateTimeDisplay();
    }

    private final BroadcastReceiver broadcastRec =  new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("android.intent.action.ACTION_MEDIA_SCANNER_FINISHED"))
            {
                ArrayAdapter<String> soundadapter = new ArrayAdapter<String>(SetClockActivity.this,
                        R.layout.spinner_select_music,
                        getSoundData());
                ssound.setAdapter(soundadapter);
            }
        }};

    private void scansdcard()
    {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        intentFilter.addDataScheme("file");
        registerReceiver(broadcastRec, intentFilter);
        unregisterReceiver(broadcastRec);
    }

    /**
     * 更新显示
     * */
    private void updateDateDisplay(){
        String tempdate = new String();
        tempdate += year;
        tempdate += "-";
        tempdate += (month+1) <10?"0":"";
        tempdate += (month+1);
        tempdate += "-";
        tempdate += day < 10?"0":"";
        tempdate += day;
        
        date = tempdate;
        tvdate.setText(tempdate);       
    }
    
    private void updateTimeDisplay(){
        String temptime = new String();
        temptime += hour < 10?"0":"";
        temptime += hour;
        temptime += ":";
        temptime += minute < 10?"0":"";
        temptime += minute;
        
        time = temptime;
        tvtime.setText(temptime);       
    }
    
    /**
    * 日期控件监听
    * */
    private View.OnClickListener datelistener = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
            
            DatePickerDialog dpd = new DatePickerDialog(SetClockActivity.this,datesetListener,year,month,day);
            dpd.show();
        }
    };
    
    private OnDateSetListener datesetListener = new OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int y, int m, int d) {
            year = y;
            month = m;
            day = d;
            
            updateDateDisplay();
        }
        
    };
    
    /**
     * 时间控件监听
     * */
    private View.OnClickListener timelistener = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
            
            TimePickerDialog tpd = new TimePickerDialog(SetClockActivity.this,timesetListener,hour,minute,true);
            tpd.show();
        }
    };
    
    private OnTimeSetListener timesetListener = new OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int h, int m) {
            hour = h;
            minute = m;
            
            updateTimeDisplay();
        }
        
    };
        

    /**
     * check监听
     * */    
    private CheckBox.OnCheckedChangeListener checklistener = new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton buttonview, boolean ischecked) {
            if(buttonview.getId() == checksound.getId()){
                check ^= 2;
            }
            else if(buttonview.getId() == checkvibrate.getId()){
                check ^= 1;
            }
            
        }};
        
    /**
     * 选择音乐监听
     * */
    private Spinner.OnItemSelectedListener soundlistener = new Spinner.OnItemSelectedListener() {
        
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            sound = ssound.getSelectedItem().toString();
            
        }
        
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

            
        }
    };
    
    private List<String> getSoundData() {
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.SIZE},
                null, null, null);
        List<String> dataList = new ArrayList<String>();
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                dataList.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                cursor.moveToNext();
            }
        }
        return dataList;
    }
    
    /**
    * 保存按钮监听
    * */    
    private OnClickListener saveListener = new OnClickListener(){

        @Override
        public void onClick(View v) {
            title = ettitle.getText().toString();
            content = etcontent.getText().toString();
            Model md = new Model();
            md.date = date.toString();
            md.time = time.toString();
            md.title = title.toString();
            md.content = content.toString();
//                md.way = way;
            md.sound = check;
            md.music = sound.toString();
            
            LocalDB ldb = new LocalDB(getApplicationContext());
            AlarmClockBusiness acb = new AlarmClockBusiness();
            long row;
            boolean flag;
            if (eventId == -1)
            {
                row = ldb.insert(md);
                if (row == -1)
                {
                	ldb.close();
                    Toast.makeText(SetClockActivity.this, "保存失败。", Toast.LENGTH_SHORT).show();
                }
                else
                {
                	md._id = row;
                    flag = acb.addAlarmClock(md, SetClockActivity.this);
                    if (flag == true)
                    {
                        ldb.close();
                        Toast.makeText(SetClockActivity.this, "保存成功。", Toast.LENGTH_SHORT).show();
                        DailyNotificationBusiness dnb = new DailyNotificationBusiness();
                        dnb.setNotificationTriggerAlarm(SetClockActivity.this, 8, 0);
                        SetClockActivity.this.finish();
                    }
                    else
                    {
                        ldb.close();
                        Toast.makeText(SetClockActivity.this,"保存成功，但请注意设置的时间在当前时间之前。", Toast.LENGTH_LONG).show();
                        DailyNotificationBusiness dnb = new DailyNotificationBusiness();
                        dnb.setNotificationTriggerAlarm(SetClockActivity.this, 8, 0);
                        Intent intent = new Intent(SetClockActivity.this, SetClockActivity.class);
                        intent.putExtra("id", row);
                        startActivityForResult(intent, 0);
                        SetClockActivity.this.finish();
                    }
                }
            }
            else
            {
                flag = ldb.update(eventId, md);
                if (flag == false)
                {
                	ldb.close();
                    Toast.makeText(SetClockActivity.this, "保存失败。", Toast.LENGTH_SHORT).show();
                }
                else
                {
                	md._id = eventId;
                    flag = acb.updateAlarmClock(md, SetClockActivity.this);
                    if (flag == true)
                    {
                        ldb.close();
                        Toast.makeText(SetClockActivity.this, "保存成功。", Toast.LENGTH_SHORT).show();
                        DailyNotificationBusiness dnb = new DailyNotificationBusiness();
                        dnb.setNotificationTriggerAlarm(SetClockActivity.this, 8, 0);
                        SetClockActivity.this.finish();
                    }
                    else
                    {
                        ldb.close();
                        Toast.makeText(SetClockActivity.this, "保存成功，但请注意设置的时间在当前时间之前。", Toast.LENGTH_LONG).show();
                        DailyNotificationBusiness dnb = new DailyNotificationBusiness();
                        dnb.setNotificationTriggerAlarm(SetClockActivity.this, 8, 0);
                    }
                }
            }
        }
        
    };
    
    /**
    * 删除按钮监听
    * */    
    private OnClickListener deleteListener = new OnClickListener(){

        @Override
        public void onClick(View v) {
            LocalDB ldb = new LocalDB(getApplicationContext());
            boolean flag = ldb.delete(eventId);
            if (flag == true)
            {
            	AlarmClockBusiness acb = new AlarmClockBusiness();
            	acb.deleteAlarmClock(eventId, SetClockActivity.this);
                ldb.close();
                Toast.makeText(SetClockActivity.this, "删除成功。", Toast.LENGTH_SHORT).show();
                SetClockActivity.this.finish();
            }
            else
            {
                ldb.close();
                Toast.makeText(SetClockActivity.this, "删除失败。", Toast.LENGTH_SHORT).show();
            }
        }
        
    };
}
