package advent;

import java.io.*;
import java.util.*;

public class TenOne {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input10.txt");
		Scanner sc = new Scanner(file);
		int register = 1;
		int cycle = 1;
		int signalStrength = 0;
		ArrayList<Integer> critCycles = new ArrayList<Integer>();
		
		critCycles.add(20);
		critCycles.add(60);
		critCycles.add(100);
		critCycles.add(140);
		critCycles.add(180);
		critCycles.add(220);
		
		while (sc.hasNextLine()) {
			String feed[] = sc.nextLine().split(" ");
			if (critCycles.contains(cycle)) {
				signalStrength += cycle*register;
			}
			if (feed[0].equals("addx")){
				cycle++;
				if (critCycles.contains(cycle)) {
					signalStrength += cycle*register;
				}
				register += Integer.parseInt(feed[1]);
			}
			cycle++;
		}
		System.out.println(signalStrength);
	}
}
