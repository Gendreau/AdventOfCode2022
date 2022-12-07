package advent;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class ThreeTwo {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input3.txt");
		Scanner sc = new Scanner(file);
		String line1, line2, line3;
		char shared = 'f';
		int total = 0;
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		while(sc.hasNextLine()) {
			line1 = sc.nextLine();
			line2 = sc.nextLine();
			line3 = sc.nextLine();
			for (int i=0; i<line1.length(); i++) {
				for (int j=0; j<line2.length(); j++) {
					if (line1.charAt(i) == line2.charAt(j)) {
						for (int k=0; k<line3.length(); k++) {
							if (line2.charAt(j) == line3.charAt(k)) shared = line3.charAt(k);
						}
					}
				}
			}
			for (int i=0; i<alphabet.length(); i++) {
				if (alphabet.charAt(i) == shared) total += i+1;
			}
		}
		System.out.println(total);
	}
}