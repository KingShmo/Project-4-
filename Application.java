import javax.swing.*;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * Application class
 * <p>
 * This class starts the program and asks a user to sign in or register
 *
 * @author Artemii, Zuhair Almansouri, Anish Ketha
 * @version November 15, 2021
 */

public class Application {

    private static String welcomeMessage = "Welcome to ClassroomClient!";
    private static String signInOrRegister = "Do you want to Sign-in or Register?";
    private static String incorrectAnswer = "Sorry, you entered an incorrect answer. Please try again.";
    private static String enterFirstName = "Enter your first name.";
    private static String enterLastName = "Enter your last name.";
    private static String enterUsername = "Enter your username.";
    private static String enterPassword = "Enter your password.";
    private static String takenUsername = "Sorry, this username is already taken. Please enter a different username";
    private static String usernameDoesntExist = "Sorry, there is no user with this username. Please try again or Register.";
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
    static ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    static ArrayList<Student> students = new ArrayList<Student>();
    /**
     * These "check" variables indicate if there is the object in either teachers ArrayList or students ArrayList with corresponding username
     */

    static QuizArchive quizArchive;

    static int check3 = 0;

    //If ever equal to 1, quit the program
    public static int quitProgram = 0;

    /**
     * Runs the program from the beginning
     * Checks if user wants to Sign In or Register
     * Runs an infinite loop till the user decides to Sign Out
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        Student.readTeachers(teachers);
        Student.readStudents();

        quizArchive = new QuizArchive();
        PrintInformation.readQuizQuestions(quizArchive);

        Teacher.readAllCourses();

        StudentAnish.readStudentSubmissions(quizArchive);

        //Student.readStudentsScores();

        teachers = Teacher.teachers;
        students = Student.students;

        JOptionPane.showMessageDialog(null, welcomeMessage, "ClassroomClient",
                JOptionPane.INFORMATION_MESSAGE);

        do {
            String choiceSignInOrRegister = signInOrRegisterInputDialog();
            //quit program if user presses the exit button on GUI
            if (quitProgram == 1 || choiceSignInOrRegister == null) {
                return;
            }

            if (choiceSignInOrRegister.equals("Sign-In")) {
                signIn(scanner);
                //quit program if user presses the exit button on GUI
                if (quitProgram == 1) {
                    return;
                }
            } else if (choiceSignInOrRegister.equals("Register")) {
                register(scanner);
                //quit program if user presses the exit button on GUI
                if (quitProgram == 1) {
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, incorrectAnswer,
                        "Sign In", JOptionPane.ERROR_MESSAGE);
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
    public static void signIn(Scanner scanner) throws Exception { // Sign in method either for teachers or students

        int check1 = 0;
        int check2 = 0;
        while (true) {

            String choiceTeacherOrStudent = signInInputDialog();
            //get out of method if user presses the exit button on GUI
            if (quitProgram == 1 || choiceTeacherOrStudent == null) {
                return;
            }


            if (choiceTeacherOrStudent.equals("Teacher")) {

                String username = enterUsernameDialog("Sign In");
                //get out of method if user presses the exit button on GUI
                if (quitProgram == 1) {
                    return;
                }

                for (Teacher item : teachers) {

                    if (username.equals(item.getUsername())) {
                        check2 = 1;
                        String password = enterPasswordDialog("Sign In");
                        //get out of method if user presses the exit button on GUI
                        if (quitProgram == 1) {
                            return;
                        }
                        if (password.equals(item.getPassword())) {

                            JOptionPane.showMessageDialog(null, "Success!",
                                    "Sign In", JOptionPane.INFORMATION_MESSAGE);
                            menuTeacher(username, scanner);

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

                String username = enterUsernameDialog("Sign In");
                //get out of method if user presses the exit button on GUI
                if (quitProgram == 1) {
                    return;
                }

                for (Student item : students) {

                    if (username.equals(item.getUsername())) {
                        check1 = 1;
                        String password = enterPasswordDialog("Sign In");
                        //get out of method if user presses the exit button on GUI
                        if (quitProgram == 1) {
                            return;
                        }
                        if (password.equals(item.getPassword())) {

                            JOptionPane.showMessageDialog(null, "Success!",
                                    "Sign In", JOptionPane.INFORMATION_MESSAGE);
                            menuStudent(username, scanner);

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
     * Allows either student or teacher to register with their first name, last name, username and password
     *
     * @param scanner = object of an imported class Scanner
     * @throws Exception
     */
    public static void register(Scanner scanner) throws Exception { // Allows user to register and then Sign In


        while (true) {
            int usernameStatus = 1;
            String choiceTeacherOrStudent = registerInputDialog();
            //get out of method if user presses the exit button on GUI
            if (quitProgram == 1) {
                return;
            }


            if (choiceTeacherOrStudent.equals("Teacher")) {
                String firstName = enterFirstNameDialog();
                //get out of method if user presses the exit button on GUI
                if (quitProgram == 1) {
                    return;
                }

                String lastName = enterLastNameDialog();
                //get out of method if user presses the exit button on GUI
                if (quitProgram == 1) {
                    return;
                }

                String username = enterUsernameDialog("Register");
                //get out of method if user presses the exit button on GUI
                if (quitProgram == 1) {
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
                    String password = enterPasswordDialog("Register");
                    //get out of method if user presses the exit button on GUI
                    if (quitProgram == 1) {
                        return;
                    }
                    teachers.add(new Teacher(firstName, lastName, username, password));
                    Teacher teacher = new Teacher(firstName, lastName, username, password);
                    Teacher.createAccount();
                    JOptionPane.showMessageDialog(null, "Success!",
                            "Register", JOptionPane.INFORMATION_MESSAGE);
                    //teacher.createAccount(firstName, lastName, username, password);
                    //signIn(scanner);
                }
                break;
            } else if (choiceTeacherOrStudent.equals("Student")) {
                String firstName = enterFirstNameDialog();
                //get out of method if user presses the exit button on GUI
                if (quitProgram == 1) {
                    return;
                }

                String lastName = enterLastNameDialog();
                //get out of method if user presses the exit button on GUI
                if (quitProgram == 1) {
                    return;
                }

                String username = enterUsernameDialog("Register");
                //get out of method if user presses the exit button on GUI
                if (quitProgram == 1) {
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
                    String password = enterPasswordDialog("Register");
                    //get out of method if user presses the exit button on GUI
                    if (quitProgram == 1) {
                        return;
                    }
                    students.add(new Student(firstName, lastName, username, password));
                    //Student student = new Student(firstName, lastName, username, password);
                    Student.createAccount();
                    JOptionPane.showMessageDialog(null, "Success!",
                            "Register", JOptionPane.INFORMATION_MESSAGE);
                    //student.createAccount(firstName, lastName, username, password);
                    //signIn(scanner);


                }
                break;
            } else {
                JOptionPane.showMessageDialog(null, incorrectAnswer,
                        "Register", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * Allows teachers to change their passwords
     *
     * @param scanner  = object of an imported class Scanner
     * @param username = A unique username of the particular teacher
     * @throws Exception
     */
    public static void changePasswordTeacher(String username, Scanner scanner) throws Exception {
        for (Teacher item : teachers) {
            String newPassword = enterNewPasswordDialog();
            if (newPassword == null) {
                quitProgram = 1;
                return;
            }
            String oldPassword = item.getPassword();
            //Teacher teacher = new Teacher(null, null, username, oldPassword);
            //teacher.changePassword(username, oldPassword, newPassword);
            item.setPassword(newPassword);
            Teacher.createAccount();
                //Teacher teacher = new Teacher(null, null, username, oldPassword);
                //teacher.changePassword(username, oldPassword, newPassword);
                item.setPassword(newPassword);
                Teacher.createAccount();
            JOptionPane.showMessageDialog(null, "Success!",
                    "Change Password", JOptionPane.INFORMATION_MESSAGE);
            signIn(scanner);
            break;
            }
        }

    /**
     * Allows students to change their passwords
     *
     * @param scanner  = object of an imported class Scanner
     * @param username = A unique username of the particular student
     * @throws Exception
     */
    public static void changePasswordStudent(String username, Scanner scanner) throws Exception {
        for (Student item : students) {
            if (username.equals(item.getUsername())) {
                String newPassword = enterNewPasswordDialog();
                if (newPassword == null) {
                    quitProgram = 1;
                    return;
                }
                String oldPassword = item.getPassword();
                //Student student = new Student(null, null, username, oldPassword);
                //item.changePassword(username, oldPassword, newPassword);
                item.setPassword(newPassword);
                Student.createAccount();
                JOptionPane.showMessageDialog(null, "Success!",
                        "Change Password", JOptionPane.INFORMATION_MESSAGE);
                signIn(scanner);
                break;
            }
        }
    }

    /**
     * Allows teachers to delete their accounts
     *
     * @param scanner  = object of an imported class Scanner
     * @param username = A unique username of the particular teacher
     * @throws Exception
     */
    public static void deleteAccountTeacher(String username, Scanner scanner) throws Exception {
        try {
            for (Teacher item : teachers) {
                if (username.equals(item.getUsername())) {
                    String confirmation = confirmQuestionInputDialog();
                    //get out of method if user presses the exit button on GUI
                    if (quitProgram == 1) {
                        return;
                    }
                    if (confirmation.equals("Yes")) {
                        teachers.remove(item);
                        Teacher.teachers.remove(item);
                        Teacher teacher = new Teacher(enterFirstName, enterLastName, username, enterPassword);
                        //teacher.deleteAccount(username);
                        Teacher.createAccount();
                        JOptionPane.showMessageDialog(null, "Account deleted!",
                                "Account Details", JOptionPane.INFORMATION_MESSAGE);
                        String choice = signInOrRegisterInputDialog();
                        //get out of method if user presses the exit button on GUI
                        if (quitProgram == 1) {
                            return;
                        }
                        if (choice.equals("Sign-In")) {
                            signIn(scanner);
                        } else if (choice.equals("Register")) {
                            register(scanner);
                        }

                    } else if (confirmation.equals("No")) {
                        menuTeacher(username, scanner);
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
     * @param scanner  = object of an imported class Scanner
     * @param username = A unique username of the particular student
     * @throws Exception
     */
    public static void deleteAccountStudent(String username, Scanner scanner) throws Exception {
        try {
            for (Student item : students) {
                if (username.equals(item.getUsername())) {
                    String confirmation = confirmQuestionInputDialog();
                    //get out of method if user presses the exit button on GUI
                    if (quitProgram == 1) {
                        return;
                    }
                    if (confirmation.equals("Yes")) {
                        students.remove(item);
                        Student student = new Student(enterFirstName, enterLastName, username, enterPassword);
                        //student.deleteAccount(username);
                        Student.createAccount();
                        JOptionPane.showMessageDialog(null, "Account deleted!",
                                "Account Details", JOptionPane.INFORMATION_MESSAGE);
                        do {
                            String choice = signInOrRegisterInputDialog();
                            //get out of method if user presses the exit button on GUI
                            if (quitProgram == 1) {
                                return;
                            }
                            if (choice.equals("Sign-In")) {
                                signIn(scanner);
                                break;
                            } else if (choice.equals("Register")) {
                                register(scanner);
                                break;
                            }
                        } while (true);
                    } else if (confirmation.equals("No")) {
                        menuStudent(username, scanner);
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
     * @param scanner  = object of an imported class Scanner
     * @param username = A unique username of the particular teacher
     * @throws Exception
     */
    public static void menuTeacher(String username, Scanner scanner) throws Exception {
        try {
            while (true) {
                String choice;
                String[] optionList = {"1", "2", "3", "4"};
                choice = (String) JOptionPane.showInputDialog(null, menuTeacher, "Teacher Portal",
                        JOptionPane.QUESTION_MESSAGE, null, optionList, optionList[0]);
                if (choice == null) {
                    JOptionPane.showMessageDialog(null, "Thank you for using the Teacher Portal!",
                            "Teacher Portal", JOptionPane.INFORMATION_MESSAGE);
                    quitProgram = 1;
                    return;
                }
                else if (choice.equals("1")) {
                    changePasswordTeacher(username, scanner);
                    break;
                } else if (choice.equals("2")) {
                    deleteAccountTeacher(username, scanner);
                    break;
                } else if (choice.equals("3")) {
                    signOutTeacher(scanner);
                    break;
                } else if (choice.equals("4")) {
                    Teacher.main(username);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the menu with all the things a student can do
     *
     * @param scanner  = object of an imported class Scanner
     * @param username = A unique username of the particular student
     * @throws Exception
     */
    public static void menuStudent(String username, Scanner scanner) throws Exception {
        try {
            while (true) {
                String choice;
                String[] optionList = {"1", "2", "3", "4"};
                choice = (String) JOptionPane.showInputDialog(null, menuStudent, "Student Portal",
                        JOptionPane.QUESTION_MESSAGE, null, optionList, optionList[0]);

                if (choice == null)  {
                    JOptionPane.showMessageDialog(null, "Thank you for using the Student Portal!",
                            "Student Portal", JOptionPane.INFORMATION_MESSAGE);
                    quitProgram = 1;
                    return;
                }
                else if (choice.equals("1")) {
                    changePasswordStudent(username, scanner);
                    break;
                } else if (choice.equals("2")) {
                    deleteAccountStudent(username, scanner);
                    break;
                } else if (choice.equals("3")) {
                    signOut(scanner);
                    break;
                } else if (choice.equals("4")) {
                    StudentAnish.main(username);
                    if (StudentAnish.courseChoice == null || StudentAnish.courseOption == null ||
                            StudentAnish.quizChoice == null) {
                        quitProgram = 1;
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
     *
     * @param scanner = object of an imported class Scanner
     */


    public static void signOut(Scanner scanner) {
        JOptionPane.showMessageDialog(null, "Thank you for using the Student Portal!",
                "Student Portal", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void signOutTeacher(Scanner scanner) {
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

    //sign in dialog for options
    public static String signInInputDialog() {
        String option;
        String[] optionList = {"Teacher", "Student"};
        option = (String) JOptionPane.showInputDialog(null, teacherOrStudentSignIn,
                "Sign In", JOptionPane.QUESTION_MESSAGE, null, optionList,
                optionList[0]);
        if (option == null) {
            JOptionPane.showMessageDialog(null, "Thank you for using Classroom Client!",
                    "Classroom Client", JOptionPane.INFORMATION_MESSAGE);
            quitProgram = 1;
            return null;
        } else {
            return option;
        }
    }

    //Prompts for username
    public static String enterUsernameDialog(String title) {
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
                quitProgram = 1;
            } else if (username.isBlank()) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty!",
                        title, JOptionPane.ERROR_MESSAGE);
                loop = 1;
            }
        } while (loop == 1);
        return username;
    }

    //Prompts for password
    public static String enterPasswordDialog(String title) {
        String password;
        int loop = 0;
        do {
            password = JOptionPane.showInputDialog(null, enterPassword,
                    title, JOptionPane.QUESTION_MESSAGE);
            //if user wants to exit the program
            if (password == null) {
                JOptionPane.showMessageDialog(null, "Thank you for using Classroom Client!",
                        "Classroom Client", JOptionPane.INFORMATION_MESSAGE);
                quitProgram = 1;
            } else if (password.isBlank()) {
                JOptionPane.showMessageDialog(null, "Password cannot be empty!",
                        title, JOptionPane.ERROR_MESSAGE);
                loop = 1;
            }
        } while (loop == 1);
        return password;
    }

    //sign in dialog for options
    public static String registerInputDialog() {
        String option;
        String[] optionList = {"Teacher", "Student"};
        option = (String) JOptionPane.showInputDialog(null, teacherOrStudentRegister,
                "Register", JOptionPane.QUESTION_MESSAGE, null, optionList,
                optionList[0]);
        if (option == null) {
            JOptionPane.showMessageDialog(null, "Thank you for using Classroom Client!",
                    "Classroom Client", JOptionPane.INFORMATION_MESSAGE);
            quitProgram = 1;
            return null;
        } else {
            return option;
        }
    }

    //Prompts for firstname
    public static String enterFirstNameDialog() {
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
                quitProgram = 1;
            } else if (firstName.isBlank()) {
                JOptionPane.showMessageDialog(null, "First name cannot be empty!",
                        "Register", JOptionPane.ERROR_MESSAGE);
                loop = 1;
            }
        } while (loop == 1);
        return firstName;
    }

    //Prompts for lastname
    public static String enterLastNameDialog() {
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
                quitProgram = 1;
            } else if (lastName.isBlank()) {
                JOptionPane.showMessageDialog(null, "Last name cannot be empty!",
                        "Register", JOptionPane.ERROR_MESSAGE);
                loop = 1;
            }
        } while (loop == 1);
        return lastName;
    }
    public static String enterNewPasswordDialog() {
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
                quitProgram = 1;
                return null;
            } else if (password.isBlank()) {
                JOptionPane.showMessageDialog(null, "Your new password cannot be empty!",
                        "Change Password", JOptionPane.ERROR_MESSAGE);
                loop = 1;
            }
        } while (loop == 1);
        return password;
    }
    //sign in dialog for options
    public static String confirmQuestionInputDialog() {
        String option;
        String[] optionList = {"Yes", "No"};
        option = (String) JOptionPane.showInputDialog(null, confirmQuestion,
                "Confirmation", JOptionPane.QUESTION_MESSAGE, null, optionList,
                optionList[0]);
        if (option == null) {
            quitProgram = 1;
            return null;
        } else {
            return option;
        }
    }
    public static void incorrectMessageDialog() {
        JOptionPane.showMessageDialog(null, incorrectAnswer,
                "Sign In", JOptionPane.ERROR_MESSAGE);
    }

}

