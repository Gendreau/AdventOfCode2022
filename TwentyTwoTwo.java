package advent;

import java.util.*;
import java.io.*;

public class TwentyTwoTwo {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input22.txt");
		Scanner sc = new Scanner(file);
		char[][] maze = parseMaze(sc);
		ArrayList<Instruction> movements = parseMovements(sc);
		int xPos = 51;
		int yPos = 1;
		int direction = 0;
		int[] teleport;
		int[] xSteps = {1, 0, -1, 0};
		int[] ySteps = {0, 1, 0, -1};
		for (Instruction inst : movements) {
			for (int i=0; i<inst.steps; i++) {
				if (maze[yPos+ySteps[direction]][xPos+xSteps[direction]] == '.') {
					xPos += xSteps[direction];
					yPos += ySteps[direction];
				}
				else if (maze[yPos+ySteps[direction]][xPos+xSteps[direction]] == '@') {
					teleport = cubify(maze, xPos+xSteps[direction], yPos+ySteps[direction], direction);
					if (maze[teleport[1]][teleport[0]] == '.') {
						xPos = teleport[0];
						yPos = teleport[1];
						direction = teleport[2];
					}
					else break;
				}
				else break;
			}
			if (inst.rotation == 'R') direction++;
			else if (inst.rotation == 'L') direction--;
			if (direction == 4) direction = 0;
			if (direction == -1) direction = 3;
		}

		
		System.out.println("x: " + xPos);
		System.out.println("y: " + yPos);
		System.out.println("Direction: " + direction);
		System.out.println((1000*yPos)+(4*xPos)+direction);
	}
	
	public static char[][] parseMaze(Scanner sc) {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		String[] feed = sc.nextLine().split("");
		while (sc.hasNextLine()) {
			lines.add(feed.clone());
			feed = sc.nextLine().split("");
			if (feed.length==1) break;
		}
		int maxLength = 0;
		for (int i=0; i<lines.size(); i++) if (lines.get(i).length > maxLength) maxLength = lines.get(i).length;
		char[][] maze = new char[lines.size()+2][maxLength + 2];
		for (int i=0; i<maze[0].length; i++) {
			maze[0][i] = ' ';
		}
		for (int i=1; i<maze.length-1; i++) {
			maze[i][0] = ' ';
			for (int j=1; j<maze[0].length; j++) {
				if (j-1<lines.get(i-1).length) {
					maze[i][j] = lines.get(i-1)[j-1].charAt(0);
				}
				else maze[i][j] = ' ';
			}
		}
		for (int i=0; i<maze[0].length; i++) {
			maze[maze.length-1][i] = ' ';
		}
		for (int i=0; i<maze.length-1; i++) {
			for (int j=0; j<maze[0].length-1; j++) {
				if (maze[i][j] == ' ' && maze[i+1][j] != ' ' && maze[i+1][j] != '@') maze[i][j] = '@';
				if (maze[i][j] != ' ' && maze[i][j] != '@' && maze[i+1][j] == ' ') maze[i+1][j] = '@';
				if (maze[i][j] == ' ' && maze[i][j+1] != ' ' && maze[i][j+1] != '@') maze[i][j] = '@';
				if (maze[i][j] != ' ' && maze[i][j] != '@' && maze[i][j+1] == ' ') maze[i][j+1] = '@';
			}
		}
		
		return maze;
	}
	
	public static ArrayList<Instruction> parseMovements(Scanner sc) {
		ArrayList<Instruction> movements = new ArrayList<Instruction>();
		String feed = sc.nextLine();
		while (sc.hasNextLine()) {
			feed += sc.nextLine();
		}
		String[] splitfeed = feed.split("(?<=\\D)(?=\\d)");
		splitfeed[splitfeed.length-1] += 'X';
		for (String s : splitfeed) {
			if (s.length()==2) movements.add(new Instruction(Character.getNumericValue(s.charAt(0)),s.charAt(1)));
			else movements.add(new Instruction(Integer.parseInt(s.substring(0,2)),s.charAt(2)));
		}
		return movements;
	}
	
	public static int[] cubify(char[][] maze, int x, int y, int dir) {	
		int[] newPos = new int[3];
		//6 right > 4 up
		//As start y increases, end x increases
		if (dir == 0 && x==51 && y<=200 && y>=151) {
			newPos[2] = 3;
			newPos[1] = 151-1;
			newPos[0] = y-100;
		}
		//6 down > 1 down
		//As start x increases, end x increases
		else if (dir == 1 && y == 201 && x>=1 && x<=50) {
			newPos[2] = 1;
			newPos[1] = 0 + 1;
			newPos[0] = x+100;
		}
		//6 left > 2 down
		//As start y increases, end x increases
		else if (dir == 2 && x == 0 && y>=151 && y<=200) {
			newPos[2] = 1;
			newPos[1] = 0 + 1;
			newPos[0] = y-100;
		}
		//5 left > 2 right
		//As start y increases, end y decreases
		else if (dir == 2 && x == 0 && y<=150 && y >= 101) {
			newPos[2] = 0;
			newPos[0] = 50 + 1;
			newPos[1] = 151-y;
		}
		//5 up > 3 right
		//As start x increases, end y increases
		else if (dir == 3 && y==100 && x>=1 && x <= 50) {
			newPos[2] = 0;
			newPos[0] = 50 + 1;
			newPos[1] = x+50;
		}
		//3 right > 1 up
		//As start y increases, end x increases
		else if (dir == 0 && x == 101 && y <= 100 && y >= 51) {
			newPos[2] = 3;
			newPos[1] = 51 - 1;
			newPos[0] = y+50;
		}
		//4 right > 1 left
		//As start y increases, end y decreases
		else if (dir == 0 && x == 101 && y >= 101 && y <= 150) {
			newPos[2] = 2;
			newPos[0] = 151-1;
			newPos[1] = 151-y;
		}
		//1 right > 4 left
		//As start y increases, end y decreases
		else if (dir == 0 && x == 151 && y >= 1 && y <=50) {
			newPos[2] = 2;
			newPos[0] = 101-1;
			newPos[1] = 151-y;
		}
		//1 up > 6 up
		//As start x increases, end x increases
		else if (dir == 3 && y == 0 && x >= 101 && x <= 150) {
			newPos[2] = 3;
			newPos[1] = 201 - 1;
			newPos[0] = x-100;
		}
		//1 down > 3 left
		//As start x increases, end y increases
		else if (dir == 1 && y == 51 && x >= 101 && x <= 150) {
			newPos[2] = 2;
			newPos[0] = 101 - 1;
			newPos[1] = x-50;
		}
		//2 up > 6 right
		//As start x increases, end y increases
		else if (dir == 3 && y == 0 && x >= 51 && x <= 100) {
			newPos[2] = 0;
			newPos[0] = 0 + 1;
			newPos[1] = x + 100;
		}
		//2 left > 5 right
		//As start y increases, end y decreases
		else if (dir == 2 && x == 50 && y >= 1 && y <= 50) {
			newPos[2] = 0;
			newPos[0] = 0 + 1;
			newPos[1] = 151-y;
		}
		//3 left > 5 down
		//As start y increases, end x increases
		else if (dir == 2 && x == 50 && y >= 51 && y <= 100) {
			newPos[2] = 1;
			newPos[1] = 100 + 1;
			newPos[0] = y-50;
		}
		//4 down > 6 left
		//As start x increases, end y increases
		else if (dir == 1 && y == 151 && x >= 51 && x <= 100) {
			newPos[2] = 2;
			newPos[0] = 51 - 1;
			newPos[1] = x + 100;
		}
		return newPos;
	}
	
	public static void printMaze(char[][] maze) {
		for (char[] a : maze) {
			for (char c : a) {
				System.out.print(c);
			}
			System.out.println();
		}
	}
}