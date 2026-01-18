package model;

public class Directory extends FileSystemNode {

    public Directory(String name) {
        super(name);
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public void display(int depth) {
        String indent = " ".repeat(depth * 2);
        System.out.println(indent + getIcon() + " " + this.name + " (" + this.children.size() + " items)");

        for (FileSystemNode child : this.children.values()) {
            child.display(depth + 1);
        }
    }
}
