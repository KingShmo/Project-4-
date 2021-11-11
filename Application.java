/*
This class starts the program and asks a user to sign in or register
*
In order to save all the registration information, I need Troy's file technology implemented
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
    private static String teacherOrStudentSignIn = 
    "Do you want to sign in as a teacher or as a student?\n" +
    "Pick [1] for teacher or [2] for student.";
    private static String teacherOrStudentRegister = 
    "Do you want to register as a teacher or as a student?\n" +
    "Pick [1] for teacher or [2] for student.";
    static ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    static ArrayList<Student> students = new ArrayList<Student>();
    static int usernameStatus = 1; // Checks if there is already a user with that username
    



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(teachers);

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

    public static void signIn(Scanner scanner) { // Sign in method either for teachers or students
        
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
                    System.out.println(enterPassword);
                    String password = scanner.nextLine();
                    if (item.getPassword() == password) {
                        // The list of things that the teacher can do
                    }
                } 
                else
                {
                    System.out.println(usernameDoesntExist);
                }
            }
        } 
        else if (choiceTeacherOrStudent == 2)
        {
            System.out.println(enterUsername);
            scanner.nextLine();
            String username = scanner.nextLine();
            System.out.println(username);

            for (Student item: students) {

                if (username.equals(item.getUsername())) 
                {
                    System.out.println(enterPassword);
                    String password = scanner.nextLine();
                    if (item.getPassword() == password) {
                        // The list of things that the student can do
                    }
                } 
                else
                {
                    System.out.println(usernameDoesntExist);
                }
            }
        }
        else 
        {
            System.out.println(incorrectAnswer);
        }
    }

    public static void register(Scanner scanner) { // Allows user to register and then Sign In

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
                System.out.println(teachers);
                signIn(scanner);
            }
        } 
        else if (choiceTeacherOrStudent == 2)
        {
            System.out.println(enterFirstName);
            scanner.nextLine();
            String firstName = scanner.nextLine();
            System.out.println(firstName);
            System.out.println(enterLastName);
            String lastName = scanner.nextLine();
            System.out.println(lastName);
            System.out.println(enterUsername);
            String username = scanner.nextLine();
            System.out.println(username);

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
                System.out.println(password);

                students.add(new Student(firstName, lastName, username, password));
                System.out.println(students.get(0).getUsername());
                signIn(scanner);
            }
        }
        else 
        {
            System.out.println(incorrectAnswer);
        }


    }
    
}
