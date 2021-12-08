import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.spec.ECField;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * Application class
 *
 * This class starts the program and asks a user to sign in or register
 *
 * @author Artemii, Zuhair Almansouri, Anish Ketha
 *
 * @version November 15, 2021
 *
 */

public class ToCompare extends MultiApps {

    private static String welcomeMessage = "Welcome to the quiz app!";
    private static String signInOrRegister = "If you want to Sign In - enter [1], If you want to Register - enter [2]";
    private static String incorrectAnswer = "Sorry, you entered an incorrect answer. Please try again.";
    private static String enterFirstName = "Enter your first name.";
    private static String enterLastName = "Enter your last name.";
    private static String enterUsername = "Enter your username.";
    private static String enterPassword = "Enter your password.";
    private static String takenUsername = "Sorry, this username is already taken. Please enter a different username";
    private static String usernameDoesntExist = "Sorry, there is no user with this username. Please try again or Register.";
    private static String passwordDoesntMatch = "Sorry, the password you entered doesn't match the username";
    private static String enterNewPassword = "Please, enter the new password";
    private static String signInAgain = "Please Sign In again to confirm your password change";
    private static String thankYouMessage = "Thank you for using Quiz app!";
    /**
     * Asks a user to confirm that they want to delete their account
     */
    private static String confirmQuestion =
            "Are you sure that you want to delete your account?\n" +
                    "Press [1] for yes, or [2] for no";
    private static String teacherOrStudentSignIn =
            "Do you want to sign in as a teacher or as a student?\n" +
                    "Pick [1] for teacher or [2] for student.";
    /**
     * Choose to sign in either as a teacher, or as a student
     */
    private static String teacherOrStudentRegister =
            "Do you want to register as a teacher or as a student?\n" +
                    "Pick [1] for teacher or [2] for student.";
    /**
     * Choose to register either as a teacher, or as a student
     */
    private static String menuTeacher =
            "Menu:\n" +
                    "[1] Change your password\n" +
                    "[2] Delete your account\n" +
                    "[3] Sign Out\n";
    private static String menuStudent =
            "Menu:\n" +
                    "[1] Change your password\n" +
                    "[2] Delete your account\n" +
                    "[3] Sign Out\n";
    /**
     * Choose an option from the menu
     */
    private static String chooseOne = "Pick a corresponding number to choose an option";
    static ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    static ArrayList<Student> students = new ArrayList<Student>();
    /**
     * These "check" variables indicate if there is the object in either teachers ArrayList or students ArrayList with corresponding username
     */

    static QuizArchive quizArchive;

    static int check3 = 0;

    private static ServerSocket serverSocket;

    //If ever equal to 1, quit the program TT
    public static int quitProgram = 0;

    /**
     * Runs the program from the beginning
     * Checks if user wants to Sign In or Register
     * Runs an infinite loop till the user decides to Sign Out
     *
     * @throws Exception
     */
    public static void main() throws Exception {
        Scanner scanner = new Scanner(System.in);

        if (MultiApps.initialLaunch) {
            MultiApps.initialLaunch = false;
            String[] files = {"QuizQuestions.txt", "CourseDetails.txt", "TeacherAccounts.txt", "StudentAccounts.txt"
                    , "StudentQuizzes.txt"};
            for (int i = 0; i < files.length; i++) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(files[i]));
                } catch (FileNotFoundException e) {
                    FileOutputStream fot = new FileOutputStream(files[i]);
                }
            }


            Student.readTeachers(teachers);
            Student.readStudents();

            quizArchive = new QuizArchive();
            PrintInformation.readQuizQuestions(quizArchive);

            Teacher.readAllCourses();

            StudentAnish.readStudentSubmissions(quizArchive);

            teachers = Teacher.teachers;
            students = Student.students;


            serverSocket = new ServerSocket(1234);
        }

        System.out.println("Waiting for a client to connect...");

        Socket socket = serverSocket.accept();

        System.out.println("Connected!");

        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        pw.println("You are connected!");
        pw.flush();

        System.out.println(welcomeMessage);
        pw.println(welcomeMessage);
        pw.flush();
        do {
            System.out.println(signInOrRegister);
            pw.println(signInOrRegister);

            pw.println("3. View Connected clients");

            pw.println("4. Exit the app");
            pw.flush();
            pw.println("esc");
            pw.flush();
            System.out.println("3. View connected clients");
            System.out.println("4. Exit the app");
            String choiceSignInOrRegister;

            String response = br.readLine();
            choiceSignInOrRegister = response;

            if (choiceSignInOrRegister.equals("1")) {
                signIn(scanner, br, pw);
            } else if (choiceSignInOrRegister.equals("2")) {
                register(scanner, br, pw);
            } else if (choiceSignInOrRegister.equals("3")) {
                MultiApps.viewClients();
            } else if (choiceSignInOrRegister.equals("4")) {
                break;
            }
            else {
                pw.println(incorrectAnswer);
                pw.flush();
                System.out.println(incorrectAnswer);
            }
        } while (check3 == 0);

        Teacher.createAccount();
        Student.createAccount();

    }

    /**
     * @return = ArrayList of teachers
     */
    public ArrayList<Teacher> getTeachers() {

        return teachers;
    }

    /**
     * @return = ArrayList of students
     */
    public ArrayList<Student> getStudents() {

        return students;
    }

    /**
     * Allows either student or teacher to sign in with their username and password
     *
     * @param scanner = object of an imported class Scanner
     * @throws Exception
     */
    public static void signIn(Scanner scanner, BufferedReader br, PrintWriter pw) throws Exception { // Sign in method either for teachers or students

        int check1 = 0;
        int check2 = 0;
        while (true) {
            System.out.println("Sign In\n");
            System.out.println(teacherOrStudentSignIn);
            pw.println("Sign In\n" + teacherOrStudentSignIn);
            pw.flush();
            pw.println("esc");
            pw.flush();

            String choiceTeacherOrStudent;

            String response = br.readLine();
            choiceTeacherOrStudent = response;

            if (choiceTeacherOrStudent.equals("1")) {

                System.out.println(enterUsername);
                pw.println(enterUsername);
                pw.flush();
                pw.println("esc");
                pw.flush();

                String username;
                response = br.readLine();
                username = response;

                for (Teacher item : teachers) {

                    if (username.equals(item.getUsername())) {
                        check2 = 1;
                        System.out.println(enterPassword);
                        pw.println(enterPassword);
                        pw.flush();
                        pw.println("esc");
                        pw.flush();

                        response = br.readLine();
                        String password;
                        password = response;

                        if (password.equals(item.getPassword())) {

                            System.out.println("Success!\n");
                            pw.println("Success!\n");
                            pw.flush();

                            menuTeacher(username, scanner, br, pw);

                        } else {
                            pw.println(passwordDoesntMatch);
                            pw.flush();
                            System.out.println(passwordDoesntMatch);

                        }
                        break;

                    }
                }
                if (check2 == 0) {
                    pw.println(usernameDoesntExist);
                    pw.flush();
                    System.out.println(usernameDoesntExist);

                }
                break;
            } else if (choiceTeacherOrStudent.equals("2")) {

                System.out.println(enterUsername);
                pw.println(enterUsername);
                pw.flush();
                pw.println("esc");
                pw.flush();

                String username;
                response = br.readLine();
                username = response;

                for (Student item : students) {

                    if (username.equals(item.getUsername())) {
                        check1 = 1;
                        System.out.println(enterPassword);
                        pw.println(enterPassword);
                        pw.flush();
                        pw.println("esc");
                        pw.flush();

                        String password;
                        response = br.readLine();
                        password = response;

                        if (password.equals(item.getPassword())) {

                            System.out.println("Success!\n");
                            pw.println("Success!\n");
                            pw.flush();

                            menuStudent(username, scanner, br, pw);

                        } else {
                            pw.println(passwordDoesntMatch);
                            pw.flush();
                            System.out.println(passwordDoesntMatch);

                        }
                        break;
                    }
                }
                if (check1 == 0) {
                    pw.println(usernameDoesntExist);
                    pw.flush();
                    System.out.println(usernameDoesntExist);

                }
                break;
            } else {
                pw.println(incorrectAnswer);
                pw.flush();
                System.out.println(incorrectAnswer);
            }

        }
    }

    /**
     * Allows either student or teacher to register with their first name, last name, username and password
     *
     * @param scanner = object of an imported class Scanner
     * @throws Exception
     */
    public static void register(Scanner scanner, BufferedReader br,
                                PrintWriter pw) throws Exception { // Allows user to register and then Sign In


        while (true) {
            int usernameStatus = 1;
            System.out.println("Register\n");
            pw.println("Register\n");
            pw.flush();
            System.out.println(teacherOrStudentRegister);
            pw.println(teacherOrStudentRegister);
            pw.flush();
            pw.println("esc");
            pw.flush();
            String choiceTeacherOrStudent;
            String response = br.readLine();
            choiceTeacherOrStudent = response;

            if (choiceTeacherOrStudent.equals("1")) {
                System.out.println(enterFirstName);
                pw.println(enterFirstName);
                pw.flush();
                pw.println("esc");
                pw.flush();

                String firstName;
                response = br.readLine();
                firstName = response;
                System.out.println(enterLastName);
                pw.println(enterLastName);
                pw.flush();
                pw.println("esc");
                pw.flush();
                response = br.readLine();
                String lastName;
                lastName = response;
                System.out.println(enterUsername);
                pw.println(enterUsername);
                pw.flush();
                pw.println("esc");
                pw.flush();
                String username;
                response = br.readLine();
                username = response;

                for (Teacher item : teachers) {

                    if (username.equals(item.getUsername())) {
                        pw.println(takenUsername);
                        pw.flush();
                        System.out.println(takenUsername);
                        usernameStatus = 0;
                        break;
                    }
                }


                if (usernameStatus == 1) {
                    System.out.println(enterPassword);
                    pw.println(enterPassword);
                    pw.flush();
                    pw.println("esc");
                    pw.flush();
                    String password;
                    response = br.readLine();
                    password = response;
                    teachers.add(new Teacher(firstName, lastName, username, password));

                    Teacher.createAccount();

                }
                break;
            } else if (choiceTeacherOrStudent.equals("2")) {
                System.out.println(enterFirstName);
                pw.println(enterFirstName);
                pw.flush();
                pw.println("esc");
                pw.flush();
                String firstName;
                response = br.readLine();
                firstName = response;
                System.out.println(enterLastName);
                pw.println(enterLastName);
                pw.flush();
                pw.println("esc");
                pw.flush();
                String lastName;
                response = br.readLine();
                lastName = response;
                System.out.println(enterUsername);
                pw.println(enterUsername);
                pw.flush();
                pw.println("esc");
                pw.flush();
                String username;
                response = br.readLine();
                username = response;

                for (Student item : students) {

                    if (username.equals(item.getUsername())) {
                        pw.println(takenUsername);
                        pw.flush();
                        System.out.println(takenUsername);
                        usernameStatus = 0;
                        break;
                    }
                }

                if (usernameStatus == 1) {
                    System.out.println(enterPassword);
                    pw.println(enterPassword);
                    pw.flush();
                    pw.println("esc");
                    pw.flush();
                    String password;
                    response = br.readLine();
                    password = response;
                    students.add(new Student(firstName, lastName, username, password));

                    Student.createAccount();


                }
                break;
            } else {
                System.out.println(incorrectAnswer);
            }
        }

    }

    /**
     * Allows teachers to change their passwords
     *
     * @param scanner = object of an imported class Scanner
     * @param username = A unique username of the particular teacher
     * @throws Exception
     */
    public static void changePasswordTeacher(String username, Scanner scanner, PrintWriter pw,
                                             BufferedReader br) throws Exception {
        for (Teacher item : teachers) {
            if (username.equals(item.getUsername())) {
                System.out.println(enterNewPassword);
                pw.println(enterNewPassword);
                pw.flush();
                pw.println("esc");
                pw.flush();

                String newPassword;
                String response = br.readLine();
                newPassword = response;

                item.setPassword(newPassword);
                Teacher.createAccount();
                System.out.println(signInAgain);
                pw.println(signInAgain);
                pw.flush();
                signIn(scanner, br, pw);
                break;
            }
        }
    }

    /**
     * Allows students to change their passwords
     *
     * @param scanner = object of an imported class Scanner
     * @param username = A unique username of the particular student
     * @throws Exception
     */
    public static void changePasswordStudent(String username, Scanner scanner, BufferedReader br,
                                             PrintWriter pw) throws Exception {
        for (Student item : students) {
            if (username.equals(item.getUsername())) {
                System.out.println(enterNewPassword);
                pw.println(enterNewPassword);
                pw.flush();
                pw.println("esc");
                pw.flush();
                String newPassword;
                String response = br.readLine();
                newPassword = response;

                item.setPassword(newPassword);
                Student.createAccount();
                System.out.println(signInAgain);
                pw.println(signInAgain);
                pw.flush();
                signIn(scanner, br, pw);
                break;
            }
        }
    }

    /**
     * Allows teachers to delete their accounts
     *
     * @param scanner = object of an imported class Scanner
     * @param username = A unique username of the particular teacher
     * @throws Exception
     */
    public static void deleteAccountTeacher(String username, Scanner scanner, BufferedReader br,
                                            PrintWriter pw) throws Exception {
        try {
            for (Teacher item : teachers) {
                if (username.equals(item.getUsername())) {
                    System.out.println(confirmQuestion);
                    pw.println(confirmQuestion);
                    pw.flush();
                    pw.println("esc");
                    pw.flush();

                    String confirmation;
                    String response = br.readLine();
                    confirmation = response;

                    if (confirmation.equals("1")) {
                        teachers.remove(item);
                        Teacher.teachers.remove(item);

                        Teacher.createAccount();
                        System.out.println("Account deleted!\n");
                        pw.println("Account deleted!\n");
                        pw.flush();
                        System.out.println(signInOrRegister);
                        pw.println(signInOrRegister);
                        pw.println("esc");
                        pw.flush();

                        String choice;
                        response = br.readLine();
                        choice = response;

                        if (choice.equals("1")) {
                            signIn(scanner, br, pw);
                        } else if (choice.equals("2")) {
                            register(scanner, br, pw);
                        } else {
                            System.out.println(incorrectAnswer);
                        }

                    } else if (confirmation.equals("2")) {
                        menuTeacher(username, scanner, br, pw);
                    } else {
                        System.out.println(incorrectAnswer);
                        menuTeacher(username, scanner, br, pw);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Allows students to delete their accounts
     *
     * @param scanner = object of an imported class Scanner
     * @param username = A unique username of the particular student
     * @throws Exception
     */
    public static void deleteAccountStudent(String username, Scanner scanner, BufferedReader br,
                                            PrintWriter pw) throws Exception {
        try {
            for (Student item : students) {
                if (username.equals(item.getUsername())) {
                    pw.println(confirmQuestion);
                    pw.flush();
                    pw.println("esc");
                    pw.flush();
                    System.out.println(confirmQuestion);
                    String confirmation;
                    String response = br.readLine();
                    confirmation = response;

                    if (confirmation.equals("1")) {
                        students.remove(item);
                        Student student = new Student(enterFirstName, enterLastName, username, enterPassword);

                        Student.createAccount();
                        System.out.println("Account deleted!\n");
                        pw.println("Account deleted!\n");
                        pw.flush();
                        do {
                            System.out.println(signInOrRegister);
                            pw.println(signInOrRegister);
                            pw.flush();
                            pw.println("esc");
                            pw.flush();

                            String choice;
                            response = br.readLine();
                            choice = response;

                            if (choice.equals("1")) {
                                signIn(scanner, br, pw);
                                break;
                            } else if (choice.equals("2")) {
                                register(scanner, br, pw);
                                break;
                            } else {
                                pw.println(incorrectAnswer);
                                pw.flush();
                                System.out.println(incorrectAnswer);
                            }
                        } while (true);
                    } else if (confirmation.equals("2")) {
                        menuStudent(username, scanner, br, pw);
                    } else {
                        pw.println(incorrectAnswer);
                        pw.flush();
                        System.out.println(incorrectAnswer);
                        menuStudent(username, scanner, br, pw);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the menu with all the things a teacher can do
     *
     * @param scanner = object of an imported class Scanner
     * @param username = A unique username of the particular teacher
     * @throws Exception
     */
    public static void menuTeacher(String username, Scanner scanner, BufferedReader br, PrintWriter pw) throws Exception {
        try {
            while (true) {
                System.out.println(menuTeacher + "[4] Go to Teacher Portal");
                pw.println(menuTeacher + "[4] Go to Teacher Portal");
                pw.flush();

                System.out.println(chooseOne);
                pw.println(chooseOne);
                pw.flush();
                pw.println("esc");
                pw.flush();

                String choice;
                String response = br.readLine();
                choice = response;

                if (choice.equals("1")) {
                    changePasswordTeacher(username, scanner, pw, br);
                    break;
                } else if (choice.equals("2")) {
                    deleteAccountTeacher(username, scanner, br, pw);
                    break;
                } else if (choice.equals("3")) {
                    signOut(scanner, br, pw);
                    break;
                } else if (choice.equals("4")) {
                    try {
                        Teacher.main(username);
                    } catch (Exception e) {
                        throw new SocketException();
                    }
                    break;
                } else {
                    System.out.println(incorrectAnswer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the menu with all the things a student can do
     *
     * @param scanner = object of an imported class Scanner
     * @param username = A unique username of the particular student
     * @throws Exception
     */
    public static void menuStudent(String username, Scanner scanner, BufferedReader br,
                                   PrintWriter pw) throws Exception {
        int loop = 0;

        try {
            while (true) {
                System.out.println(menuStudent + "[4] View Courses");
                pw.println(menuStudent + "[4] View Courses");
                pw.flush();
                System.out.println(chooseOne);
                pw.println(chooseOne);
                pw.flush();
                pw.println("esc");
                pw.flush();

                String choice;
                String response = br.readLine();
                choice = response;

                if (choice.equals("1")) {
                    changePasswordStudent(username, scanner, br, pw);
                    break;
                } else if (choice.equals("2")) {
                    deleteAccountStudent(username, scanner, br, pw);
                    break;
                } else if (choice.equals("3")) {
                    signOut(scanner, br, pw);
                    break;
                } else if (choice.equals("4")) {
                    try {
                        StudentAnish.main(username, br, pw);
                    } catch (Exception e) {
                        throw new SocketException();
                    }

                    break;
                } else {
                    pw.println(incorrectAnswer);
                    pw.flush();
                    System.out.println(incorrectAnswer);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Allows either student or teacher to Sign Out from their accounts
     *
     * @param scanner = object of an imported class Scanner
     */
    public static void signOut(Scanner scanner, BufferedReader br, PrintWriter pw) {
        pw.println(thankYouMessage);
        pw.flush();
        System.out.println(thankYouMessage);
    }

}