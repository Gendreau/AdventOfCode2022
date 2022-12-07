package advent;

import java.io.*;
import java.util.*;

public class Six {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input6.txt");
		Scanner sc = new Scanner(file);
		String input = sc.nextLine();
		System.out.println(findPacketStart(input, 4));
		System.out.println(findPacketStart(input, 14));
		sc.close();
	}
	public static int findPacketStart(String s, int markerLength) {
		HashSet<Character> checker = new HashSet<Character>();
		for (int i=0; i<s.length()-markerLength+1; i++) {
			for (int j=0; j<markerLength; j++) {
				checker.add(s.charAt(i+j));
			}
			if (checker.size() == markerLength) {
				return i+markerLength;
			}
			checker.clear();
		}
		return 0;
	}
}