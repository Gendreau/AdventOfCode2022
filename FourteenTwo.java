package advent;

import java.util.*;
import java.io.*;

public class FourteenTwo {
	static final int dropLocation = 500;
	public static boolean finishFlag = false;
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input14.txt");
		Scanner sc = new Scanner(file);
		int counter = 0;
		
		char[][] caveMap = parseText(sc);
		while (caveMap[3][dropLocation] != '0') {
			caveMap = dropSand(caveMap, dropLocation, 3);
			counter++;
		}
		//printCaveMap(caveMap);
		System.out.println(counter);
	}
	
	public static char[][] parseText(Scanner sc) {
		ArrayList<Integer> rockXs = new ArrayList<Integer>();
		ArrayList<Integer> rockYs = new ArrayList<Integer>();
		while (sc.hasNextLine()) {
			String[] feed = sc.nextLine().replaceAll(" -> ",",").split(",");
			for (int i=0; i<feed.length-2; i+=2) {
				int rockX1 = Integer.parseInt(feed[i]);
				int rockY1 = Integer.parseInt(feed[i+1]);
				int rockX2 = Integer.parseInt(feed[i+2]);
				int rockY2 = Integer.parseInt(feed[i+3]);
				if (rockY1==rockY2) {
					if (rockX1>rockX2) {
						int tmp = rockX1;
						rockX1 = rockX2;
						rockX2 = tmp;
					}
					for (int j=0; j<=rockX2-rockX1; j++) {
						rockXs.add(rockX1+j);
						rockYs.add(rockY1);
					}
				} else if (rockX1==rockX2) {
					if (rockY1>rockY2) {
						int tmp = rockY1;
						rockY1 = rockY2;
						rockY2 = tmp;
					}
					for (int j=0; j<=rockY2-rockY1; j++) {
						rockXs.add(rockX1);
						rockYs.add(rockY1+j);
					}
				}
			}
		}
		
		for (int i=0; i<rockXs.size(); i++) {
			rockXs.set(i, rockXs.get(i));
		}
		
		char[][] caveMap = new char[Collections.max(rockYs)+6][1000];
		
		for (int i=0; i<3; i++) {
			for (int j=0; j<caveMap[0].length; j++) {
				caveMap[i][j] = ' ';
			}
		}
		
		for (int i=3; i<caveMap.length; i++) {
			for (int j=0; j<caveMap[0].length; j++) {
				caveMap[i][j] = '.';
			}
		}
		
		for (int i=0; i<rockXs.size(); i++) {
			caveMap[rockYs.get(i)+3][rockXs.get(i)] = '#';
		}
		
		for (int i=0; i<caveMap[0].length; i++) {
			caveMap[caveMap.length-1][i] = '#';
		}
		
		caveMap[0][dropLocation] = '|';
		caveMap[1][dropLocation] = '|';
		caveMap[2][dropLocation] = 'V';
		caveMap[3][dropLocation] = '+';
		
		return caveMap;
	}
	
	public static void printCaveMap(char[][] caveMap) {
		for (int i=0; i<caveMap.length; i++) {
			for (int j=0; j<caveMap[0].length; j++) {
				if (caveMap[i][j]=='#') System.out.print((char)27 + "[32m" + caveMap[i][j]);
				else if(caveMap[i][j]=='0') System.out.print((char) 27 + "[36m" + caveMap[i][j]);
				else System.out.print((char) 27 + "[39m" + caveMap[i][j]);
			}
			System.out.println();
		}
	}
	
	public static char[][] dropSand(char[][] caveMap, int xpos, int ypos) {
		for (int i=ypos; i<caveMap.length-1; i++) {
			//Activates when sand index hits a wall/other sand
			if (caveMap[i+1][xpos] == '#' || caveMap[i+1][xpos] == '0') {
				if (xpos==0 || xpos==caveMap[0].length-1) {
					finishFlag = true;
					return caveMap;
				}
				//Checks if there's room diagonally to the left
				if (caveMap[i+1][xpos-1] != '#' && caveMap[i+1][xpos-1] != '0') {
					return dropSand(caveMap, xpos-1, i+1);
				}
				//Checks if there's room diagonally to the right
				if (caveMap[i+1][xpos+1] != '#' && caveMap[i+1][xpos+1] != '0') {
					return dropSand(caveMap, xpos+1, i+1);
				}
				caveMap[i][xpos] = '0';
				return caveMap;
			}
		}
		return caveMap;
	}
}
