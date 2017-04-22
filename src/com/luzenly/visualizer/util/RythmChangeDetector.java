package com.luzenly.visualizer.util;

public class RythmChangeDetector {

	private float[] currentValues;
	private float[] previousValues;
	private long startingMillis;
	private long currentMillis;
	private int fftSize;
	private int seconds = 0;
	
	public RythmChangeDetector(int seconds, int FFTSize){
		startingMillis = System.currentTimeMillis();
		currentValues = new float[FFTSize];
		previousValues = null;
		currentMillis = startingMillis;
		fftSize = FFTSize;
		this.seconds = seconds;
	}
	
	public boolean rythmChanged(float[] fft, float tolerance){
		for(int i = 0; i< fftSize; i++){
			currentValues[i]+= fft[i];
		}
		currentMillis = System.currentTimeMillis();
		boolean check = currentMillis - startingMillis > seconds*1000;
		if(check){
			if(previousValues == null){
				previousValues = currentValues;
				currentValues = new float[fftSize];
			}else{
				float prevAverage = getAverage(previousValues);
				float currentAverage = getAverage(currentValues);
				double abs = Math.abs(prevAverage-currentAverage);
				if(abs > tolerance || Double.isNaN(abs)){
					return true;
				}
			}
			startingMillis = currentMillis;
		}
		
		return false;
	}
	
	private float getAverage(float[] f){
		float average = 0;
		for(float fl : f){
			average+=fl;
		}
		average/=f.length;
		
		return average;
	}
}
