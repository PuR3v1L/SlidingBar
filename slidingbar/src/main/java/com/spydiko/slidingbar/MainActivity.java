package com.spydiko.slidingbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends Activity {

	private Intent globalService;
	private SlidingMenu slidingMenu;
	private MyApplication myApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		globalService = new Intent(this,GlobalTouchService.class);
		slidingMenu = new SlidingMenu(this);
		myApplication = (MyApplication) getApplication();
		myApplication.setUseActivity(this);
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.slidingmenu_shadow);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//		slidingMenu.setAboveOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		slidingMenu.setMenu(R.layout.sliding_menu_layout);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ( keyCode == KeyEvent.KEYCODE_MENU ) {
			this.slidingMenu.toggle();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.slidingMenu.toggle();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		if ( slidingMenu.isMenuShowing()) {
			slidingMenu.toggle();
		}
		else {
			super.onBackPressed();
		}
	}

	public void buttonClicked(View v){

		if(v.getTag() == null){
			startService(globalService);
			v.setTag("on");
			Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
		}
		else{
			stopService(globalService);
			v.setTag(null);
			Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
		}

	}

}
