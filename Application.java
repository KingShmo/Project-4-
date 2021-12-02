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

    //If ever equal to 1, quit the program
    private static int quitProgram = 0;

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

        JOptionPane.showMessageDialog(null, welcomeMessage, "Quiz Application",
                JOptionPane.INFORMATION_MESSAGE);

        do {
            String choiceSignInOrRegister = signInOrRegisterInputDialog();
            //quit program if user presses the exit button on GUI
            if (quitProgram == 1) {
                return;
            }

            if (choiceSignInOrRegister.equals("1")) {
                signIn(scanner);
                //quit program if user presses the exit button on GUI
                if (quitProgram == 1) {
                    return;
                }
            } else if (choiceSignInOrRegister.equals("2")) {
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
            if (quitProgram == 1) {
                return;
            }


            if (choiceTeacherOrStudent.equals("1")) {

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
            } else if (choiceTeacherOrStudent.equals("2")) {

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


            if (choiceTeacherOrStudent.equals("1")) {
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
                    //teacher.createAccount(firstName, lastName, username, password);
                    //signIn(scanner);
                }
                break;
            } else if (choiceTeacherOrStudent.equals("2")) {
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
                    //student.createAccount(firstName, lastName, username, password);
                    //signIn(scanner);

                }
                break;
            } else {
                JOptionPane.showMessageDialog(null, incorrectAnswer,
                        "Sign In", JOptionPane.ERROR_MESSAGE);
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
            if (username.equals(item.getUsername())) {
                System.out.println(enterNewPassword);
                String newPassword = scanner.nextLine();
                String oldPassword = item.getPassword();
                //Teacher teacher = new Teacher(null, null, username, oldPassword);
                //teacher.changePassword(username, oldPassword, newPassword);
                item.setPassword(newPassword);
                Teacher.createAccount();
                System.out.println(signInAgain);
                signIn(scanner);
                break;
            }
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
                System.out.println(enterNewPassword);
                String newPassword = scanner.nextLine();
                String oldPassword = item.getPassword();
                //Student student = new Student(null, null, username, oldPassword);
                //item.changePassword(username, oldPassword, newPassword);
                item.setPassword(newPassword);
                Student.createAccount();
                System.out.println(signInAgain);
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
                    System.out.println(confirmQuestion);
                    String confirmation = scanner.nextLine();
                    if (confirmation.equals("1")) {
                        teachers.remove(item);
                        Teacher.teachers.remove(item);
                        Teacher teacher = new Teacher(enterFirstName, enterLastName, username, enterPassword);
                        //teacher.deleteAccount(username);
                        Teacher.createAccount();
                        System.out.println("Account deleted!\n");
                        System.out.println(signInOrRegister);
                        String choice = scanner.nextLine();

                        if (choice.equals("1")) {
                            signIn(scanner);
                        } else if (choice.equals("2")) {
                            register(scanner);
                        } else {
                            System.out.println(incorrectAnswer);
                        }

                    } else if (confirmation.equals("2")) {
                        menuTeacher(username, scanner);
                    } else {
                        System.out.println(incorrectAnswer);
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
                    System.out.println(confirmQuestion);
                    String confirmation = scanner.nextLine();
                    if (confirmation.equals("1")) {
                        students.remove(item);
                        Student student = new Student(enterFirstName, enterLastName, username, enterPassword);
                        //student.deleteAccount(username);
                        Student.createAccount();
                        System.out.println("Account deleted!\n");
                        do {
                            System.out.println(signInOrRegister);
                            String choice = scanner.nextLine();

                            if (choice.equals("1")) {
                                signIn(scanner);
                                break;
                            } else if (choice.equals("2")) {
                                register(scanner);
                                break;
                            } else {
                                System.out.println(incorrectAnswer);
                            }
                        } while (true);
                    } else if (confirmation.equals("2")) {
                        menuStudent(username, scanner);
                    } else {
                        System.out.println(incorrectAnswer);
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
                System.out.println(menuTeacher + "[4] Go to Teacher Portal");
                System.out.println(chooseOne);
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    changePasswordTeacher(username, scanner);
                    break;
                } else if (choice.equals("2")) {
                    deleteAccountTeacher(username, scanner);
                    break;
                } else if (choice.equals("3")) {
                    signOut(scanner);
                    break;
                } else if (choice.equals("4")) {
                    Teacher.main(username);
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
     * @param scanner  = object of an imported class Scanner
     * @param username = A unique username of the particular student
     * @throws Exception
     */
    public static void menuStudent(String username, Scanner scanner) throws Exception {
        int loop = 0;

        try {
            while (true) {
                System.out.println(menuStudent + "[4] View Courses");
                System.out.println(chooseOne);
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
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
     * Allows either student or teacher to Sign Out from their accounts
     *
     * @param scanner = object of an imported class Scanner
     */


    public static void signOut(Scanner scanner) {
        System.out.println(thankYouMessage);
    }

    //sign in or register dialog for options
    public static String signInOrRegisterInputDialog() {
        String option;
        String[] optionList = {"1", "2"};
        option = (String) JOptionPane.showInputDialog(null, signInOrRegister,
                "Fantasy Football", JOptionPane.QUESTION_MESSAGE, null, optionList,
                optionList[0]);
        if (option == null) {
            quitProgram = 1;
            return null;
        } else {
            return option;
        }
    }

    //sign in dialog for options
    public static String signInInputDialog() {
        String option;
        String[] optionList = {"1", "2"};
        option = (String) JOptionPane.showInputDialog(null, teacherOrStudentSignIn,
                "Sign In", JOptionPane.QUESTION_MESSAGE, null, optionList,
                optionList[0]);
        if (option == null) {
            quitProgram = 1;
            return null;
        } else {
            return option;
        }
    }

    //Prompts for username
    public static String enterUsernameDialog(String title) {
        String username;
        boolean enterUser = false;
        do {
            username = JOptionPane.showInputDialog(null, enterUsername,
                    title, JOptionPane.QUESTION_MESSAGE);
            //if user wants to exit the program
            if (username == null) {
                enterUser = true;
                quitProgram = 1;
            } else if (username.isBlank()) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty!",
                        title, JOptionPane.ERROR_MESSAGE);
                enterUser = false;
            } else {
                enterUser = true;
            }
        } while (!enterUser);
        return username;
    }

    //Prompts for password
    public static String enterPasswordDialog(String title) {
        String password;
        boolean enterPass = false;
        do {
            password = JOptionPane.showInputDialog(null, enterPassword,
                    title, JOptionPane.QUESTION_MESSAGE);
            //if user wants to exit the program
            if (password == null) {
                enterPass = true;
                quitProgram = 1;
            } else if (password.isBlank()) {
                JOptionPane.showMessageDialog(null, "Password cannot be empty!",
                        title, JOptionPane.ERROR_MESSAGE);
                enterPass = false;
            } else {
                enterPass = true;
            }
        } while (!enterPass);
        return password;
    }

    //sign in dialog for options
    public static String registerInputDialog() {
        String option;
        String[] optionList = {"1", "2"};
        option = (String) JOptionPane.showInputDialog(null, teacherOrStudentRegister,
                "Register", JOptionPane.QUESTION_MESSAGE, null, optionList,
                optionList[0]);
        if (option == null) {
            quitProgram = 1;
            return null;
        } else {
            return option;
        }
    }

    //Prompts for firstname
    public static String enterFirstNameDialog() {
        String firstName;
        boolean enterFirstName = false;
        do {
            firstName = JOptionPane.showInputDialog(null, enterUsername,
                    "Register", JOptionPane.QUESTION_MESSAGE);
            //if user wants to exit the program
            if (firstName == null) {
                enterFirstName = true;
                quitProgram = 1;
            } else if (firstName.isBlank()) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty!",
                        "Register", JOptionPane.ERROR_MESSAGE);
                enterFirstName = false;
            } else {
                enterFirstName = true;
            }
        } while (!enterFirstName);
        return firstName;
    }

    //Prompts for lastname
    public static String enterLastNameDialog() {
        String lastName;
        boolean enterLastName = false;
        do {
            lastName = JOptionPane.showInputDialog(null, enterUsername,
                    "Register", JOptionPane.QUESTION_MESSAGE);
            //if user wants to exit the program
            if (lastName == null) {
                enterLastName = true;
                quitProgram = 1;
            } else if (lastName.isBlank()) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty!",
                        "Register", JOptionPane.ERROR_MESSAGE);
                enterLastName = false;
            } else {
                enterLastName = true;
            }
        } while (!enterLastName);
        return lastName;
    }



}
