package advent;

import java.io.*;
import java.util.Scanner;

public class Four {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input4.txt");
		Scanner sc = new Scanner(file);
		String line;
		String[] arr;
		int left, right;
		int compOverlap = 0;
		int someOverlap = 0;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			arr = line.split("[-,]");
			left = Integer.parseInt(arr[2])-Integer.parseInt(arr[0]);
			right = Integer.parseInt(arr[3])-Integer.parseInt(arr[1]);
			if (left*right <= 0) compOverlap++;
			left = (Integer.parseInt(arr[1])-Integer.parseInt(arr[2]));
			right = Integer.parseInt(arr[0])-Integer.parseInt(arr[3]);
			if (left*right <=0) someOverlap++;
		}
		System.out.println(compOverlap);
		System.out.println(someOverlap);
	}
}