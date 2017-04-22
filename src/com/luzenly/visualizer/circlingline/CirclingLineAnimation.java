package com.luzenly.visualizer.circlingline;

import com.luzenly.visualizer.MusicVisualizer;
import com.luzenly.visualizer.util.Animation;
import com.luzenly.visualizer.util.Color;
import com.luzenly.visualizer.util.InitializeModes;
import com.luzenly.visualizer.util.Point;
import com.luzenly.visualizer.util.RythmChangeDetector;
import com.luzenly.visualizer.util.TransitionAnimator;

import processing.sound.FFT;

public class CirclingLineAnimation implements Animation{

	boolean animating = false;
	boolean transitioning = false;
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
	Color currentColor = Color.getRandomColor();
	RythmChangeDetector rythm = null;
	Point center;
	int transSize = 0;
	TransitionAnimator trans;
	Point[] line;
	double degrees = 0;
	int rows = (int)(Math.random() * 8) + 1;
	int columns = (int)(Math.random() * 5) + 1;
	int separationX;
	int separationY;
	int currentPoint = 1;
	int lineSize = 500;
	
	@Override
	public boolean animate(FFT fft) {
		//visualizer.background(bg);
    	float[] spectrum = new float[count*2];
    	fft.analyze(spectrum);		
    	animating = !rythm.rythmChanged(spectrum, 5);
    	transitioning = !animating;
    	renderLines(spectrum);
    	trans.open(spectrum, mode);
		return animating;
	}
	
	private void renderLines(float[] spectrum){
		visualizer.stroke(currentColor);
		visualizer.strokeWeight(5);
		
		visualizer.line(line[0].getX() + center.getX(), line[0].getY() + center.getY(), 
				line[currentPoint].getX() + center.getX(), line[currentPoint].getY()+ center.getY());
		if(currentPoint == 5){
			currentPoint = 1;
			for(int i = 0; i< line.length; i++)
				line[i] = Point.rotatePoint(line[i], degrees);
			degrees+=Math.max(10, (200 * spectrum[currentPoint]));
			if(degrees > 360){
				degrees = 0;
				lineSize*=0.8;
				setUpLines(lineSize);
				currentColor = Color.getRandomColor();
			}
		}else{
			currentPoint++;
		}

//		if(endX){
//			if(x <= 0){
//				endX = false;
//			}
//			x--;
//		}else{
//			x++;
//			if(x >= visualizer.width){
//				endX = true;
//			}
//		}
//		if(endY){
//			if(y <= 0){
//				endY = false;
//			}
//			y--;
//		}else{
//			y++;
//			if(y >= visualizer.height){
//				endY = true;
//			}
//		}
//			
	}
	
	@Override
	public boolean transition(FFT fft) {
		visualizer.background(bg);

		float[] spectrum = new float[count*2];
    	fft.analyze(spectrum);		
    	renderLines(spectrum);
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
		setUpLines(count);
    	animating = true;
    	rythm = new RythmChangeDetector(6, count*2);
    	center = new Point(p.width/2, p.height/2);
    	trans = new TransitionAnimator(p, bg, fftFix);
    	setUpLines(lineSize);
    	separationX = p.width / ( +1);
    	separationY = p.height / ( + 1);
	}
	
    private void setUpLines(int size){
//    	line = new Point[2];
//    	line[0] = new Point(size, 0);
//    	line[1] = new Point(0, size);
//    	
    	line = new Point[6];
    	line[0] = new Point(size, 0);
    	for(int i=1; i<6; i++){
    		line[i] = Point.rotatePoint(line[0], (90/6)*i);
    	}
    	
//    	for(int i=0; i<10; i++){
//    		line[i] = new Point(0, (size/10*i));
//    	}
    	
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
