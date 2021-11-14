import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Teacher {
    private static String firstName;
    private static String lastName;
    private static String username;
    private static String password;
    ArrayList<Course> courses = new ArrayList<Course>();
    static ArrayList<Teacher> teachers = new ArrayList<Teacher>();

    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        teachers = new ArrayList<>();
        addATeacher(this);
    }

    public Teacher(String firstName, String lastName, String username, String password) {
        this(firstName, lastName);
        this.username = username;
        this.password = password;


    }

    public void addATeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public static ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public String getFirstName() { //Returns the first name of a teacher
        return firstName;
    }

    public String getLastName() { //Returns the last name of a teacher
        return lastName;
    }

    public String getName() { //Returns the full name of a teacher
        return firstName + " " + lastName;
    }

    public String getUsername() { //Returns the username of a teacher (use it to find out if username already exists)
        return username;
    }

    public String getPassword() { //Returns the password of a teacher
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Course> getCourses() { //Returns the list of Course objects that this teacher has
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    /*
    Adds a course to the ArrayList of courses
    *
    We also need to have an ArrayList of all courses (from different teachers)
    because students can access any course (should be created in the Course.java file)
    */

    public void addCourse(Course course) {
        courses.add(course);
    }

    public static void createAccount(String firstName, String lastName, String username, String password) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream("TeacherAccounts.txt", true);
        StringBuilder courses = new StringBuilder();
        PrintWriter pw = new PrintWriter(fos);
        pw.println("Name: " + firstName + " " + lastName);
        pw.println("Username: " + username);
        pw.println("Password: " + password);
        pw.println();
        pw.flush();
    }

    /**
     * public static ArrayList<String> getAllUsernames(){
     * ArrayList<String> fileContents = new ArrayList<>();
     * ArrayList<String> allUsernames = new ArrayList<>();
     * try (BufferedReader bufferedReader = new BufferedReader(new FileReader("TeacherAccounts.txt"))) {
     * String line;
     * while ((line = bufferedReader.readLine()) != null) {
     * fileContents.add(line);
     * }
     * } catch (IOException e) {
     * e.printStackTrace();
     * return null;
     * }
     * if (fileContents.size() == 0) {
     * return null;
     * } else {
     * for (int i = 0; i < fileContents.size() / 4; i++) {
     * String user = fileContents.get(1 + 4 * i);
     * allUsernames.add(user.substring(10));
     * }
     * }
     * return allUsernames;
     * }
     */

    public static String deleteAccount(String username) throws FileNotFoundException {
        int deleteAcc = 0;
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("TeacherAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + " \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContents = buffer.toString();
        if (buffer.isEmpty()) {
            return "No teacher accounts have been created.";
        } else {
            for (int i = 0; i < fileContents.length(); i++) {
                if (fileContents.contains(username)) {
                    fileContents = fileContents.replace("Username: " + username, "Username: deleteAccount");
                    deleteAcc = 1;
                }
            }
            if (deleteAcc != 1) {
                return "Please input correct password";
            }
            String[] splitContents = fileContents.split("\n");
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (int i = 0; i < splitContents.length; i++) {
                stringArrayList.add(splitContents[i]);
            }
            for (int i = 0; i < stringArrayList.size(); i++) {
                if (stringArrayList.get(i).contains("Username: deleteAccount")) {
                    stringArrayList.remove(i + 2);
                    stringArrayList.remove(i + 1);
                    stringArrayList.remove(i);
                    stringArrayList.remove(i - 1);
                }
            }
            FileOutputStream fos = new FileOutputStream("TeacherAccounts.txt", false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < stringArrayList.size(); i++) {
                pw.println(stringArrayList.get(i));
            }
            pw.flush();
        }
        return "Your account has been deleted!";
    }

    public static ArrayList<String> getAllUsernamesAndPasswords() {
        ArrayList<String> fileContents = new ArrayList<>();
        ArrayList<String> allInfo = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("TeacherAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (fileContents.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < fileContents.size() / 4; i++) {
                String user = fileContents.get(1 + 4 * i);
                allInfo.add(user.substring(10));
                String pass = fileContents.get(2 + 4 * i);
                allInfo.add(pass.substring(10));
            }
        }
        return allInfo;
    }

    public static ArrayList<String> getAllTeacherNames() {
        ArrayList<String> fileContents = new ArrayList<>();
        ArrayList<String> allInfo = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("TeacherAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (fileContents.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < fileContents.size() / 4; i++) {
                String name = fileContents.get(4 * i);
                allInfo.add(name.substring(6));
            }
        }
        return allInfo;
    }

    public static String getSpecificPassword(String inputUsername) {
        ArrayList<String> fileContents = new ArrayList<>();
        ArrayList<String> allUsernames = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (fileContents.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < fileContents.size(); i++) {
                if (fileContents.get(i).equals("Username: " + inputUsername)) {
                    return fileContents.get(i + 1).substring(10);
                }
            }
        }
        return null;
    }

    public static String changeUsername(String oldUsername, String newUsername) throws FileNotFoundException {
        int usernameExist = 0;
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("TeacherAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + " \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContents = buffer.toString();
        if (buffer.isEmpty()) {
            return "No teacher accounts have been created.";
        } else {
            for (int i = 0; i < fileContents.length(); i++) {
                if (fileContents.contains(oldUsername)) {
                    fileContents = fileContents.replace(oldUsername, newUsername);
                    usernameExist = 1;
                }
            }
            if (usernameExist != 1) {
                return "Your current username does not exist.";
            }
            String[] splitContents = fileContents.split("\n");
            FileOutputStream fos = new FileOutputStream("TeacherAccounts.txt", false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < splitContents.length; i++) {
                pw.println(splitContents[i]);
            }
            pw.flush();
        }
        return "Your username has been changed!";
    }

    public static String changePassword(String username, String oldPassword, String newPassword) throws FileNotFoundException {
        int usernameExist = 0;
        int oldPasswordExist = 0;
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("TeacherAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + " \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContents = buffer.toString();
        if (buffer.isEmpty()) {
            return "No teacher accounts have been created.";
        } else {
            for (int i = 0; i < fileContents.length(); i++) {
                if (fileContents.contains(username)) {
                    usernameExist = 1;
                    if (fileContents.contains(oldPassword)) {
                        fileContents = fileContents.replace(oldPassword, newPassword);
                        oldPasswordExist = 1;
                    }
                }
            }
            if (oldPasswordExist != 1) {
                return "This is not your current password. Please enter your current password correctly.";
            }
            if (usernameExist != 1) {
                return "Your current username does not exist.";
            }
            String[] splitContents = fileContents.split("\n");
            FileOutputStream fos = new FileOutputStream("TeacherAccounts.txt", false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < splitContents.length; i++) {
                System.out.println(splitContents[i]);
                pw.println(splitContents[i]);
            }
            pw.flush();
        }
        return "Your password has been changed!";
    }

    public static ArrayList<String> readQuizByStudentName(String firstName, String lastName) throws FileNotFoundException {
        ArrayList<String> allQuizInfo = new ArrayList<>();
        ArrayList<String> specificQuiz = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentQuizzes.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                allQuizInfo.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (allQuizInfo.size() == 0) {
            specificQuiz.add("No student has taken a quiz yet");
            return specificQuiz;
        } else {
            for (int i = 0; i < allQuizInfo.size(); i++) {
                if (allQuizInfo.get(i).equals("Name: " + firstName + " " + lastName)) {
                    specificQuiz.add(allQuizInfo.get(i));
                    specificQuiz.add(allQuizInfo.get(i + 1));
                    specificQuiz.add(allQuizInfo.get(i + 2));
                    specificQuiz.add(allQuizInfo.get(i + 3));
                    specificQuiz.add(" ");
                }
            }
            if (specificQuiz.size() == 0) {
                specificQuiz.add("This student has not taken a quiz yet");
            }
        }
        return specificQuiz;
    }

    public static ArrayList<String> readQuizByQuizName(String quizName) throws FileNotFoundException {
        ArrayList<String> allQuizInfo = new ArrayList<>();
        ArrayList<String> specificQuiz = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentQuizzes.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                allQuizInfo.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (allQuizInfo.size() == 0) {
            specificQuiz.add("No student has taken a quiz yet");
            return specificQuiz;
        } else {
            for (int i = 0; i < allQuizInfo.size(); i++) {
                if (allQuizInfo.get(i).equals(quizName)) {
                    specificQuiz.add(allQuizInfo.get(i));
                    specificQuiz.add(allQuizInfo.get(i + 1));
                    specificQuiz.add(allQuizInfo.get(i + 2));
                    specificQuiz.add(allQuizInfo.get(i + 3));
                    specificQuiz.add(" ");
                }
            }
            if (specificQuiz.size() == 0) {
                specificQuiz.add("No students have taken this particular quiz.");
            }
        }
        return specificQuiz;
    }


    public static void main(String[] args) throws InvalidCourseException, InvalidQuizException, FileNotFoundException {
        Scanner scanner=new Scanner(System.in);
        boolean check;
        do {
            check=TheCourseFunction.courseFunctionMenu(scanner);
        } while (check);

    }

    public static String inputChecker(Scanner scanner, String[] choices, String question, String errorMessage) {

        do {

            String input = scanner.nextLine();

            if (input != null) {
                for (int i = 0; i < choices.length; i++) {
                    if (input.equals(choices[i]))
                        return input;
                }
            }
            System.out.println(errorMessage);
            System.out.println(question);

        } while (true);

    }
}


