package advent;

import java.io.*;
import java.util.Scanner;

public class TwoTwo {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input2.txt");
		Scanner sc = new Scanner(file);
		String line;
		int total = 0;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			switch (line) {
				case "A X":
					total += 3;
					break;
				case "A Y":
					total += 4;
					break;
				case "A Z":
					total += 8;
					break;
				case "B X":
					total += 1;
					break;
				case "B Y":
					total += 5;
					break;
				case "B Z":
					total += 9;
					break;
				case "C X":
					total += 2;
					break;
				case "C Y":
					total += 6;
					break;
				case "C Z":
					total += 7;
					break;
			}
		}
		System.out.println(total);
	}
}