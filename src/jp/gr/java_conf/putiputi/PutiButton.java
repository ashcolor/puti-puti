package jp.gr.java_conf.putiputi;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.view.Display;
import android.view.WindowManager;

class PutiButton extends ImageView {
	
	private Player p;
	private boolean status = true;
	
	public PutiButton(Context context, Player p) {
		super(context);
		this.p = p;
		setImageResource(R.drawable.expand);
        setScaleType(ImageView.ScaleType.FIT_CENTER);

	}

	public PutiButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PutiButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void Explosion(){
		setStatus(false);
		setImageResource(R.drawable.explosion);
	}
	
	public void Recover(){
		setStatus(true);
		setImageResource(R.drawable.expand);
	}
	
}
