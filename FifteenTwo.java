package advent;

import java.io.*;
import java.util.*;

public class FifteenTwo {
	public static void main(String[] args) throws FileNotFoundException {
		final int MIN_BOUND = 0;
		final int MAX_BOUND = 4000000;
		File file = new File("/Users/gendreaum/Downloads/input15.txt");
		Scanner sc = new Scanner(file);
		ArrayList<Sensor> sensors = parseText(sc);
		ArrayList<Line> downRights = new ArrayList<Line>();
		ArrayList<Line> downLefts = new ArrayList<Line>();
		boolean magicCoord;
		for (Sensor s : sensors) {
			downRights.add(new Line(s.getPos()[0], s.getPos()[1]+s.getRadius()+1, s.getPos()[0]+s.getRadius()+1, s.getPos()[1]));
			downRights.add(new Line(s.getPos()[0]-s.getRadius()-1, s.getPos()[1],s.getPos()[0], s.getPos()[1]-s.getRadius()-1));
			downLefts.add(new Line(s.getPos()[0], s.getPos()[1]+s.getRadius()+1, s.getPos()[0]-s.getRadius()-1, s.getPos()[1]));
			downLefts.add(new Line(s.getPos()[0]+s.getRadius()+1,s.getPos()[1],s.getPos()[0],s.getPos()[1]+s.getRadius()-1));
		}
		for (Line l : downRights) {
			int xpos = l.getX1();
			int ypos = l.getY1();
			while (xpos<=l.getX2()) {
				magicCoord = true;
				for (Sensor s : sensors) {
					if ((Math.abs(ypos-s.getPos()[1]) + Math.abs(xpos-s.getPos()[0]))<=s.getRadius()) {
						magicCoord = false;
					}
				}
				if (magicCoord && xpos>=MIN_BOUND && xpos<=MAX_BOUND && ypos>=MIN_BOUND && ypos<=MAX_BOUND) System.out.println((long)(xpos)*4000000+ypos);
				xpos++;
				ypos--;
			}
		}
		for (Line l : downLefts) {
			int xpos = l.getX1();
			int ypos = l.getY1();
			while (xpos>=l.getX2()) {
				magicCoord = true;
				for (Sensor s : sensors) {
					if ((Math.abs(ypos-s.getPos()[1])+Math.abs(xpos-s.getPos()[0]))<=s.getRadius()) {
						magicCoord = false;
					}
				}
				if (magicCoord && xpos>=MIN_BOUND && xpos<=MAX_BOUND && ypos>=MIN_BOUND && ypos<=MAX_BOUND) System.out.println((long)(xpos)*4000000+ypos);
				xpos--;
				ypos--;
			}
		}
	}
	
	public static ArrayList<Sensor> parseText(Scanner sc) {
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		while (sc.hasNextLine()) {
			String[] feed = sc.nextLine().split("[^0-9-]+");
			sensors.add(new Sensor(Integer.parseInt(feed[1]), Integer.parseInt(feed[2]), Integer.parseInt(feed[3]), Integer.parseInt(feed[4])));
		}
		return sensors;
	}
}

class Line {
	int x1, x2, y1, y2;
	
	Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	int getX1() {
		return this.x1;
	}
	
	int getX2() {
		return this.x2;
	}
	
	int getY1() {
		return this.y1;
	}
	
	int getY2() {
		return this.y2;
	}
	
	void setX1(int bound) {
		this.x1 = bound;
	}
	
	void setX2(int bound) {
		this.x2 = bound;
	}
	
	void setY1(int bound) {
		this.y1 = bound;
	}
	
	void setY2(int bound) {
		this.y2 = bound;
	}
	
	@Override
	public String toString() {
		return new String("(" + x1 + "," + y1 + "),(" + x2 + "," + y2 + ")");
	}
}
