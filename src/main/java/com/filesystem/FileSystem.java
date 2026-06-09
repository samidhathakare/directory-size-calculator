package com.filesystem;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class FileSystem {
    private final DirectoryNode root;
    private DirectoryNode currentDir;
    private final Deque<DirectoryNode> history = new ArrayDeque<>();
    
    public FileSystem(DirectoryNode root) {
        this.root = root;
        this.currentDir = root;
    }
    //cd
    public String cd(String target){
        switch(target){
            case "/":
                history.clear();
                currentDir = root;
                return "Now at: /";
            case " ..":
                if (history.isEmpty())
                    return "Alredy at root. Cannot go up.";
                currentDir = history.pop();
                return "Now at: " + getCurrentpath();
            default:
                FileSystemNode found = currentDir.findChild(target);
                if (found ==null)
                   return "cd:not such directory:" +target;
                if(!found.isDirectory())
                   return "cd: not a directory: " +target;
                history.push (currentDir);
                currentDir = (Directorynode) found;
                return "Now at:" + getCurrentpath();
        }
    }
        //ls
        public String ls(){
        List<FileSystemNode> children = currentDir.getChildren();
        if (children.isEmpty()) return "(empty directory)";

        StringBuilder sb = new StringBuilder();
        sb.append("Contents of").append(getCurrentPath()).append(":\n");
        sb.append(String.format("%-28s %s%n", "NAME" , "SIZE"));
        sb.append(" "+"-".repeat(42) + "\n");

        for (FileSystemNode child : children){
        if (child.isDirectory()) {
        sb.append(String.format("[DIR] %-24s%n", child.getName() + "/"));

        }else {
            sb.append(String.format("[FILE] %-24 %s%n", child.getName(),FileNode.formatSize(child.getSize())));
            }
        }
            return sb.toString().stripTrailing();

        }
            // ── size ────────────────────────────────────────────────────────
    public String size() {
        long total = currentDir.getSize();
        return String.format("Size of '%s': %s  (%,d bytes)",
                getCurrentPath(), FileNode.formatSize(total), total);
    }
 
    // ── Helpers 
    public String getCurrentPath() {
        if (currentDir == root) return "/";
        Deque<String> parts = new ArrayDeque<>();
        parts.push(currentDir.getName());
        for (DirectoryNode dir : history)
            if (dir != root) parts.add(dir.getName());
        List<String> ordered = new ArrayList<>(parts);
        java.util.Collections.reverse(ordered);
        return "/" + String.join("/", ordered);
    }
 
    public String getCurrentDirName() {
        return currentDir == root ? "/" : currentDir.getName();
    }
}


