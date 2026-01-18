package service;

import model.*;

public class FileSystem {
    private final FileSystemNode root;

    public FileSystem() {
        this.root = new Directory("/");
    }

    private static final class InstanceHolder {
        private static final FileSystem INSTANCE = new FileSystem();
    }

    public static FileSystem getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private boolean isValidPath(String path) {
        return path != null && path.startsWith("/");
    }

    public boolean createPath(String path) {
        if (!isValidPath(path)) return false;

        String[] pathComponents = path.split("/"); 

        FileSystemNode node = root;
        for (int  i = 0; i < pathComponents.length - 1; i++) {
            String component =  pathComponents[i];
            if (component.isEmpty()) continue;
            
            if (!node.hasChild(component)) {
                FileSystemNode newDirectory = new Directory(component);
                node.addChild(component, newDirectory);
            }

            FileSystemNode child = node.getChild(component);
            if (child.isFile()) {
                return false;
            }
            
            node = child;
        }
        
        String lastComponent = pathComponents[pathComponents.length - 1];
        
        if (lastComponent.isEmpty()) return false;
        if (node.hasChild(lastComponent)) return false;
        
        FileSystemNode newNode;
        if (lastComponent.contains(".")) {
            newNode = new File(lastComponent);
        } else {
            newNode = new Directory(lastComponent);
        }
        
        node.addChild(lastComponent, newNode);
        return true;
    }
    
    private FileSystemNode getNode(String path) {
        if (!isValidPath(path)) return null;
        if (path.equals("/")) return root;
        
        String[] pathComponents = path.split("/");
        FileSystemNode node = root;

        for (String component : pathComponents) {
            if (component.isEmpty()) continue;
            if (!node.hasChild(component)) return null;
            
            node = node.getChild(component);
        }
        
        return node;
    }
    
    public boolean deletePath(String path) {
        if (!isValidPath(path) || path.equals("/")) return false;
        
        String parentPath = getParentPath(path);
        FileSystemNode parent =  getNode(parentPath);
        
        if (parent == null || parent.isFile()) return false;
        
        String lastComponent = path.substring(path.lastIndexOf('/') + 1);
        if (!parent.hasChild(lastComponent)) return false;
        
        return parent.removeChild(lastComponent);
    }
    
    private String getParentPath(String path) {
        int lastSlash = path.lastIndexOf('/');
        if (lastSlash <= 0) return "/";
        
        return path.substring(0, lastSlash);
    }
    
    public void display() {
        root.display(0);
    }
    
    public boolean setFileContent(String path, String content) {
        FileSystemNode node = getNode(path);
        if (node == null || !node.isFile()) return false;

        File file = (File) node;
        file.setContent(content);
        return true;
    }

    public String getFileContent(String path) {
        FileSystemNode node = getNode(path);
        if (node == null || !node.isFile()) return null;

        return ((File) node).getContent();
    }
}
