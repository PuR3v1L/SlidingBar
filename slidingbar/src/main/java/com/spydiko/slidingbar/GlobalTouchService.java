package com.spydiko.slidingbar;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by jim on 7/2/2014.
 */
public class GlobalTouchService extends Service implements View.OnTouchListener {
	private String TAG = this.getClass().getSimpleName();
	// window manager
	private WindowManager mWindowManager;
	// linear layout will use to detect touch event
	private LinearLayout touchLayout;
	private SlidingMenu slidingMenu;
	private MyApplication myApplication;
	private LayoutInflater li;
	private View myview;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// create linear layout
		touchLayout = new LinearLayout(this);
		// set layout width 30 px and height is equal to full screen
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(30, LinearLayout.LayoutParams.MATCH_PARENT);
		touchLayout.setLayoutParams(lp);
		// set color if you want layout visible on screen
		//  touchLayout.setBackgroundColor(Color.CYAN);
		// set on touch listener
		touchLayout.setOnTouchListener(this);

		myApplication = (MyApplication) getApplication();

		// fetch window manager object
		mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		// set layout parameter of window manager
		WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
				30, // width of layout 30 px
				30, // height is equal to full screen
				WindowManager.LayoutParams.TYPE_PHONE, // Type Phone, These are non-application windows providing user interaction with the phone (in particular incoming calls).
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // this window won't ever get key input focus
				PixelFormat.TRANSLUCENT);
		mParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
		Log.i(TAG, "add View");

		mWindowManager.addView(touchLayout, mParams);

	}


	@Override
	public void onDestroy() {
		if (mWindowManager != null) {
			if (touchLayout != null) mWindowManager.removeView(touchLayout);
		}
		super.onDestroy();
	}

	public void showToast(String what) {
		mWindowManager.removeView(myview);
		myview=null;
		Toast.makeText(this, "I see you clicked " + what + " button...", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
			if (myview == null) {
				Log.i(TAG, "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :" + event.getRawY());
				li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				WindowManager.LayoutParams params = new WindowManager.LayoutParams(
						//WindowManager.LayoutParams.TYPE_INPUT_METHOD |
						//					WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,// | WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
						WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
						WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
						PixelFormat.TRANSLUCENT);

				params.gravity = Gravity.RIGHT | Gravity.TOP;

				myview = li.inflate(R.layout.testing_layout, null);
				myview.setOnTouchListener(this);
				Button mybutton = (Button) myview.findViewById(R.id.button1);
				mybutton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						showToast("first");
					}
				});

				Button mybutton1 = (Button) myview.findViewById(R.id.button2);
				mybutton1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						showToast("second");
					}
				});

				Button mybutton2 = (Button) myview.findViewById(R.id.button3);
				mybutton2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						showToast("third");
					}
				});

				mWindowManager.addView(myview, params);
			} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Log.i(TAG, "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :" + event.getRawY());
				View popup = myview.findViewById(R.id.popup_window);
				Log.i(TAG, "myview.getWidth() : " + popup.getWidth());
				Log.i(TAG, "VIEW : " + v.getId());

				if ((int) event.getRawX() > popup.getWidth()) {
					mWindowManager.removeView(myview);
					myview = null;
				}
			}
		}


		return true;
	}
}
