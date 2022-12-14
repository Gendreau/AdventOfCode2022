package advent;

import java.util.*;
import java.io.*;

public class Eleven {
	public static void main(String[] args) throws FileNotFoundException {
		final int NUMBER_OF_ROUNDS = 10000;
		
		File file = new File("/Users/gendreaum/Downloads/input11.txt");
		Scanner sc = new Scanner(file);
		ArrayList<Monkey> monkeys = parseMonkeys(sc);
		ArrayList<Long> inspectCount = new ArrayList<Long>();
		long period = 1;
		
		for (Monkey m : monkeys) {
			period *= m.getTestConstant();
		}
		for (int i=0; i<NUMBER_OF_ROUNDS; i++) {
			for (Monkey m : monkeys) {
				m.monkeyBusiness(monkeys, period);
			}
		}
		for (Monkey m : monkeys) {
			inspectCount.add(m.getInspectCount());
		}
		Collections.sort(inspectCount, Collections.reverseOrder());
		System.out.println("Monkey Business score after " + NUMBER_OF_ROUNDS + " rounds: " + inspectCount.get(0)*inspectCount.get(1));
	}
	
	public static ArrayList<Monkey> parseMonkeys(Scanner sc) {
		ArrayList<Monkey> monkeys = new ArrayList<Monkey>();
		int count = 0;
		while (sc.hasNextLine()) {
			String[] feed = sc.nextLine().split("[ :]");
			monkeys.add(new Monkey());
			feed = sc.nextLine().substring(18).split("[, ]+");
			for (String s : feed) {
				monkeys.get(count).addItem(Long.parseLong(s));
			}
			feed = sc.nextLine().split(" ");
			if (feed[7].equals("old")) monkeys.get(count).setOperation("^", 2);
			else monkeys.get(count).setOperation(feed[6], Long.parseLong(feed[7]));
			feed=sc.nextLine().split(" ");
			monkeys.get(count).setTest(Long.parseLong(feed[5]));
			feed=sc.nextLine().split(" ");
			monkeys.get(count).setTrueTarget(Integer.parseInt(feed[9]));
			feed=sc.nextLine().split(" ");
			monkeys.get(count).setFalseTarget(Integer.parseInt(feed[9]));
			if(sc.hasNextLine()) sc.nextLine();
			count++;
		}
		return monkeys;
	}
}

class Monkey {
	ArrayList<Long> backpack = new ArrayList<Long>();
	String operand = new String();
	long opConstant;
	long testConstant;
	int trueTarget;
	int falseTarget;
	long inspectCount;
	
	Monkey() {
		this.inspectCount = 0L;
	}
	
	void addItem(long worryLevel) {
		backpack.add(worryLevel);
	}
	
	ArrayList<Long> getBackpack() {
		return this.backpack;
	}
	
	Long getTestConstant() {
		return this.testConstant;
	}
	
	void setOperation(String operand, long opConstant) {
		this.operand = operand;
		this.opConstant = opConstant;
	}
	
	void setTest (long testConstant) {
		this.testConstant = testConstant;
	}
	
	void setTrueTarget(int trueTarget) {
		this.trueTarget = trueTarget;
	}
	
	void setFalseTarget(int falseTarget) {
		this.falseTarget = falseTarget;
	}
	
	void monkeyBusiness(ArrayList<Monkey> monkeys, long period) {
		for (long item : this.backpack) {
			switch (this.operand) {
				case "*": item*=this.opConstant;
					break;
				case "+": item+=this.opConstant;
					break;
				case "^": item = (long) Math.pow(item, this.opConstant);
					break;
			}
			//item/=3;
			item %= period;
			if (item % this.testConstant == 0) monkeys.get(this.trueTarget).addItem(item);
			else monkeys.get(this.falseTarget).addItem(item);
			this.inspectCount++;
		}
		this.backpack.clear();
	}
	
	long getInspectCount() {
		return this.inspectCount;
	}
}
