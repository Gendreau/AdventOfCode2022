package advent;

import java.io.*;
import java.util.*;

public class Twelve {
	public static void main(String[] args) throws FileNotFoundException {
		
		char[][] heightMap = new char[41][114];
		int source = 0;
		int destination = 0;
		ArrayList<Integer> minHeights = new ArrayList<Integer>();
		ArrayList<Integer> minHeightsPaths = new ArrayList<Integer>();
		File file = new File("/Users/gendreaum/Downloads/input12.txt");
		Scanner sc = new Scanner(file);
		
		
		for (int i=0; i<heightMap.length; i++) {
			String feed = sc.nextLine();
			for (int j=0; j<heightMap[0].length; j++) {
				heightMap[i][j] = feed.charAt(j);
				if (heightMap[i][j]=='S') {
					source = j+(i*heightMap[0].length);
					heightMap[i][j]='a';
				}
				if (heightMap[i][j]=='E') {
					destination = j+(i*heightMap[0].length);
					heightMap[i][j]='z';
				}
				if (heightMap[i][j] == 'a') {
					minHeights.add(j+(i*heightMap[0].length));
				}
			}
		}
		
		
		Graph g = heightMapToGraph(heightMap);
		System.out.println("Shortest path from source to destination is " + g.findShortestPath(source, destination));
		
		for (int i : minHeights) minHeightsPaths.add(g.findShortestPath(i, destination));
		minHeightsPaths.removeAll(Collections.singleton(0));
		
		System.out.println("Shortest path from any minimum height starting spot is " + Collections.min(minHeightsPaths));
		
		sc.close();
	}
	
	public static Graph heightMapToGraph(char[][] heightMap) {
		int lineLength = heightMap[0].length;
		Graph g = new Graph(heightMap.length*heightMap[0].length);
		for (int y=0; y<heightMap.length; y++) {
			for (int x=0; x<heightMap[0].length; x++) {
				//LEFT ADJACENT
				if (x>0 && heightMap[y][x]-heightMap[y][x-1]>-2) {
					g.addEdge(x+(y*lineLength), (x+(y*lineLength)-1));
				}
				//RIGHT ADJACENT
				if (x<lineLength-1 && heightMap[y][x]-heightMap[y][x+1]>-2) {
					g.addEdge(x+(y*lineLength), (x+(y*lineLength+1)));
				}
				//UP ADJACENT
				if (y>0 && heightMap[y][x]-heightMap[y-1][x]>-2) {
					g.addEdge(x+(y*lineLength),(x+((y-1)*lineLength)));
				}
				//DOWN ADJACENT
				if (y<heightMap.length-1 && heightMap[y][x]-heightMap[y+1][x]>-2) {
					g.addEdge(x+(y*lineLength),(x+((y+1)*lineLength)));
				}
			}
		}
		return g;
	}
}

class Graph {
	int V; //number of vertices
	Vector<Integer> adj[]; //adjacency lists
	static int level;
	
	@SuppressWarnings("unchecked")
	Graph (int v) {
		this.V = v;
		this.adj = new Vector[v];
		for (int i=0; i<v; i++) {
			adj[i] = new Vector<Integer>();
		}
	}
	
	void addEdge(int v, int w) {
		adj[v].add(w);
	}
	
	int findShortestPath(int src, int dest) {
		boolean[] visited = new boolean[this.V];
		int[] parent = new int[this.V];
		
		for (int i=0; i<this.V; i++) {
			visited[i] = false;
			parent[i] = -1;
		}
		
		Queue<Integer> queue = new LinkedList<>();
		
		visited[src] = true;
		queue.add(src);
		
		while (!queue.isEmpty()) {
			int s = queue.peek();
			
			if (s == dest) return printShortestPath(parent,s,dest);
			queue.poll();
			
			for (int i : this.adj[s]) {
				if (!visited[i]) {
					visited[i] = true;
					queue.add(i);
					parent[i]=s;
				}
			}
		}
		return 0;
	}
	
	int printShortestPath(int[] parent, int s, int d) {
		level = 0;
		
		if (parent[s] == -1) {
			return level;
		}
		
		printShortestPath(parent, parent[s], d);
		
		level++;
		return level;
	}
}