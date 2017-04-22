package com.luzenly.visualizer.kaleidoscope;

import com.luzenly.visualizer.MusicVisualizer;
import com.luzenly.visualizer.util.Animation;
import com.luzenly.visualizer.util.Color;
import com.luzenly.visualizer.util.InitializeModes;
import com.luzenly.visualizer.util.Point;
import com.luzenly.visualizer.util.RythmChangeDetector;
import com.luzenly.visualizer.util.TransitionAnimator;

import processing.sound.FFT;

public class KaleidoscopeAnimation implements Animation{

	boolean animating = false;
	boolean transitioning = false;
	KaleidoscopeUtil k[];
	KaleidoscopeUtil transK = null;
	int count = 0;
	int size = 0;
	int fftFix= 300;
	int initValue = 0;
	boolean initialized = false;
	boolean initializing = true;
	InitializeModes mode = getRandomInitMode();
	MusicVisualizer visualizer;
	Color bg = Color.BLACK;
	Color transColor = Color.getRandomColor();
	RythmChangeDetector rythm = null;
	Point center;
	int transSize = 0;
	TransitionAnimator trans;
	
	@Override
	public boolean animate(FFT fft) {
		visualizer.background(bg);
    	float[] spectrum = new float[count*2];
    	fft.analyze(spectrum);		
    	animating = !rythm.rythmChanged(spectrum, 0);
    	transitioning = !animating;
    	renderKaleidoscopes(spectrum);
    	trans.open(spectrum, mode);
		return animating;
	}
	
	private void renderKaleidoscopes(float[] spectrum){
		for(int i = 0; i< count; i++){
			if(i%6 == 0)
			k[i].moveUp(Math.max(Math.min(6, (int)(spectrum[i*2]*fftFix)),1));
		else if(i%5 == 0)
			k[i].moveLeft(Math.max(Math.min(6, (int)(spectrum[i*2]*fftFix)),1));
		else if(i%4 == 0)
			k[i].moveUp(Math.max(Math.min(6, (int)(spectrum[i*2]*fftFix)),1));
		else if(i%3 == 0)
			k[i].moveLeft(Math.max(Math.min(6, (int)(spectrum[i*2]*fftFix)),1));
		else
			k[i].moveRight(Math.max(Math.min(6, (int)(spectrum[i*2]*fftFix)),1));
	}
	}
	
	@Override
	public boolean transition(FFT fft) {
		visualizer.background(bg);

		float[] spectrum = new float[count*2];
    	fft.analyze(spectrum);		
    	renderKaleidoscopes(spectrum);
    	return trans.closeWithKaleidoscope(spectrum, transColor);
	}

	@Override
	public boolean isAnimating() {
		return animating;
	}

	@Override
	public boolean isTranstitioning() {
		return transitioning;
	}

	@Override
	public void setup(MusicVisualizer p) {
		visualizer = p;
		bg = p.getBgColor();
		p.background(bg);
		count =  (int)(Math.random() * 50) + 5;
    	setUpKaleidoscopes(count);
    	animating = true;
    	rythm = new RythmChangeDetector(3, count*2);
    	center = new Point(p.width/2, p.height/2);
    	trans = new TransitionAnimator(p, bg, fftFix);
	}
	
    private void setUpKaleidoscopes(int size){
    	k = new KaleidoscopeUtil[size];
    	int circle = 300;
    	for(int i = 0; i< size; i++){
    		if(circle > 50)
    			circle-=15;
    		k[i] = new KaleidoscopeUtil(visualizer, Color.getRandomColor(), (int)(Math.random() * circle) + 15,
    				new Point((int)(Math.random() * 200), (int)(Math.random() * 300)),
    				(int)(Math.random() * 4) + 4);
    	}
    }


	@Override
	public Color getTransitionColor() {
		return transColor;
	}
	
	private InitializeModes getRandomInitMode(){
		int r = (int)(Math.random()*5);
		switch(r){
		case 0:
			return InitializeModes.LEFT_TO_RIGHT;
		case 1:
			return InitializeModes.RIGHT_TO_LEFT;
		case 2:
			return InitializeModes.UP_TO_DOWN;
		case 3:
			return InitializeModes.DOWN_TO_UP;
		case 4:
			return InitializeModes.OUT_TO_IN;
		default:
			return InitializeModes.OUT_TO_IN;
		}
	}
}
