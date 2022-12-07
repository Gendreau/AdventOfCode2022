package advent;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class ThreeOne {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input3.txt");
		Scanner sc = new Scanner(file);
		String line;
		char shared = 'f';
		int total = 0;
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			for (int i=0; i<line.length()/2; i++) {
				for (int j=line.length()/2; j<line.length(); j++) {
					if (line.charAt(i) == line.charAt(j)) shared = line.charAt(i);
				}
			}
			for (int i=0; i<alphabet.length(); i++) {
				if (alphabet.charAt(i) == shared) total += i+1;
			}
		}
		System.out.println(total);
	}
}