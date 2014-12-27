package com.edu.thss.smartdental.model;

public class AlarmClock {
	public long _id;
	public String date;
	public String time;
	public String title;
	public String content;
	public int way;
	public int sound;
	public String music;
	public AlarmClock(String date,String time,String title,String content, int way,int sound, String music){
		this.date = date;
		this.time = time;
		this.title = title;
		this.content = content;
		this.way = way;
		this.sound = sound;
		this.music = music;
	}
}
