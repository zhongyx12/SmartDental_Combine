package com.edu.thss.smartdental.model.tooth;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;

public class ToothChartSurface {
	public int width;
	public int height;
	public int realWidth = 410; 
	public int realHeight = 410;
	//��ɫ
	public int bgColor = Color.WHITE;
	public int colorText = Color.BLACK;  //text����ɫ
	public int colorTextHighlight;
	public int colorBackHighlight;
	public int colorBackSimple=Color.rgb(150,145,153);//opendal�õı���ɫ
	
	public Paint bgPaint;
	public Paint rectPaint;
	public Paint occlusalPaint;
	public Path occlusalPath;
	public Paint outlinePaint;


	//��ʱ�Ȳ�����ӳ������
//	private float WidthProjection; //ͶӰ����Ļ�ϵĿ��
//	private int preferredPixelFormatNum;  


	public void init(){
		rectPaint = new Paint();
		rectPaint.setColor(colorBackSimple);
		rectPaint.setStyle(Style.FILL);
		
		this.bgPaint = new Paint();
		
		this.occlusalPaint = new Paint();
		
		this.outlinePaint = new Paint();
		this.outlinePaint.setColor(Color.GRAY);
		this.outlinePaint.setStyle(Style.STROKE);
		
	}
}
