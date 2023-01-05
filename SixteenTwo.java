package advent;

import java.util.*;
import java.io.*;

public class SixteenTwo {
	public static ArrayList<Valve> valves;
	public static ArrayList<String>[][] masterPaths;
	public static HashMap<ArrayList<String>, Integer> stateMap = new HashMap<ArrayList<String>, Integer>();
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input16.txt");
		Scanner sc = new Scanner(file);
		valves = parseText(sc);
		masterPaths = new ArrayList[valves.size()][valves.size()];
		int timeRemaining = 26;
		calculateOptimalPath(getValveByName("AA"), timeRemaining);
		int max = 0;
		for (ArrayList<String> s : stateMap.keySet()) {
			for (ArrayList<String> l : stateMap.keySet()) {
				ArrayList<String> tmp = (ArrayList<String>)s.clone();
				tmp.remove("AA");
				tmp.retainAll(l);
				if (tmp.size()==0) {
					max = Math.max(max, stateMap.get(s) + stateMap.get(l));
				}
			}
		}
		System.out.println(max);
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
	
	public static void calculateOptimalPath(Valve source, int timeRemaining) {
		Queue<State> queue = new LinkedList<>();
		queue.add(new State(source.name, timeRemaining, 0, new ArrayList<String>()));
		while (!queue.isEmpty()) {
			State s = queue.poll();
			s.vo.add(s.cv);
			Collections.sort(s.vo);;
			if (stateMap.containsKey(s.vo)) {
				if (stateMap.get(s.vo) < s.tf) {
					stateMap.put(s.vo, s.tf);
				}
			}
			else {
				stateMap.put(s.vo, s.tf);
			}
			ArrayList<String>[] paths = getShortestPaths(getValveByName(s.cv));
			
			for (ArrayList<String> p : paths) {
				if (s.time > 0 && !p.isEmpty() && p.size()+1 <= s.time && !s.vo.contains(p.get(0))) {
					queue.add(new State(p.get(0), s.time-p.size()-1, s.tf+getValveByName(p.get(0)).flow * (s.time-p.size()-1), (ArrayList<String>)s.vo.clone()));
				}
			}
		}
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

class State {
	String cv;
	int time, tf;
	ArrayList<String> vo;
	
	State(String cv, int time, int tf, ArrayList<String> vo) {
		this.cv = cv;
		this.time = time;
		this.tf = tf;
		this.vo = vo;
	}
}