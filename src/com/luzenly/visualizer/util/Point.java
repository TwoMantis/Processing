package com.luzenly.visualizer.util;

public class Point {

	private float x;
	private float y;

	public static Point rotatePoint(Point p, double degrees){
		double radians = Math.toRadians(degrees);
	    double cosRadians =  Math.cos(radians);
	    double sinRadians =  Math.sin(radians);
	    return new Point(p.getX() *cosRadians - p.getY()*sinRadians,
	    		p.getX() * sinRadians + p.getY()*cosRadians);
	}
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(double x, double y) {
		this.x = (float)x;
		this.y = (float)y;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
}
