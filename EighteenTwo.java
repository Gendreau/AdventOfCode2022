package advent;

import java.util.*;
import java.io.*;

public class EighteenTwo {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input18.txt");
		Scanner sc = new Scanner(file);
		System.out.println(findSurfaceArea(parseText(sc)));
	}
	
	public static boolean[][][] parseText(Scanner sc) {
		boolean[][][] obsidian = new boolean[24][24][24];
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
			obsidian[x][y][z] = true;
		}
		
		return obsidian;
	}
	
	public static int findSurfaceArea(boolean[][][] grid) {
		int surfaceArea = 0;
		int dRow[] = {1, -1, 0, 0, 0, 0};
		int dCol[] = {0, 0, 1, -1, 0, 0};
		int dDepth[] = {0, 0, 0, 0, 1, -1};
		
		boolean[][][] visited = new boolean[24][24][24];
		for (int i=0; i<visited.length; i++) {
			for (int j=0; j<visited[i].length; j++) {
				for (int k=0; k<visited[i][j].length; k++) {
					visited[i][j][k] = false;
				}
			}
		}
		Queue<Triplet> queue = new LinkedList<>();
		
		queue.add(new Triplet(0, 0, 0));
		visited[0][0][0] = true;
		
		while (!queue.isEmpty()) {
			Triplet block = queue.peek();
			
			queue.remove();
			
			for (int i=0; i<6; i++) {
				int adjX = block.x + dRow[i];
				int adjY = block.y + dCol[i];
				int adjZ = block.z + dDepth[i];
				
				if (isValid(visited, adjX, adjY, adjZ)) {
					if (!grid[adjX][adjY][adjZ]) {
						queue.add(new Triplet(adjX, adjY, adjZ));
						visited[adjX][adjY][adjZ] = true;
					}
					else surfaceArea++;
				}
			}
		}
		
		return surfaceArea;
	}
	
	public static boolean isValid(boolean[][][] visited, int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 || x >= visited.length || 
				y >= visited[0].length || z >= visited[0][0].length) {
			return false;
		}
		
		if (visited[x][y][z]) return false;
		
		return true;
	}
}

class Triplet {
	int x, y, z;
	
	public Triplet(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
