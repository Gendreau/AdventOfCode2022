package advent;

import java.io.*;
import java.util.Scanner;

public class One {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input.txt");
		Scanner sc = new Scanner(file);
		String line;
		int max1 = 0;
		int max2 = 0;
		int max3 = 0;
		int elf = 0;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			if (!line.isEmpty())
				elf += Integer.parseInt(line);
			else {
				if (max1 < elf) {
					max3 = max2;
					max2 = max1;
					max1 = elf;
				}
				else if (max2 < elf) {
					max3 = max2;
					max2 = elf;
				}
				else if (max3 < elf) max3 = elf;
				elf = 0;
			}
		}
		System.out.println(max1);
		System.out.println(max1 + max2 + max3);
	}
}
