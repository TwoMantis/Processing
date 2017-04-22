package com.luzenly.visualizer;


import com.luzenly.visualizer.circlingline.CirclingLineAnimation;
import com.luzenly.visualizer.kaleidoscope.KaleidoscopeAnimation;
import com.luzenly.visualizer.kaleidoscope.KaleidoscopeUtil;
import com.luzenly.visualizer.util.Animation;
import com.luzenly.visualizer.util.Color;

import processing.core.PApplet;
import processing.sound.AudioIn;
import processing.sound.FFT;

public class MusicVisualizer extends PApplet{

	KaleidoscopeUtil k[];
	int fftFix= 300;
	AudioIn in;
	Animation currentAnim = null;
	FFT fft;
	Color bg = Color.getRandomColor();
	boolean animating = false;
	public static void main(String[] args) {
		PApplet.main("com.luzenly.visualizer.MusicVisualizer");
	}

   public void settings(){
        size(1280,720);
        this.fullScreen();
    }

    public void setup(){
    	fft = new FFT(this, 255);
    	in = new AudioIn(this, 0);
    	in.start();
    	fft.input(in); 
    	noStroke();
    	animating = true;
    	currentAnim = getAnimation();
    	currentAnim.setup(this);
   }

    public void draw(){
    	boolean status;
    	
    	if(animating)
    		animating = currentAnim.animate(fft);
    	else{
    		status = currentAnim.transition(fft);
    		if(!status){
    			animating = true;
    			bg = currentAnim.getTransitionColor();
    			currentAnim = getAnimation();
    	    	currentAnim.setup(this);
    		}
    	}
    }
    
    public void fill(Color c){
    	fill(c.getRed(), c.getGreen(), c.getBlue());
    }
    
    public void background(Color c){
    	background(c.getRed(), c.getGreen(), c.getBlue());
    }
    
    public Animation getAnimation(){
    	//return new KaleidoscopeAnimation();
    	return new CirclingLineAnimation();
    }
    
    public Color getBgColor(){
    	return bg;
    }
    
    public void stroke(Color c){
    	this.stroke(c.getRed(), c.getGreen(), c.getBlue());
    }
}
