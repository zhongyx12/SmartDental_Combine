package com.edu.thss.smartdental.ui.calendar;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.Typeface;
import android.text.StaticLayout;
import android.text.TextPaint;

public class AppointmentChartSurface {
 
	public float density;
	public int width;
	public int height;
	public float collineWidth = 5; //���̶��߿��
	public float rowlineWidth = 1; //��̶��߿��
	public float minuteWidth; //���ӿ��
	public float hourWidth; //Сʱ���
	public float hourHeight;//���Ӹ߶�
	public float minuteHeight; //���Ӹ߶�
	public float textHeight;//ԤԼ���Ƹ߶�
	public float spaceHeight; //�����¼��ļ���߶�
	public float textWidth; //ԤԼ���ƿ��
	
	public int bgColor = Color.parseColor("#ffffff");
	public int timeColor = Color.parseColor("#00bfff");
	public int lineColor = Color.parseColor("#00bfff");
	public int textColor = Color.parseColor("#00bfff");
	public int circleColor = Color.parseColor("#00bfff");
	
	public Paint linePaint;
	public TextPaint textPaint;
	public Paint timePaint;
	public Paint circlePaint;
	
	StaticLayout textLayout;
	
	public void init(){
		
		
		this.linePaint = new Paint();
		this.linePaint.setColor(lineColor);
		this.linePaint.setStyle(Paint.Style.STROKE);
		//this.linePaint.setStrokeWidth(lineWidth);
		
		this.timePaint = new Paint();
		this.timePaint.setColor(timeColor);
		this.timePaint.setAntiAlias(true);
		this.timePaint.setTypeface(Typeface.DEFAULT_BOLD); //����
		//����Сʱ��
		this.timePaint.setTextSize(60);
		this.hourWidth = this.timePaint.measureText("24");
		FontMetrics fm = timePaint.getFontMetrics();
		this.hourHeight = (float)Math.ceil(fm.descent - fm.top) + 2;
		//���÷��ӵ�
		this.timePaint.setTextSize(40);
		this.minuteWidth = this.timePaint.measureText("30");
		fm = timePaint.getFontMetrics();
		this.minuteHeight = (float)Math.ceil(fm.descent - fm.top) + 2;
	
		this.textPaint = new TextPaint();
		this.textPaint.setColor(this.textColor);
		this.textPaint.setAntiAlias(true);
		this.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
		this.textPaint.setTextSize(40);
		fm = textPaint.getFontMetrics();
		this.textHeight = (float)Math.ceil(fm.descent - fm.top) + 2;
		
		
		this.circlePaint = new Paint();
		this.circlePaint.setColor(circleColor);
		this.circlePaint.setStyle(Paint.Style.FILL);
		
		
	}
}
