package com.javangon.virtualbilliards;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View implements OnGestureListener {

	public static final String TAG = "DrawView"; 
	
	private int friction = 5;
	private float scale = 1.0f; 
	
	private float xmin = 0.0f;
	private float xmax;
	
	private float ymin = 0.0f;
	private float ymax;
	
	private Paint bgColor;
	
	private PointF m1, m2;
	private GestureDetector gd; 
	
	private List<Ball> ballList = new ArrayList<Ball>();
	
	public DrawView(Context context) {
		super(context);
	}

	public DrawView(Context c, AttributeSet attrs) {
		super(c, attrs);
		bgColor = new Paint();
		bgColor.setColor(0xff808080);
		gd = new GestureDetector(c, this);  
	}
	
	public void incrementScale() {
		scale += .1;
	}
	
	public void decrementScale() {
		scale -= .1;
	}
	
	public void clearTable() {
		ballList.clear();
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		return gd.onTouchEvent(event);	
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawPaint(bgColor);
		xmax = (float) canvas.getWidth();
		ymax = (float) canvas.getHeight();
		Paint p = new Paint();
		p.setARGB(200, 255, 0, 0);
		p.setStrokeWidth(3);
		
		//Draw all balls
		for(Ball ball : ballList) {
			canvas.drawCircle(ball.xpos, ball.ypos, ball.radius, p);
			ball.xpos = scale * ball.xvel + ball.xpos;
			ball.ypos = scale * ball.yvel + ball.ypos;
			if (ball.xvel > -friction && ball.xvel < friction) 
				ball.xvel = 0f;
			else {
				if (ball.xvel < 0)
					ball.xvel += friction;
				else
					ball.xvel -= friction;
			}
			if (ball.yvel > -friction && ball.yvel < friction) 
				ball.yvel = 0f;
			else {
				if (ball.yvel < 0)
					ball.yvel += friction;
				else
					ball.yvel -= friction;
			}
			
			//Detect collision with left side of table
			if(ball.xpos <= ball.radius && ball.xvel < 0)
				ball.xvel *= -1.0f;
			
			//Detect collision with right side of table
			if(ball.xpos >= (xmax - ball.radius) && ball.xvel > 0)
				ball.xvel *= -1.0f;
			
			//Detect collision with top of table
			if(ball.ypos <= ball.radius && ball.yvel < 0)
				ball.yvel *= -1.0f;
			
			//Detect collision with bottom of table
			if(ball.ypos >= (ymax - ball.radius) && ball.yvel > 0)
				ball.yvel *= -1.0f;
		}
		
		//Calculate update all balls position
		
		
	}

	@Override
	public boolean onDown(MotionEvent e) {
		Log.i(TAG, "touch down: (" + e.getX() + "," + e.getY() + ")"); 
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		Log.i(TAG, "long press: (" + e.getX() + "," + e.getY() + ")"); 
		Ball b = new Ball();
		b.xpos = e.getX();
		b.ypos = e.getY();
		
		ballList.add(b);
//		invalidate(); 
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		Log.i(TAG, "touch scroll: (" + distanceX + "," + distanceY + ")"); 
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return true;
	}
	
}
