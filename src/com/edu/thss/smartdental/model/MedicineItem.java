package com.edu.thss.smartdental.model;

import android.widget.TextView;

public class MedicineItem {

	public String name;
	public String num;
	public String cost;
	public String officialCost;
	public String selfCost;
	public String addition;
	
	public MedicineItem(String name,String num,String cost, String officialCost,String selfCost,String addition){
		this.name = name;
		this.num = num;
		this.cost = cost;
		this.officialCost = officialCost;
		this.selfCost = selfCost;
		this.addition = addition;
	}
}
