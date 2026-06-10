package com.filesystem;

import java.util.ArrayDeque;
import java.util.ArrayList;
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
        return switch (target) {
            case "/" -> {
                history.clear();
                currentDir = root;
                yield "Now at: /";
            }
            case " .." -> {
                if (history.isEmpty())
                    yield "Alredy at root. Cannot go up.";
                currentDir = history.pop();
                yield "Now at: " + getCurrentPath();
            }
            default -> {
                FileSystemNode found = currentDir.findChild(target);
                if (found == null)
                    yield "cd:not such directory:" + target;
                if (!found.isDirectory())
                    yield "cd: not a directory: " + target;
                history.push(currentDir);
                currentDir = (DirectoryNode) found;
                yield "Now at:" + getCurrentPath();
            }
        };
    }
        //ls
        public String ls() {
    List<FileSystemNode> children = currentDir.getChildren();
    if (children.isEmpty()) return "(empty directory)";

    StringBuilder sb = new StringBuilder();
    sb.append("Contents of ").append(getCurrentPath()).append(":\n");
    sb.append(String.format("  %-28s  %s%n", "NAME", "SIZE"));
    sb.append("  ").append("-".repeat(42)).append("\n");

    for (FileSystemNode child : children) {
        if (child.isDirectory()) {
            sb.append(String.format("  [DIR]  %-26s%n", child.getName() + "/"));
        } else {
            sb.append(String.format("  [FILE] %-26s  %s%n",
                    child.getName(), FileNode.formatSize(child.getSize())));
        }
    }
    return sb.toString().stripTrailing();
}// ── size ────────────────────────────────────────────────────────
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


