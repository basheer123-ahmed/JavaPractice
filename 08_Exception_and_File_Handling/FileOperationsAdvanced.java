/*
 * Program 99: Advanced File Operations
 * 
 * Problem Statement:
 *   Demonstrate advanced file and directory operations in Java:
 *   1. List all files in a directory
 *   2. Filter files by extension (e.g., find all .java files)
 *   3. Get file properties: size, last modified date, permissions (read/write/execute)
 *   4. Create and delete directories (single and nested)
 *   5. Walk a file tree recursively using Files.walk() and Files.walkFileTree()
 *   
 *   The program creates a temporary demo directory structure, performs all operations
 *   on it, and then cleans up after itself.
 * 
 * Sample Input: (No user input required)
 * 
 * Sample Output:
 *   === Setup: Creating demo directory structure ===
 *   Created: demo_advanced_files/
 *   Created: demo_advanced_files/src/
 *   Created: demo_advanced_files/src/main/
 *   ...
 *   === Demo 1: List All Files in a Directory ===
 *   Contents of 'demo_advanced_files/src/main':
 *     [FILE] App.java (45 bytes)
 *     [FILE] Utils.java (38 bytes)
 *     [DIR]  resources/
 *   ...
 *   === Demo 3: File Properties ===
 *   ...
 *   === Demo 5: Walk File Tree (Files.walk) ===
 *   All paths (depth-first):
 *     demo_advanced_files
 *     demo_advanced_files/src
 *     ...
 *   .java files found: 4
 *   Total size of all files: 523 bytes
 *   ...
 *   Cleanup complete.
 * 
 * Explanation:
 *   - File / Path: File is the legacy (java.io) class; Path is the modern (java.nio) equivalent.
 *     Both represent file system paths. Path is preferred for new code.
 *   - Files.list(): Lists immediate children of a directory (non-recursive). Returns Stream<Path>.
 *   - Files.walk(): Recursively walks the file tree depth-first. Returns Stream<Path>.
 *   - Files.walkFileTree(): More control via FileVisitor callbacks (preVisitDirectory, visitFile,
 *     postVisitDirectory, visitFileFailed). Useful for complex operations like recursive delete.
 *   - DirectoryStream: Lightweight alternative to list() with built-in glob filtering.
 *   - BasicFileAttributes: Provides size, creation time, last modified time, etc.
 *   - Files.createDirectories(): Creates a directory and all missing parent directories.
 * 
 * Time Complexity: O(n) where n = number of files and directories in the tree
 * Space Complexity: O(n) for storing file paths during walk; O(d) stack depth for recursion
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileOperationsAdvanced {

    // Root directory for all demo operations
    private static final String DEMO_ROOT = "demo_advanced_files";

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║      ADVANCED FILE OPERATIONS                    ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        try {
            setupDemoStructure();
            demoListFiles();
            demoFilterByExtension();
            demoFileProperties();
            demoCreateDeleteDirectories();
            demoFilesWalk();
            demoFilesWalkFileTree();
            demoDirectoryStream();
        } finally {
            cleanupDemoStructure();
        }

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("  1. Files.list() — non-recursive listing of a directory.");
        System.out.println("  2. Files.walk() — recursive depth-first traversal (Stream API).");
        System.out.println("  3. Files.walkFileTree() — visitor pattern for complex tree ops.");
        System.out.println("  4. DirectoryStream — lightweight glob-based filtering.");
        System.out.println("  5. BasicFileAttributes — portable access to file metadata.");
        System.out.println("  6. Files.createDirectories() — creates parent dirs automatically.");
        System.out.println("  7. Always close streams from Files.list()/walk() (try-with-resources).");
        System.out.println("═══════════════════════════════════════════════════");
    }

    // ==================== Setup: Create Demo Directory Structure ====================

    private static void setupDemoStructure() {
        System.out.println("=== Setup: Creating demo directory structure ===\n");

        try {
            // Create directory structure:
            // demo_advanced_files/
            // ├── src/
            // │   ├── main/
            // │   │   ├── App.java
            // │   │   ├── Utils.java
            // │   │   └── resources/
            // │   │       ├── config.properties
            // │   │       └── data.csv
            // │   └── test/
            // │       ├── AppTest.java
            // │       └── UtilsTest.java
            // ├── docs/
            // │   ├── README.md
            // │   └── CHANGELOG.md
            // ├── build/
            // │   ├── App.class
            // │   └── Utils.class
            // └── notes.txt

            String[][] dirs = {
                    {DEMO_ROOT},
                    {DEMO_ROOT, "src"},
                    {DEMO_ROOT, "src", "main"},
                    {DEMO_ROOT, "src", "main", "resources"},
                    {DEMO_ROOT, "src", "test"},
                    {DEMO_ROOT, "docs"},
                    {DEMO_ROOT, "build"}
            };

            for (String[] dirParts : dirs) {
                Path dir = Paths.get(dirParts[0], java.util.Arrays.copyOfRange(dirParts, 1, dirParts.length));
                Files.createDirectories(dir);
                System.out.println("  Created: " + dir + "/");
            }

            // Create files with content
            String[][] files = {
                    {DEMO_ROOT + "/src/main/App.java", "public class App { public static void main(String[] args) {} }"},
                    {DEMO_ROOT + "/src/main/Utils.java", "public class Utils { /* utility methods */ }"},
                    {DEMO_ROOT + "/src/main/resources/config.properties", "db.host=localhost\ndb.port=5432\ndb.name=myapp"},
                    {DEMO_ROOT + "/src/main/resources/data.csv", "id,name,value\n1,alpha,100\n2,bravo,200"},
                    {DEMO_ROOT + "/src/test/AppTest.java", "import org.junit.*;\npublic class AppTest { @Test public void test() {} }"},
                    {DEMO_ROOT + "/src/test/UtilsTest.java", "import org.junit.*;\npublic class UtilsTest { @Test public void test() {} }"},
                    {DEMO_ROOT + "/docs/README.md", "# My Project\n\nThis is a demo project for file operations."},
                    {DEMO_ROOT + "/docs/CHANGELOG.md", "# Changelog\n\n## v1.0.0\n- Initial release"},
                    {DEMO_ROOT + "/build/App.class", "<<compiled bytecode placeholder>>"},
                    {DEMO_ROOT + "/build/Utils.class", "<<compiled bytecode placeholder>>"},
                    {DEMO_ROOT + "/notes.txt", "Remember to update the README before release."}
            };

            for (String[] filePair : files) {
                Path filePath = Paths.get(filePair[0]);
                Files.writeString(filePath, filePair[1]);
                System.out.println("  Created: " + filePath + " (" + Files.size(filePath) + " bytes)");
            }

        } catch (IOException e) {
            System.out.println("Error during setup: " + e.getMessage());
            System.exit(1);
        }
        System.out.println();
    }

    // ==================== Demo 1: List All Files in a Directory ====================

    public static void demoListFiles() {
        System.out.println("=== Demo 1: List All Files in a Directory ===\n");

        // --- Method 1: Using java.io.File (Legacy) ---
        System.out.println("Method 1: Using java.io.File.listFiles()");
        File dir = new File(DEMO_ROOT + "/src/main");
        File[] fileArray = dir.listFiles();
        if (fileArray != null) {
            System.out.println("Contents of '" + dir.getPath() + "':");
            for (File f : fileArray) {
                if (f.isDirectory()) {
                    System.out.println("  [DIR]  " + f.getName() + "/");
                } else {
                    System.out.println("  [FILE] " + f.getName()
                            + " (" + f.length() + " bytes)");
                }
            }
        }

        // --- Method 2: Using Files.list() (NIO) ---
        System.out.println("\nMethod 2: Using Files.list() (Java NIO)");
        Path nioDir = Paths.get(DEMO_ROOT, "src", "main");
        try (Stream<Path> entries = Files.list(nioDir)) {
            System.out.println("Contents of '" + nioDir + "':");
            entries.forEach(path -> {
                try {
                    String type = Files.isDirectory(path) ? "[DIR] " : "[FILE]";
                    String size = Files.isRegularFile(path) ?
                            " (" + Files.size(path) + " bytes)" : "/";
                    System.out.println("  " + type + " " + path.getFileName() + size);
                } catch (IOException e) {
                    System.out.println("  Error: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.out.println("Error listing directory: " + e.getMessage());
        }
        System.out.println();
    }

    // ==================== Demo 2: Filter Files by Extension ====================

    public static void demoFilterByExtension() {
        System.out.println("=== Demo 2: Filter Files by Extension ===\n");

        Path root = Paths.get(DEMO_ROOT);

        // --- Find all .java files recursively ---
        System.out.println("All .java files in '" + DEMO_ROOT + "':");
        try (Stream<Path> walk = Files.walk(root)) {
            List<Path> javaFiles = walk
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .collect(Collectors.toList());

            for (Path jf : javaFiles) {
                // Show relative path from root
                System.out.println("  " + root.relativize(jf));
            }
            System.out.println("  Total: " + javaFiles.size() + " file(s)");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // --- Find all .md files ---
        System.out.println("\nAll .md files:");
        try (Stream<Path> walk = Files.walk(root)) {
            walk.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".md"))
                    .forEach(p -> System.out.println("  " + root.relativize(p)));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // --- Find files matching multiple extensions ---
        System.out.println("\nAll config/data files (.properties, .csv, .txt):");
        try (Stream<Path> walk = Files.walk(root)) {
            walk.filter(Files::isRegularFile)
                    .filter(p -> {
                        String name = p.getFileName().toString().toLowerCase();
                        return name.endsWith(".properties")
                                || name.endsWith(".csv")
                                || name.endsWith(".txt");
                    })
                    .forEach(p -> System.out.println("  " + root.relativize(p)));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // --- Using File.listFiles with FilenameFilter (Legacy) ---
        System.out.println("\nUsing File.listFiles(FilenameFilter) for .java in src/main:");
        File srcMain = new File(DEMO_ROOT + "/src/main");
        File[] javaOnly = srcMain.listFiles((d, name) -> name.endsWith(".java"));
        if (javaOnly != null) {
            for (File f : javaOnly) {
                System.out.println("  " + f.getName());
            }
        }

        System.out.println();
    }

    // ==================== Demo 3: File Properties ====================

    public static void demoFileProperties() {
        System.out.println("=== Demo 3: File Properties ===\n");

        Path filePath = Paths.get(DEMO_ROOT, "src", "main", "App.java");

        try {
            System.out.println("Properties of: " + filePath);
            System.out.println("  Absolute path: " + filePath.toAbsolutePath());
            System.out.println("  File name:     " + filePath.getFileName());
            System.out.println("  Parent:        " + filePath.getParent());
            System.out.println("  Exists:        " + Files.exists(filePath));
            System.out.println("  Is file:       " + Files.isRegularFile(filePath));
            System.out.println("  Is directory:  " + Files.isDirectory(filePath));
            System.out.println("  Is symbolic link: " + Files.isSymbolicLink(filePath));
            System.out.println("  Size:          " + Files.size(filePath) + " bytes");

            // Permissions
            System.out.println("  Readable:      " + Files.isReadable(filePath));
            System.out.println("  Writable:      " + Files.isWritable(filePath));
            System.out.println("  Executable:    " + Files.isExecutable(filePath));
            System.out.println("  Hidden:        " + Files.isHidden(filePath));

            // BasicFileAttributes — portable across OS
            BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            System.out.println("\n  BasicFileAttributes:");
            System.out.println("    Creation time:     " + formatFileTime(attrs.creationTime().toInstant(), formatter));
            System.out.println("    Last modified:     " + formatFileTime(attrs.lastModifiedTime().toInstant(), formatter));
            System.out.println("    Last access:       " + formatFileTime(attrs.lastAccessTime().toInstant(), formatter));
            System.out.println("    Size:              " + attrs.size() + " bytes");
            System.out.println("    Is regular file:   " + attrs.isRegularFile());
            System.out.println("    Is directory:      " + attrs.isDirectory());
            System.out.println("    Is symbolic link:  " + attrs.isSymbolicLink());
            System.out.println("    Is other:          " + attrs.isOther());

        } catch (IOException e) {
            System.out.println("Error getting file properties: " + e.getMessage());
        }

        // Show properties for a directory
        System.out.println("\nProperties of directory: " + DEMO_ROOT + "/src");
        Path dirPath = Paths.get(DEMO_ROOT, "src");
        try {
            System.out.println("  Is directory: " + Files.isDirectory(dirPath));
            System.out.println("  Size on disk: " + Files.size(dirPath) + " bytes");
            BasicFileAttributes dirAttrs = Files.readAttributes(dirPath, BasicFileAttributes.class);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println("  Created:      " + formatFileTime(dirAttrs.creationTime().toInstant(), formatter));
            System.out.println("  Modified:     " + formatFileTime(dirAttrs.lastModifiedTime().toInstant(), formatter));
        } catch (IOException e) {
            System.out.println("  Error: " + e.getMessage());
        }

        System.out.println();
    }

    private static String formatFileTime(Instant instant, DateTimeFormatter formatter) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).format(formatter);
    }

    // ==================== Demo 4: Create and Delete Directories ====================

    public static void demoCreateDeleteDirectories() {
        System.out.println("=== Demo 4: Create and Delete Directories ===\n");

        // --- Create a single directory ---
        Path singleDir = Paths.get(DEMO_ROOT, "temp_single");
        try {
            Files.createDirectory(singleDir);
            System.out.println("Created single directory: " + singleDir);
            System.out.println("  Exists: " + Files.exists(singleDir));
        } catch (IOException e) {
            System.out.println("Error creating directory: " + e.getMessage());
        }

        // --- Create nested directories (like mkdir -p) ---
        Path nestedDir = Paths.get(DEMO_ROOT, "temp_nested", "level1", "level2", "level3");
        try {
            Files.createDirectories(nestedDir);
            System.out.println("Created nested directories: " + nestedDir);
            System.out.println("  Exists: " + Files.exists(nestedDir));
        } catch (IOException e) {
            System.out.println("Error creating directories: " + e.getMessage());
        }

        // Create a file inside the nested directory
        try {
            Path tempFile = nestedDir.resolve("temp_data.txt");
            Files.writeString(tempFile, "Temporary data");
            System.out.println("Created file in nested dir: " + tempFile);
        } catch (IOException e) {
            System.out.println("Error creating temp file: " + e.getMessage());
        }

        // --- Delete a single empty directory ---
        try {
            Files.delete(singleDir);
            System.out.println("\nDeleted single directory: " + singleDir);
            System.out.println("  Exists: " + Files.exists(singleDir));
        } catch (IOException e) {
            System.out.println("Error deleting directory: " + e.getMessage());
        }

        // --- Attempting to delete non-empty directory (will fail) ---
        Path tempNested = Paths.get(DEMO_ROOT, "temp_nested");
        try {
            Files.delete(tempNested);
            System.out.println("Deleted: " + tempNested);
        } catch (IOException e) {
            System.out.println("\nCannot delete non-empty directory '" + tempNested + "': "
                    + e.getClass().getSimpleName());
            System.out.println("  Must delete contents first (use recursive delete).");
        }

        // --- Recursive delete using Files.walk() ---
        System.out.println("\nRecursive delete of '" + tempNested + "':");
        try (Stream<Path> walk = Files.walk(tempNested)) {
            // Sort in reverse order so files are deleted before their parent directories
            walk.sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                            System.out.println("  Deleted: " + path);
                        } catch (IOException e) {
                            System.out.println("  Error deleting " + path + ": " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            System.out.println("Error walking directory: " + e.getMessage());
        }
        System.out.println("  Directory exists: " + Files.exists(tempNested));
        System.out.println();
    }

    // ==================== Demo 5: Files.walk() ====================

    public static void demoFilesWalk() {
        System.out.println("=== Demo 5: Walk File Tree (Files.walk) ===\n");

        Path root = Paths.get(DEMO_ROOT);

        // --- Walk entire tree (depth-first) ---
        System.out.println("All paths (depth-first):");
        try (Stream<Path> walk = Files.walk(root)) {
            walk.forEach(path -> {
                int depth = root.relativize(path).getNameCount();
                String indent = "  ".repeat(depth);
                String suffix = Files.isDirectory(path) ? "/" : "";
                System.out.println("  " + indent + path.getFileName() + suffix);
            });
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // --- Walk with max depth ---
        System.out.println("\nWalk with maxDepth=1 (immediate children only):");
        try (Stream<Path> walk = Files.walk(root, 1)) {
            walk.skip(1) // Skip the root itself
                    .forEach(path -> {
                        String suffix = Files.isDirectory(path) ? "/" : "";
                        System.out.println("  " + path.getFileName() + suffix);
                    });
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // --- Count files by extension ---
        System.out.println("\nFile count by extension:");
        try (Stream<Path> walk = Files.walk(root)) {
            walk.filter(Files::isRegularFile)
                    .collect(Collectors.groupingBy(
                            p -> {
                                String name = p.getFileName().toString();
                                int dot = name.lastIndexOf('.');
                                return dot >= 0 ? name.substring(dot) : "(no extension)";
                            },
                            Collectors.counting()
                    ))
                    .entrySet().stream()
                    .sorted(java.util.Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(e -> System.out.println("  " + e.getKey() + " : " + e.getValue() + " file(s)"));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // --- Calculate total size of all files ---
        System.out.println("\nTotal size of all files:");
        try (Stream<Path> walk = Files.walk(root)) {
            long totalSize = walk
                    .filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try { return Files.size(p); }
                        catch (IOException e) { return 0; }
                    })
                    .sum();
            System.out.println("  " + totalSize + " bytes");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();
    }

    // ==================== Demo 6: Files.walkFileTree() with FileVisitor ====================

    public static void demoFilesWalkFileTree() {
        System.out.println("=== Demo 6: Files.walkFileTree (FileVisitor Pattern) ===\n");

        Path root = Paths.get(DEMO_ROOT);

        try {
            System.out.println("Walking file tree with custom FileVisitor:");
            FileStatisticsVisitor visitor = new FileStatisticsVisitor(root);
            Files.walkFileTree(root, visitor);

            System.out.println("\n  === Statistics ===");
            System.out.println("  Directories visited: " + visitor.getDirCount());
            System.out.println("  Files visited:       " + visitor.getFileCount());
            System.out.println("  Total file size:     " + visitor.getTotalSize() + " bytes");
            System.out.println("  Largest file:        " + visitor.getLargestFile()
                    + " (" + visitor.getLargestSize() + " bytes)");

        } catch (IOException e) {
            System.out.println("Error walking file tree: " + e.getMessage());
        }

        System.out.println();
    }

    // ==================== Demo 7: DirectoryStream with Glob Filter ====================

    public static void demoDirectoryStream() {
        System.out.println("=== Demo 7: DirectoryStream with Glob Filter ===\n");

        Path srcMain = Paths.get(DEMO_ROOT, "src", "main");

        // --- Glob filter: *.java ---
        System.out.println("Files matching '*.java' in " + srcMain + ":");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(srcMain, "*.java")) {
            for (Path entry : stream) {
                System.out.println("  " + entry.getFileName());
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // --- Glob filter: *.{java,class} ---
        Path buildDir = Paths.get(DEMO_ROOT, "build");
        System.out.println("\nFiles matching '*.{java,class}' in " + buildDir + ":");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(buildDir, "*.{java,class}")) {
            for (Path entry : stream) {
                System.out.println("  " + entry.getFileName());
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // --- Custom filter using DirectoryStream.Filter ---
        System.out.println("\nFiles larger than 50 bytes in docs/:");
        Path docsDir = Paths.get(DEMO_ROOT, "docs");
        DirectoryStream.Filter<Path> sizeFilter = entry ->
                Files.isRegularFile(entry) && Files.size(entry) > 50;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(docsDir, sizeFilter)) {
            for (Path entry : stream) {
                System.out.println("  " + entry.getFileName()
                        + " (" + Files.size(entry) + " bytes)");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();
    }

    // ==================== Cleanup ====================

    private static void cleanupDemoStructure() {
        System.out.println("=== Cleanup: Removing demo directory structure ===\n");
        Path root = Paths.get(DEMO_ROOT);

        if (!Files.exists(root)) {
            System.out.println("  Nothing to clean up.");
            return;
        }

        try (Stream<Path> walk = Files.walk(root)) {
            walk.sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            System.out.println("  Error deleting " + path + ": " + e.getMessage());
                        }
                    });
            System.out.println("  Cleanup complete. '" + DEMO_ROOT + "' removed.");
        } catch (IOException e) {
            System.out.println("Error during cleanup: " + e.getMessage());
        }
        System.out.println();
    }
}

// ==================== FileVisitor Implementation ====================

/**
 * A custom FileVisitor that collects statistics about the file tree.
 * Demonstrates all four visitor callbacks:
 *   - preVisitDirectory:  Called before entering a directory
 *   - visitFile:          Called for each regular file
 *   - visitFileFailed:    Called if a file cannot be accessed
 *   - postVisitDirectory: Called after all entries in a directory are visited
 */
class FileStatisticsVisitor extends SimpleFileVisitor<Path> {

    private final Path root;
    private int dirCount = 0;
    private int fileCount = 0;
    private long totalSize = 0;
    private Path largestFile = null;
    private long largestSize = 0;

    public FileStatisticsVisitor(Path root) {
        this.root = root;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        int depth = root.relativize(dir).getNameCount();
        String indent = "  ".repeat(depth);
        System.out.println("  " + indent + ">> Entering: " + dir.getFileName() + "/");
        dirCount++;
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        int depth = root.relativize(file).getNameCount();
        String indent = "  ".repeat(depth);
        long size = attrs.size();
        System.out.println("  " + indent + "   " + file.getFileName()
                + " [" + size + " bytes]");

        fileCount++;
        totalSize += size;

        if (size > largestSize) {
            largestSize = size;
            largestFile = file;
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.out.println("  !! Failed to visit: " + file + " (" + exc.getMessage() + ")");
        return FileVisitResult.CONTINUE; // Continue despite the error
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        int depth = root.relativize(dir).getNameCount();
        String indent = "  ".repeat(depth);
        System.out.println("  " + indent + "<< Leaving:  " + dir.getFileName() + "/");
        return FileVisitResult.CONTINUE;
    }

    // Getters for statistics
    public int getDirCount() { return dirCount; }
    public int getFileCount() { return fileCount; }
    public long getTotalSize() { return totalSize; }
    public Path getLargestFile() { return largestFile; }
    public long getLargestSize() { return largestSize; }
}
