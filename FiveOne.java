package advent;

import java.io.*;
import java.util.*;

public class FiveOne {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input5.txt");
		Scanner sc = new Scanner(file);
		String line;
		String[] arr;
		List<Stack<Character>> stacks = new ArrayList<Stack<Character>>(9);
		String[] lists = new String[]{"DLJRVGF", "TPMBVHJS", "VHMFDGPC", "MDPNGQ","JLHNF","NFVQDGTZ","FDBL","MJBSVDN","GLD"};
		for (int i=0; i<9; i++) {
			stacks.add(new Stack<Character>());
			for (int j=0; j<lists[i].length();j++) {
				stacks.get(i).push(lists[i].charAt(j));
			}
		}
		for (int i=0; i<10; i++) {
			line = sc.nextLine();
		}
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			arr = line.split(" ");
			for (int i = 0; i < Integer.parseInt(arr[1]); i++) {
				stacks.get(Integer.parseInt(arr[5])-1).push(stacks.get(Integer.parseInt(arr[3])-1).pop());
			}
		}
		System.out.println(stacks);
		sc.close();
	}
}