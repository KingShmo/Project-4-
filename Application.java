import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * Application class
 * <p>
 * This class starts the program and asks a user to sign in or register
 *
 * @author Artemii, Zuhair Almansouri, Anish Ketha
 * @version December 11, 2021
 */

public class Application implements Runnable {

    private static String welcomeMessage = "Welcome to ClassroomClient!";
    private static String signInOrRegister = "Do you want to Sign-in or Register?";
    private static String incorrectAnswer = "Sorry, you entered an incorrect answer. Please try again.";
    private static String enterFirstName = "Enter your first name.";
    private static String enterLastName = "Enter your last name.";
    private static String enterUsername = "Enter your username.";
    private static String enterPassword = "Enter your password.";
    private static String takenUsername = "Sorry, this username is already taken. Please enter a different username";
    private static String usernameDoesntExist = "Sorry, there is no user with this username. " +
            "Please try again or Register.";
    private static String passwordDoesntMatch = "Sorry, the password you entered doesn't match the username";
    private static String enterNewPassword = "Please enter the new password";
    private static String signInAgain = "Please Sign In again to confirm your password change";
    private static String thankYouMessage = "Thank you for using Quiz app!";
    /**
     * Asks a user to confirm that they want to delete their account
     */
    private static String confirmQuestion =
            "Are you sure that you want to delete your account?";
    private static String teacherOrStudentSignIn =
            "Do you want to sign in as a Teacher or as a Student?";
    /**
     * Choose to sign in either as a teacher, or as a student
     */
    private static String teacherOrStudentRegister =
            "Do you want to register as a Teacher or as a Student?";
    /**
     * Choose to register either as a teacher, or as a student
     */
    private static String menuTeacher =
            "Menu:\n" +
                    "[1] Change your password\n" +
                    "[2] Delete your account\n" +
                    "[3] Sign Out\n" +
                    "[4] Go to Teacher Portal";
    private static String menuStudent =
            "Menu:\n" +
                    "[1] Change your password\n" +
                    "[2] Delete your account\n" +
                    "[3] Sign Out\n" +
                    "[4] View Courses";
    /**
     * Choose an option from the menu
     */
    private static String chooseOne = "Pick a corresponding number to choose an option";
    /**
     * all teachers
     */
    static ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    /**
     * all students
     */
    static ArrayList<Student> students = new ArrayList<Student>();

    /**
     * contains quizzes
     */
    static QuizArchive quizArchive;

    /**
     * sync threads
     */
    private static Object sync = new Object();

    /**
     * used for the while loop in the run method
     */
    static int check3 = 0;

    /**
     * If ever equal to 1, quit the program
     */
    public int[] quitProgram = {0};

    /**
     * socket offered for the client
     */
    private Socket socket;

    /**
     * Runs the program from the beginning
     * Checks if user wants to Sign In or Register
     * Runs an infinite loop till the user decides to Sign Out
     *
     * @throws Exception
     */

    public Application() {

    }

    public Application(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws Exception {

        //Text files to be created
        String[] files = {"QuizQuestions.txt", "CourseDetails.txt", "TeacherAccounts.txt", "StudentAccounts.txt"
                , "StudentQuizzes.txt"};
        for (int i = 0; i < files.length; i++) {
            try {
                //Test if these files exist
                BufferedReader br = new BufferedReader(new FileReader(files[i]));
            } catch (FileNotFoundException e) {
                //Create the files that do not exist
                FileOutputStream fot = new FileOutputStream(files[i]);
            }
        }


        //Read teachers and students from files
        Student.readTeachers(teachers);
        Student.readStudents();

        quizArchive = new QuizArchive();

        //Read quizzes from files
        PrintInformation.readQuizQuestions(quizArchive);

        //Read courses from files
        Teacher.readAllCourses();

        //Read student submissions from files
        StudentAnish.readStudentSubmissions(quizArchive);

        teachers = Teacher.teachers;
        students = Student.students;

        ServerSocket serverSocket = new ServerSocket(1234);


        while (true) {

            Socket socket = serverSocket.accept();
            Application server = new Application(socket);
            new Thread(server).start();


        }

    }

    /**
     * Runs the whole program for clients
     */
    public void run() {

        int[] quitProgram = new Application().quitProgram;

        JOptionPane.showMessageDialog(null, welcomeMessage, "Classroom Client",
                JOptionPane.INFORMATION_MESSAGE);

        do {
            String choiceSignInOrRegister = signInOrRegisterInputDialog();
            //quit program if user presses the exit button on GUI
            if (quitProgram[0] == 1 || choiceSignInOrRegister == null) {
                return;
            }

            if (choiceSignInOrRegister.equals("Sign-In")) {
                try {
                    signIn(quitProgram);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //quit program if user presses the exit button on GUI
                if (quitProgram[0] == 1) {
                    return;
                }
            } else if (choiceSignInOrRegister.equals("Register")) {
                try {
                    register(quitProgram);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //quit program if user presses the exit button on GUI
                if (quitProgram[0] == 1) {
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, incorrectAnswer,
                        "Sign In", JOptionPane.ERROR_MESSAGE);
            }
        } while (check3 == 0);

        try {
            Student.createAccount();
            Teacher.createAccount();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
     * Sign in menu
     *
     * @param quitProgram to check if the user quits
     * @throws Exception
     */
    public static void signIn(int[] quitProgram) throws Exception { // Sign in method either for teachers or students

        int check1 = 0;
        int check2 = 0;
        while (true) {

            String choiceTeacherOrStudent = signInInputDialog(quitProgram);
            //get out of method if user presses the exit button on GUI
            if (quitProgram[0] == 1 || choiceTeacherOrStudent == null) {
                return;
            }


            if (choiceTeacherOrStudent.equals("Teacher")) {

                String username = enterUsernameDialog("Sign In", quitProgram);
                //get out of method if user presses the exit button on GUI
                if (quitProgram[0] == 1) {
                    return;
                }

                for (Teacher item : teachers) {

                    if (username.equals(item.getUsername())) {
                        check2 = 1;
                        String password = enterPasswordDialog("Sign In", quitProgram);
                        //get out of method if user presses the exit button on GUI
                        if (quitProgram[0] == 1) {
                            return;
                        }
                        if (password.equals(item.getPassword())) {

                            JOptionPane.showMessageDialog(null, "Success!",
                                    "Sign In", JOptionPane.INFORMATION_MESSAGE);
                            menuTeacher(username, quitProgram);

                        } else {
                            JOptionPane.showMessageDialog(null, passwordDoesntMatch,
                                    "Sign In", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    }
                }
                if (check2 == 0) {
                    JOptionPane.showMessageDialog(null, usernameDoesntExist,
                            "Sign In", JOptionPane.ERROR_MESSAGE);
                }
                break;
            } else if (choiceTeacherOrStudent.equals("Student")) {

                String username = enterUsernameDialog("Sign In", quitProgram);
                //get out of method if user presses the exit button on GUI
                if (quitProgram[0] == 1) {
                    return;
                }

                for (Student item : students) {

                    if (username.equals(item.getUsername())) {
                        check1 = 1;
                        String password = enterPasswordDialog("Sign In", quitProgram);
                        //get out of method if user presses the exit button on GUI
                        if (quitProgram[0] == 1) {
                            return;
                        }
                        if (password.equals(item.getPassword())) {

                            JOptionPane.showMessageDialog(null, "Success!",
                                    "Sign In", JOptionPane.INFORMATION_MESSAGE);
                            menuStudent(username, quitProgram);

                        } else {
                            JOptionPane.showMessageDialog(null, passwordDoesntMatch,
                                    "Sign In", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    }
                }
                if (check1 == 0) {
                    JOptionPane.showMessageDialog(null, usernameDoesntExist,
                            "Sign In", JOptionPane.ERROR_MESSAGE);

                }
                break;
            } else {
                JOptionPane.showMessageDialog(null, incorrectAnswer,
                        "Sign In", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Register menu
     *
     * @param quitProgram to check if the user quits
     * @throws Exception
     */
    public static void register(int[] quitProgram) throws Exception { // Allows user to register and then Sign In


        while (true) {
            int usernameStatus = 1;
            String choiceTeacherOrStudent = registerInputDialog(quitProgram);
            //get out of method if user presses the exit button on GUI
            if (quitProgram[0] == 1) {
                return;
            }


            if (choiceTeacherOrStudent.equals("Teacher")) {
                String firstName = enterFirstNameDialog(quitProgram);
                //get out of method if user presses the exit button on GUI
                if (quitProgram[0] == 1) {
                    return;
                }

                String lastName = enterLastNameDialog(quitProgram);
                //get out of method if user presses the exit button on GUI
                if (quitProgram[0] == 1) {
                    return;
                }

                String username = enterUsernameDialog("Register", quitProgram);
                //get out of method if user presses the exit button on GUI
                if (quitProgram[0] == 1) {
                    return;
                }

                for (Teacher item : teachers) {

                    if (username.equals(item.getUsername())) {
                        JOptionPane.showMessageDialog(null, takenUsername,
                                "Register", JOptionPane.ERROR_MESSAGE);
                        usernameStatus = 0;
                        break;
                    }
                }


                if (usernameStatus == 1) {
                    String password = enterPasswordDialog("Register", quitProgram);
                    //get out of method if user presses the exit button on GUI
                    if (quitProgram[0] == 1) {
                        return;
                    }
                    synchronized (sync) {
                        teachers.add(new Teacher(firstName, lastName, username, password));
                    }
                    Teacher teacher = new Teacher(firstName, lastName, username, password);
                    Teacher.createAccount();
                    JOptionPane.showMessageDialog(null, "Success!",
                            "Register", JOptionPane.INFORMATION_MESSAGE);
                    //teacher.createAccount(firstName, lastName, username, password);
                    //signIn();
                }
                break;
            } else if (choiceTeacherOrStudent.equals("Student")) {
                String firstName = enterFirstNameDialog(quitProgram);
                //get out of method if user presses the exit button on GUI
                if (quitProgram[0] == 1) {
                    return;
                }

                String lastName = enterLastNameDialog(quitProgram);
                //get out of method if user presses the exit button on GUI
                if (quitProgram[0] == 1) {
                    return;
                }

                String username = enterUsernameDialog("Register", quitProgram);
                //get out of method if user presses the exit button on GUI
                if (quitProgram[0] == 1) {
                    return;
                }

                for (Student item : students) {

                    if (username.equals(item.getUsername())) {
                        JOptionPane.showMessageDialog(null, takenUsername,
                                "Register", JOptionPane.ERROR_MESSAGE);
                        usernameStatus = 0;
                        break;
                    }
                }

                if (usernameStatus == 1) {
                    String password = enterPasswordDialog("Register", quitProgram);
                    //get out of method if user presses the exit button on GUI
                    if (quitProgram[0] == 1) {
                        return;
                    }
                    synchronized (sync) {
                        students.add(new Student(firstName, lastName, username, password));
                    }
                    //Student student = new Student(firstName, lastName, username, password);
                    Student.createAccount();
                    JOptionPane.showMessageDialog(null, "Success!",
                            "Register", JOptionPane.INFORMATION_MESSAGE);
                    //student.createAccount(firstName, lastName, username, password);
                    //signIn();


                }
                break;
            } else {
                JOptionPane.showMessageDialog(null, incorrectAnswer,
                        "Register", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * Change teacher passwords
     *
     * @param quitProgram to check if the user quits
     * @throws Exception
     */
    public static void changePasswordTeacher(int[] quitProgram, String username) throws Exception {

        for (Teacher item : teachers) {
            if (item.getUsername().equals(username)) {
                String newPassword = enterNewPasswordDialog(quitProgram);
                if (newPassword == null) {
                    quitProgram[0] = 1;
                    return;
                }

                item.setPassword(newPassword);
                Teacher.createAccount();

                JOptionPane.showMessageDialog(null, "Success!",
                        "Change Password", JOptionPane.INFORMATION_MESSAGE);
                signIn(quitProgram);
                break;
            }
        }
    }

    /**
     * change students passwords
     *
     * @param username    = the student whose password will change
     * @param quitProgram to check if the user quits
     * @throws Exception
     */
    public static void changePasswordStudent(String username, int[] quitProgram) throws Exception {
        for (Student item : students) {
            if (username.equals(item.getUsername())) {
                String newPassword = enterNewPasswordDialog(quitProgram);
                if (newPassword == null) {
                    quitProgram[0] = 1;
                    return;
                }
                String oldPassword = item.getPassword();
                //Student student = new Student(null, null, username, oldPassword);
                //item.changePassword(username, oldPassword, newPassword);
                item.setPassword(newPassword);
                Student.createAccount();
                JOptionPane.showMessageDialog(null, "Success!",
                        "Change Password", JOptionPane.INFORMATION_MESSAGE);
                signIn(quitProgram);
                break;
            }
        }
    }

    /**
     * Delete a teacher account
     *
     * @param username    = teacher's account
     * @param quitProgram = to check if the user quits
     * @throws Exception
     */
    public static void deleteAccountTeacher(String username, int[] quitProgram) throws Exception {
        try {
            for (Teacher item : teachers) {
                if (username.equals(item.getUsername())) {
                    String confirmation = confirmQuestionInputDialog(quitProgram);
                    //get out of method if user presses the exit button on GUI
                    if (quitProgram[0] == 1) {
                        return;
                    }
                    if (confirmation.equals("Yes")) {
                        synchronized (sync) {
                            teachers.remove(item);
                            Teacher.teachers.remove(item);
                        }
                        Teacher teacher = new Teacher(enterFirstName, enterLastName, username, enterPassword);
                        //teacher.deleteAccount(username);
                        Teacher.createAccount();
                        JOptionPane.showMessageDialog(null, "Account deleted!",
                                "Account Details", JOptionPane.INFORMATION_MESSAGE);
                        String choice = signInOrRegisterInputDialog();
                        //get out of method if user presses the exit button on GUI
                        if (quitProgram[0] == 1) {
                            return;
                        }
                        if (choice.equals("Sign-In")) {
                            signIn(quitProgram);
                        } else if (choice.equals("Register")) {
                            register(quitProgram);
                        }

                    } else if (confirmation.equals("No")) {
                        menuTeacher(username, quitProgram);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delete student accounts
     *
     * @param username    = student's account
     * @param quitProgram to check if the user quits
     * @throws Exception
     */
    public static void deleteAccountStudent(String username, int[] quitProgram) throws Exception {
        try {
            for (Student item : students) {
                if (username.equals(item.getUsername())) {
                    String confirmation = confirmQuestionInputDialog(quitProgram);
                    //get out of method if user presses the exit button on GUI
                    if (quitProgram[0] == 1) {
                        return;
                    }
                    if (confirmation.equals("Yes")) {
                        synchronized (sync) {
                            students.remove(item);
                        }
                        Student student = new Student(enterFirstName, enterLastName, username, enterPassword);
                        //student.deleteAccount(username);
                        Student.createAccount();
                        JOptionPane.showMessageDialog(null, "Account deleted!",
                                "Account Details", JOptionPane.INFORMATION_MESSAGE);
                        do {
                            String choice = signInOrRegisterInputDialog();
                            //get out of method if user presses the exit button on GUI
                            if (quitProgram[0] == 1) {
                                return;
                            }
                            if (choice.equals("Sign-In")) {
                                signIn(quitProgram);
                                break;
                            } else if (choice.equals("Register")) {
                                register(quitProgram);
                                break;
                            }
                        } while (true);
                    } else if (confirmation.equals("No")) {
                        menuStudent(username, quitProgram);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Teacher menu
     *
     * @param username    signed in teacher
     * @param quitProgram to check if the user quits
     * @throws Exception
     */
    public static void menuTeacher(String username, int[] quitProgram) throws Exception {
        try {
            while (true) {
                quitProgram[0] = 0;
                String choice;
                String[] optionList = {"1", "2", "3", "4"};
                choice = (String) JOptionPane.showInputDialog(null, menuTeacher, "Teacher Portal",
                        JOptionPane.QUESTION_MESSAGE, null, optionList, optionList[0]);
                if (choice == null) {
                    JOptionPane.showMessageDialog(null, "Thank you for using the Teacher Portal!",
                            "Teacher Portal", JOptionPane.INFORMATION_MESSAGE);
                    quitProgram[0] = 1;
                    return;
                } else if (choice.equals("1")) {
                    changePasswordTeacher(quitProgram, username);
                    break;
                } else if (choice.equals("2")) {
                    deleteAccountTeacher(username, quitProgram);
                    break;
                } else if (choice.equals("3")) {
                    signOutTeacher();
                    break;
                } else if (choice.equals("4")) {
                    Teacher.main(username, quitProgram);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * displays student menu
     *
     * @param username    signed in student
     * @param quitProgram to check if the user quits
     * @throws Exception
     */
    public static void menuStudent(String username, int[] quitProgram) throws Exception {
        try {
            while (true) {
                String choice;
                String[] optionList = {"1", "2", "3", "4"};
                choice = (String) JOptionPane.showInputDialog(null, menuStudent, "Student Portal",
                        JOptionPane.QUESTION_MESSAGE, null, optionList, optionList[0]);

                if (choice == null) {
                    JOptionPane.showMessageDialog(null, "Thank you for using the Student Portal!",
                            "Student Portal", JOptionPane.INFORMATION_MESSAGE);
                    quitProgram[0] = 1;
                    return;
                } else if (choice.equals("1")) {
                    changePasswordStudent(username, quitProgram);
                    break;
                } else if (choice.equals("2")) {
                    deleteAccountStudent(username, quitProgram);
                    break;
                } else if (choice.equals("3")) {
                    signOut();
                    break;
                } else if (choice.equals("4")) {
                    StudentAnish.main(username);
                    if (StudentAnish.courseChoice == null || StudentAnish.courseOption == null ||
                            StudentAnish.quizChoice == null || StudentAnish.fileName == null || StudentAnish.fileAnswer
                            == JOptionPane.CLOSED_OPTION || StudentAnish.quizSelection == null) {
                        quitProgram[0] = 1;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Allows either student or teacher to Sign Out from their accounts
     */
    public static void signOut() {
        JOptionPane.showMessageDialog(null, "Thank you for using the Student Portal!",
                "Student Portal", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * teacher signing out
     */
    public static void signOutTeacher() {
        JOptionPane.showMessageDialog(null, "Thank you for using the Teacher Portal!",
                "Teacher Portal", JOptionPane.INFORMATION_MESSAGE);
    }

    //sign in or register dialog for options
    public static String signInOrRegisterInputDialog() {
        String option;
        String[] optionList = {"Sign-In", "Register"};
        option = (String) JOptionPane.showInputDialog(null, signInOrRegister,
                "ClassroomClient", JOptionPane.QUESTION_MESSAGE, null, optionList,
                optionList[0]);
        if (option == null) {
            JOptionPane.showMessageDialog(null, "Thank you for using Classroom Client!",
                    "Classroom Client", JOptionPane.INFORMATION_MESSAGE);
            return null;
        } else {
            return option;
        }
    }

    /**
     * used for GUIs
     *
     * @param quitProgram to check if the user quits
     * @return the answer from user
     */
    public static String signInInputDialog(int[] quitProgram) {
        String option;
        String[] optionList = {"Teacher", "Student"};
        option = (String) JOptionPane.showInputDialog(null, teacherOrStudentSignIn,
                "Sign In", JOptionPane.QUESTION_MESSAGE, null, optionList,
                optionList[0]);
        if (option == null) {
            JOptionPane.showMessageDialog(null, "Thank you for using Classroom Client!",
                    "Classroom Client", JOptionPane.INFORMATION_MESSAGE);
            quitProgram[0] = 1;
            return null;
        } else {
            return option;
        }
    }

    //Prompts for username
    public static String enterUsernameDialog(String title, int[] quitProgram) {
        String username;
        int loop = 0;
        do {
            loop = 0;
            username = JOptionPane.showInputDialog(null, enterUsername,
                    title, JOptionPane.QUESTION_MESSAGE);
            //if user wants to exit the program
            if (username == null) {
                JOptionPane.showMessageDialog(null, "Thank you for using Classroom Client!",
                        "Classroom Client", JOptionPane.INFORMATION_MESSAGE);
                quitProgram[0] = 1;
            } else if (username.isBlank()) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty!",
                        title, JOptionPane.ERROR_MESSAGE);
                loop = 1;
            }
        } while (loop == 1);
        return username;
    }

    //Prompts for password
    public static String enterPasswordDialog(String title, int[] quitProgram) {
        String password;
        int loop = 0;
        do {
            loop = 0;
            password = JOptionPane.showInputDialog(null, enterPassword,
                    title, JOptionPane.QUESTION_MESSAGE);
            //if user wants to exit the program
            if (password == null) {
                JOptionPane.showMessageDialog(null, "Thank you for using Classroom Client!",
                        "Classroom Client", JOptionPane.INFORMATION_MESSAGE);
                quitProgram[0] = 1;
            } else if (password.isBlank()) {
                JOptionPane.showMessageDialog(null, "Password cannot be empty!",
                        title, JOptionPane.ERROR_MESSAGE);
                loop = 1;
            }
        } while (loop == 1);
        return password;
    }

    //sign in dialog for options
    public static String registerInputDialog(int[] quitProgram) {
        String option;
        String[] optionList = {"Teacher", "Student"};
        option = (String) JOptionPane.showInputDialog(null, teacherOrStudentRegister,
                "Register", JOptionPane.QUESTION_MESSAGE, null, optionList,
                optionList[0]);
        if (option == null) {
            JOptionPane.showMessageDialog(null, "Thank you for using Classroom Client!",
                    "Classroom Client", JOptionPane.INFORMATION_MESSAGE);
            quitProgram[0] = 1;
            return null;
        } else {
            return option;
        }
    }

    //Prompts for firstname
    public static String enterFirstNameDialog(int[] quitProgram) {
        String firstName;
        int loop = 0;
        do {
            loop = 0;
            firstName = JOptionPane.showInputDialog(null, enterFirstName,
                    "Register", JOptionPane.QUESTION_MESSAGE);
            //if user wants to exit the program
            if (firstName == null) {
                JOptionPane.showMessageDialog(null, "Thank you for using Classroom Client!",
                        "Classroom Client", JOptionPane.INFORMATION_MESSAGE);
                quitProgram[0] = 1;
            } else if (firstName.isBlank()) {
                JOptionPane.showMessageDialog(null, "First name cannot be empty!",
                        "Register", JOptionPane.ERROR_MESSAGE);
                loop = 1;
            }
        } while (loop == 1);
        return firstName;
    }

    //Prompts for lastname
    public static String enterLastNameDialog(int[] quitProgram) {
        String lastName;
        int loop = 0;
        do {
            loop = 0;
            lastName = JOptionPane.showInputDialog(null, enterLastName,
                    "Register", JOptionPane.QUESTION_MESSAGE);
            //if user wants to exit the program
            if (lastName == null) {
                JOptionPane.showMessageDialog(null, "Thank you for using Classroom Client!",
                        "Classroom Client", JOptionPane.INFORMATION_MESSAGE);
                quitProgram[0] = 1;
            } else if (lastName.isBlank()) {
                JOptionPane.showMessageDialog(null, "Last name cannot be empty!",
                        "Register", JOptionPane.ERROR_MESSAGE);
                loop = 1;
            }
        } while (loop == 1);
        return lastName;
    }

    //Prompts for new password
    public static String enterNewPasswordDialog(int[] quitProgram) {
        String password;
        int loop = 0;
        do {
            loop = 0;
            password = JOptionPane.showInputDialog(null, enterNewPassword,
                    "Change Password", JOptionPane.QUESTION_MESSAGE);
            //if user wants to exit the program
            if (password == null) {
                JOptionPane.showMessageDialog(null, "Thank you for using Classroom Client!",
                        "Classroom Client", JOptionPane.INFORMATION_MESSAGE);
                quitProgram[0] = 1;
            } else if (password.isBlank()) {
                JOptionPane.showMessageDialog(null, "Your new password cannot be empty!",
                        "Change Password", JOptionPane.ERROR_MESSAGE);
                loop = 1;
            }
        } while (loop == 1);
        return password;
    }

    //sign in dialog for options
    public static String confirmQuestionInputDialog(int[] quitProgram) {
        String option;
        String[] optionList = {"Yes", "No"};
        option = (String) JOptionPane.showInputDialog(null, confirmQuestion,
                "Confirmation", JOptionPane.QUESTION_MESSAGE, null, optionList,
                optionList[0]);
        if (option == null) {
            quitProgram[0] = 1;
            return null;
        } else {
            return option;
        }
    }

    //Displays error message
    public static void incorrectMessageDialog() {
        JOptionPane.showMessageDialog(null, incorrectAnswer,
                "Sign In", JOptionPane.ERROR_MESSAGE);
    }

}