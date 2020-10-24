package jp.gr.java_conf.putiputi;

import com.google.ads.*;
import com.google.ads.AdRequest;
import java.io.*;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Main extends Activity {
	
	private AdView adView;
	private Player p;
	private TextView totalView;
	private ImageButton tweetButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		

		
		/*----------プレイヤー設定----------*/
		p = new Player();
		File file = this.getFileStreamPath("playerdata2.bat");  
		if(file.exists()){
			p = p.Load(this);
		}else{
			p.Save(this);
		}
		
		/*----------画面設定----------*/
		totalView = (TextView)findViewById(R.id.TotalPutiMain);	
		totalView.setText("あなたは今まで\n" + p.getTotalpt() + "回\nぷちぷちしました。");
		
		tweetButton = (ImageButton)findViewById(R.id.Tweet);
		tweetButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					String s = String.valueOf(p.getTotalpt());
					Uri uri = Uri.parse("http://twitter.com/intent/tweet?text=" + "あなたは今まで" + s + "回ぷちぷちしました。 http://bit.ly/12SxOmR %23ぷちぷち");
					Intent i = new Intent(Intent.ACTION_VIEW,uri);
					startActivity(i);
			}
			 
		});
				
		Button EndressButton = (Button)findViewById(R.id.EndressMode);
		EndressButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				p = p.Load(Main.this);
				Intent intent = new Intent(Main.this, EndressMode.class);
				intent.putExtra("Player", p);
				startActivity(intent);
			}
			 
		});
		
		Button TimeAttackButton = (Button)findViewById(R.id.TimeAttackMode);
		TimeAttackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				p = p.Load(Main.this);
				Intent intent = new Intent(Main.this, TimeAttackMode.class);
				intent.putExtra("Player", p);
				startActivity(intent);
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
	    AdRequest adRequest = new AdRequest();
//	    adRequest.addTestDevice(AdRequest.TEST_EMULATOR);               // エミュレータ
//	    adRequest.addTestDevice("9BFD94EC73B95B1151803FD737D8AA5B");    // Android 端末をテスト

	    adView.loadAd(adRequest);

	}
	
    public void onRestart(){
        super.onRestart();
        
		p = p.Load(this);
		totalView.setText("あなたは今まで\n" + p.getTotalpt() + "回\nぷちぷちしました。");
	}
    
    @Override
    public void onDestroy() {
      super.onDestroy();
      adView.destroy();
    }
	
}