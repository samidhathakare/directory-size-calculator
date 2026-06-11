package com.filesystem;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		DirectoryNode root = SeedData.buildTree();
		FileSystem fs = new FileSystem(root);
		Scanner scanner = new Scanner(System.in);

		printBanner();

		while (true) {
			System.out.print("[" + fs.getCurrentDirName() + "]$ ");
			String line = scanner.nextLine().trim();
			if (line.isEmpty())
				continue;

			String[] parts = line.split("\\s+", 2);
			String command = parts[0].toLowerCase();
			String arg = parts.length > 1 ? parts[1] : "";

			switch (command) {
				case "cd" -> {
					if (arg.isEmpty())
						System.out.println("Usage: cd <dir>  |  cd ..  |  cd /");
					else
						System.out.println(fs.cd(arg));
				}
				case "ls" -> System.out.println(fs.ls());
				case "size" -> System.out.println(fs.size());
				case "pwd" -> System.out.println(fs.getCurrentPath());
				case "help" -> printHelp();
				case "exit", "quit" -> {
					System.out.println("Goodbye!");
					scanner.close();
					return;
				}
				default ->
					System.out.println("Unknown command: '" + command + "'. Type 'help' for available commands.");
			}
			System.out.println();
		}
	}

	private static void printBanner() {
		System.out.println("Directory Size Calculator  v1.0   ");
		System.out.println("Type 'help' to see available commands.");
		System.out.println();
	}

	private static void printHelp() {
		System.out.println("Available commands:");
		System.out.println("  ls          List contents of current directory");
		System.out.println("  cd <name>   Move into a subdirectory");
		System.out.println("  cd ..       Move up to parent directory");
		System.out.println("  cd /        Jump back to root");
		System.out.println("  size        Recursive total size of current dir");
		System.out.println("  pwd         Print current path");
		System.out.println("  help        Show this help message");
		System.out.println("  exit        Quit the application");
	}
}
