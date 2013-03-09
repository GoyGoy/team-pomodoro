package com.pomodoro;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import com.tools.*;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TimeScheduler extends Activity {
	
	private ImageView image;
	private TimeScheduler myTimer;
	//EditText mTextField;
	static long msecondRemaining;
	static boolean update = false;
	final Handler handler = new Handler();
	TextView tv_minute,tv_second;
	MediaPlayer mp =null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		
		
		    while(mp == null){
		         mp = MediaPlayer.create(this, R.raw.alarm);
		    }
		try {
			mp.prepare();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			Toast.makeText(this, e1.getMessage(), Toast.LENGTH_LONG).show();
} catch (IOException e1) {
			// TODO Auto-generated catch block
	Toast.makeText(this, e1.getMessage(), Toast.LENGTH_LONG).show();
	}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);
		msecondRemaining = 1225000;
		
		//mTextField = (EditText) findViewById(R.id.et_timer);
		tv_minute = (TextView) findViewById(R.id.tv_minute);
		tv_second = (TextView) findViewById(R.id.tv_second);
		
		LinearLayout layout = new LinearLayout( this );
        layout.setBackgroundColor( Color.WHITE );
        layout.setPadding(50, 50, 50, 50);


    
	
			  new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					TimeScheduler.msecondRemaining = 3000;
					TimeScheduler.update = true;
					//Toast.makeText(TimeScheduler.this, String.valueOf(msecondRemaining), Toast.LENGTH_LONG).show();
					
				}
			}).start();
			
		
		
		MyCountDownTimer remainingTimeCounter = new MyCountDownTimer(msecondRemaining, 1000) {

            public void onTick(long millisUntilFinished) {
            	Long seconds = millisUntilFinished / 1000;
            	Long min = seconds/60;
            	
            	String minuteText = min<10?"0"+min.toString():min.toString();
            	
            	
            	Long sec = seconds-(min * 60);
            	
            	String secondsText = sec<10?"0"+sec.toString():sec.toString();
            	
            	tv_minute.setText(minuteText);
            	tv_second.setText(secondsText);
            	
            	//mTextField.setText("countdown");
            	
            	 if (update) {
            		 update=false;
            		 this.setMillisInFuture(msecondRemaining); // here we change the millisInFuture of our timer
            		 
                     this.start();
				}
            }

            public void onFinish() {
            	tv_minute.setText("00");
            	tv_second.setText("00");
            	// mTextField.setText("finished " );
            	
            	
                mp.start();
                
                        
            }
        }.start();
		  
        
       
        TimerTask task = new TimerTask() {
            public void run() {
            	
            	handler.post(new Runnable() {
					
					@Override
					public void run() {
						
						//sync with server
						Toast.makeText(TimeScheduler.this, "yelp", Toast.LENGTH_LONG).show();
						
					}
				});
            
        		
               
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000, 60000);
		  
	}
	
	

}

abstract class MyCountDownTimer {

    /**
     * Millis since epoch when alarm should stop.
     */
    private long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     */
    private long mCountdownInterval;

    private long mStopTimeInFuture;



    /**
     * @param millisInFuture The number of millis in the future from the call
     *   to {@link #start()} until the countdown is done and {@link #onFinish()}
     *   is called.
     * @param countDownInterval The interval along the way to receive
     *   {@link #onTick(long)} callbacks.
     */
    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    public void setMillisInFuture(long millisInFuture) {
        this.mMillisInFuture = millisInFuture;
    }

    public void setCountdownInterval(long countdownInterval) {
        this.mCountdownInterval = countdownInterval;
    }

    /**
     * Cancel the countdown.
     */
    public final void cancel() {
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     */
    public synchronized final MyCountDownTimer start() {
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }


    /**
     * Callback fired on regular interval.
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();


    private static final int MSG = 1;


    // handles counting down
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (MyCountDownTimer.this) {
                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();

                if (millisLeft <= 0) {
                    onFinish();
                } else if (millisLeft < mCountdownInterval) {
                    // no tick, just delay until done
                    sendMessageDelayed(obtainMessage(MSG), millisLeft);
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    onTick(millisLeft);

                    // take into account user's onTick taking time to execute
                    long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime();

                    // special case: user's onTick took more than interval to
                    // complete, skip to next interval
                    while (delay < 0) delay += mCountdownInterval;

                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };
}


