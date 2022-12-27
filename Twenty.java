package advent;

import java.util.*;
import java.io.*;

public class Twenty {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input20.txt");
		Scanner sc = new Scanner(file);
		ArrayList<Nulean> sequence = parseText(sc);
		for (int x=0; x<10; x++) {
			for (int placemark=0; placemark<sequence.size(); placemark++) {
				double value = 0;
				int index = -1;
				for (Nulean n : sequence) {
					if (n.i == placemark) {
						value = n.n;
						index = sequence.indexOf(n);
					}
				}

				sequence.remove(index);
				int normalizedValue = (int)(value % sequence.size());
				
				if (normalizedValue == 0) {
					sequence.add(index, new Nulean(value, placemark));
				}
				else if (normalizedValue + index == 0) {
					sequence.add(new Nulean(value,placemark));
				}
				else if (normalizedValue + index == sequence.size()) {
					sequence.add(0, new Nulean(value, placemark));
				}
				else if (normalizedValue + index < 0) {
					normalizedValue += index;
					sequence.add(sequence.size()+normalizedValue, new Nulean(value, placemark));
				}
				else if (normalizedValue + index > sequence.size()) {
					normalizedValue += index;
					sequence.add(normalizedValue-sequence.size(), new Nulean(value, placemark));
				}
				else {
					sequence.add(normalizedValue+index, new Nulean(value, placemark));
				}
			}
		}
		int indexOfZero = -1;
		for (Nulean n : sequence) {
			if (n.n == 0) {
				indexOfZero = sequence.indexOf(n);
			}
		} 
		int[] magicNumbers = {(indexOfZero+1000)%sequence.size(), (indexOfZero+2000)%sequence.size(), (indexOfZero+3000)%sequence.size()};
		//System.out.println(sequence.get(magicNumbers[0]).n + " + " + sequence.get(magicNumbers[1]).n + " + " + sequence.get(magicNumbers[2]).n + " = " + (sequence.get(magicNumbers[0]).n + sequence.get(magicNumbers[1]).n + sequence.get(magicNumbers[2]).n));
		System.out.printf("%.1f", sequence.get(magicNumbers[0]).n + sequence.get(magicNumbers[1]).n + sequence.get(magicNumbers[2]).n);
	}
	
	public static ArrayList<Nulean> parseText(Scanner sc) {
		final double multiplier = 811589153;
		ArrayList<Nulean> sequence = new ArrayList<Nulean>();
		for (int i=0; sc.hasNextLine(); i++) {
			sequence.add(new Nulean(Double.parseDouble(sc.nextLine()),i,multiplier));
		}
		return sequence;
	}
}

class Nulean {
	double n;
	int i;
	
	Nulean(double n, int i, double multiplier) {
		this.n = n*multiplier;
		this.i = i;
	}
	
	Nulean(double n, int i) {
		this.n = n;
		this.i = i;
	}
}