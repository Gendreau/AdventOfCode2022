package advent;

import java.io.*;
import java.util.*;

public class TenTwo {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input10.txt");
		Scanner sc = new Scanner(file);
		int register = 1;
		int cycle = 1;
		int pos = 0;
		
		while (sc.hasNextLine()) {
			String feed[] = sc.nextLine().split(" ");
			if (pos>39) {
				pos=0;
				System.out.println();
			}
			if (Math.abs(register-(pos))<=1) {
				System.out.print("#");
			}
			else System.out.print(".");
			pos++;
			if (feed[0].equals("addx")) {
				cycle++;
				if (pos>39) {
					pos = 0;
					System.out.println();
				}
				if (Math.abs(register-(pos))<=1) {
					System.out.print("#");
				}
				else System.out.print(".");
				pos++;
				register += Integer.parseInt(feed[1]);
			}
			cycle++;
		}
	}
}
