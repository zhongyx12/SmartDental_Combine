package com.edu.thss.smartdental.adapter;

import com.edu.thss.smartdental.ToothObjectFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ToothPageAdapter extends FragmentStatePagerAdapter{

	public ToothPageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	/**
	 * ����ÿҳ��fragment
	 * */
	public Fragment getItem(int i) {
		Fragment fragment = new ToothObjectFragment();
		Bundle args = new Bundle();
		args.putInt(ToothObjectFragment.ARG_OBJECT, i+1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return 3; //transplan, miss,primary
	}

	@Override
	public CharSequence getPageTitle(int position) {
		String str = "";
		switch(position){
		case 0: str = "���Ƽƻ�"; break;
		case 1: str = "ȱʧ������";break;
		case 2: str = "�����ͺ���";break;
		default: break;
		}
		return str;
	}

}
