package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class FileSystemNode {
    protected String name;
    protected Map<String, FileSystemNode> children;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    public FileSystemNode(String name) {
        this.name = name;
        this.children = new HashMap<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public abstract boolean isFile();
    public abstract void display(int depth);

    public void addChild(String name, FileSystemNode child) {
        this.children.put(name, child);
    }

    public boolean hasChild(String name) {
        return this.children.containsKey(name);
    }

    public FileSystemNode getChild(String name) {
        return this.children.get(name);
    }

    public boolean removeChild(String name) {
        if (hasChild(name)) {
            children.remove(name);
            return true;
        }
        return false;
    }

    protected String getIcon() {
        if (isFile()) {
            return "\uD83D\uDCC4";
        }
        return "\uD83D\uDCC1";
    }
}
