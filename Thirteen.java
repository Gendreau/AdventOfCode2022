package advent;

import java.util.*;
import java.io.*;

public class Thirteen implements Comparator<ArrayList<Object>> {
	public static Stack<Integer> skippy = new Stack<Integer>();
	
	public static void main(String[] args) throws FileNotFoundException {
		Thirteen thirteen = new Thirteen();
		int index = 0;
		int sumOfSuccess = 0;
		ArrayList<ArrayList<Object>> packets = new ArrayList<ArrayList<Object>>();
		File file = new File("/Users/gendreaum/Downloads/input13.txt");
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()) {
			index++;
			String packet1Text = sc.nextLine().replace("10","A");
			String packet2Text = sc.nextLine().replace("10", "A");
			skippy.clear();
			ArrayList<Object> packet1 = parseText(packet1Text);
			skippy.clear();
			ArrayList<Object> packet2 = parseText(packet2Text);
			packets.add(packet1);
			packets.add(packet2);
			if (thirteen.compare(packet1, packet2)==1) sumOfSuccess+=index;
			if (sc.hasNextLine()) sc.nextLine();
		}
		System.out.println("Sum of correct pair indices: " + sumOfSuccess);
		
		skippy.clear();
		ArrayList<Object> dividerPacket2 = parseText("[[2]]");
		packets.add(dividerPacket2);
		skippy.clear();
		ArrayList<Object> dividerPacket6 = parseText("[[6]]");
		packets.add(dividerPacket6);
		
		Collections.sort(packets, thirteen.reversed());
		System.out.println("Product of divider packet indices: " + ((packets.indexOf(dividerPacket2))+1)*((packets.indexOf(dividerPacket6))+1));
	}
	
	public static ArrayList<Object> parseText(String s) {
		ArrayList<Object> packet = new ArrayList<Object>();
		for (int i=1; i<s.length()-1; i++) {
			try {
				i+=skippy.pop();
			} catch (EmptyStackException e) {}
			if (Character.isDigit(s.charAt(i)) || s.charAt(i)=='A') {
				if (s.charAt(i)=='A') packet.add((int) 10);
				else packet.add((Character.getNumericValue(s.charAt(i))));
			}
			else if (s.charAt(i) == '[') {
				packet.add(parseText(s.substring(i)));
			}
			else if (s.charAt(i) == ']') {
				skippy.push(i);
				return packet;
			}
		}
		return packet;
	}
	
	@SuppressWarnings("unchecked")
	public int compare(ArrayList<Object> a, ArrayList<Object> b) {
		for (int i=0; i<a.size(); i++) {
			try {
				if (a.get(i) instanceof Integer && b.get(i) instanceof Integer) {
					if ((int) a.get(i) < (int) b.get(i)) return 1;
					else if ((int) a.get(i) > (int) b.get(i)) return -1;
				}
				else if (a.get(i) instanceof ArrayList && b.get(i) instanceof ArrayList) {
					int tmp = compare((ArrayList<Object>)a.get(i), (ArrayList<Object>)b.get(i));
					if (tmp != 0) return tmp;
				}
				else if (a.get(i) instanceof Integer && b.get(i) instanceof ArrayList) {
					ArrayList<Object> tmp = new ArrayList<Object>();
					tmp.add(a.get(i));
					int tmp2 = compare(tmp, (ArrayList<Object>)b.get(i));
					if (tmp2 == 1 || tmp2 == -1) return tmp2;
				}
				else if (a.get(i) instanceof ArrayList && b.get(i) instanceof Integer) {
					ArrayList<Object> tmp = new ArrayList<Object>();
					tmp.add(b.get(i));
					int tmp2 = compare((ArrayList<Object>)a.get(i),tmp);
					if (tmp2 == 1 || tmp2 == -1) return tmp2;
				}
			} catch (IndexOutOfBoundsException e) {
				return -1;
			}
		}
		if (a.size()==b.size()) return 0;
		else return 1;
	}
}