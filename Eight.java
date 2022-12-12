package advent;

import java.io.*;
import java.util.*;

public class Eight {
	public static void main(String[] args) throws FileNotFoundException {
		
		File file = new File("/Users/gendreaum/Downloads/input8.txt");
		Scanner sc = new Scanner(file);
		int numVisibleTrees = 0;
		ArrayList<ArrayList<Integer>> forestRows = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> forestCols = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> scenicScores = new ArrayList<Integer>();
		
		for (int i=0; sc.hasNextLine(); i++) {
			forestRows.add(new ArrayList<Integer>());
			String[] feed = (sc.nextLine().split(""));
			for (String s : feed) {
				forestRows.get(i).add(Integer.parseInt(s));
			}
			if (i==0) numVisibleTrees += forestRows.get(i).size() * 2;
		}
		numVisibleTrees += (forestRows.size()-2)*2;
		
		//i = current column
		for (int i=0; i<forestRows.get(0).size(); i++) {
			forestCols.add(new ArrayList<Integer>());
			//j = current row
			for (int j=0; j<forestRows.size(); j++) {
				forestCols.get(i).add(forestRows.get(j).get(i));
			}
		}
		
		//Find number of visible trees and scenic scores
		for (int i=1; i<forestRows.size()-1; i++) {
			for (int j=1; j<forestRows.get(i).size()-1; j++) {
				boolean isVisible = false;
				int lineMax;
				int scenicScore = 1;
				
				//Check left-side visibility
				lineMax = Collections.max(forestRows.get(i).subList(0, j));
				if (lineMax < forestRows.get(i).get(j) && !isVisible) {
					numVisibleTrees++;
					isVisible = true;
				}
				
				//Check left-side scenic score
				scenicScore *= findNWScenicScore(forestRows, i, j);
				
				//Check right-side visibility
				lineMax = Collections.max(forestRows.get(i).subList(j+1, forestRows.get(i).size()));
				if (lineMax < forestRows.get(i).get(j) && !isVisible) {
					numVisibleTrees++;
					isVisible = true;
				}
				
				//Check right-side scenic score
				scenicScore *= findSEScenicScore(forestRows, i, j);
				
				//Check for top-side visibility
				lineMax = Collections.max(forestCols.get(j).subList(0, i));
				if (lineMax < forestCols.get(j).get(i) && !isVisible) {
					numVisibleTrees++;
					isVisible = true;
				}
				
				//Check top-side scenic score
				scenicScore *= findNWScenicScore(forestCols, j, i);
				
				//Check for bottom-side visibility
				lineMax = Collections.max(forestCols.get(j).subList(i+1, forestCols.get(j).size()));
				if (lineMax < forestCols.get(j).get(i) && !isVisible) {
					numVisibleTrees++;
					isVisible = true;
				}
				
				//Check bottom-side scenic score
				scenicScore *= findSEScenicScore(forestCols, j, i);
				
				scenicScores.add(scenicScore);
			}
		}
		
		
		System.out.println("Number of visible trees: " + numVisibleTrees);
		System.out.println("Highest scenic score: " + Collections.max(scenicScores));
//		for (ArrayList<Integer> al : forestRows) {
//			System.out.println(al);
//		}
		
		sc.close();
	}
	
	public static int findNWScenicScore(ArrayList<ArrayList <Integer>> forestRows, int i, int j) {
		for (int k=1; k<=j; k++) {
			if (forestRows.get(i).get(j-k) >= forestRows.get(i).get(j)) {
				return k;
			}
		}
		return j;
		}
	
	public static int findSEScenicScore(ArrayList<ArrayList<Integer>> forestRows, int i, int j) {
		for (int k=1; k<=forestRows.get(i).size()-j-1; k++) {
			if (forestRows.get(i).get(j+k) >= forestRows.get(i).get(j)) {
				return k;
			}
		}
		return forestRows.get(i).size()-j-1;
	}
	
	
}