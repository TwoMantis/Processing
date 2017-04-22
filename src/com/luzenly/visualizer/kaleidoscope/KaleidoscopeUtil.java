package com.luzenly.visualizer.kaleidoscope;

import com.luzenly.visualizer.MusicVisualizer;
import com.luzenly.visualizer.util.Color;
import com.luzenly.visualizer.util.Point;

import processing.core.PApplet;

public class KaleidoscopeUtil {

	private MusicVisualizer canvas;
	private int circleDiameter = 0;
	private Point point;
	private int count = 0;
	private Color color;
	private boolean reverse = false;
	private boolean original = true;
	int originX = 0;
	int originY = 0;
	
	public KaleidoscopeUtil(MusicVisualizer canvas, Color color, int circleDiameter, Point point, int count) {
		this.canvas = canvas;
		this.circleDiameter = circleDiameter;
		this.point = point;
		if(count ==7) count ++;
		this.count = count;
		this.color = color;
		originX = canvas.width/2;//(int)(Math.random() * canvas.width);
		originY =  canvas.height/2; //(int)(Math.random() * canvas.height);
	}
	
	public void renderKaleidoscopePoint(Color color,Point p, int circleDiameter, PApplet canvas){
		canvas.fill(color.getRed(), color.getGreen(), color.getBlue());
		double angle = 360/count;
		double currentAngle = angle;
		Point temp;
		
		for(int i=0; i<count; i++){
			temp = Point.rotatePoint(p, currentAngle);
		    canvas.ellipse(temp.getX() + originX, temp.getY() + originY, circleDiameter,circleDiameter);
		    if(temp.getY() + originY < 0){
		    	reverse = !reverse;
		    }
		    currentAngle += angle;
		  }
	}
	
	public void renderKaleidoscopeLine(Color color, Point p1, PApplet canvas){
		canvas.fill(color.getRed(), color.getGreen(), color.getBlue());
		double angle = 360/count;
		double currentAngle = angle;
		Point temp;
		Point temp2;
		Point temp3;
		
		for(int i=0; i<count; i++){
			temp = Point.rotatePoint(p1, currentAngle);
			temp2 = Point.rotatePoint(temp, angle);
			temp3 = Point.rotatePoint(new Point(originX, originY), angle);
			
			canvas.triangle(originX, originY, temp.getX() + originX, temp.getY() + originY, temp2.getX() + originX, temp2.getY() + originY);
		    if(temp.getY() + originY < 0){
		    	reverse = !reverse;
		    }
		    currentAngle += angle;
		  }
	}
	
	public void moveRight(int increase, Color color){
		if(reverse){
			increase*=-1;
		}
		this.color = color;
		canvas.fill(this.color);
		this.point.setX(this.point.getX() + increase);
		renderKaleidoscopePoint(this.color, this.point, circleDiameter, canvas);
	}
	
	public void moveLineRight(int increase, Color color){
		if(reverse){
			increase*=-1;
		}
		this.color = color;
		canvas.fill(this.color);
		this.point.setX(this.point.getX() + increase);
		renderKaleidoscopeLine(this.color, this.point, canvas);
	}
	
	public void moveLineRight(int increase){
		this.moveLineRight(increase, this.color);
	}
	
	public void moveLineRight(){
		this.moveLineRight(1, this.color);
	}
	
	public void moveRight(int increase){
		this.moveRight(increase, this.color);
	}
	
	public void moveRight(){
		this.moveRight(1, this.color);
	}
	
	public void moveLeft(int increase, Color color){
		if(reverse){
			increase*=-1;
		}
		this.color = color;
		canvas.fill(this.color);
		this.point.setX(this.point.getX() - increase);
		renderKaleidoscopePoint(this.color, this.point, circleDiameter, canvas);
	}
	
	public void moveLeft(int increase){
		this.moveLeft(increase, this.color);
	}
	
	public void moveLeft(){
		this.moveLeft(1, this.color);
	}
	
	public void moveUp(int increase, Color color){
		if(reverse){
			increase*=-1;
		}
		this.color = color;
		canvas.fill(this.color);
		this.point.setY(this.point.getY() + increase);
		renderKaleidoscopePoint(this.color, this.point, circleDiameter, canvas);
	}
	
	public void moveUp(int increase){
		this.moveUp(increase, this.color);
	}
	
	public void moveUp(){
		this.moveUp(1, this.color);
	}
	
	public void moveDown(int increase, Color color){
		if(reverse){
			increase*=-1;
		}
		this.color = color;
		canvas.fill(this.color);
		this.point.setY(this.point.getY() - increase);
		renderKaleidoscopePoint(this.color, this.point, circleDiameter, canvas);
	}
	
	public void moveDown(int increase){
		this.moveDown(increase, this.color);
	}
	
	public void moveDown(){
		this.moveDown(1, this.color);
	}
}
