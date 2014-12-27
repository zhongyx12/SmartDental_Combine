package com.edu.thss.smartdental.ui.alarmclock;

import com.edu.thss.smartdental.OnedayAlarmClockActivity.AlarmClockItem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;


public class ListViewCompat extends ListView{
	private static final String TAG = "ListViewCompat";

    private SlideView mFocusedItemView;
    public int mFocusedPosition;

    public ListViewCompat(Context context) {
        super(context);
    }

    public ListViewCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewCompat(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void shrinkListItem(int position) {
        View item = getChildAt(position);

        if (item != null) {
            try {
                ((SlideView) item).shrink();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int position = pointToPosition(x, y);
            if (position != INVALID_POSITION) {
            	AlarmClockItem data = (AlarmClockItem) getItemAtPosition(position);
                mFocusedItemView = data.slideView;
                mFocusedPosition = position;
            }
        }
        default:
            break;
        }

        if (mFocusedItemView != null) {
            mFocusedItemView.onRequireTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }
}
