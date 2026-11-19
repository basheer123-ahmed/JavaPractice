/*
 * Program 98: File Read and Write Operations
 * 
 * Problem Statement:
 *   Demonstrate fundamental file I/O operations in Java using multiple approaches:
 *   1. FileReader/FileWriter — character-based low-level I/O
 *   2. BufferedReader/BufferedWriter — efficient buffered I/O for line-by-line operations
 *   3. Files utility class (Java NIO) — modern, concise API for common file operations
 *   
 *   Operations covered:
 *   - Writing data to a new file
 *   - Reading file contents (character-by-character, line-by-line, all at once)
 *   - Appending data to an existing file
 *   - Copying a file from one path to another
 *   
 *   All files are created/managed in the current working directory.
 * 
 * Sample Input: (No user input required)
 * 
 * Sample Output:
 *   === Demo 1: FileWriter / FileReader (Low-Level) ===
 *   Writing to 'demo_filewriter.txt' using FileWriter...
 *   File written successfully.
 *   Reading from 'demo_filewriter.txt' using FileReader:
 *   ------ File Content ------
 *   Hello, FileWriter!
 *   This is line 2.
 *   Java File I/O is powerful.
 *   --------------------------
 *   
 *   === Demo 2: BufferedWriter / BufferedReader (Efficient) ===
 *   Writing to 'demo_buffered.txt' using BufferedWriter...
 *   ...
 *   === Demo 4: Appending to a File ===
 *   ...
 *   === Demo 5: Copying a File ===
 *   ...
 *   === Demo 6: Java NIO — Files Utility Class ===
 *   ...
 *   
 *   Cleanup: All demo files deleted.
 * 
 * Explanation:
 *   FileReader/FileWriter:
 *     - Low-level character stream classes. Unbuffered — each read()/write() may
 *       trigger an OS call. Best for small files or when you need char-level control.
 *   
 *   BufferedReader/BufferedWriter:
 *     - Wraps a Reader/Writer with an internal buffer (default 8192 chars).
 *     - readLine() reads a full line efficiently. newLine() writes platform-specific
 *       line separators. Significantly faster for large files.
 *   
 *   Files (java.nio.file):
 *     - Modern utility class (Java 7+). Static methods for most common operations.
 *     - Files.readString() / Files.writeString() (Java 11+): one-liners for text files.
 *     - Files.readAllLines(): returns List<String> of all lines.
 *     - Files.copy(): copies files with options (REPLACE_EXISTING, COPY_ATTRIBUTES).
 *     - Files.write() with StandardOpenOption.APPEND: appending made simple.
 * 
 * Time Complexity: O(n) where n = file size in characters/bytes
 * Space Complexity: O(n) for reading entire file into memory; O(1) for streaming line-by-line
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileReadWrite {

    // Track all created files for cleanup
    private static final List<String> createdFiles = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║         FILE READ & WRITE OPERATIONS             ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        try {
            demoFileWriterReader();
            demoBufferedWriterReader();
            demoReadLineByLine();
            demoAppendToFile();
            demoCopyFile();
            demoNIOFiles();
            demoNIOReadAllAndWrite();
        } finally {
            // Cleanup: delete all demo files
            cleanupFiles();
        }

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("  1. FileReader/FileWriter: simple but unbuffered (slow for large files).");
        System.out.println("  2. BufferedReader/BufferedWriter: buffered, readLine(), best for large files.");
        System.out.println("  3. Files (NIO): modern, concise, feature-rich — preferred for new code.");
        System.out.println("  4. Always use try-with-resources for automatic resource cleanup.");
        System.out.println("  5. Specify charset explicitly (UTF-8) to avoid platform-dependent encoding.");
        System.out.println("═══════════════════════════════════════════════════");
    }

    // ==================== Demo 1: FileWriter / FileReader ====================

    public static void demoFileWriterReader() {
        System.out.println("=== Demo 1: FileWriter / FileReader (Low-Level) ===\n");

        String filename = "demo_filewriter.txt";
        createdFiles.add(filename);

        // --- WRITING with FileWriter ---
        System.out.println("Writing to '" + filename + "' using FileWriter...");
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Hello, FileWriter!\n");
            writer.write("This is line 2.\n");
            writer.write("Java File I/O is powerful.\n");
            System.out.println("File written successfully.");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
            return;
        }

        // --- READING with FileReader (character by character) ---
        System.out.println("Reading from '" + filename + "' using FileReader:");
        System.out.println("------ File Content ------");
        try (FileReader reader = new FileReader(filename)) {
            int ch; // read() returns int (-1 at EOF, else char value)
            while ((ch = reader.read()) != -1) {
                System.out.print((char) ch);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        System.out.println("--------------------------\n");
    }

    // ==================== Demo 2: BufferedWriter / BufferedReader ====================

    public static void demoBufferedWriterReader() {
        System.out.println("=== Demo 2: BufferedWriter / BufferedReader (Efficient) ===\n");

        String filename = "demo_buffered.txt";
        createdFiles.add(filename);

        // --- WRITING with BufferedWriter ---
        System.out.println("Writing to '" + filename + "' using BufferedWriter...");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            String[] lines = {
                    "Name: Alice Johnson",
                    "Age: 28",
                    "Department: Engineering",
                    "Role: Senior Developer",
                    "Skills: Java, Python, SQL"
            };
            for (String line : lines) {
                writer.write(line);
                writer.newLine(); // Platform-independent line separator
            }
            // flush() is called automatically by close(), but you can call it manually
            writer.flush();
            System.out.println("File written successfully (" + lines.length + " lines).");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
            return;
        }

        // --- READING with BufferedReader ---
        System.out.println("Reading from '" + filename + "' using BufferedReader:");
        System.out.println("------ File Content ------");
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println("  Line " + lineNum + ": " + line);
                lineNum++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        System.out.println("--------------------------\n");
    }

    // ==================== Demo 3: Read Line by Line (with processing) ====================

    public static void demoReadLineByLine() {
        System.out.println("=== Demo 3: Read Line by Line with Processing ===\n");

        // First, create a CSV-like data file
        String filename = "demo_data.csv";
        createdFiles.add(filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Name,Score,Grade"); writer.newLine();
            writer.write("Alice,95,A"); writer.newLine();
            writer.write("Bob,82,B"); writer.newLine();
            writer.write("Charlie,78,C"); writer.newLine();
            writer.write("Diana,91,A"); writer.newLine();
            writer.write("Eve,88,B"); writer.newLine();
        } catch (IOException e) {
            System.out.println("Error creating data file: " + e.getMessage());
            return;
        }

        // Read and process line by line
        System.out.println("Reading '" + filename + "' and processing each line:");
        System.out.println("------ Processed Data ------");
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String header = reader.readLine(); // Read header separately
            System.out.println("  Header: " + header);
            System.out.println("  ---");

            String line;
            int totalScore = 0;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                String grade = parts[2];
                totalScore += score;
                count++;
                System.out.printf("  Student: %-10s | Score: %3d | Grade: %s%n",
                        name, score, grade);
            }
            System.out.println("  ---");
            System.out.printf("  Average Score: %.1f (from %d students)%n",
                    (double) totalScore / count, count);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        System.out.println("----------------------------\n");
    }

    // ==================== Demo 4: Appending to a File ====================

    public static void demoAppendToFile() {
        System.out.println("=== Demo 4: Appending to a File ===\n");

        String filename = "demo_log.txt";
        createdFiles.add(filename);

        // Write initial content
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("[2024-01-15 09:00:00] Application started");
            writer.newLine();
            writer.write("[2024-01-15 09:00:01] Loading configuration");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
            return;
        }

        System.out.println("Initial file content:");
        printFileContent(filename);

        // Append more content using FileWriter with append=true
        System.out.println("Appending new log entries using FileWriter(file, true)...");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("[2024-01-15 09:00:05] Database connected");
            writer.newLine();
            writer.write("[2024-01-15 09:00:10] Server listening on port 8080");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
            return;
        }

        // Append using NIO Files.write with APPEND option
        System.out.println("Appending more entries using Files.write(APPEND)...");
        try {
            List<String> moreEntries = List.of(
                    "[2024-01-15 09:01:00] First request received",
                    "[2024-01-15 09:01:01] Request processed successfully"
            );
            Files.write(Paths.get(filename), moreEntries,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error appending with NIO: " + e.getMessage());
            return;
        }

        System.out.println("\nFinal file content after all appends:");
        printFileContent(filename);
        System.out.println();
    }

    // ==================== Demo 5: Copying a File ====================

    public static void demoCopyFile() {
        System.out.println("=== Demo 5: Copying a File ===\n");

        String sourceFile = "demo_source.txt";
        String destFile1 = "demo_copy_nio.txt";
        String destFile2 = "demo_copy_manual.txt";
        createdFiles.add(sourceFile);
        createdFiles.add(destFile1);
        createdFiles.add(destFile2);

        // Create source file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sourceFile))) {
            writer.write("This is the source file content.\n");
            writer.write("It has multiple lines.\n");
            writer.write("We will copy this file using two methods.\n");
        } catch (IOException e) {
            System.out.println("Error creating source file: " + e.getMessage());
            return;
        }

        // --- Method 1: Copy using Files.copy() (NIO) ---
        System.out.println("Method 1: Files.copy() (Java NIO)");
        try {
            Path source = Paths.get(sourceFile);
            Path dest = Paths.get(destFile1);
            Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("  Copied '" + sourceFile + "' -> '" + destFile1 + "'");
            System.out.println("  Source size: " + Files.size(source) + " bytes");
            System.out.println("  Copy size:   " + Files.size(dest) + " bytes");
        } catch (IOException e) {
            System.out.println("  Error copying file: " + e.getMessage());
        }

        // --- Method 2: Copy using BufferedReader/BufferedWriter (manual) ---
        System.out.println("\nMethod 2: Manual copy using BufferedReader/BufferedWriter");
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destFile2))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("  Copied '" + sourceFile + "' -> '" + destFile2 + "'");
        } catch (IOException e) {
            System.out.println("  Error copying file: " + e.getMessage());
        }

        // Verify both copies match
        System.out.println("\nVerification — content of copied file (NIO):");
        printFileContent(destFile1);
        System.out.println();
    }

    // ==================== Demo 6: Java NIO Files Utility ====================

    public static void demoNIOFiles() {
        System.out.println("=== Demo 6: Java NIO — Files Utility Class ===\n");

        String filename = "demo_nio.txt";
        createdFiles.add(filename);
        Path path = Paths.get(filename);

        // --- Write using Files.writeString() (Java 11+) ---
        System.out.println("Writing with Files.writeString() (Java 11+):");
        try {
            String content = "Line 1: Java NIO is modern and concise.\n"
                    + "Line 2: Files.writeString() is a one-liner.\n"
                    + "Line 3: No need for explicit streams or buffers.\n";
            Files.writeString(path, content, StandardCharsets.UTF_8);
            System.out.println("  File written successfully.");
        } catch (IOException e) {
            System.out.println("  Error writing file: " + e.getMessage());
            return;
        }

        // --- Read using Files.readString() (Java 11+) ---
        System.out.println("\nReading with Files.readString() (Java 11+):");
        try {
            String content = Files.readString(path, StandardCharsets.UTF_8);
            System.out.println("------ File Content ------");
            System.out.print(content);
            System.out.println("--------------------------");
        } catch (IOException e) {
            System.out.println("  Error reading file: " + e.getMessage());
        }

        // --- Read all lines as List<String> ---
        System.out.println("\nReading with Files.readAllLines():");
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            System.out.println("  Total lines: " + lines.size());
            for (int i = 0; i < lines.size(); i++) {
                System.out.println("  [" + i + "] " + lines.get(i));
            }
        } catch (IOException e) {
            System.out.println("  Error reading file: " + e.getMessage());
        }

        // --- Read using Files.lines() (lazy stream) ---
        System.out.println("\nReading with Files.lines() (Stream API):");
        try (var stream = Files.lines(path, StandardCharsets.UTF_8)) {
            stream.filter(line -> line.contains("NIO"))
                    .map(String::toUpperCase)
                    .forEach(line -> System.out.println("  Filtered: " + line));
        } catch (IOException e) {
            System.out.println("  Error reading file: " + e.getMessage());
        }
        System.out.println();
    }

    // ==================== Demo 7: NIO Read All Bytes and Write Bytes ====================

    public static void demoNIOReadAllAndWrite() {
        System.out.println("=== Demo 7: Files — Read/Write Bytes & Lines ===\n");

        String filename = "demo_bytes.txt";
        createdFiles.add(filename);
        Path path = Paths.get(filename);

        // --- Write lines using Files.write(Path, Iterable<String>) ---
        System.out.println("Writing lines with Files.write(Path, List<String>):");
        try {
            List<String> lines = List.of(
                    "Alpha", "Bravo", "Charlie", "Delta", "Echo"
            );
            Files.write(path, lines, StandardCharsets.UTF_8);
            System.out.println("  Wrote " + lines.size() + " lines.");
        } catch (IOException e) {
            System.out.println("  Error writing file: " + e.getMessage());
            return;
        }

        // --- Read as bytes ---
        System.out.println("\nReading with Files.readAllBytes():");
        try {
            byte[] bytes = Files.readAllBytes(path);
            System.out.println("  File size: " + bytes.length + " bytes");
            System.out.println("  Content as string: " + new String(bytes, StandardCharsets.UTF_8).replace("\n", " | ").replace("\r", ""));
        } catch (IOException e) {
            System.out.println("  Error reading file: " + e.getMessage());
        }

        // --- Check file existence and properties ---
        System.out.println("\nFile properties:");
        try {
            System.out.println("  Exists: " + Files.exists(path));
            System.out.println("  Size: " + Files.size(path) + " bytes");
            System.out.println("  Is regular file: " + Files.isRegularFile(path));
            System.out.println("  Is readable: " + Files.isReadable(path));
            System.out.println("  Is writable: " + Files.isWritable(path));
        } catch (IOException e) {
            System.out.println("  Error getting properties: " + e.getMessage());
        }
        System.out.println();
    }

    // ==================== Helper Methods ====================

    /**
     * Prints the full content of a file, with line numbers.
     */
    private static void printFileContent(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println("  " + lineNum + ": " + line);
                lineNum++;
            }
        } catch (IOException e) {
            System.out.println("  Error reading file: " + e.getMessage());
        }
    }

    /**
     * Cleans up all demo files created during the program.
     */
    private static void cleanupFiles() {
        System.out.println("Cleanup: Deleting all demo files...");
        for (String filename : createdFiles) {
            try {
                Path path = Paths.get(filename);
                if (Files.deleteIfExists(path)) {
                    System.out.println("  Deleted: " + filename);
                }
            } catch (IOException e) {
                System.out.println("  Could not delete " + filename + ": " + e.getMessage());
            }
        }
        System.out.println();
    }
}
