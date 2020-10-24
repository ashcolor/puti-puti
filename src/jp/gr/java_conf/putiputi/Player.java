package jp.gr.java_conf.putiputi;

import java.io.*;
import android.util.Log;
import android.content.Context;

class Player implements Serializable{

	static final String FILE_NAME = "playerdata2.bat";
	private int totalpt;
	private int ownpt;
	private int highscore[];
	
    public Player(){
		totalpt = 0;
		highscore = new int[10];
		for(int i=0;i<highscore.length;i++){
			highscore[i] = 0;
		}
	}

	public int getTotalpt(){
		return totalpt;
	}
	
	public int getOwnpt(){
		return ownpt;
	}
	
	public int getHighscore(int i) {
		return highscore[i];
	}
	
	public void setTotalpt(int totalpt){
		this.totalpt = totalpt;
	}
	
	public void setOwnpt(int ownpt){
		this.ownpt = ownpt;
	}
	
	public void Result(int score,Context context) {
		for(int i=0;i<highscore.length;i++){
			if(highscore[i]<score){
				int tmp = highscore[i];
				highscore[i] = score;
				score = tmp;
			}
		}
		Save(context);
	}
	
	public void Change(int x,int y){
		int tmp = x;
		x = y;
		y = tmp;
	}
	
	public void Push(Context context){
		setTotalpt(getTotalpt() + 1 );
		setOwnpt(getOwnpt() + 1 );
		Save(context);
	}
	
	
	/*----------データ保存----------*/
	public int Save(Context context){
		try {
			//FileOutputStream outFile = new FileOutputStream(FILE_NAME);
			FileOutputStream outFile = context.openFileOutput(FILE_NAME, 0);
			ObjectOutputStream outObject = new ObjectOutputStream(outFile);
			outObject.writeObject(this);
			outObject.close();
			outFile.close();
		} catch (FileNotFoundException e) {
//			LogMng.PrintException(e, "");
		} catch (IOException e) {
//			LogMng.PrintException(e, "");
		}
		return 0;
	}
	/*----------データ保存----------*/
	/*----------データ読み込み----------*/
	public Player Load(Context context){
		Player p = this;
		try {
			//FileInputStream inFile = new FileInputStream(FILE_NAME);
			FileInputStream inFile = context.openFileInput(FILE_NAME);
			ObjectInputStream inObject = new ObjectInputStream(inFile);
			p = (Player)inObject.readObject();
			inObject.close();
			inFile.close();
		} catch (FileNotFoundException e) {
//			LogMng.PrintException(e, "");
		} catch (StreamCorruptedException e) {
//			LogMng.PrintException(e, "");
		} catch (IOException e) {
//			LogMng.PrintException(e, "");
		} catch (ClassNotFoundException e) {
//			LogMng.PrintException(e, "");
		}	
		return p;
	}
	/*----------データ読み込み----------*/
}