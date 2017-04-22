package com.luzenly.visualizer.util;

import com.luzenly.visualizer.MusicVisualizer;

import processing.sound.FFT;

public interface Animation {

	public void setup(MusicVisualizer p);
	public boolean animate(FFT fft);
	public boolean transition(FFT fft);
	public boolean isAnimating();
	public boolean isTranstitioning();
	public Color getTransitionColor();
}
