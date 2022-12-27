package advent;

import java.util.*;
import java.io.*;

public class TwentyOne {
	static final boolean IsPartTwo = true;
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input21.txt");
		Scanner sc = new Scanner(file);
		HashMap<String, Shout> monkeys = parseText(sc);
		if (IsPartTwo) {
			System.out.printf("%.1f", binarySearch(monkeys, monkeys.get("root").operand1, monkeys.get("root").operand2, 3000000000000.0, 4000000000000.0));
		}
		else System.out.printf("%.1f", getShout(monkeys, "root"));
	}
	
	public static HashMap<String, Shout> parseText(Scanner sc) {
		HashMap<String, Shout> monkeys = new HashMap<String, Shout>();
		while (sc.hasNextLine()) {
			String[] feed = sc.nextLine().split("[ :]");
			if (feed.length>3) {
				monkeys.put(feed[0], new Shout(feed[2],feed[4],feed[3]));
			}
			else monkeys.put(feed[0], new Shout(Integer.parseInt(feed[2])));
		}
		return monkeys;
	}
	
	public static double getShout(HashMap<String, Shout> monkeys, String name) {
		Shout currentShout = monkeys.get(name);
		if (currentShout.dependent) {
			switch(currentShout.operator) {
			case "+":
				return (getShout(monkeys, currentShout.operand1) + getShout(monkeys, currentShout.operand2));
			case "-":
				return (getShout(monkeys, currentShout.operand1) - getShout(monkeys, currentShout.operand2));
			case "*":
				return (getShout(monkeys, currentShout.operand1) * getShout(monkeys, currentShout.operand2));
			case "/":
				return (getShout(monkeys, currentShout.operand1) / getShout(monkeys, currentShout.operand2));
			}
		}
		return currentShout.number;
	}

	public static double binarySearch(HashMap<String, Shout> monkeys, String vary, String goal, double upperBound, double lowerBound) {
		double testNum = (upperBound+lowerBound)/2;
		monkeys.put("humn", new Shout(testNum));
		if (getShout(monkeys,vary) == getShout(monkeys,goal)) {
			return testNum;
		}
		else if (getShout(monkeys, vary)<getShout(monkeys,goal)) return binarySearch(monkeys, vary, goal, upperBound, testNum);
		else return binarySearch(monkeys, vary, goal, testNum, lowerBound);
	}
}

class Shout {
	String operand1, operand2, operator;
	boolean dependent;
	double number;
	
	Shout(String operand1, String operand2, String operator) {
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.operator = operator;
		this.dependent = true;
	}
	
	Shout (double n) {
		this.number = n;
		this.dependent = false;
	}
}