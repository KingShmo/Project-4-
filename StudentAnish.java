import javax.xml.stream.events.Characters;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Student class
 * <p>
 * A class for student that has menus that only students see and has methods related to Quiz such as taking one,
 * viewing the grades for a specifics quiz, accessing courses, etc.
 *
 * @author Anish Ketha
 * @version November 15, 2021
 * All code written by me unless stated
 */

public class StudentAnish {
    int option;
    private static String firstName;
    private static String lastName;
    private static String username;
    private static String password;
    public ArrayList<Student> allStudents = new ArrayList<Student>();

    public StudentAnish(String firstName, String lastName) {
        StudentAnish.firstName = firstName;
        StudentAnish.lastName = lastName;
    }


    public static void main(CourseArchive courseArchive) throws InvalidCourseException {

        CourseArchive listOfCourses = courseArchive;

        Scanner scanner = new Scanner(System.in);
        int option = 0;
        QuizArchive quizArchive = new QuizArchive();


        System.out.println("Welcome! Which course would you like to access?");
        int course = 0;
        int secondaryLoop = 1;
        do {
            do {
                for (int i = 0; i < listOfCourses.getAllCourses().size(); i++) {
                    System.out.println((i + 1) + ". " + listOfCourses.getAllCourses().get(i).getName());
                }
                if (listOfCourses.getAllCourses().size() == 0) {
                    System.out.println("There are no courses available!");
                }
                System.out.println(listOfCourses.getAllCourses().size() + 1 + ". Exit");
                course = scanner.nextInt();
                if (course == (listOfCourses.getAllCourses().size() + 1)) {
                    System.out.println("Thank you for using the student portal!");
                    secondaryLoop = 2;
                    return;
                } else if (course <= 0 || course > listOfCourses.getAllCourses().size() + 1) {
                    System.out.println("Invalid input! Please try again!");
                }
            } while (course <= 0 || course > listOfCourses.getAllCourses().size() + 1);
            int initialLoop;
            do {
                initialLoop = 0;
                System.out.println("Select the action you want:\n1. Take a quiz\n2. View Grades\n" +
                        "3. Exit the course");
                option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    if (quizArchive.getQuizzes().size() == 0) {
                        System.out.println("There are no available quizzes for this course!");
                        initialLoop = 1;
                    } else {
                        String title;
                        System.out.println("What is the name of the quiz?");
                        title = scanner.nextLine();
                        startAQuiz(scanner, title, quizArchive);
                        break;
                    }
                } else if (option == 2) {
                    int loop = 0;
                    do {
                        loop = 0;
                        if (quizArchive.getQuizzes().size() == 0) {
                            System.out.println("There are no available Quiz Grades for this course!");
                            initialLoop = 1;
                        } else {
                            System.out.println("Which quiz do you want to view");
                            QuizArchive q = new QuizArchive();
                            ArrayList<Quiz> Quizzes = q.getQuizzes();
                            for (int i = 0; i < listOfCourses.getAllCourses().size(); i++) {
                                for (int j = 0; j < quizArchive.getQuizzes().size(); j++) {
                                    System.out.println(Quizzes.get(i).getName());
                                }
                            }
                            String quizName = "";
                            quizName = scanner.nextLine();
                            for (int i = 0; i < quizArchive.getQuizzes().size(); i++) {
                                if (Quizzes.get(i).getName().equals(quizName)) {
                                    System.out.println("Answers for " + course + " quiz: " + quizName);
                                    System.out.println("Questions Correct: " + Quizzes.get(i).getScore());
                                    System.out.println("Quiz Grade : " +
                                            Quiz.getModifiedScore(assignPointValues(Quizzes.get(i), scanner),
                                                    Quizzes.get(i)));
                                    //static method cannot be called from an object, only associated with the name of
                                    //the class
                                }
                            }
                            System.out.println("Do you want to view another quiz?");
                            System.out.println("1. Yes\n" +
                                    "2. No\n");
                            int studentOption;
                            do {
                                studentOption = scanner.nextInt();
                                if (studentOption == 1) {
                                    loop = 1;
                                } else if (studentOption == 2) {
                                    initialLoop = 1;
                                } else {
                                    System.out.println("Invalid Input!");
                                }
                            } while (studentOption != 1 && studentOption != 2);
                        }
                    } while (loop == 1);
                } else if (option == 3) {
                    initialLoop = 2;
                } else {
                    System.out.println("Invalid option! Please try again!");
                    initialLoop = 1;
                }
            } while (initialLoop == 1);
        } while (secondaryLoop == 1);
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


    public String getFirstName() { //Returns the first name of a student
        return firstName;
    }

    public String getLastName() { //Returns the last name of a student
        return lastName;
    }

    public String getUsername() { //Returns the username of a student (use it to find out if username already exists)
        return username;
    }

    public String getPassword() { //Returns the password of a student
        return password;
    }

    public static void viewQuiz(Scanner scanner, String firstName, String lastName, String course, String quizName,
                                ArrayList<Character> answersQuiz, QuizArchive q, Quiz newStudent, int[] score) {
        ArrayList<Quiz> Quizzes = q.getQuizzes();
        int loop = 0;
        do {
            loop = 0;
            System.out.println("Which quiz do you want to view");
            int viewDigit = scanner.nextInt();
            Quizzes.get(viewDigit);
            System.out.println("Name: " + firstName + " " + lastName);
            System.out.println("Answers for " + course + " quiz: " + quizName);
            for (int i = 0; i < answersQuiz.size() - 1; i++) {
                System.out.println(answersQuiz.get(i) + ";");
            }
            System.out.println(answersQuiz.get(answersQuiz.size() - 1));
            System.out.println("Questions correct: " + StudentAnish.getScore(newStudent));
            System.out.println("Score: " + StudentAnish.getModifiedScore(score, newStudent));
            System.out.println("Do you want to view another quiz?");
            System.out.println("1. Yes\n" +
                    "2. No\n");
            int option;
            option = scanner.nextInt();
            do {
                if (option == 1) {
                    loop = 1;
                } else if (option == 2) {
                    break;
                } else {
                    System.out.println("Invalid Input!");
                }
            } while (option != 1 && option != 2);
        } while (loop == 1);
    }

    public static void startAQuiz(Scanner scanner, String title, QuizArchive quizArchive) {

        var quizzes = quizArchive.getQuizzes();
        boolean check = false;
        ArrayList<Integer> studentAnswers = new ArrayList<>();
        Quiz quiz = null;
        String answer;

        for (int i = 0; i < quizzes.size(); i++) {

            if (quizzes.get(i).getName().equals(title)) {

                if (!quizzes.get(i).isQuizIsReady()) {
                    System.out.println("Don't forget to launch the quiz");

                }

                check = true;

                quiz = quizzes.get(i);

                var questions = quiz.getQuestions();
                var correctAnswer = quiz.getCorrectAnswers();


                int questionNum = 1;

                for (int j = 0; j < questions.size(); j++) {

                    String wholeQuestion = questions.get(j);

                    String quesiton = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option1 = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option2 = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option3 = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option4 = wholeQuestion;

                    System.out.println((questionNum++) + quesiton.substring(1) + ":");
                    System.out.print(option1 + option2 + option3 + option4);

                    System.out.print("Your answer: ");
                    String[] options = {"1", "2", "3", "4"};
                    answer = inputChecker(scanner, options, "Your answer: ",
                            "Answer should be from 1 to 4.");

                    studentAnswers.add(Integer.valueOf(answer));


                }
                SimpleDateFormat yearMonthDaySpaceHoursMinutesSeconds =
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                System.out.println("Quiz completed: " + yearMonthDaySpaceHoursMinutesSeconds.format(timestamp));

            }

            if (check)
                break;

        }

        if (!check) {
            System.out.println("Couldn't start the quiz.");

        }

        quiz.setStudentAnswers(studentAnswers);

    }

    public static void getStudentAnswers(Quiz answers) {
        answers.getStudentAnswers();
    }

    public static int[] assignPointValues(Quiz temp, Scanner scanner) {

        if (temp.getQuestions().size() == 0) {
            System.out.println("The quiz has zero questions.");
            return null;
        }

        int[] pointValues = new int[temp.getSizeOfQuiz()];
        for (int i = 0; i < temp.getSizeOfQuiz(); i++) {
            System.out.println("How many points is question " + (i + 1) + " worth?");
            int points = scanner.nextInt();
            pointValues[i] = points;
        }
        return pointValues;
    }

    public static String getModifiedScore(int[] pointValue, Quiz q) {

        int count = 0;

        for (int i = 0; i < q.getStudentAnswers().size(); i++) {

            if (q.getStudentAnswers().get(i) == q.getCorrectAnswers().get(i))
                count += pointValue[i];
        }
        int sum = 0;
        for (int i = 0; i < pointValue.length; i++) {
            sum = sum + pointValue[i];
        }

        return "" + count + "/" + sum;

    }

    public static String getScore(Quiz q) {

        int count = 0;

        for (int i = 0; i < q.getStudentAnswers().size(); i++) {

            if (q.getStudentAnswers().get(i) == q.getCorrectAnswers().get(i))
                count++;
        }

        return "" + count + "/" + q.getStudentAnswers().size();

    }

}
