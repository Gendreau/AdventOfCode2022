package advent;

import java.io.*;
import java.util.*;

public class Seven {
	
	public static ArrayList<Directory> fileTree = new ArrayList<Directory>();
	public static int pointer = 0;
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("/Users/gendreaum/Downloads/input7.txt");
		Scanner sc = new Scanner(file);
		fileTree.add(new Directory("root"));
		sc.nextLine();
		while (sc.hasNextLine()) {
			parseLine(sc.nextLine());
		}
		//fileTree.get(0).printTree();
		System.out.println("Combined size of directories smaller than 100000: " + fileTree.get(0).loopSize());
		System.out.println("Disk space that must be freed: " + (fileTree.get(0).getSize()-40000000));
		ArrayList<Integer> sizes = fileTree.get(0).getSizes();
		for (int i=0; i<sizes.size(); i++) {
			if (sizes.get(i) < (fileTree.get(0).getSize())-40000000) {
				sizes.set(i, Integer.MAX_VALUE);
			}
		}
		Collections.sort(sizes);
		System.out.println("Size of smallest directory to be deleted: " + sizes.get(0));
		sc.close();
	}
	
	public static void parseLine(String feed) {
		String spl[] = feed.split(" ");
		
		if (spl[0].equals("$")) {
			if (spl[1].equals("cd")) {
				if (spl[2].equals("..")) {
					fileTree.remove(pointer);
					pointer--;
				}
				else {
					fileTree.add(fileTree.get(pointer).getChild(spl[2]));
					pointer++;
				}
			}
		}
		else if (spl[0].equals("dir")) {
			fileTree.get(pointer).addChildDir(new Directory(spl[1]));
		}
		else {
			fileTree.get(pointer).addFile(new DataFile(Integer.parseInt(spl[0]), feed));
		}
	}
}

class Directory {
	
	String name;
	ArrayList<Directory> children;
	ArrayList<DataFile> files;
	static int printSpaces = 0;
	static int bigTotal = 0;
	static ArrayList<Integer> sizes = new ArrayList<Integer>();
	
	Directory(String name) {
		this.name = name;
		this.children = new ArrayList<Directory>();
		this.files = new ArrayList<DataFile>();
	}
	
	void addChildDir(Directory d) {
		children.add(d);
	}
	
	void addFile(DataFile f) {
		files.add(f);
	}
	
	String getName() {
		return this.name;
	}
	
	Directory getChild(String name) {
		for (Directory d : children) {
			if (d.getName().equals(name)) {
				return d;
			}
		}
		return null;
	}
	
	int getSize() {
		int total = 0;
		for (Directory d : this.children) {
			total += d.getSize();
		}
		for (DataFile f : this.files) {
			total += f.getSize();
		}
		
		return total;
	}
	
	int loopSize() {
		sizes.add(this.getSize());
		if (this.getSize() <= 100000) {
			bigTotal += this.getSize();
		}
		for (Directory d : this.children) {
			d.loopSize();
		}
		return bigTotal;
	}
	
	ArrayList<Integer> getSizes() {
		return sizes;
	}
	
	void printTree() {
		for (int i=0; i<printSpaces; i++) System.out.print(" ");
		System.out.println("L> " + this.name);
		printSpaces += 3;
		for (DataFile f : this.files) {
			for (int i=0; i<printSpaces; i++) System.out.print(" ");
			System.out.println("L> " + f.getName());
		}
		for (Directory d : this.children) {
			d.printTree();
			printSpaces -= 3;
		}
	}
	
}

class DataFile {
	
	int size;
	String name;
	
	DataFile(int size, String name) {
		this.size = size;
		this.name = name;
	}
	
	int getSize() {
		return this.size;
	}
	
	String getName() {
		return this.name;
	}
}