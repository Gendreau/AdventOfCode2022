package advent;

import java.io.*;
import java.util.*;

public class NineTwo {
	public static void main(String[] args) throws FileNotFoundException {
		final int NUMBER_OF_KNOTS = 10;
		File file = new File("/Users/gendreaum/Downloads/input9.txt");
		Scanner sc = new Scanner(file);
		Knot[] rope = new Knot[NUMBER_OF_KNOTS];
		HashSet<ArrayList<Integer>> visited = new HashSet<ArrayList<Integer>>();
		
		for (int i=0; i<NUMBER_OF_KNOTS; i++) {
			rope[i] = new Knot();
		}

		while (sc.hasNextLine()) {
			String feed[] = sc.nextLine().split(" ");
			for (int i=0; i<Integer.parseInt(feed[1]); i++) {
				switch(feed[0]) {
					case "R": rope[0].moveX(1);
						break;
					case "L": rope[0].moveX(-1);
						break;
					case "U": rope[0].moveY(1);
						break;
					case "D": rope[0].moveY(-1);
						break;
				}
				for (int j=1; j<NUMBER_OF_KNOTS; j++) {
					rope[j].followHead(rope[j-1]);
				}
				visited.add(new ArrayList<Integer>(Arrays.asList(rope[NUMBER_OF_KNOTS-1].getX(),rope[NUMBER_OF_KNOTS-1].getY())));
			}
		}
		System.out.println(visited.size());
		sc.close();
	}
}

class Knot {
	int xpos, ypos;
	
	Knot() {
		this.xpos = 0;
		this.ypos = 0;
	}
	
	int getX() {
		return this.xpos;
	}
	
	int getY() {
		return this.ypos;
	}
	
	void moveX(int t) {
		this.xpos += t;
	}
	
	void moveY(int t) {
		this.ypos += t;
	}
	
	void followHead(Knot head) {
		int xDiff = head.getX()-this.xpos;
		int yDiff = head.getY()-this.ypos;
		if (Math.abs(xDiff) <=1 && Math.abs(yDiff) <= 1) {}
		else if (xDiff==0 ^ yDiff == 0) {
			this.xpos += xDiff/2;
			this.ypos += yDiff/2;
		}
		else if (Math.abs(xDiff) != Math.abs(yDiff)) {
			this.xpos += Integer.signum(xDiff);
			this.ypos += Integer.signum(yDiff);
		}
		return;
	}
}