/*
This class starts the program and asks a user to sign in or register
*
In order to save all the registration information, I need Troy's file technology implemented
*
Need to make a while loop for main function + quit from the program
*/
import java.util.*;

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
    private static String confirmQuestion = 
    "Are you sure that you want to delete your account?\n" +
    "Press [1] for yes, or [2] for no";
    private static String teacherOrStudentSignIn = 
    "Do you want to sign in as a teacher or as a student?\n" +
    "Pick [1] for teacher or [2] for student.";
    private static String teacherOrStudentRegister = 
    "Do you want to register as a teacher or as a student?\n" +
    "Pick [1] for teacher or [2] for student.";
    private static String menuTeacher = 
    "[1] Change your password\n" +
    "[2] Delete your account\n"; // And other options
    private static String menuStudent = 
    "Menu:\n" +
    "[1] Change your password\n" +
    "[2] Delete your account\n"; // And other options
    private static String chooseOne = "Pick a corresponding number to choose an option";
    static ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    static ArrayList<Student> students = new ArrayList<Student>();
    static int usernameStatus = 1;
    static int check1 = 0; // Checks if there is already a user with that username
    static int check2 = 0;
    



    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);

        System.out.println(welcomeMessage);
        System.out.println(signInOrRegister);
        int choiceSignInOrRegister = scanner.nextInt();

        if (choiceSignInOrRegister == 1) 
        {
            signIn(scanner);
        }
        else if (choiceSignInOrRegister == 2) 
        {
            register(scanner);
        }
        else
        {
            System.out.println(incorrectAnswer);
        }

    }

    public static void signIn(Scanner scanner) throws Exception{ // Sign in method either for teachers or students
        
        System.out.println("Sign In\n");
        System.out.println(teacherOrStudentSignIn);
        int choiceTeacherOrStudent = scanner.nextInt();

        if (choiceTeacherOrStudent == 1) 
        {
            System.out.println(enterUsername);
            scanner.nextLine();
            String username = scanner.nextLine();

            for (Teacher item: teachers) {

                if (username.equals(item.getUsername())) 
                {
                    check2 = 1;
                    System.out.println(enterPassword);
                    String password = scanner.nextLine();
                    if (password.equals(item.getPassword())) {

                        System.out.println("Success!\n");
                        menuTeacher(username, scanner);

                    }
                    else
                    {
                        System.out.println(passwordDoesntMatch);
                    }
                } 
            }
            if (check2 == 0)
            {
                System.out.println(usernameDoesntExist);
            }
        } 
        else if (choiceTeacherOrStudent == 2)
        {
            System.out.println(enterUsername);
            scanner.nextLine();
            String username = scanner.nextLine();

            for (Student item: students) {

                if (username.equals(item.getUsername())) 
                {
                    check1 = 1;
                    System.out.println(enterPassword);
                    String password = scanner.nextLine();
                    if (password.equals(item.getPassword())) {

                        System.out.println("Success!\n");
                        menuStudent(username, scanner);

                    }
                    else
                    {
                        System.out.println(passwordDoesntMatch);
                    }
                } 
            }
            if (check1 == 0)
            {
                System.out.println(usernameDoesntExist);
            }
        }
        else 
        {
            System.out.println(incorrectAnswer);
        }
    }

    public static void register(Scanner scanner) throws Exception{ // Allows user to register and then Sign In

        System.out.println("Register\n");
        System.out.println(teacherOrStudentRegister);
        int choiceTeacherOrStudent = scanner.nextInt();
        

        if (choiceTeacherOrStudent == 1) 
        {
            System.out.println(enterFirstName);
            scanner.nextLine();
            String firstName = scanner.nextLine();
            System.out.println(enterLastName);
            String lastName = scanner.nextLine();
            System.out.println(enterUsername);
            String username = scanner.nextLine();

            for (Teacher item: teachers) {

                if (username.equals(item.getUsername())) 
                {
                    System.out.println(takenUsername);
                    usernameStatus = 0;
                    break;
                }
            }
                
            if (usernameStatus == 1) {
                System.out.println(enterPassword);
                String password = scanner.nextLine();
                teachers.add(new Teacher(firstName, lastName, username, password));
                signIn(scanner);
            }
        } 
        else if (choiceTeacherOrStudent == 2)
        {
            System.out.println(enterFirstName);
            scanner.nextLine();
            String firstName = scanner.nextLine();
            System.out.println(enterLastName);
            String lastName = scanner.nextLine();
            System.out.println(enterUsername);
            String username = scanner.nextLine();

            for (Student item: students) {

                if (username.equals(item.getUsername())) 
                {
                    System.out.println(takenUsername);
                    usernameStatus = 0;
                    break;
                }
            }
                
            if (usernameStatus == 1) {
                System.out.println(enterPassword);
                String password = scanner.nextLine();

                students.add(new Student(firstName, lastName, username, password));
                signIn(scanner);
            }
        }
        else 
        {
            System.out.println(incorrectAnswer);
        }

    }

    public static void changePasswordTeacher(String username, Scanner scanner) throws Exception{
        for (Teacher item: teachers) {
            if (username.equals(item.getUsername()))
            {
                System.out.println(enterNewPassword);
                scanner.nextLine();
                String newPassword = scanner.nextLine();
                item.setPassword(newPassword);
                System.out.println(signInAgain);
                signIn(scanner);
            }
        }
    }

    public static void changePasswordStudent(String username, Scanner scanner) throws Exception{
        for (Student item: students) {
            if (username.equals(item.getUsername()))
            {
                System.out.println(enterNewPassword);
                scanner.nextLine();
                String newPassword = scanner.nextLine();
                item.setPassword(newPassword);
                System.out.println(signInAgain);
                signIn(scanner);
            }
        }
    }

    public static void deleteAccountTeacher(String username, Scanner scanner) throws Exception{
        try {
            for (Teacher item: teachers) {
                if (username.equals(item.getUsername()))
                {
                    System.out.println(confirmQuestion);
                    int confirmation = scanner.nextInt();
                    if (confirmation == 1) 
                    {
                        teachers.remove(item);
                        System.out.println("Account deleteded!\n");
                        System.out.println(signInOrRegister);

                        //if ()
                        register(scanner);
                    }
                    else if (confirmation == 2) 
                    {
                        menuTeacher(username, scanner);
                    }
                    else 
                    {
                        System.out.println(incorrectAnswer);
                        menuTeacher(username, scanner);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAccountStudent(String username, Scanner scanner) throws Exception{
        System.out.println(confirmQuestion);
        int confirmation = scanner.nextInt();
        for (Student item: students) {
            if (username.equals(item.getUsername()))
            {
                if (confirmation == 1) 
                {
                    students.remove(item);
                    System.out.println("Account deleted!\n");
                    register(scanner);
                }
                else if (confirmation == 2) 
                {
                    menuStudent(username, scanner);
                }
                else 
                {
                    System.out.println(incorrectAnswer);
                    menuStudent(username, scanner);
                }
            }
        }
    }

    public static void menuTeacher(String username, Scanner scanner) throws Exception{
        try {
            System.out.println(menuTeacher);
            System.out.println(chooseOne);
            int choice = scanner.nextInt();

            switch(choice) {
                case 1:
                changePasswordTeacher(username, scanner);
                break;

                case 2:
                deleteAccountTeacher(username, scanner);
                break;

                default:
                System.out.println(incorrectAnswer);
                break;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public static void menuStudent(String username, Scanner scanner) throws Exception{
        System.out.println(menuStudent);
        System.out.println(chooseOne);
        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
            changePasswordStudent(username, scanner);
            break;

            case 2:
            deleteAccountStudent(username, scanner);
            break;

            default:
            System.out.println(incorrectAnswer);
            break;
        }
    }
    
}

