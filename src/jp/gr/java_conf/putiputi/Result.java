package jp.gr.java_conf.putiputi;

import com.google.ads.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.ClipData;
import android.os.Bundle;
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

public class Result extends Activity {

	AdView adView;
	Player p;
	int score;
	TextView totalView;
	TextView resultView;
	TextView newrecordView;
	TextView highscoreView[];
	
	LinearLayout HighScoreLayout;

	String f = "  ";
	
	@SuppressLint("DefaultLocale")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
				
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			p = (Player)getIntent().getSerializableExtra("Player");
		}
		
		p.Result(score,this);
		
		Intent intent = getIntent();
		score = intent.getIntExtra("Result", 0 );
		
		p.Result(score,this);

		/*----------テキスト表示----------*/
		totalView = (TextView)findViewById(R.id.TotalPuti);	
		totalView.setText("Total:" + p.getTotalpt() + "Pt");

		resultView = (TextView)findViewById(R.id.ResultView);	
		resultView.setText(score + "Pt");
		
		if(p.getHighscore(9) < score){
			newrecordView = (TextView)findViewById(R.id.NewRecord);	
			newrecordView.setText("NewRecord");
		}
		
		
		highscoreView = new TextView[10];
	    HighScoreLayout = (LinearLayout)findViewById(R.id.HighScoreLayout);
		for(int i=0;i < 10;i++){
			if(score == p.getHighscore(i)){f = "★";}
			else{f = "    ";}
			String s=String.format("%3d: %s%4dPt",i+1,f,p.getHighscore(i));
			highscoreView[i] = new TextView(this);
			highscoreView[i].setTextSize(24.0f);
			highscoreView[i].setText(s);
		    HighScoreLayout.addView(highscoreView[i]);
		}
		
		
	    // adView を作成する
	    adView = new AdView(this, AdSize.BANNER, "a15140623641dbe");

	    // 属性 android:id="@+id/mainLayout" が与えられているものとして
	    // LinearLayout をルックアップする
	    LinearLayout Adlayout = (LinearLayout)findViewById(R.id.AdView);

	    // adView を追加
	    Adlayout.addView(adView);

      // 一般的なリクエストを行って広告を読み込む
	    adView.loadAd(new AdRequest());
	}
	

	/*----------Activity終了時イベント----------*/
	@Override
	public void onDestroy(){
	    super.onDestroy();

		finish();
	}
	
}
