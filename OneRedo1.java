package advent;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class OneRedo1 {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input.txt");
		Scanner sc = new Scanner(file);
		String line;
		ArrayList<Integer> list = new ArrayList<Integer>();
		int elf = 0;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			if (!line.isEmpty())
				elf += Integer.parseInt(line);
			else {
				list.add(elf);
				elf = 0;
			}
		}
		Collections.sort(list, Collections.reverseOrder());
		System.out.println(list.get(0));
		System.out.println(list.get(0) + list.get(1) + list.get(2));
	}
}
