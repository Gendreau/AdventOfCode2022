package advent;

import java.io.*;
import java.util.*;

public class FifteenOne {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input15.txt");
		Scanner sc = new Scanner(file);
		ArrayList<Sensor> sensors = parseText(sc);
		ArrayList<ArrayList<Integer>> ranges = new ArrayList<ArrayList<Integer>>();
		for (Sensor s : sensors) {
			ArrayList<Integer> range = s.checkInfluence(2000000);
			if (!range.isEmpty()) {
				for (int i=0; i<ranges.size(); i++) {
					//TODO: check all ranges against each other again
					if (range.get(0)<=ranges.get(i).get(1) && range.get(1)>=ranges.get(i).get(0)) {
						int outerLeftBound = Math.min(range.get(0), ranges.get(i).get(0));
						int innerRightBound = Math.min(range.get(1), ranges.get(i).get(1));
						int outerRightBound = Math.max(range.get(1), ranges.get(i).get(1));
						range.set(0, outerLeftBound);
						range.set(1, innerRightBound);
						ranges.get(i).set(0, innerRightBound+1);
						ranges.get(i).set(1, outerRightBound);
					}
				}
			}
			if (!range.isEmpty()) ranges.add(range);
		}
		int totalCovered = 0;
		for (int i=0; i<ranges.size()-1; i++) {
			for (int j=i+1; j<ranges.size(); j++) {
				if (ranges.get(i).get(0)<=ranges.get(j).get(1) && ranges.get(i).get(1)>=ranges.get(j).get(0)) {
					int outerLeftBound = Math.min(ranges.get(i).get(0), ranges.get(j).get(0));
					int innerRightBound = Math.min(ranges.get(i).get(1), ranges.get(j).get(1));
					int outerRightBound = Math.max(ranges.get(i).get(1), ranges.get(j).get(1));
					ranges.get(i).set(0, outerLeftBound);
					ranges.get(i).set(1, innerRightBound);
					ranges.get(j).set(0, innerRightBound+1);
					ranges.get(j).set(1, outerRightBound);
				}
			}
		}
		for (ArrayList<Integer> r : ranges) {
			totalCovered += r.get(1)-r.get(0)+1;
		}
		System.out.println(totalCovered-1);
	}
	
	public static ArrayList<Sensor> parseText(Scanner sc) {
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		while (sc.hasNextLine()) {
			String[] feed = sc.nextLine().split("[^0-9]+");
			sensors.add(new Sensor(Integer.parseInt(feed[1]), Integer.parseInt(feed[2]), Integer.parseInt(feed[3]), Integer.parseInt(feed[4])));
		}
		return sensors;
	}
}

class Sensor {
	int[] pos;
	int[] beaconPos;
	int taxicabRadius;
	
	Sensor(int x1, int y1, int x2, int y2) {
		this.pos = new int[]{x1, y1};
		this.beaconPos = new int[] {x2, y2};
		this.taxicabRadius = Math.abs(y2-y1)+Math.abs(x2-x1);
	}
	
	ArrayList<Integer> checkInfluence(int y) {
		ArrayList<Integer> range = new ArrayList<Integer>();
		if (!(this.pos[1]+this.taxicabRadius<y || this.pos[1]-this.taxicabRadius>y)) {
			int boundOne = this.taxicabRadius - Math.abs(y-this.pos[1]) + this.pos[0];
			int boundTwo = (-1 * (this.taxicabRadius - Math.abs(y-this.pos[1]))) + this.pos[0];
			if (boundTwo < boundOne) {
				int tmp = boundOne;
				boundOne = boundTwo;
				boundTwo = tmp;
			}
			range.add(boundOne);
			range.add(boundTwo);
			return range;
		}
		return range;
	}
	
	int [] getPos() {
		return this.pos;
	}
	
	int getRadius() {
		return this.taxicabRadius;
	}
}
