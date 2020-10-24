package jp.gr.java_conf.putiputi;

import com.google.ads.*;

import android.app.Activity;
import android.content.Intent;
import android.content.ClipData;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.media.*;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import android.view.View.DragShadowBuilder;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.*;

public class TimeAttackMode extends Activity {
	
	AdView adView;
	Player p;
	TextView totalView;
	PutiButton[][] puti;
	static int BUTTON_ROW = 7;
	static int BUTTON_COLUMN = 6;
	
	SoundPool sp;
	int resID[] = {R.raw.puti1,R.raw.puti2,R.raw.puti3};
	int seID[];
	int se_num = 0;
	
	Vibrator vib;
	
	Handler  mHandler;

	TextView timeView;
	TextView scoreView;
	TextView countView;
	
	boolean active;
	
	double count;
	double time;
	int score;
	
	Timer timer;

		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeattackmode);

				
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			p = (Player)getIntent().getSerializableExtra("Player");
		}
		
        count = 3.0;
		time = 10.0;
		score = 0;
		
		active = false;
		
		mHandler   = new Handler();        //Handlerのインスタンス生成

		/*----------SE読み込み----------*/
		sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0 );
		seID = new int[resID.length];
			for(int i = 0;i < resID.length;i++){
				seID[i] = sp.load(this,resID[i],1 );
			}
		vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);

		/*----------テキスト表示----------*/
		totalView = (TextView)findViewById(R.id.TotalPuti);	
		totalView.setText("Total:" + p.getTotalpt() + "Pt");
		
		timeView = (TextView)findViewById(R.id.Time);	
		
		scoreView = (TextView)findViewById(R.id.Score);	

		countView = (TextView)findViewById(R.id.Count);	
				
		/*----------ボタンテーブル----------*/
		TableLayout tableLayout = (TableLayout)findViewById(R.id.ButtonLayout);
		puti = new PutiButton[BUTTON_ROW][BUTTON_COLUMN];
		for (int iRow = 0; iRow < BUTTON_ROW; iRow++) {
			TableRow tableRow = new TableRow(this);
			tableRow.setGravity(Gravity.CENTER);
			for (int iCol = 0; iCol < BUTTON_COLUMN; iCol++) {
				puti[iRow][iCol] = new PutiButton(this, p);
				tableRow.addView(puti[iRow][iCol]);
			
				/*----------ボタン設定----------*/
				puti[iRow][iCol].setOnDragListener(new View.OnDragListener() {
					@Override
			        public boolean onDrag(View v, DragEvent event) {
						if (event.getAction() == DragEvent.ACTION_DRAG_LOCATION) {
							PutiButton pb = (PutiButton)v;
							Push(pb);
					}
						return true;
					}
				});

				puti[iRow][iCol].setOnTouchListener(new View.OnTouchListener() {
				    @Override
				    public boolean onTouch(View v, MotionEvent event) {
				            v.startDrag(null, new DragShadowBuilder(), null, 0);
				            PutiButton pb = (PutiButton)v;
							Push(pb);
				            return true;
				    }
				});
			}
			tableLayout.addView(tableRow);
		}    

		Button renew = (Button)findViewById(R.id.Renew);
		renew.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int iRow = 0; iRow < BUTTON_ROW; iRow++) {
					for (int iCol = 0; iCol < BUTTON_COLUMN; iCol++) {
						puti[iRow][iCol].Recover();

					}
				}
			}			 
		});
		
	    // adView を作成する
	    adView = new AdView(this, AdSize.BANNER, "a15140623641dbe");

	    // 属性 android:id="@+id/mainLayout" が与えられているものとして
	    // LinearLayout をルックアップする
	    LinearLayout layout = (LinearLayout)findViewById(R.id.AdView);

	    // adView を追加
	    layout.addView(adView);

	    // 一般的なリクエストを行って広告を読み込む
	    adView.loadAd(new AdRequest());
	    
		
        timer = new Timer(true);
        timer.schedule( new TimerTask(){
            @Override
            public void run() {
                // mHandlerを通じてUI Threadへ処理をキューイング
                mHandler.post( new Runnable() {
                    public void run() {
		                if(getCount() > 0){
		                	countView.setText((int)Math.ceil(getCount()) + "");
		                	setCount(getCount()-0.01);
		                	if(getCount() <= 0){
			               		active = true;
			                	countView.setText("");
		                	}
		               	}else if(getTime() > 0){
		                	setTime(getTime()-0.01);
		                	timeView.setText("Time:" + (Math.floor(getTime()*100))/100);
		                	if(getTime() <= 0){
		                        timer.cancel();
		                        timer.purge();

		                		Intent intent = new Intent(TimeAttackMode.this, Result.class);
		                		intent.putExtra("Player", p);
		                		intent.putExtra("Result", getScore());
		                		startActivity(intent);
		                		onDestroy();

		                		
		                	}
		               	}
                    }
                });
            }
        }, 0, 10);


	}
	
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void Push(PutiButton pb){
		if(pb.isStatus() && active == true){
			pb.Explosion();
	    	sp.play(seID[se_num++%resID.length] , 1.0F , 1.0F , 0 , 0 , 1.0F );	//引数の左から、ID、左スピーカー音量、右スピーカー音量、優先度、ループ回数、再生速度
	    	vib.vibrate(20);
	    	p.Push(this);
			totalView.setText("Total:" + p.getTotalpt() + "Pt");
			setScore(getScore()+1);
			scoreView.setText("Score:" + getScore());
		}
		
	}
	

	


	/*----------Activity終了時イベント----------*/
	@Override
	public void onDestroy(){
	    super.onDestroy();

        sp.release();  
        



		
		finish();
	}
	
}
