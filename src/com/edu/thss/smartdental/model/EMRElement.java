package com.edu.thss.smartdental.model;

public class EMRElement {
	public String name;
	public String description;
	public String time;
	public String department;
	public boolean isMarked;
	public boolean isHidden;
	public boolean isRead;
	public int emrClass; // ������� 1 ���� 2סԺ 3����
	
	public EMRElement(String n,String d,String t,int ec ){
		this.name = n;
		this.description = d;
		this.time =t;
		this.department = "";
		this.isHidden = false;
		this.isMarked =false;
		this.isRead = false;
		this.emrClass = ec;
	}
	
}
