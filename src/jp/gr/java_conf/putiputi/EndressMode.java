package jp.gr.java_conf.putiputi;

import com.google.ads.*;
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

public class EndressMode extends Activity {

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
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.endressmode);
				
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			p = (Player)getIntent().getSerializableExtra("Player");
		}

		/*----------SE�ǂݍ���----------*/
		sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0 );
		seID = new int[resID.length];
			for(int i = 0;i < resID.length;i++){
				seID[i] = sp.load(this,resID[i],1 );
			}
		vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);

		/*----------�e�L�X�g�\��----------*/
		totalView = (TextView)findViewById(R.id.TotalPuti);	
		totalView.setText("Total:" + p.getTotalpt() + "Pt");
		
		/*----------�{�^���e�[�u��----------*/
		TableLayout tableLayout = (TableLayout)findViewById(R.id.ButtonLayout);
		puti = new PutiButton[BUTTON_ROW][BUTTON_COLUMN];
		for (int iRow = 0; iRow < BUTTON_ROW; iRow++) {
			TableRow tableRow = new TableRow(this);
			tableRow.setGravity(Gravity.CENTER);
			for (int iCol = 0; iCol < BUTTON_COLUMN; iCol++) {
				puti[iRow][iCol] = new PutiButton(this, p);
				tableRow.addView(puti[iRow][iCol]);
			
				/*----------�{�^���ݒ�----------*/
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
		
	    // adView ���쐬����
	    adView = new AdView(this, AdSize.BANNER, "a15140623641dbe");

	    // ���� android:id="@+id/mainLayout" ���^�����Ă�����̂Ƃ���
	    // LinearLayout �����b�N�A�b�v����
	    LinearLayout layout = (LinearLayout)findViewById(R.id.AdView);

	    // adView ��ǉ�
	    layout.addView(adView);

	    // ��ʓI�ȃ��N�G�X�g���s���čL����ǂݍ���
	    adView.loadAd(new AdRequest());
	}
	
	public void Push(PutiButton pb){
		if(pb.isStatus()){
			pb.Explosion();
	    	sp.play(seID[se_num++%resID.length] , 1.0F , 1.0F , 0 , 0 , 1.0F );	//�����̍�����AID�A���X�s�[�J�[���ʁA�E�X�s�[�J�[���ʁA�D��x�A���[�v�񐔁A�Đ����x
	    	vib.vibrate(20);
	    	p.Push(this);
			totalView.setText("Total:" + p.getTotalpt() + "Pt");

		}
		
	}

	/*----------Activity�I�����C�x���g----------*/
	@Override
	public void onDestroy(){
	    super.onDestroy();

        sp.release();  

		finish();
	}
	
}
