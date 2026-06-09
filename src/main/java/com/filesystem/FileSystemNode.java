package com.filesystem;

public interface FileSystemNode {
    //Returns the name of this file or directory
    String getName ();
    // For a file:      returns its own size in bytes
    // For a directory: recursively sums all children's sizes
    long getSize();
    // Used by ls to label entries, and by cd to block
    // navigating into a file
    boolean isDirectory();  
}


