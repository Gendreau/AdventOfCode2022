package advent;

import java.util.*;
import java.io.*;

public class TwentyFour {
	static int sourceX, sourceY, destX, destY;
	static final int NUMBER_OF_TRIPS = 3;
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input24.txt");
		Scanner sc = new Scanner(file);
		boolean[][][] vo = parseText(sc);
		int total = 0;
		int lastTrip = 0;
		for (int i=0; i<NUMBER_OF_TRIPS; i++) {
			total += (leastMoves(vo, total%vo.length));
			System.out.println("Trip " + (i+1) + ": " + (total-lastTrip) + " minutes");
			lastTrip = total;
			swapValues();
		}
		System.out.println("Total time taken: " + total + " minutes");
	}
	
	public static boolean[][][] parseText(Scanner sc) {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		while (sc.hasNextLine()) {
			lines.add(sc.nextLine().split(""));
		}
		int height = lines.size();
		int width = lines.get(0).length;
		int timeSize = lcm(height-2,width-2);
		sourceX = 1;
		sourceY = 0;
		destX = width-2;
		destY = height-1;
		boolean[][][] vo = new boolean[timeSize][height][width];
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				int tmpx = x;
				int tmpy = y;
				if (!lines.get(y)[x].equals(".")) {
					for (int t = 0; t<timeSize; t++) {
						vo[t][tmpy][tmpx] = true;
						switch (lines.get(y)[x]) {
						case ">":
							tmpx++;
							if (tmpx >= width-1) tmpx = 1;
							break;
						case "<":
							tmpx--;
							if (tmpx <= 0) tmpx = width-2;
							break;
						case "^":
							tmpy--;
							if (tmpy <= 0) tmpy = height-2;
							break;
						case "v":
							tmpy++;
							if (tmpy >= height-1) tmpy = 1;
							break;
						}
					}
				}
			}
		}
		return vo;
	}
	
	public static int leastMoves(boolean[][][] vo, int startTime) {
		int dRow[] = {1, -1, 0, 0, 0};
		int dCol[] = {0, 0, 1, -1, 0};
		
		boolean[][][] visited = new boolean[vo.length][vo[0].length][vo[0][0].length];

		Queue<Quadruplet> queue = new LinkedList<>();
		
		queue.add(new Quadruplet(sourceX, sourceY, startTime, 0));
		visited[startTime][sourceY][sourceX] = true;
		
		while (!queue.isEmpty()) {
			Quadruplet tile = queue.remove();
			
			if (tile.x == destX && tile.y == destY) return tile.dist;
			
			for (int i=0; i<5; i++) {
				int adjX = tile.x + dRow[i];
				int adjY = tile.y + dCol[i];
				int adjZ = (tile.z+1)%vo.length;
				
				if (isValid(visited, adjX, adjY, adjZ) && !vo[adjZ][adjY][adjX]) {
					queue.add(new Quadruplet(adjX, adjY, adjZ, tile.dist+1));
					visited[adjZ][adjY][adjX] = true;
				}
			}
		}
		return -1;
	}
	
	public static boolean isValid(boolean[][][] visited, int x, int y, int z) {
		if (x < 0 || y < 0 || x >= visited[0][0].length || y >= visited[0].length || visited[z][y][x]) return false;
		return true;
	}
	
	public static void swapValues() {
		int tmp = sourceX;
		sourceX = destX;
		destX = tmp;
		tmp = sourceY;
		sourceY = destY;
		destY = tmp;
	}
	
	public static int gcd(int a, int b) {
		if (a == 0 || b == 0) return a + b;
		else { 
			int big = Math.max(Math.abs(a), Math.abs(b));
			int small = Math.min(Math.abs(a), Math.abs(b));
			return gcd(big % small, small);
		}
	}
	
	public static int lcm(int a, int b) {
		if (a==0 || b==0) return 0;
		else {
			int gcd = gcd(a, b);
			return Math.abs(a*b)/gcd;
		}
	}
}

class Quadruplet extends Triplet {
	int dist;
	public Quadruplet(int x, int y, int z, int dist) {
		super(x, y, z);
		this.dist = dist;
	}
}