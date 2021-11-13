import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Student {
    
    int option;
    private static String firstName;
    private static String lastName;
    private static String username;
    private static String password;
    private final ArrayList studentCourses;
    private final ArrayList studentQuizzes;
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


    public Student(String firstName, String lastName, String username, String password, ArrayList studentCourses, ArrayList studentQuizzes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.studentCourses = studentCourses;
        this.studentQuizzes = studentQuizzes;
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
    public void viewQuiz(String firstName, String lastName, String course, String quizName, ArrayList<Character> answersQuiz, int grade, QuizArchive q) {
        Scanner scanner = new Scanner(System.in);
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
            System.out.println("Score: " + grade + "/100");
            System.out.println("Do you want to view another quiz?");
            System.out.println("1. Yes\n" +
                    "2. No\n");
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

        for (int i=0; i<quizzes.size(); i++) {

            if (quizzes.get(i).getName().equals(title)) {

                if (!quizzes.get(i).isQuizIsReady()) {
                    System.out.println("Don't forget to launch the quiz");

                }

                check = true;

                quiz = quizzes.get(i);

                var questions = quiz.getQuestions();
                var correctAnswer = quiz.getCorrectAnswers();


                int questionNum = 1;

                for (int j=0; j<questions.size(); j++) {

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
                SimpleDateFormat yearMonthDaySpaceHoursMinutesSeconds = new SimpleDateFormat
                        ("yyyy-MM-dd HH:mm:ss");
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                System.out.println(yearMonthDaySpaceHoursMinutesSeconds.format(timestamp));


            }

            if (check)
                break;

        }

        if (!check) {
            System.out.println("Couldn't start the quiz.");

        }

        quiz.setStudentAnswers(studentAnswers);

    }

}
