import java.util.*;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        AuthenticationManager authManager = new AuthenticationManager();
        User user = authManager.login(username, password);
        if (user != null) {
            System.out.println("Login successful. Welcome, " + user.getUsername());

            // Retrieve student details
            StudentManagementSystem sms = new StudentManagementSystem();
            Student student = sms.getStudentDetails(user);
            if (student != null) {
                System.out.println("Student Details:");
                System.out.println("Name: " + student.getName());
                System.out.println("Course: " + student.getCourse());
                System.out.println("Parent's Name: " + student.getParentName());
                System.out.println("Parent's Phone Number: " + student.getParentPhoneNumber());

                // Get course details and grades for 5 courses
                Gradebook gradebook = new Gradebook();
                for (int i = 0; i < 5; i++) {
                    System.out.print("Enter course name " + (i + 1) + ": ");
                    String courseName = scanner.nextLine();

                    System.out.print("Enter grade for " + user.getUsername() + " in " + courseName + ": ");
                    double grade = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline character

                    gradebook.inputGrade(courseName, user.getUsername(), grade);
                }

                // Calculate GPA
                double gpa = gradebook.calculateGPA(user.getUsername());
                System.out.println("GPA: " + gpa);

                // Notify parents
                MessagingSystem messagingSystem = new MessagingSystem();
                messagingSystem.sendNotification(user.getParent(), "Your child's GPA is: " + gpa);
            } else {
                System.out.println("Student details not found.");
            }

        } else {
            System.out.println("Invalid username or password.");
        }

        scanner.close();
    }
}

class AuthenticationManager {
    // Simulated user database
    private Map<String, String> userCredentials;

    public AuthenticationManager() {
        userCredentials = new HashMap<>();
        userCredentials.put("Keerthana", "1234"); // Simulating admin credentials
        userCredentials.put("Kowshika", "1234");
         // New user credentials
        // Add more users as needed
    }

    public User login(String username, String password) {
        // Check if the credentials match
        if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
            // If credentials match, return a user object
            return new User(username);
        }
        // If credentials don't match, return null
        return null;
    }
}

class User {
    private String username;
    private UserRole role;

    public User(String username) {
        this.username = username;
        // Simulated role assignment
        if (username.equals("admin")) {
            this.role = UserRole.ADMINISTRATOR;
        } else {
            this.role = UserRole.STUDENT; // Assign student role by default
        }
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    // Simulated method to get parent for a student
    public User getParent() {
        return new User("parent");
    }
}

enum UserRole {
    STUDENT, TEACHER, ADMINISTRATOR, PARENT
}

class StudentManagementSystem {
    // Simulated database to store student details
    private Map<String, Student> students;

    public StudentManagementSystem() {
        students = new HashMap<>();
        // Initialize student details
        Student student1 = new Student("Keerthana", "CSBS", "Varadharajan", "1234567890");
        Student student2 = new Student("Kowshika", "CSBS", "Ganesh", "9876543210");
        students.put("Keerthana", student1);
        students.put("Kowshika", student2);
        // Add more students as needed
    }

    public Student getStudentDetails(User user) {
        return students.get(user.getUsername());
    }
}

class Student {
    private String name;
    private String course;
    private String parentName;
    private String parentPhoneNumber;

    public Student(String name, String course, String parentName, String parentPhoneNumber) {
        this.name = name;
        this.course = course;
        this.parentName = parentName;
        this.parentPhoneNumber = parentPhoneNumber;
    }

    // Getters for student details
    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getParentName() {
        return parentName;
    }

    public String getParentPhoneNumber() {
        return parentPhoneNumber;
    }
}

class Gradebook {
    private Map<String, Map<String, Double>> grades; // courseId -> studentId -> grade

    public Gradebook() {
        grades = new HashMap<>();
    }

    public void inputGrade(String courseId, String studentId, double grade) {
        // Simplified implementation to input grades
        grades.computeIfAbsent(courseId, k -> new HashMap<>()).put(studentId, grade);
    }

    public double calculateGPA(String studentId) {
        // Simplified implementation to calculate GPA
        double totalGrade = 0.0;
        int count = 0;
        for (Map<String, Double> courseGrades : grades.values()) {
            Double grade = courseGrades.get(studentId);
            if (grade != null) {
                totalGrade += grade;
                count++;
            }
        }
        return count == 0 ? 0 : totalGrade / count;
    }
}

class MessagingSystem {
    public void sendMessage(User sender, User receiver, String message) {
        // Simplified implementation to send message
        System.out.println("Message sent from " + sender.getUsername() + " to " + receiver.getUsername() + ": " + message);
    }

    public void sendNotification(User user, String notification) {
        // Simplified implementation to send notification
        System.out.println("Notification sent to " + user.getUsername() + ": " + notification);
    }
}
