/*
 * Program 100: Serialization and Deserialization Demo
 * 
 * Problem Statement:
 *   Demonstrate Java object serialization and deserialization:
 *   1. Basic serialization: Convert objects to byte streams using ObjectOutputStream
 *      and restore them with ObjectInputStream.
 *   2. transient fields: Fields marked 'transient' are excluded from serialization
 *      (useful for passwords, cached data, non-serializable references).
 *   3. serialVersionUID: Explicit version control for serialized class compatibility.
 *      Prevents InvalidClassException when the class evolves.
 *   4. Custom serialization: Override writeObject() and readObject() to control
 *      exactly what gets serialized — useful for encryption, validation, or
 *      reconstructing transient fields during deserialization.
 *   5. Serialization of object graphs (nested objects, collections).
 * 
 * Sample Input: (No user input required)
 * 
 * Sample Output:
 *   === Demo 1: Basic Serialization ===
 *   Before serialization:
 *     Employee{id=101, name='Alice Johnson', department='Engineering', salary=85000.0}
 *   Serialized to 'employee.ser' (247 bytes)
 *   After deserialization:
 *     Employee{id=101, name='Alice Johnson', department='Engineering', salary=85000.0}
 *   Objects are equal: true
 *   
 *   === Demo 2: Transient Fields ===
 *   Before serialization:
 *     UserAccount{username='alice', password='s3cretP@ss', sessionToken='tok-abc123', loginCount=5}
 *   After deserialization:
 *     UserAccount{username='alice', password='null', sessionToken='null', loginCount=0}
 *   Note: password, sessionToken, and loginCount (all transient) are lost!
 *   ...
 *   === Demo 4: Custom Serialization ===
 *   Before serialization:
 *     SecureUser{username='bob', password='myP@ssw0rd', ...}
 *   After deserialization:
 *     SecureUser{username='bob', password='myP@ssw0rd', ...}
 *   Custom writeObject encrypted the password; readObject decrypted it back!
 * 
 * Explanation:
 *   SERIALIZATION (ObjectOutputStream.writeObject):
 *     - Converts an object's state into a byte stream that can be saved to a file,
 *       sent over a network, or stored in a database.
 *     - The class must implement java.io.Serializable (marker interface, no methods).
 *   
 *   DESERIALIZATION (ObjectInputStream.readObject):
 *     - Reconstructs the object from the byte stream.
 *     - The class must be available on the classpath with a compatible serialVersionUID.
 *     - The constructor is NOT called — fields are set directly from the stream.
 *   
 *   TRANSIENT KEYWORD:
 *     - Marks a field to be skipped during serialization.
 *     - After deserialization, transient fields have their default values
 *       (null for objects, 0 for primitives, false for booleans).
 *   
 *   serialVersionUID:
 *     - A long value that acts as a version number for the class.
 *     - If not declared, Java auto-generates one from class structure — but this is
 *       fragile and can change if you add a method or field.
 *     - Best practice: always declare it explicitly.
 *   
 *   CUSTOM SERIALIZATION (writeObject / readObject):
 *     - Private methods with exact signatures that Java's serialization mechanism calls.
 *     - Allows encryption of sensitive data, validation, or rebuilding transient caches.
 * 
 * Time Complexity: O(n) where n = number of fields/objects being serialized
 * Space Complexity: O(n) for the serialized byte stream
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class SerializationDemo {

    // Track created files for cleanup
    private static final List<String> createdFiles = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║      SERIALIZATION & DESERIALIZATION DEMO        ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        try {
            demoBasicSerialization();
            demoTransientFields();
            demoSerialVersionUID();
            demoCustomSerialization();
            demoObjectGraph();
        } finally {
            cleanupFiles();
        }

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("  1. Implement Serializable to enable serialization.");
        System.out.println("  2. transient fields are excluded — use for sensitive/non-serializable data.");
        System.out.println("  3. Always declare serialVersionUID for version control.");
        System.out.println("  4. Custom writeObject/readObject give full control over serialization.");
        System.out.println("  5. Object graphs (nested objects) are serialized automatically.");
        System.out.println("  6. Use try-with-resources for ObjectOutputStream/ObjectInputStream.");
        System.out.println("═══════════════════════════════════════════════════");
    }

    // ==================== Demo 1: Basic Serialization ====================

    public static void demoBasicSerialization() {
        System.out.println("=== Demo 1: Basic Serialization ===\n");

        String filename = "employee.ser";
        createdFiles.add(filename);

        // Create an Employee object
        Employee emp = new Employee(101, "Alice Johnson", "Engineering", 85000.0);
        System.out.println("Before serialization:");
        System.out.println("  " + emp);

        // --- SERIALIZE: Object → File ---
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(emp);
            long fileSize = new File(filename).length();
            System.out.println("Serialized to '" + filename + "' (" + fileSize + " bytes)");
        } catch (IOException e) {
            System.out.println("Serialization error: " + e.getMessage());
            return;
        }

        // --- DESERIALIZE: File → Object ---
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            Employee restored = (Employee) ois.readObject();
            System.out.println("After deserialization:");
            System.out.println("  " + restored);
            System.out.println("Objects are equal: " + emp.equals(restored));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Deserialization error: " + e.getMessage());
        }

        System.out.println();
    }

    // ==================== Demo 2: Transient Fields ====================

    public static void demoTransientFields() {
        System.out.println("=== Demo 2: Transient Fields ===\n");

        String filename = "user_account.ser";
        createdFiles.add(filename);

        // Create a UserAccount with sensitive fields
        UserAccount user = new UserAccount("alice", "s3cretP@ss", "tok-abc123", 5);
        System.out.println("Before serialization:");
        System.out.println("  " + user);

        // Serialize
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(user);
            System.out.println("Serialized to '" + filename + "'");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // Deserialize
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            UserAccount restored = (UserAccount) ois.readObject();
            System.out.println("After deserialization:");
            System.out.println("  " + restored);
            System.out.println();
            System.out.println("  Observations:");
            System.out.println("    - username (non-transient): '" + restored.getUsername() + "' ✓ preserved");
            System.out.println("    - password (transient):     '" + restored.getPassword() + "' ✗ lost (null)");
            System.out.println("    - sessionToken (transient): '" + restored.getSessionToken() + "' ✗ lost (null)");
            System.out.println("    - loginCount (transient int): " + restored.getLoginCount() + " ✗ lost (default 0)");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();
    }

    // ==================== Demo 3: serialVersionUID ====================

    public static void demoSerialVersionUID() {
        System.out.println("=== Demo 3: serialVersionUID ===\n");

        String filename = "product.ser";
        createdFiles.add(filename);

        // Serialize a Product
        Product product = new Product("PROD-001", "Wireless Mouse", 29.99, 150);
        System.out.println("Before serialization:");
        System.out.println("  " + product);
        System.out.println("  serialVersionUID: " + Product.getSerialVersion());

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(product);
            System.out.println("Serialized successfully.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // Deserialize — works because serialVersionUID matches
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            Product restored = (Product) ois.readObject();
            System.out.println("After deserialization:");
            System.out.println("  " + restored);
            System.out.println("  Deserialization successful — serialVersionUID matched!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("  WHY serialVersionUID MATTERS:");
        System.out.println("  - If you serialize an object with version 1L,");
        System.out.println("    then change the class and set version 2L,");
        System.out.println("    deserialization will throw InvalidClassException.");
        System.out.println("  - This prevents loading incompatible data into a changed class.");
        System.out.println("  - If you DON'T declare it, Java auto-generates one from the class");
        System.out.println("    structure. Adding any field or method changes it, breaking");
        System.out.println("    backward compatibility unexpectedly.");
        System.out.println();
    }

    // ==================== Demo 4: Custom Serialization ====================

    public static void demoCustomSerialization() {
        System.out.println("=== Demo 4: Custom Serialization (writeObject / readObject) ===\n");

        String filename = "secure_user.ser";
        createdFiles.add(filename);

        SecureUser user = new SecureUser("bob", "myP@ssw0rd", "bob@example.com");
        System.out.println("Before serialization:");
        System.out.println("  " + user);

        // Serialize — custom writeObject will encrypt the password
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(user);
            System.out.println("Serialized to '" + filename + "' (password encrypted in stream)");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // Deserialize — custom readObject will decrypt the password
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            SecureUser restored = (SecureUser) ois.readObject();
            System.out.println("After deserialization:");
            System.out.println("  " + restored);
            System.out.println("  Password match: " + user.getPassword().equals(restored.getPassword()));
            System.out.println("  Cache rebuilt: connectionCount = " + restored.getConnectionCount());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("  HOW IT WORKS:");
        System.out.println("  - writeObject(): Called automatically during serialization.");
        System.out.println("    We encrypt the password with Base64 before writing.");
        System.out.println("  - readObject(): Called automatically during deserialization.");
        System.out.println("    We decrypt the password and rebuild transient caches.");
        System.out.println("  - Both methods MUST be private and have exact signatures:");
        System.out.println("    private void writeObject(ObjectOutputStream out) throws IOException");
        System.out.println("    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException");
        System.out.println();
    }

    // ==================== Demo 5: Object Graph Serialization ====================

    public static void demoObjectGraph() {
        System.out.println("=== Demo 5: Object Graph (Nested Objects & Collections) ===\n");

        String filename = "team.ser";
        createdFiles.add(filename);

        // Create a team with multiple employees
        Employee lead = new Employee(201, "Carol Williams", "Engineering", 120000.0);
        Employee dev1 = new Employee(202, "David Brown", "Engineering", 95000.0);
        Employee dev2 = new Employee(203, "Eva Martinez", "Engineering", 92000.0);

        Team team = new Team("Platform Team", lead);
        team.addMember(dev1);
        team.addMember(dev2);
        team.addProject("Cloud Migration");
        team.addProject("API Gateway v2");

        System.out.println("Before serialization:");
        System.out.println("  " + team);

        // Serialize the entire object graph
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(team);
            long fileSize = new File(filename).length();
            System.out.println("Serialized to '" + filename + "' (" + fileSize + " bytes)");
            System.out.println("  (The entire object graph — Team + Employees + Lists — is serialized)");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // Deserialize the entire object graph
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            Team restored = (Team) ois.readObject();
            System.out.println("\nAfter deserialization:");
            System.out.println("  " + restored);
            System.out.println("  Team name matches: " + team.getName().equals(restored.getName()));
            System.out.println("  Member count matches: " + (team.getMembers().size() == restored.getMembers().size()));
            System.out.println("  Project count matches: " + (team.getProjects().size() == restored.getProjects().size()));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // --- Demonstrate serializing multiple objects to same stream ---
        System.out.println("\n--- Multiple Objects in One Stream ---");
        String multiFile = "multi_objects.ser";
        createdFiles.add(multiFile);

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(multiFile))) {
            // Write multiple objects sequentially
            oos.writeObject(lead);
            oos.writeObject(dev1);
            oos.writeObject(dev2);
            oos.writeInt(42);        // Can also write primitives
            oos.writeUTF("metadata"); // And strings
            System.out.println("Wrote 3 Employee objects + int + String to '" + multiFile + "'");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // Read them back in the same order
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(multiFile))) {
            Employee r1 = (Employee) ois.readObject();
            Employee r2 = (Employee) ois.readObject();
            Employee r3 = (Employee) ois.readObject();
            int num = ois.readInt();
            String meta = ois.readUTF();

            System.out.println("Read back:");
            System.out.println("  " + r1);
            System.out.println("  " + r2);
            System.out.println("  " + r3);
            System.out.println("  int: " + num);
            System.out.println("  String: " + meta);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();
    }

    // ==================== Cleanup ====================

    private static void cleanupFiles() {
        System.out.println("Cleanup: Deleting serialized files...");
        for (String filename : createdFiles) {
            File file = new File(filename);
            if (file.exists() && file.delete()) {
                System.out.println("  Deleted: " + filename);
            }
        }
        System.out.println();
    }
}

// ==================== Serializable Classes ====================

/**
 * Basic serializable class — Employee.
 * Implements Serializable and declares serialVersionUID.
 */
class Employee implements Serializable {

    // Explicit serialVersionUID — best practice for forward/backward compatibility
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s', department='%s', salary=%.1f}",
                id, name, department, salary);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Employee)) return false;
        Employee other = (Employee) obj;
        return id == other.id
                && name.equals(other.name)
                && department.equals(other.department)
                && Double.compare(salary, other.salary) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, name, department, salary);
    }
}

/**
 * Demonstrates transient fields.
 * password, sessionToken, and loginCount are transient — they will NOT be serialized.
 */
class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;                    // Will be serialized
    private transient String password;          // TRANSIENT: sensitive data — excluded
    private transient String sessionToken;      // TRANSIENT: runtime-only state — excluded
    private transient int loginCount;           // TRANSIENT: primitive — will become 0

    public UserAccount(String username, String password, String sessionToken, int loginCount) {
        this.username = username;
        this.password = password;
        this.sessionToken = sessionToken;
        this.loginCount = loginCount;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getSessionToken() { return sessionToken; }
    public int getLoginCount() { return loginCount; }

    @Override
    public String toString() {
        return String.format("UserAccount{username='%s', password='%s', sessionToken='%s', loginCount=%d}",
                username, password, sessionToken, loginCount);
    }
}

/**
 * Demonstrates explicit serialVersionUID for version control.
 */
class Product implements Serializable {

    // If you change this value and try to deserialize old data, you'll get
    // InvalidClassException — that's the purpose: version incompatibility detection.
    private static final long serialVersionUID = 1L;

    private String productId;
    private String name;
    private double price;
    private int stockQuantity;

    public Product(String productId, String name, double price, int stockQuantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public static long getSerialVersion() { return serialVersionUID; }

    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', price=%.2f, stock=%d}",
                productId, name, price, stockQuantity);
    }
}

/**
 * Demonstrates custom serialization with writeObject and readObject.
 * - writeObject: Encrypts the password before writing to the stream.
 * - readObject: Decrypts the password after reading from the stream.
 *               Also rebuilds transient cached state.
 */
class SecureUser implements Serializable {

    private static final long serialVersionUID = 2L;

    private String username;
    private String password;      // Will be encrypted in writeObject
    private String email;

    // Transient field that gets rebuilt in readObject
    private transient int connectionCount;

    public SecureUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.connectionCount = initializeConnectionCount();
    }

    /**
     * Custom serialization — called automatically by ObjectOutputStream.writeObject().
     * We encrypt the password before writing the object.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // Encrypt the password before serialization (Base64 as a simple demo)
        String originalPassword = this.password;
        this.password = Base64.getEncoder().encodeToString(password.getBytes());

        // Call defaultWriteObject() to serialize all non-transient fields normally
        out.defaultWriteObject();

        // Restore the original password in this live object
        this.password = originalPassword;

        // Optionally write additional data
        out.writeInt(42); // Extra marker for validation
    }

    /**
     * Custom deserialization — called automatically by ObjectInputStream.readObject().
     * We decrypt the password after reading the object.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // Read all non-transient fields normally
        in.defaultReadObject();

        // Decrypt the password
        this.password = new String(Base64.getDecoder().decode(this.password));

        // Read the extra data we wrote in writeObject
        int marker = in.readInt();
        if (marker != 42) {
            throw new IOException("Invalid serialization format: bad marker");
        }

        // Rebuild transient fields
        this.connectionCount = initializeConnectionCount();
    }

    private int initializeConnectionCount() {
        // Simulate initializing a cache or connection pool
        return 1; // Fresh connection
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public int getConnectionCount() { return connectionCount; }

    @Override
    public String toString() {
        return String.format("SecureUser{username='%s', password='%s', email='%s', connectionCount=%d}",
                username, password, email, connectionCount);
    }
}

/**
 * Demonstrates serialization of object graphs — a Team containing multiple Employees.
 * All objects in the graph must be Serializable.
 */
class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Employee lead;                     // Nested serializable object
    private List<Employee> members;            // Collection of serializable objects
    private List<String> projects;             // Collection of Strings (Serializable)

    public Team(String name, Employee lead) {
        this.name = name;
        this.lead = lead;
        this.members = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public void addMember(Employee member) {
        members.add(member);
    }

    public void addProject(String project) {
        projects.add(project);
    }

    public String getName() { return name; }
    public Employee getLead() { return lead; }
    public List<Employee> getMembers() { return members; }
    public List<String> getProjects() { return projects; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Team{name='").append(name).append("'");
        sb.append(", lead=").append(lead.getName());
        sb.append(", members=[");
        for (int i = 0; i < members.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(members.get(i).getName());
        }
        sb.append("]");
        sb.append(", projects=").append(projects);
        sb.append("}");
        return sb.toString();
    }
}
