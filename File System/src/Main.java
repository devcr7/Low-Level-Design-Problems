import service.FileSystem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileSystem fs = FileSystem.getInstance();

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        System.out.println("File System Manager -  Commands: ");
        System.out.println("1. create <path> - Create a new path");
        System.out.println("2. write <path> <content> - Write content to a file");
        System.out.println("3. read <path> - Read content of a file");
        System.out.println("4. delete<path> - Delete a path");
        System.out.println("5. display - Show the entire file system structure");
        System.out.println("6. exit");

        while (isRunning) {
            System.out.println("Enter command: ");
            String input = scanner.nextLine().trim();

            String[] parts = input.split(" ");
            if (parts.length == 0) continue;

            String command = parts[0].toLowerCase();

            try {
                switch (command) {
                    case "create" -> {
                        if (parts.length > 1) {
                            String path = parts[1];
                            boolean isCreated = fs.createPath(path);
                            System.out.println(isCreated ? "Path created successfully" : "Failed to create");
                        } else {
                            System.out.println("Usage: create <path>");
                        }
                    }
                    case "write" -> {
                        if (parts.length > 2) {
                            String path = parts[1];
                            String content = parts[2];
                            boolean isWritten = fs.setFileContent(path, content);
                            System.out.println(isWritten ? "Content written successfully" : "Failed to write content");
                        } else {
                            System.out.println("Usage: write <path>");
                        }
                    }
                    case "read" -> {
                        if (parts.length > 1) {
                            String path = parts[1];
                            String content = fs.getFileContent(path);
                            System.out.println(content == null ? "Content not found" : "Content: " + content);
                        }else {
                            System.out.println("Usage: read <path>");
                        }
                    }
                    case "delete" -> {
                        if (parts.length > 1) {
                            String path = parts[1];
                            boolean isDeleted = fs.deletePath(path);
                            System.out.println(isDeleted ? "Path deleted successfully" : "Failed to delete path");
                        } else {
                            System.out.println("Usage: delete <path>");
                        }
                    }
                    case "display" -> {
                        System.out.println("File System Structure:");
                        fs.display();
                    }
                    case "exist" -> {
                        isRunning = false;
                        System.out.println("Exiting");
                    }
                    default -> System.out.println("Invalid command");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}