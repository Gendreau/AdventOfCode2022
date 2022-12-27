package advent;

import java.util.*;
import java.io.*;

public class TwentyTwoOne {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input22.txt");
		Scanner sc = new Scanner(file);
		char[][] maze = parseMaze(sc);
		ArrayList<Instruction> movements = parseMovements(sc);
		int xPos = 1 + indexOfAt(maze, 1, 'x', 'r');
		int yPos = 1 + indexOfAt(maze, xPos, 'y', 't');
		int direction = 0;
		int tpSpace;
		for (Instruction inst : movements) {
//			maze[yPos][xPos] = '%';
//			printMaze(maze);
//			maze[yPos][xPos] = '.';
			switch (direction) {
			case 0:
				for (int i=0; i<inst.steps; i++) {
					
					if (maze[yPos][xPos+1] == '.') {
						xPos++;
					}
					else if (maze[yPos][xPos+1] == '@') {
						tpSpace = indexOfAt(maze, yPos, 'x', 'r') + 1;
						if (maze[yPos][tpSpace] == '.') {
							xPos = tpSpace;
						}
						else if (maze[yPos][tpSpace] == '@') {
							while (maze[yPos][tpSpace]=='@') {
								tpSpace++;
							}
							if (maze[yPos][tpSpace] == '.') xPos = tpSpace;
							else break;
						}
					}
				}
				if (inst.rotation == 'R') direction = 1;
				else if (inst.rotation == 'L') direction = 3;
				break;
			case 1:
				for (int i=0; i<inst.steps; i++) {
					
					if (maze[yPos+1][xPos] == '.') {
						yPos++;
					}
					else if (maze[yPos+1][xPos] == '@') {
						tpSpace = indexOfAt(maze, xPos, 'y', 't') + 1;
						if (maze[tpSpace][xPos] == '.') {
							yPos = tpSpace;
						}
						else if (maze[tpSpace][xPos] == '@') {
							while (maze[tpSpace][xPos]=='@') {
								tpSpace++;
							}
							if (maze[tpSpace][xPos] == '.') yPos = tpSpace;
							else break;
						}
					}
				}
				if (inst.rotation == 'R') direction = 2;
				else if (inst.rotation == 'L') direction = 0;
				break;
			case 2:
				for (int i=0; i<inst.steps; i++) {
					if (maze[yPos][xPos-1]=='.') {
						xPos--;
					}
					else if (maze[yPos][xPos-1] == '@') {
						tpSpace = indexOfAt(maze, yPos, 'x', 'l') - 1;
						if (maze[yPos][tpSpace] == '.') {
							xPos = tpSpace;
						}
						else if (maze[yPos][tpSpace] == '@') {
							while (maze[yPos][tpSpace]=='@') {
								tpSpace--;
							}
							if (maze[yPos][tpSpace] == '.') xPos = tpSpace;
							else break;
						}
					}
				}
				if (inst.rotation == 'R') direction = 3;
				else if (inst.rotation == 'L') direction = 1;
				break;
			case 3:
				for (int i=0; i<inst.steps; i++) {
					if (maze[yPos-1][xPos] == '.') {
						yPos--;
					}
					else if (maze[yPos-1][xPos] == '@') {
						tpSpace = indexOfAt(maze,xPos,'y', 'b')-1;
						if (maze[tpSpace][xPos] == '.') {
							yPos = tpSpace;
						}
						else if (maze[tpSpace][xPos] == '@') {
								maze[yPos][xPos] = '%';
								printMaze(maze);
								maze[yPos][xPos] = '.';
								while (maze[tpSpace][xPos]=='@') {
									tpSpace--;
								}
								if (maze[tpSpace][xPos] == '.') yPos = tpSpace;
								else break;
						}
					}
				}
				if (inst.rotation == 'R') direction = 0;
				else if (inst.rotation =='L') direction = 2;
				break;
			}
		}
		
		System.out.println("x: " + xPos);
		System.out.println("y: " + yPos);
		System.out.println("Direction: " + direction);
		System.out.println((1000*yPos)+(4*xPos)+direction);
		maze[yPos][xPos] = '%';
		//printMaze(maze);
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
	
	public static int indexOfAt(char[][] maze, int index, char axis, char RLTB) {
		if (axis == 'x') {
			if (RLTB == 'r') {
				for (int i=0; i<maze[index].length; i++) {
					if (maze[index][i]=='@') return i;
				}
			}
			else if (RLTB == 'l') {
				for (int i=maze[index].length-1; i>=0; i--) {
					if (maze[index][i]=='@') return i;
				}
			}
		}
		else if (axis == 'y') {
			if (RLTB == 't') {
				for (int i=0; i<maze.length; i++) {
					if (maze[i][index] == '@') return i;
				}
			}
			else if (RLTB == 'b') {
				for (int i=maze.length-1; i>=0; i--) {
					if (maze[i][index]=='@') return i;
				}
			}
		}
		return -1;
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

class Instruction {
	int steps;
	char rotation;
	
	Instruction(int steps, char rotation) {
		this.steps = steps;
		this.rotation = rotation;
	}
}