package com.edu.thss.smartdental.util;

import android.os.Environment;

public class Tools {
	/*
	 * ����Ƿ����SDCard
	 * */
	public static boolean hasSDCard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
}
