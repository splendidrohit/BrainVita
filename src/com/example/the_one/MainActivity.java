package com.example.the_one;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;



public class MainActivity extends Activity {

	button[][] bt;
	LinearLayout l1,l,rg,k;
	button last,des,temp,killer,killed,current;
	private int score;
	TextView sview,view,hview;
	RotateAnimation rot,rot1;
	PopupWindow w;
	Button b1,b2,b3;
	ImageButton i1;
	Button ihelp;
	int r,c;
	AlertDialog.Builder d;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		last=null;
		score=32;
		
		
		setContentView(R.layout.activity_main);
		l1= new LinearLayout(this);
		l1.setOrientation(1);
		
		l1.setBackgroundResource(R.drawable.mar);
		l1.setLayoutParams(new LayoutParams(10,10));
		setpop();
		 w=new PopupWindow(l1,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		 w.setContentView(l1);
		 
		 rg=(LinearLayout)findViewById(R.id.rg);
		 k=(LinearLayout)findViewById(R.id.main);
		 ihelp=(Button)findViewById(R.id.image1);
		 ihelp.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				w.showAsDropDown(k,0,0);
				 
			}
		});
		 
		
		sview=(TextView)findViewById(R.id.score);
		view=(TextView)findViewById(R.id.over);
		((LinearLayout)findViewById(R.id.mn)).setBackgroundResource(R.drawable.bg);
		rot=new RotateAnimation(0,360,35.0f,35.0f);
		rot1=new RotateAnimation(0,25,0.0f,35.0f);
		rot.setRepeatCount(Animation.INFINITE);
		rot1.setRepeatCount(Animation.INFINITE);
		rot.setRepeatMode(Animation.RESTART);
		rot1.setRepeatMode(Animation.REVERSE);
		rot.setDuration(1000);
		rot1.setDuration(500);
		
		((Button)findViewById(R.id.reset)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				
				alert();
			}
			
		});
		
		((Button)findViewById(R.id.undo)).setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v)
			{
				
				killer.id=2;
				killed.id=2;
				current.id=0;
				//killer.setClickable(true);
				//killed.setClickable(true);
				//killer.setBackgroundResource(R.drawable.marble);
				//killed.setBackgroundResource(R.drawable.marble);
				current.setBackgroundResource(R.drawable.disable);
				
				current.setClickable(false);
				((Button)findViewById(R.id.undo)).setClickable(false);
				//reset();
			}
			
		});
		set();
		ihelp.startAnimation(rot1);
		((Button)findViewById(R.id.undo)).setClickable(false);
		
	}
	
	public void alert()
	{
		new AlertDialog.Builder(this)
	    .setTitle("Reset Game")
	    .setMessage("Are you sure to reset game ?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	for(int i=0;i<7;i++)
	    		{
	    			for(int j=0;j<7;j++)
	    			{
	    				if(bt[i][j]!=null)
	    				{
	    					((ViewGroup)bt[i][j].getParent()).removeView(bt[i][j]);
	    				}
	    			}
	    		}
	            set();
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            dialog.cancel();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
	
	@Override
    public void onBackPressed()
	{
		new AlertDialog.Builder(this)
	    .setTitle("Exit !!!")
	    .setMessage("Are you sure to EXIT ?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	finish();
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            dialog.cancel();
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
		
	}
	
	public void set(){
		
		
		
		bt=null;
		
		view.setText("GOOD LUCK..");
		view.startAnimation((Animation)AnimationUtils.loadAnimation(this,R.anim.swap));
		bt=new button[7][7];
		score=32;
		sview.setText(String.valueOf(score));
        for(int i=0;i<7;i++)
        {
        	l=new LinearLayout(this);
        	
        	for(int j=0;j<7;j++)
        		{if((i>1 && i<5) || (j>1 && j<5))
        			{bt[i][j]=new button(this);
        			bt[i][j].id=2;
        			bt[i][j].i=i;
        			bt[i][j].j=j;
        			bt[i][j].setLayoutParams(new LayoutParams(70,LayoutParams.WRAP_CONTENT));
        			bt[i][j].setBackgroundResource(R.drawable.marble);
        			bt[i][j].setOnClickListener(new OnClickListener(){
        				@Override
        				public void onClick(View v)
        				{
        					
        					if(last!=null)
        						last.clearAnimation();
        					button b=(button)v;
        					if(b.id==2)
        					{
        						if(last!=null)
        						last.setBackgroundResource(R.drawable.marble);
        						b.setBackgroundResource(R.drawable.marble);
        						b.startAnimation(rot);
        						last=b;
        						reset();
        						findit(b);
        					}
        					else if(b.id==1)
        					{
        						
        						if(last.i==b.i)
        							
        							if(last.j>b.j)
        								des=bt[last.i][last.j-1];
        							else
        								des=bt[last.i][last.j+1];
        						else if(last.j==b.j)
        							if(last.i>b.i)
        								des=bt[last.i-1][last.j];
        							else
        								des=bt[last.i+1][last.j];        						
        						
        						b.setBackgroundResource(R.drawable.marble);
        						b.id=2;
        						last.id=0;
        						last.setBackgroundResource(R.drawable.disable);
        						last.setClickable(false);
        						des.id=0;
        						rot.cancel();
        						des.setBackgroundResource(R.drawable.disable);
        						des.setClickable(false);
        						reset();
        						last=null;
        						update();
        						killer=last;
        						killed=des;
        						current=b;
        						((Button)findViewById(R.id.undo)).setClickable(true);
        						
        					}
        				}
        			});
        			l.addView(bt[i][j]);
  
        			}
        		else
        			bt[i][j]=null;
        		}
        	
        	if(i<2)
        		((LinearLayout)findViewById(R.id.l1)).addView(l);
        	else if(i<5)
        		((LinearLayout)findViewById(R.id.l2)).addView(l);
        	else
        		((LinearLayout)findViewById(R.id.l3)).addView(l);
        	
        }
        bt[3][3].setClickable(false);
        bt[3][3].setBackgroundResource(R.drawable.disable);
        bt[3][3].id=0;
        
        
	}
	

	public void reset()
	{
		for(int i=0;i<7;i++)
			for(int j=0;j<7;j++)
				if(bt[i][j]!=null && bt[i][j].id==1)
					{bt[i][j].id=0;
					bt[i][j].setBackgroundResource(R.drawable.disable);
					bt[i][j].setClickable(false);
					}
				
			
				
	}
	public void findit(button b)
	{
		 int row=b.i;
		 int col=b.j;
		
		if(col<5 && bt[row][col+2]!=null && bt[row][col+1].id==2 && bt[row][col+2].id==0)
		{
			bt[row][col+2].id=1;
			bt[row][col+2].setClickable(true);
			bt[row][col+2].setBackgroundResource(R.drawable.focus);
			
		}
		
		if(col>=2 && bt[row][col-2]!=null && bt[row][col-1].id==2 && bt[row][col-2].id==0)
		{
			bt[row][col-2].id=1;
			bt[row][col-2].setClickable(true);
			bt[row][col-2].setBackgroundResource(R.drawable.focus);
			
		}
		if(row<5 && bt[row+2][col]!=null && bt[row+1][col].id==2 && bt[row+2][col].id==0)
		{
			bt[row+2][col].id=1;
			bt[row+2][col].setClickable(true);
			bt[row+2][col].setBackgroundResource(R.drawable.focus);
			
		}
		if(row>=2 && bt[row-2][col]!=null && bt[row-1][col].id==2 && bt[row-2][col].id==0)
		{
			bt[row-2][col].id=1;
			bt[row-2][col].setClickable(true);
			bt[row-2][col].setBackgroundResource(R.drawable.focus);
			
		}
	}
	public boolean checker(int row,int col)
	{
		if(col<5 && bt[row][col+2]!=null && bt[row][col+1].id==2 && bt[row][col+2].id==0)
		{
			return true;
		}
		
		if(col>=2 && bt[row][col-2]!=null && bt[row][col-1].id==2 && bt[row][col-2].id==0)
		{
			return true;
		}
		if(row<5 && bt[row+2][col]!=null && bt[row+1][col].id==2 && bt[row+2][col].id==0)
		{
			return true;
		}
		if(row>=2 && bt[row-2][col]!=null && bt[row-1][col].id==2 && bt[row-2][col].id==0)
		{
			return true;
		}
		return false;
	}
	public void update()
	{
		boolean flag=false;
		sview.startAnimation((Animation)AnimationUtils.loadAnimation(this,R.anim.swap1));
		sview.setText(String.valueOf(--score));
		if(score==1)
		{
			view.setText("CONGRATS ...");
			view.startAnimation((Animation)AnimationUtils.loadAnimation(this,R.anim.swap));
		}
		else
		{
		for(int i=0;i<7;i++)
		{
			for(int j=0;j<7;j++)
				{if(bt[i][j]!=null && bt[i][j].id==2)
					if(checker(i,j))
					{
						flag=true;
						break;
					}
				}
			if(flag)
			break;
		}
		if(!flag)
			disable();
		}
		
	}
	public void disable()
	{
		for(int i=0;i<7;i++)
			for(int j=0;j<7;j++)
				if(bt[i][j]!=null && bt[i][j].isClickable())
					bt[i][j].setClickable(false);
		view.setText("GAME OVER ...");
		view.startAnimation((Animation)AnimationUtils.loadAnimation(this,R.anim.swap));
	}
	public void setpop()
	{
		b1=new Button(this);
		b2=new Button(this);
		b3=new Button(this);
		l=new LinearLayout(this);
		
		
		l.setOrientation(0);
		hview=new TextView(this);
		hview.setText("MARBLE:  WHEN YOU SELECT ANY MARBLE IT START ROTATING ");
		hview.setPadding(20, 0,0,0);
		b1.setLayoutParams(new LayoutParams(70,LayoutParams.WRAP_CONTENT));
		b1.setBackgroundResource(R.drawable.marble);
		l.addView(b1);
		l.addView(hview);
		l1.addView(l);
		
		l=new LinearLayout(this);
		l.setOrientation(0);
		hview=new TextView(this);
		hview.setPadding(20, 0,0,0);
		
		hview.setText("EMPTY PLACE ...IT DOES NOT CONTAIN ANY MARBLE");
		b2.setLayoutParams(new LayoutParams(70,LayoutParams.WRAP_CONTENT));
		b2.setBackgroundResource(R.drawable.disable);
		l.addView(b2);
		l.addView(hview);
		l1.addView(l);
		
		l=new LinearLayout(this);
		l.setOrientation(0);
		hview=new TextView(this);
		hview.setPadding(20, 0,0,0);
		
		hview.setText("FOCUSED PLACE:  THIS IS PLACE AVAILABLE FOR SELECTED MARBLE");
		b3.setLayoutParams(new LayoutParams(70,LayoutParams.WRAP_CONTENT));
		b3.setBackgroundResource(R.drawable.focus);
		l.addView(b3);
		l.addView(hview);
		l1.addView(l);
		
		hview=new TextView(this);
		
		
		hview.setText("If SELECTED MARBLE goes into FOCUSED place then marble between them vanished..");
		hview.setPadding(30, 30, 0,0);
		l1.addView(hview);
		l=new LinearLayout(this);
		l.setOrientation(0);
		i1=new ImageButton(this);
		i1.setLayoutParams(new LayoutParams(54,54));
		i1.setImageResource(R.drawable.i);
		
		
		hview=new TextView(this);
		hview.setPadding(30, 10, 0,0);
		hview.setText("Your task is to keep as less as possible marbles...You Won if only one marble remain..THANK YOU");
		l1.addView(hview);
		hview=new TextView(this);
		hview.setText("About :->  ");
		l.addView(hview);
		b1=new Button(this);
		b1.setText("Got It");
		l.addView(i1);
		l1.addView(l);
		l1.addView(b1);
		d=new AlertDialog.Builder(this);
		i1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				
			    d.setTitle("RB.....")
			    .setMessage("Developed By ROHIT BHARDWAJ....")
			    .setIcon(R.drawable.my).show();
			     
			}
		});
		
		b1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				w.dismiss();
			}
		});
		
		
		
		
	}
}
