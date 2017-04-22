package com.luzenly.visualizer.util;

public class Color {

	private int red;
	private int green;
	private int blue;
	
	public Color(int red, int green, int blue){
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	public int getRed() {
		return red;
	}
	public void setRed(int red) {
		this.red = red;
	}
	public int getGreen() {
		return green;
	}
	public void setGreen(int green) {
		this.green = green;
	}
	public int getBlue() {
		return blue;
	}
	public void setBlue(int blue) {
		this.blue = blue;
	}
	
	public static Color RED = new Color(255,0,0);
	public static Color GREEN = new Color(0,255,0);
	public static Color BLUE = new Color(0,0,255);
	public static Color BLACK = new Color(0,0,0);
	public static Color WHITE = new Color(255,255,255);
	public static Color TEAL = new Color(0,128,128);
	
	public static Color getRandomColor(){
		return new Color((int)(Math.random() * 255),
				(int)(Math.random() * 255),
				(int)(Math.random() * 255));
	}
}
