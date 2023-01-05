package advent;

import java.util.*;
import java.io.*;

public class TwentyThree {
	
	public static void main(String[] args) throws FileNotFoundException {
		final int NUMBER_OF_ROUNDS = 10;
		File file = new File("/Users/gendreaum/Downloads/input23.txt");
		Scanner sc = new Scanner(file);
		ArrayList<Elf> elves = parseText(sc);
		int round = 0;
		
//		for (int i=0; i<NUMBER_OF_ROUNDS; i++) {
		while (!checkLast(elves)) {
			for (Elf e : elves) {
				e.toMove = findNextMove(e, elves);
			}
			for (Elf e : elves) {
				e.canMove = validateMove(e, elves);
			}
			for (Elf e : elves) {
				e.move();
			}
			round++;
		}
//		System.out.println(findRectangle(elves));
		System.out.println(round);
	}
	
	public static ArrayList<Elf> parseText(Scanner sc) {
		ArrayList<Elf> elves = new ArrayList<Elf>();
		for (int i=0; sc.hasNextLine(); i++) {
			String[] feed = sc.nextLine().split("");
			for (int j=0; j<feed.length; j++) {
				if (feed[j].equals("#")) {
					elves.add(new Elf(j, i));
				}
			}
		}
		return elves;
	}
	
	@SuppressWarnings("unchecked")
	public static int findNextMove(Elf dobby, ArrayList<Elf> elves) {
		ArrayList<Elf> shallowElves = (ArrayList<Elf>) elves.clone();
		shallowElves.remove(dobby);
		int potentialMove = -1;
		boolean elfWatch = false;
		boolean stash = false;
		int stashed = -1;
		int[] dx = {-1, 0, 1, -1, 0, 1, -1, -1, -1, 1, 1, 1};
		int[] dy = {-1, -1, -1, 1, 1, 1, -1, 0, 1, -1, 0, 1};
		
		for (int dir : dobby.directions) {
			potentialMove = dir;
			for (Elf e : shallowElves) {
				for (int i=0; i<3; i++) {
					if (dobby.x+dx[dir*3+i] == e.x && dobby.y+dy[dir*3+i] == e.y) {
						potentialMove = 4;
						elfWatch = true;
					}
				}
			}
			if (potentialMove != 4 && !stash) {
				stash = true;
				stashed = potentialMove;
			}
		}
		if (elfWatch && stash) return stashed;
		else return 4;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean validateMove(Elf dobby, ArrayList<Elf> elves) {
		ArrayList<Elf> shallowElves = (ArrayList<Elf>) elves.clone();
		shallowElves.remove(dobby);
		int[] dx = {0, 0, -1, 1, 0};
		int[] dy = {-1, 1, 0, 0, 0};
		for (Elf e : shallowElves) {
			if (dobby.x + dx[dobby.toMove] == e.x + dx[e.toMove] && dobby.y + dy[dobby.toMove] == e.y + dy[e.toMove]) {
				return false;
			}
		}
		return true;
	}
	
	public static int findRectangle(ArrayList<Elf> elves) {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = minX;
		int maxY = maxX;
		char[][] elfMap = new char[13][13];
//		for (int i=0; i<elfMap.length; i++) {
//			for (int j=0; j<elfMap[0].length; j++) {
//				elfMap[i][j] = '.';
//			}
//		}
		for (Elf e : elves) {
			if (e.x < minX) minX = e.x;
			if (e.x > maxX) maxX = e.x;
			if (e.y < minY) minY = e.y;
			if (e.y > maxY) maxY = e.y;
//			elfMap[e.y][e.x] = '#'; 
		}
//		for (int i=0; i<elfMap.length; i++) {
//			for (int j=0; j<elfMap[0].length; j++) {
//				System.out.print(elfMap[i][j]);
//			}
//			System.out.println();
//		}
		return (Math.abs((maxX-minX+1)*(maxY-minY+1))-elves.size());
	}
	
	public static boolean checkLast(ArrayList<Elf> elves) {
		boolean noMoves = true;
		for (Elf e : elves) {
			if (!(e.x == e.lastX && e.y == e.lastY)) noMoves = false;
		}
		return noMoves;
	}
}

class Elf {
	int x, y, lastX, lastY;
	ArrayList<Integer> directions = new ArrayList<Integer>();
	int toMove;
	boolean canMove;
	HashMap<Integer, Integer> dx = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> dy = new HashMap<Integer, Integer>();
	
	Elf(int x, int y) {
		this.x = x;
		this.y = y;
		this.lastX = 0;
		this.lastY = 0;
		directions.add(0);
		directions.add(1);
		directions.add(2);
		directions.add(3);
		this.dx.put(0, 0);
		this.dx.put(1, 0);
		this.dx.put(2, -1);
		this.dx.put(3, 1);
		this.dx.put(4, 0);
		this.dy.put(0, -1);
		this.dy.put(1, 1);
		this.dy.put(2, 0);
		this.dy.put(3, 0);
		this.dy.put(4, 0);
	}
	
	void prepareMove (int toMove) {
		this.toMove = toMove;
	}
	
	void move() {
		this.lastX = this.x;
		this.lastY = this.y;
		if (canMove) {
			this.x += dx.get(toMove);
			this.y += dy.get(toMove);
		}
		directions.add(directions.get(0));
		directions.remove(0);
	}
}