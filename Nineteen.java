package advent;

import java.util.*;
import java.io.*;

public class Nineteen {
	public static int[] maxRobots;
	public static int maxGeodes;
	public static final boolean isPartTwo = true;
	
	public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
		File file = new File("/Users/gendreaum/Downloads/input19.txt");
		Scanner sc = new Scanner(file);
		ArrayList<int[][]> blueprints = parseText(sc);
		int qualityLevel = isPartTwo ? 1 : 0;
		int consider = isPartTwo ? 3 : blueprints.size();
		int time = isPartTwo ? 32 : 24;
		for (int i=0; i<consider; i++) {
			findMaxRobots(blueprints.get(i));
			findMaxGeodes(blueprints.get(i), new int[4], new int[] {1,0,0,0}, time);
			qualityLevel = isPartTwo ? maxGeodes * qualityLevel : qualityLevel + maxGeodes*(i+1);
			maxGeodes = 0;
		}
		System.out.println(qualityLevel);
	}
	
	public static ArrayList<int[][]> parseText(Scanner sc) {
		ArrayList<int[][]> blueprints = new ArrayList<int[][]>();
		while (sc.hasNextLine()) {
			String[] feed = sc.nextLine().split("[^0-9]+");
			int[][] bp = new int[4][3];
			bp[0] = new int[] {Integer.parseInt(feed[2]),0,0};
			bp[1] = new int[] {Integer.parseInt(feed[3]),0,0};
			bp[2] = new int[] {Integer.parseInt(feed[4]),Integer.parseInt(feed[5]),0};
			bp[3] =new int[] {Integer.parseInt(feed[6]),0,Integer.parseInt(feed[7])};
			blueprints.add(bp);
		}
		return blueprints;
	}
	
	public static void findMaxRobots(int[][] bp) {
		maxRobots = new int[3];
		for (int j=0; j<bp[0].length; j++) {
			for (int i=0; i<bp.length; i++) {
				maxRobots[j] = Math.max(maxRobots[j], bp[i][j]);
			}
		}
	}
	
	public static int findMaxGeodes(int[][] bp, int[] mats, int[] bots, int timeRemaining) {
		int expectedGeodes = mats[3] + bots[3]*timeRemaining;
		int maxWorkingGeodes = timeRemaining*(timeRemaining-1)/2;
		if (expectedGeodes + maxWorkingGeodes <= maxGeodes) return 0;
		int skip;
		
		for (int i=0; i<4; i++) {
			if (i<3 && bots[i] >= maxRobots[i]) continue;
			skip = timeToProduce(bp[i], mats, bots) + 1;
			if (timeRemaining-skip < 1) continue;
		
			int[] newMats = mats.clone();
			int[] newBots = bots.clone();
			
			for (int j=0; j<3; j++) newMats[j] += bots[j]*skip - bp[i][j];
			newMats[3] += bots[3]*skip;
			newBots[i]++;
			expectedGeodes = Math.max(expectedGeodes, findMaxGeodes(bp, newMats, newBots, timeRemaining-skip));
		}
		
		maxGeodes = Math.max(maxGeodes, expectedGeodes);
		return expectedGeodes;
	}
	
	public static int timeToProduce(int[] cost, int[] mats, int[] bots) {
		int ttp = 0;
		for (int i=0; i<3; i++) {
			if (cost[i] == 0) continue;
			if (bots[i] == 0) return 99999;
			ttp = Math.max(ttp, (cost[i]-mats[i]-1+bots[i])/bots[i]);
		}
		return ttp;
	}
}