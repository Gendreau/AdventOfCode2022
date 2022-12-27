package advent;

import java.util.*;
import java.io.*;

public class EighteenOne {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input18.txt");
		Scanner sc = new Scanner(file);
		System.out.println(parseText(sc));
	}
	
	public static int parseText(Scanner sc) {
		boolean[][][] obsidian = new boolean[24][24][24];
		int surfaceArea = 0;
		for (int i=0; i<obsidian.length; i++) {
			for (int j=0; j<obsidian[i].length; j++) {
				for (int k=0; k<obsidian[i][j].length; k++) {
					obsidian[i][j][k] = false;
				}
			}
		}
		while (sc.hasNext()) {
			String[] feed = sc.nextLine().split(",");
			int x = Integer.parseInt(feed[0])+1;
			int y = Integer.parseInt(feed[1])+1;
			int z = Integer.parseInt(feed[2])+1;
			surfaceArea+=6;
			obsidian[x][y][z] = true;
			if (obsidian[x+1][y][z]) surfaceArea-=2;
			if (obsidian[x-1][y][z]) surfaceArea-=2;
			if (obsidian[x][y+1][z]) surfaceArea-=2;
			if (obsidian[x][y-1][z]) surfaceArea-=2;
			if (obsidian[x][y][z+1]) surfaceArea-=2;
			if (obsidian[x][y][z-1]) surfaceArea-=2;
		}
		return surfaceArea;
	}
}
