package advent;

import java.io.*;
import java.util.*;

public class NineOne {
	public static void main(String[] args) throws FileNotFoundException {
		
		File file = new File("/Users/gendreaum/Downloads/input9.txt");
		Scanner sc = new Scanner(file);
		Integer[] headPos = {0, 0};
		Integer[] tailPos = {0, 0};
		HashSet<ArrayList<Integer>> visited = new HashSet<ArrayList<Integer>>();

		visited.add(new ArrayList<Integer>(Arrays.asList(tailPos[0],tailPos[1])));
		while (sc.hasNextLine()) {
			String feed[] = sc.nextLine().split(" ");
			int headX, tailX, headY, tailY;
			for (int i=0; i<Integer.parseInt(feed[1]); i++) {
				switch(feed[0]) {
					case "R": headPos[0] += 1;
						break;
					case "L": headPos[0] -= 1;
						break;
					case "U": headPos[1] += 1;
						break;
					case "D": headPos[1] -= 1;
						break;
				}
				//If head is orthogonal to tail
				headX = headPos[0];
				tailX = tailPos[0];
				headY = headPos[1];
				tailY = tailPos[1];
				if ((headX==tailX) ^ (headY==tailY)) {
					tailPos = moveTailOrthogonal(headPos, tailPos);
				}
				//If head is diagonal to tail
				else if ((headX != tailX && headY != tailY)) {
					tailPos = moveTailDiagonal(headPos, tailPos);
				}
				visited.add(new ArrayList<Integer>(Arrays.asList(tailPos[0],tailPos[1])));
			}
		}
		System.out.println(visited.size());
		sc.close();
	}
	
	public static Integer[] moveTailOrthogonal(Integer[] x, Integer[] y) {
		int xDiff = x[0]-y[0];
		int yDiff = x[1]-y[1];
		if (Math.abs(xDiff) <= 1 && Math.abs(yDiff) <=1) {
			return y;
		}
		y[0] += xDiff/2;
		y[1] += yDiff/2;
		return y;
	}
	
	public static Integer[] moveTailDiagonal(Integer[] x, Integer[] y) {
		int xDiff = x[0]-y[0];
		int yDiff = x[1]-y[1];
		Integer[] move = new Integer[] {0, 0};
		//If head is adjacent to tail
		if (Math.abs(xDiff) == Math.abs(yDiff)) {
			return y;
		}
		if (xDiff > 0) move[0]=1;
		if (yDiff > 0) move[1]=1;
		if (xDiff < 0) move[0]=-1;
		if (yDiff < 0) move[1]=-1;
		y[0] += move[0];
		y[1] += move[1];
		return y;
	}
}