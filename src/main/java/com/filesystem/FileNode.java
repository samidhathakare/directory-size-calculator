package com.filesystem;
public class FileNode implements FileSystemNode {
    private final String name;
    private final long size; // in bytes

    public FileNode(String name, long size){
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("File name cannot be empty.");
        if (size < 0)
            throw new IllegalArgumentException("File name cannot be empty.");
        this.name = name;
        this.size = size;
    }
    @Override
    public String getNae() {return name;}

    //BASE CASE: a file's total size is just itself 
    @Override
    public long getSize() {return size;}

    @Override
    public boolean isDirectory() {return false;}
    //Converts bytes to human-readable: B, KB, MB, GB
    //Static so other classes can call FileNode.formatSize()
    public static String formatSize(long bytes) {
        if (bytes < 1_024)         return   bytes + "B";
        if (bytes < 1_048_576)     return   String.format("%.1f KB", bytes / 1_024.0);
        if (bytes < 1_073_741_824) return   String.format("%.1f MB", bytes / 1_048_576.0);
        return                              String.format("%.2f GB", bytes / 1_073_741_824.0);

    }
    

    
}
