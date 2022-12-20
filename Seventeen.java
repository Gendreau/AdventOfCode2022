package advent;

import java.util.*;
import java.io.*;

public class Seventeen {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input17.txt");
		Scanner sc = new Scanner(file);
		int numPiecesDropped = 0;
		ArrayList<String> jets = parseText(sc);
		ArrayList<char[]> tetris = new ArrayList<char[]>();
		char[] line = {'+','-','-','-','-','-','-','-','+'};
		tetris.add(line);
		
		double cycles = Math.floor(Math.pow(10, 12)/1720)*2738;
		double PIECES_TO_DROP = Math.pow(10,12)%1720;
		
		for (int i=0; numPiecesDropped < PIECES_TO_DROP; numPiecesDropped++) {
			
			boolean trigger = true;
			tetrisPiece pieceToDrop;
			int topLayer;
			
			switch(numPiecesDropped%5) {
			case 0:
				topLayer = getTopLayer(tetris);
				tetris=addRows(tetris, (tetris.size()-1-topLayer) >= 0 ? 4-(tetris.size()-1-topLayer) : 0);
				pieceToDrop = new tetrisPiece(3, topLayer+4, "row");
				break;
			case 1:
				topLayer = getTopLayer(tetris);
				tetris=addRows(tetris, (tetris.size()-1-topLayer) >= 0 ? 6-(tetris.size()-1-topLayer) : 0);
				pieceToDrop = new tetrisPiece(3, topLayer+4, "plus");
				break;
			case 2:
				topLayer = getTopLayer(tetris);
				tetris=addRows(tetris, (tetris.size()-1-topLayer) >= 0 ? 6-(tetris.size()-1-topLayer) : 0);
				pieceToDrop = new tetrisPiece(3, topLayer+4, "corner");
				break;
			case 3:
				topLayer = getTopLayer(tetris);
				tetris=addRows(tetris, (tetris.size()-1-topLayer) >= 0 ? 7-(tetris.size()-1-topLayer) : 0);
				pieceToDrop = new tetrisPiece(3, topLayer+4, "tower");
				break;
			case 4:
				topLayer = getTopLayer(tetris);
				tetris=addRows(tetris, (tetris.size()-1-topLayer) >= 0 ? 5-(tetris.size()-1-topLayer) : 0);
				pieceToDrop = new tetrisPiece(3, topLayer+4, "square");
				break;
			default:
				pieceToDrop = new tetrisPiece(-1, -1, null);
			}
			for (;trigger; i++) {
				if (jets.get(i%jets.size()).equals(">")) pieceToDrop.tryMoveRight(tetris);
				else if (jets.get(i%jets.size()).equals("<")) pieceToDrop.tryMoveLeft(tetris);
				trigger = pieceToDrop.tryMoveDown(tetris);
			}
			tetris = pieceToDrop.stampArray(tetris);
			//if (pieceToDrop.shape.equals("row")) System.out.println("Row Piece | Pieces Dropped: " + numPiecesDropped + " | Stream: " + i%jets.size() + " | Height: " + getTopLayer(tetris));
		}
		//printTetris(tetris);
		System.out.printf("%.1f", getTopLayer(tetris)+cycles);
	}
	
	public static ArrayList<String> parseText(Scanner sc) {
		String[] feed = sc.next().split("");
		ArrayList<String> jets = new ArrayList<String>();
		for (String s : feed) {
			jets.add(s);
		}
		return jets;
	}
	
	public static void printTetris(ArrayList<char[]> tetris) {
		for (int i=tetris.size()-1; i>=0; i--) {
			for (char c : tetris.get(i)) {
				System.out.print(c);
			}
			System.out.println();
		}
	}

	public static ArrayList<char[]> addRows(ArrayList<char[]> tetris, int n) {
		for (int i=0; i<n; i++) {
			char[] line = {'|','.','.','.','.','.','.','.','|'};
			tetris.add(line);
		}
		return tetris;
	}

	public static int getTopLayer(ArrayList<char[]> tetris) {
		ArrayList<Integer> topOfEachColumn = new ArrayList<Integer>();
		for (int i=1; i<tetris.get(0).length-1; i++) {
			for (int j=tetris.size()-1; j>=0; j--) {
				if (tetris.get(j)[i] != '.') topOfEachColumn.add(j);
			}
		}
		return Collections.max(topOfEachColumn);
	}
}

class tetrisPiece {
	int x, y;
	String shape;
	
	tetrisPiece(int x, int y, String shape) {
		this.x=x;
		this.y=y;
		this.shape=shape;
	}
	
	boolean tryMoveDown(ArrayList<char[]> tetris) {
		switch (this.shape) {
		case "row":
			if (tetris.get(this.y-1)[this.x] == '.' && tetris.get(this.y-1)[this.x+1] =='.'
					&& tetris.get(this.y-1)[this.x+2] == '.' && tetris.get(this.y-1)[this.x+3] == '.') {
				this.y--;
				return true;
			}
			break;
		case "plus":
			if (tetris.get(this.y-1)[this.x+1] == '.' && tetris.get(this.y)[this.x] == '.'
					&& tetris.get(this.y)[this.x+2] == '.') {
				this.y--;
				return true;
			}
			break;
		case "corner":
			if (tetris.get(this.y-1)[this.x] == '.' && tetris.get(this.y-1)[this.x+1] =='.'
			&& tetris.get(this.y-1)[this.x+2] == '.') {
				this.y--;
				return true;
			}
			break;
		case "tower":
			if (tetris.get(this.y-1)[this.x] == '.') {
				this.y--;
				return true;
			}
			break;
		case "square":
			if (tetris.get(this.y-1)[this.x] == '.' && tetris.get(this.y-1)[this.x+1] == '.') {
				this.y--;
				return true;
			}
			break;
		}
		return false;
	}
	
	boolean tryMoveLeft(ArrayList<char[]> tetris) {
		switch (this.shape) {
		case "row":
			if (tetris.get(this.y)[this.x-1] == '.') {
				this.x--;
				return true;
			}
			break;
		case "plus":
			if (tetris.get(this.y)[this.x] == '.' && tetris.get(this.y+1)[this.x-1] == '.'
					&& tetris.get(this.y+2)[this.x] == '.') {
				this.x--;
				return true;
			}
			break;
		case "corner":
			if (tetris.get(this.y)[this.x-1] == '.' && tetris.get(this.y+1)[this.x+1] == '.'
					&& tetris.get(this.y+2)[this.x+2] == '.') {
				this.x--;
				return true;
			}
			break;
		case "tower":
			if (tetris.get(this.y)[this.x-1] == '.' && tetris.get(this.y+1)[this.x-1] == '.'
					&& tetris.get(this.y+2)[this.x-1] == '.' && tetris.get(this.y+3)[this.x-1] == '.') {
				this.x--;
				return true;
			}
			break;
		case "square":
			if (tetris.get(this.y)[this.x-1] == '.' && tetris.get(this.y+1)[this.x-1] == '.') {
				this.x--;
				return true;
			}
			break;
		}
		return false;
	}
	
	boolean tryMoveRight(ArrayList<char[]> tetris) {
		switch (this.shape) {
		case "row":
			if (tetris.get(this.y)[this.x+4] == '.') {
				this.x++;
				return true;
			}
			break;
		case "plus":
			if (tetris.get(this.y)[this.x+2] == '.' && tetris.get(this.y+1)[this.x+3] == '.'
					&& tetris.get(this.y+2)[this.x+2] == '.') {
				this.x++;
				return true;
			}
			break;
		case "corner":
			if (tetris.get(this.y)[this.x+3] == '.' && tetris.get(this.y+1)[this.x+3] == '.'
					&& tetris.get(this.y+2)[this.x+3] == '.') {
				this.x++;
				return true;
			}
			break;
		case "tower":
			if (tetris.get(this.y)[this.x+1] == '.' && tetris.get(this.y+1)[this.x+1] == '.'
					&& tetris.get(this.y+2)[this.x+1] == '.' && tetris.get(this.y+3)[this.x+1] == '.') {
				this.x++;
				return true;
			}
			break;
		case "square":
			if (tetris.get(this.y)[this.x+2] == '.' && tetris.get(this.y+1)[this.x+2] == '.') {
				this.x++;
				return true;
			}
			break;
		}
		return false;
	}

	ArrayList<char[]> stampArray(ArrayList<char[]> tetris) {
		switch(this.shape) {
		case "row":
			for (int i=0; i<4; i++) tetris.get(this.y)[this.x+i] = '#';
			break;
		case "plus":
			tetris.get(this.y)[this.x+1] = '#';
			tetris.get(this.y+1)[this.x] = '#';
			tetris.get(this.y+1)[this.x+1] = '#';
			tetris.get(this.y+1)[this.x+2] = '#';
			tetris.get(this.y+2)[this.x+1] = '#';
			break;
		case "corner":
			for (int i=0; i<3; i++) {
				tetris.get(this.y)[this.x+i] = '#';
				tetris.get(this.y+i)[this.x+2] = '#';
			}
			break;
		case "tower":
			for (int i=0; i<4; i++) tetris.get(this.y+i)[this.x]= '#'; 
			break;
		case "square":
			tetris.get(this.y)[this.x] = '#';
			tetris.get(this.y)[this.x+1] = '#';
			tetris.get(this.y+1)[this.x] = '#';
			tetris.get(this.y+1)[this.x+1] = '#';
			break;
		}
		return tetris;
	}
}
