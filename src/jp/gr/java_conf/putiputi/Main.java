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
		

		
		/*----------�v���C���[�ݒ�----------*/
		p = new Player();
		File file = this.getFileStreamPath("playerdata2.bat");  
		if(file.exists()){
			p = p.Load(this);
		}else{
			p.Save(this);
		}
		
		/*----------��ʐݒ�----------*/
		totalView = (TextView)findViewById(R.id.TotalPutiMain);	
		totalView.setText("���Ȃ��͍��܂�\n" + p.getTotalpt() + "��\n�Ղ��Ղ����܂����B");
		
		tweetButton = (ImageButton)findViewById(R.id.Tweet);
		tweetButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					String s = String.valueOf(p.getTotalpt());
					Uri uri = Uri.parse("http://twitter.com/intent/tweet?text=" + "���Ȃ��͍��܂�" + s + "��Ղ��Ղ����܂����B http://bit.ly/12SxOmR %23�Ղ��Ղ�");
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
		
	    // adView ���쐬����
	    adView = new AdView(this, AdSize.BANNER, "a15140623641dbe");

	    // ���� android:id="@+id/mainLayout" ���^�����Ă�����̂Ƃ���
	    // LinearLayout �����b�N�A�b�v����
	    LinearLayout layout = (LinearLayout)findViewById(R.id.AdView);

	    // adView ��ǉ�
	    layout.addView(adView);

	    // ��ʓI�ȃ��N�G�X�g���s���čL����ǂݍ���
	    AdRequest adRequest = new AdRequest();
//	    adRequest.addTestDevice(AdRequest.TEST_EMULATOR);               // �G�~�����[�^
//	    adRequest.addTestDevice("9BFD94EC73B95B1151803FD737D8AA5B");    // Android �[�����e�X�g

	    adView.loadAd(adRequest);

	}
	
    public void onRestart(){
        super.onRestart();
        
		p = p.Load(this);
		totalView.setText("���Ȃ��͍��܂�\n" + p.getTotalpt() + "��\n�Ղ��Ղ����܂����B");
	}
    
    @Override
    public void onDestroy() {
      super.onDestroy();
      adView.destroy();
    }
	
}