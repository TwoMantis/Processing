package com.luzenly.visualizer.util;

import com.luzenly.visualizer.MusicVisualizer;
import com.luzenly.visualizer.kaleidoscope.KaleidoscopeUtil;

public class TransitionAnimator{
	
	boolean started = false;
	boolean starting = true;
	boolean closed = false;
	int initValue = 0;
	int closeValue = 0;
	int fftFix = 0;
	int transitionRate = 20;
	KaleidoscopeUtil k;
	Color bg;
	Point center;
	MusicVisualizer visualizer;
	
	public TransitionAnimator(MusicVisualizer v, Color c, int fftFix){
		this.visualizer = v;
		this.bg = c;
		this.fftFix = fftFix;
		int closePointRandom = (int)(Math.random() * 5) + 4;
		center = new Point(v.width/closePointRandom + 200, v.height/closePointRandom + 200);
		k = new KaleidoscopeUtil(visualizer, Color.getRandomColor(), (int)(Math.random() * 200) + 15,
				new Point((int)(Math.random() * 310), (int)(Math.random() * 640)),
				(int)(Math.random() * closePointRandom) + 3);
	}
	
	public void open(float[] spectrum, InitializeModes mode){
		if(!started){
			visualizer.fill(bg);
			
			if(starting){
				switch(mode){
					case UP_TO_DOWN:
					case RIGHT_TO_LEFT:
					case LEFT_TO_RIGHT:
						initValue = 0;
						break;
					default:
						initValue = visualizer.width;
				}
				starting = false;
			}
			visualizer.noStroke();
			switch(mode){
			case UP_TO_DOWN:
				visualizer.rect(0, initValue, visualizer.width, visualizer.height);
	    		initValue += Math.max(Math.min(transitionRate, (int)(spectrum[0]*fftFix)),transitionRate);
	    		if(initValue > visualizer.width){
	    			started = true;
	    		}
				break;
			case LEFT_TO_RIGHT:
				visualizer.rect(initValue, 0, visualizer.width, visualizer.height);
				initValue += Math.max(Math.min(transitionRate, (int)(spectrum[0]*fftFix)),transitionRate);
	    		if(initValue > visualizer.width){
	    			started = true;
	    		}
				break;
				
			case RIGHT_TO_LEFT:
				visualizer.rect(initValue, 0, visualizer.width, visualizer.height);
				initValue -= Math.max(Math.min(transitionRate, (int)(spectrum[0]*fftFix)),transitionRate);
	    		if(initValue > visualizer.width){
	    			started = true;
	    		}
				break;
				
			case DOWN_TO_UP:
	    		visualizer.rect(0, 0, visualizer.width, initValue);
	    		initValue -= Math.max(Math.min(transitionRate, (int)(spectrum[0]*fftFix)),transitionRate);
	    		if(initValue < 0){
	    			started = true;
	    		}
				break;
			case OUT_TO_IN:
				visualizer.ellipse(visualizer.width/2, visualizer.height/2, initValue, initValue);
	    		initValue -= Math.max(Math.min(transitionRate, (int)(spectrum[0]*fftFix)),transitionRate);
	    		if(initValue < 0){
	    			started = true;
	    		}
				break;
			default:
				initValue = visualizer.width;
		}
		}
	}

	public boolean closeWithKaleidoscope(float[] spectrum, Color transColor) {
		if(!closed){
			if(closeValue > 1000){
				closed = true;
	    	}
			visualizer.noStroke();
	    	closeValue += Math.max(Math.min(20, (int)(spectrum[0]*fftFix)), 20);
			k.renderKaleidoscopePoint(transColor, center, closeValue, visualizer);
		}
		return !closed;
	}
}
