package advent;

import java.util.*;
import java.io.*;

public class SixteenOne {
	public static ArrayList<Valve> valves;
	public static ArrayList<String>[][] masterPaths;
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input16.txt");
		Scanner sc = new Scanner(file);
		valves = parseText(sc);
		masterPaths = new ArrayList[valves.size()][valves.size()];
		int timeRemaining = 30;
		System.out.println(calculateOptimalPath(getValveByName("AA"), timeRemaining));
	}
	
	public static ArrayList<Valve> parseText(Scanner sc) {
		ArrayList<Valve> valves = new ArrayList<Valve>();
		
		for (int i=0; sc.hasNextLine(); i++) {
			String feed[] = sc.nextLine().split("[^A-Z0-9]+");
			Valve v = new Valve(feed[1], Integer.parseInt(feed[2]), i);
			for (int j=3; j<feed.length; j++) {
				v.addAdjacent(feed[j]);
			}
			valves.add(v);
		}
		return valves;
	}
	
	public static Valve getValveByName(String name) {
		for (Valve v : valves) if (name.equals(v.name)) return v; 
		return null;
	}
	
	public static Valve getValveByID(int id) {
		for (Valve v : valves) if (id == v.id) return v; 
		return null;
	}
	
	public static ArrayList<String>[] getShortestPaths(Valve source) {
		if (masterPaths[source.id][0] != null) return masterPaths[source.id]; 
		ArrayList<String>[] paths = new ArrayList[valves.size()];
		for (int i=0; i<paths.length; i++) {
			paths[i] = new ArrayList<String>();
		}
		for (Valve v : valves) {
			if (v.flow > 0) {
				boolean visited [] = new boolean[valves.size()];
				int[] parent = new int[valves.size()];
				for (int i=0; i<valves.size(); i++) {
					visited[i] = false;
					parent[i] = -1;
				}
				
				Queue<Valve> queue = new LinkedList<>();
				
				visited[source.id] = true;
				queue.add(source);
				
				while (!queue.isEmpty()) {
					Valve s = queue.peek();
					if (s.id == v.id) {
						for (;parent[s.id]!=-1; s=getValveByID(parent[s.id])) {
							paths[v.id].add(s.name);
						}
					}
					queue.poll();
					
					for (String ch : s.children) {
						if (!visited[getValveByName(ch).id]) {
							visited[getValveByName(ch).id] = true;
							queue.add(getValveByName(ch));
							parent[getValveByName(ch).id] = s.id;
						}
					}
				}
			}
		}
		masterPaths[source.id] = paths; 
		return paths;
	}
	
	public static void printShortestPaths(Valve source, ArrayList<String>[] paths) {
		System.out.println("Shortest path from Node " + source.name + " to all nodes with positive flow:");
		for (ArrayList<String> als : paths) {
			if (!als.isEmpty()) {
				for (String s : als) {
					System.out.print(s + " > ");
				}
				System.out.println(source.name);
			}
		}
	}
	
	public static int calculateOptimalPath(Valve source, int timeRemaining) {
		int maxGain = 0;
		int gain = 0;
		ArrayList<String>[] paths = getShortestPaths(source);
		for (ArrayList<String> p : paths) {
			if (timeRemaining > 0 && !p.isEmpty() && p.size()+1 <= timeRemaining && !getValveByName(p.get(0)).activated) {
				gain = getValveByName(p.get(0)).flow * (timeRemaining-p.size()-1);
				getValveByName(p.get(0)).activated = true;
				gain += calculateOptimalPath(getValveByName(p.get(0)),timeRemaining-p.size()-1);
				getValveByName(p.get(0)).activated = false;
				
			}
			if (gain > maxGain) maxGain = gain;
		}
		return maxGain;
	}
	
	public static void printValves() {
		for (int i=0; i<valves.size(); i++) {
			String str = "Valve " + valves.get(i).name + " | Flow: " + valves.get(i).flow + "\t| Connected to ";
			for (String child : valves.get(i).children) {
				str += child + ", ";
			}
			System.out.println(str);
		}
	}
	

}

class Valve {
	String name;
	int id;
	int flow;
	boolean activated;
	ArrayList<String> children = new ArrayList<String>();
	
	Valve (String n, int f, int id) {
		this.name = n;
		this.flow = f;
		this.id = id;
	}
	
	void addAdjacent(String name) {
		children.add(name);
	}
}