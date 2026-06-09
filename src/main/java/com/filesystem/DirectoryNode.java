package com.filesystem;


import java.util.ArrayList;
import java.util.List;

public abstract class DirectoryNode implements FileSystemNode {
 
    private final String name;
    private final List<FileSystemNode> children = new ArrayList<>();
 
    public DirectoryNode(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Directory name cannot be empty.");
        this.name = name;
    }
    @Override
    public String getName() { return name;}

    //RECURSIVE STEP :sum the size of every child 
    @Override
    public long getSize () {
        long total = 0;
        for (FileSystemNode child: children){
            total += child.getSize();//polymorphic - works for files AND dirs
        }
        return total;
    }
    @Override 
    public boolean isDirectory() { return true;}

    public void addchild(FileSystemNode node) {
        if (node == null) throw new IllegalArgumentException("Cannot add null");
        children.add(node);
    }
    //return a copy so callers cannot modify the internal list 
    public List<FileSystemNode> getChildren(){
        return new ArrayList <> (children);
    }
    //find a direct child by name (case-sensitive), null if not found
    public FileSystemNode findChild(String name){
        for (FileSystemNode child : children)
            if(child.getName().equals(name)) return child;
        return null;
    }

}