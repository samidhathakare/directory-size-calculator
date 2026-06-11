package com.filesystem;

public interface FileSystemNode {
	String getName();
	long getSize();
	boolean isDirectory();
}
