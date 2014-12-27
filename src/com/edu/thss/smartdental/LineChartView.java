package com.edu.thss.smartdental;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.edu.thss.smartdental.model.general.ParseJson;
import com.edu.thss.smartdental.model.general.SDAccount;
/**
 * TODO: document your custom view class.
 */
public class LineChartView extends View {
	private String accountData;
	private final static float canvasHeightOffset = 30;
	private final static float canvasWidthOffset = 90;
	private final static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	private float min_tot, max_tot;
	private DateTime min_datetime, max_datetime;
	private int max_duration;
	
	public String getAccountData() {
		return accountData; 
	}
	
	public void setAccountData(String data) {
		accountData = data;
	}
	
	public LineChartView(Context context) {
		super(context);
		init(null, 0);
	}

	public LineChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public LineChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		// Load attributes
		final TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.LineChartView, defStyle, 0);

		a.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// TODO: consider storing these as member variables to reduce
		// allocations per draw cycle.


		drawFrame(canvas);
		try {
			drawLine(canvas);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	private void drawFrame(Canvas canvas) {
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();

		int contentWidth = getWidth() - paddingLeft - paddingRight;
		int contentHeight = getHeight() - paddingTop - paddingBottom;
		
		Paint paint = new Paint();
		paint.setColor(0xff00ffff);
		paint.setStrokeWidth(5);
		canvas.drawLine(canvasWidthOffset, 0, canvasWidthOffset, contentHeight, paint);
		canvas.drawLine(canvasWidthOffset, 0, canvasWidthOffset - 10, 15, paint);
		canvas.drawLine(canvasWidthOffset, 0, canvasWidthOffset + 10, 15, paint);
		canvas.drawLine(canvasWidthOffset, contentHeight - canvasHeightOffset, contentWidth, contentHeight - canvasHeightOffset, paint);
		paint.setStrokeWidth(3);
		paint.setColor(0xff00ffff);
		contentHeight -= canvasHeightOffset;
		for (int i = 0; i < 5; i++) {
			canvas.drawLine(canvasWidthOffset, contentHeight * (float)0.1 + contentHeight * (float)0.2 * i, contentWidth, contentHeight * (float) 0.1 + contentHeight * (float)0.2 * i, paint);
		}
	}

	private void drawLine(Canvas canvas) throws ParseException {
		ParseJson strToJson = new ParseJson();
		SDAccount data[] = mergeAccount(strToJson.m_parseSimpleAccount(accountData));
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();

		int contentWidth = getWidth() - paddingLeft - paddingRight;
		int contentHeight = getHeight() - paddingTop - paddingBottom;
		contentWidth -= canvasWidthOffset;
		contentHeight -= canvasHeightOffset;
		setBound(data);

		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(7);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		
		float x = 0;
		float y = 0;
		int i;
		int len = data.length;
		for (i = 0; i < len; i++) {
			int duration = getDuration(data[i].time.substring(0, 10), min_datetime);
			float _x = getCordinate(contentWidth * (float)0.8, (float)duration / max_duration, canvasWidthOffset + contentWidth * (float)0.1);
			float _y = getCordinate(contentHeight * (float)0.8, (max_tot - (float)data[i].firstTotal) / (max_tot - min_tot), contentHeight * (float)0.1);
			if (x != 0 || y != 0) canvas.drawLine(x, y, _x, _y, paint);
			x = _x;
			y = _y;
			canvas.drawCircle(x, y, 5, paint);
		}
		x = 0;
		y = 0;
        PathEffect effects = new DashPathEffect(new float[] {35, 15, 35, 15}, 1);
        paint.setPathEffect(effects);
		paint.setColor(Color.GREEN);
		paint.setTextSize(30);
		for (i = 0; i < len; i++) {
			int duration = getDuration(data[i].time.substring(0, 10), min_datetime);
			float _x = getCordinate(contentWidth * (float)0.8, (float)duration / max_duration, canvasWidthOffset + contentWidth * (float)0.1);
			float _y = getCordinate(contentHeight * (float)0.8, (max_tot - (float)data[i].finalTotal) / (max_tot - min_tot), contentHeight * (float)0.1);
			if (x != 0 || y != 0) {
				Path path = new Path(); 
				path.moveTo(x, y); 
				path.lineTo(_x, _y); 
				canvas.drawPath(path, paint);
			}
			x = _x;
			y = _y;
			canvas.drawCircle(x, y, 5, paint);
		}
		paint.setStyle(Paint.Style.FILL);
		contentHeight += canvasHeightOffset;
		paint.setColor(0xff00ffff);
		paint.setTextSize(25);
		float tot[];
		tot = new float[len * 2];
		for (i = 0; i < len; i++) {
			tot[i*2] = (float)data[i].finalTotal;
			tot[i*2+1] = (float)data[i].firstTotal;
			int duration = getDuration(data[i].time.substring(0, 10), min_datetime);
			float _x = getCordinate(contentWidth * (float)0.8, (float)duration / max_duration, canvasWidthOffset + contentWidth * (float)0.1);
			if (i != 0 && duration * 0.8 * contentWidth / max_duration < paint.measureText(data[i].time.substring(5, 10)) * 1.5 + 10) {
				continue;
			}
			if (i != len - 1 && getDuration(data[i].time.substring(0, 10), max_datetime) * 0.8 * contentWidth / max_duration < paint.measureText(data[i].time.substring(5, 10)) * 1.5 + 10) {
				continue;
			}
			if (i == 0 || i == len - 1 || getDuration(data[i].time.substring(0, 10), data[i-1].time.substring(0, 10)) * 0.8 * contentWidth / max_duration > paint.measureText(data[i].time.substring(5, 10)) + 10) {
				if (i == 0||i == len - 1) {
					canvas.drawText(data[i].time.substring(0, 10), _x - paint.measureText(data[i].time.substring(0, 10)) / 2, contentHeight, paint);
				}
				else { 
					canvas.drawText(data[i].time.substring(5, 10), _x - paint.measureText(data[i].time.substring(5, 10)) / 2, contentHeight, paint);
				}
			}
		}
		for (i = 0; i < len * 2 - 1; i++) {
			for (int j = i + 1; j < len * 2; j++) {
				if (tot[i] > tot[j]) {
					tot[i] += tot[j];
					tot[j] = tot[i] - tot[j];
					tot[i] -= tot[j];
				}
			}
		}
		contentHeight -= canvasHeightOffset;
		len *= 2;
		i = 0;
		while (i < len - 1) {
			if (tot[i] == tot[i+1]) {
				int j = i+1;
				while (j < len-1) {
					tot[j] = tot[j+1];
					j++;
				}
				len--;
			}
			else {
				i++;
			}
 		}		
		for (i = 0; i < len; i++) {
			float _y = getCordinate(contentHeight * (float)0.8, (max_tot - tot[i]) / (max_tot - min_tot), contentHeight * (float)0.1);
			if (i == len - 2 && (tot[i+1] - tot[i]) * contentHeight * (float)0.8 / (max_tot - min_tot) < 30) {
				continue;
			}
			if (i == 0 || i == len - 1 || (tot[i] - tot[i-1]) * contentHeight * (float)0.8 / (max_tot - min_tot) > 30) {
				canvas.drawText(Float.toString(tot[i]), canvasWidthOffset - 10 - paint.measureText(Float.toString(tot[i])), _y + (float)12.5, paint);
			}
		}
	}
	
	private SDAccount[] mergeAccount(SDAccount[] data) {
		int len = data.length;
		int i = 0;
		//Merge account with same time field
		while (i < len - 1) {
			if (data[i].time.substring(0,10).equals(data[i+1].time.substring(0,10))) {
				data[i].firstTotal += data[i+1].firstTotal;
				data[i].finalTotal += data[i+1].finalTotal;
				int j = i+1;
				while (j < len-1) {
					data[j] = data[j+1];
					j++;
				}
				len--;
			}
			else {
				i++;
			}
 		}
		SDAccount result[] = new SDAccount[len];
		SDAccount tmpAccount;
		for (i = 0; i < len; i++) {
			result[i] = data[i];
		}
		for (i = 0; i < len - 1; i++) {
			for (int j = i + 1; j < len; j++) {
				if (result[i].time.compareTo(result[j].time) > 0) {
					tmpAccount = result[i];
					result[i] = result[j];
					result[j] = tmpAccount;
				}
			}
		}
		return result;
	}

	private void setBound(SDAccount[] data) throws ParseException {
		Date min_date, max_date;
		max_date = df.parse("1899-12-31");
		min_date = df.parse("2999-12-31");
		min_tot = 1000000;
		max_tot = 0;
		Date tmp;
		int len = data.length;
		int i = 0;
		for (i = 0; i < len; i++) {
			SDAccount account = data[i];
			tmp = df.parse(account.time.substring(0,10));
			if (tmp.before(min_date)) min_date = tmp;
			if (tmp.after(max_date)) max_date = tmp;
			if (account.firstTotal > max_tot) max_tot = (float)account.firstTotal;
			if (account.finalTotal < min_tot) min_tot = (float)account.finalTotal;
		}

		min_datetime = new DateTime(min_date);
		max_datetime = new DateTime(max_date);
		//check if only need to draw 1 point
		if (len == 1) {
			min_datetime = min_datetime.minusDays(1);
			max_datetime = max_datetime.plusDays(1);
		}
		if (min_tot == max_tot) {
			min_tot -= 1;
			max_tot += 1;
		}
		max_duration = Days.daysBetween(min_datetime, max_datetime).getDays();
	}
	
	private int getDuration(String str, DateTime dt) throws ParseException  {
		int result = Days.daysBetween(dt, new DateTime(df.parse(str))).getDays();
		return Math.abs(result);
	}
	
	private int getDuration(String str1, String str2) throws ParseException {
		int result = Days.daysBetween(new DateTime(df.parse(str1)), new DateTime(df.parse(str2))).getDays();
		return Math.abs(result);
	}
	
	private float getCordinate(float axisLength , float ratio, float offset) {
		return offset + axisLength * ratio;
	}
}
