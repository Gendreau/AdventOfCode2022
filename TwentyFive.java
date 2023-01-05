package advent;

import java.util.*;
import java.io.*;

public class TwentyFive {
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input25.txt");
		Scanner sc = new Scanner(file);
		HashMap<String, String> lookup = new HashMap<String, String>();
		lookup.put("==", "-1");lookup.put("=-", "-2");lookup.put("=0", "0=");lookup.put("=1", "0-");lookup.put("=2", "00");
		lookup.put("-=", "-2");lookup.put("--", "0=");lookup.put("-0", "0-");lookup.put("-1", "00");lookup.put("-2", "01");
		lookup.put("0=", "0=");lookup.put("0-", "0-");lookup.put("00", "00");lookup.put("01", "01");lookup.put("02", "02");
		lookup.put("1=", "0-");lookup.put("1-", "00");lookup.put("10", "01");lookup.put("11", "02");lookup.put("12", "1=");
		lookup.put("2=", "00");lookup.put("2-", "01");lookup.put("20", "02");lookup.put("21", "1=");lookup.put("22", "1-");
		String total = new String();
		while (sc.hasNextLine()) {
			String feed = sc.nextLine();
			if (total.isBlank()) total = feed;
			else {
				total = addQuinary(total, feed, lookup);
			}
		}
		System.out.println(total);
	}
	
	public static String addQuinary(String total, String feed, HashMap<String, String> lookup) {
		String ret = "";
		String carry = "0";
			for (int i=feed.length()-1, j=total.length()-1; i>=0 && j>=0; i--, j--) {
				String digitSum = lookup.get("" + feed.charAt(i) + total.charAt(j));
				String carrySum = lookup.get(carry + digitSum.charAt(1));
				ret = carrySum.charAt(1) + ret;
				carry = lookup.get("" + digitSum.charAt(0) + carrySum.charAt(0)).substring(1);
			}
			if (feed.length() > total.length()) {
				for (int i=feed.length()-total.length()-1; i>=0; i--) {
					String carrySum = lookup.get(carry+feed.charAt(i));
					ret = carrySum.charAt(1) + ret;
					carry = "" + carrySum.charAt(0);
				}
			}
			else if (total.length() > feed.length()) {
				for (int i=total.length()-feed.length()-1; i>=0; i--) {
					String carrySum = lookup.get(carry+total.charAt(i));
					ret = carrySum.charAt(1) + ret;
					carry = "" + carrySum.charAt(0);
				}
			}
			if (!carry.equals("0")) ret = carry + ret;
			return ret;
	}
}