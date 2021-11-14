import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TheQuizFunction
 *
 * Runs a quiz feature in a learning management system.
 *
 * @author Zuhair Almansouri, lab sec L16
 *
 * @version November 8, 2021
 *
 */


public class TheQuizFunction {

    public static void main() throws InvalidQuizException {

        Scanner scanner = new Scanner(System.in);
        String answer;
        QuizArchive quizArchive = new QuizArchive();

        do {
            System.out.println("Select the action you want:");
            System.out.println("1. Create a quiz");
            System.out.println("2. Modify a quiz");
            System.out.println("3. Launch a quiz");
            System.out.println("4. Randomize a quiz");
            System.out.println("5. Exit");
            String temp = "Select the action you want:\n1. Create a quiz\n2. Modify a quiz\n3. Launch a quiz\n" +
                    "4. Randomize a quiz\n5. Exit";
            String[] options = {"1", "2", "3", "4", "5"};
            answer = inputChecker(scanner, options, temp, "Invalid input.");

            if (answer.equals("1"))
                creatingAQuiz(scanner, quizArchive);
            else if (answer.equals("2")) {
                System.out.println("What's the quiz title?");
                answer = scanner.nextLine();
                modifyAQuiz(scanner, answer, quizArchive);
            }
            else if (answer.equals("3")) {
                System.out.println("What's the quiz title?");
                answer = scanner.nextLine();
                launchAQuiz(answer, quizArchive);
            } else if (answer.equals("4")) {

                System.out.println("What's the quiz title?");
                answer = scanner.nextLine();
                randomizeQuestions(answer, quizArchive);

            } else if (answer.equals("5"))
                break;

        } while (true);

        System.out.println("Thank you for using our quiz portal!");

    }

    public static void randomizeQuestions(String title, QuizArchive quizArchive) {

        var quizzes = quizArchive.getQuizzes();

        Quiz quiz = null;

        for (int i = 0; i < quizzes.size(); i++) {

            if (quizzes.get(i).getName().equals(title)) {
                quiz = quizzes.get(i);
                break;
            }

        }
        if (quiz != null)
            System.out.println(quiz.randomizeQuestions(quiz));
        else
            System.out.println("No questions found.");

    }


    public static void launchAQuiz(String title, QuizArchive quizArchive) {

        var quizzes = quizArchive.getQuizzes();

        for (int i=0; i<quizzes.size(); i++) {

            if (quizzes.get(i).getName().equals(title)) {
                quizzes.get(i).launchQuiz();
                System.out.println("Quiz launched!");
                return;
            }
        }

        System.out.println("Quiz not found.");

    }

    /**
     * Modifies a quiz question or options
     * @param scanner = accepted user input
     * @param title = title of the quiz to be modified.
     * @param quizArchive = the archive that contains the quizzes.
     * @return true if the modification was done successfully. Otherwise, false.
     * @throws InvalidQuizException if the quiz is not found, or the newOptions/newQuestion to be modified is not valid.
     */
    public static boolean modifyAQuiz(Scanner scanner, String title, QuizArchive quizArchive) throws InvalidQuizException {

        String answer;
        var quizzes = quizArchive.getQuizzes();
        Quiz quiz = null;
        boolean check = true;

        for (int i=0; i<quizzes.size(); i++) {

            if (quizzes.get(i).getName().equals(title)) {
                quiz = quizzes.get(i);
                check = false;
            }
        }

        if (check)
            return false;

        System.out.println("Do you want to change the quizz's title? (yes/no)");
        answer = inputChecker(scanner, new String[]{"Yes", "yes", "No", "no"}, "Do you want to change the quiz title?", "Invalid input.");
        if (answer.equals("Yes") || answer.equals("yes")) {

            System.out.println("Type the new title:");
            answer = scanner.nextLine();
            quiz.setName(answer);
            System.out.println("Quiz name modified!");
            return true;
        }

        int size = quiz.getSizeOfQuiz();
        if (size == 0)
            return false;

        System.out.println("Question number you want to modify?");


        String[] options = new String[size];
        for (int i=1; i<=size; i++) {

            options[i-1] = "" + i;

        }
        answer = inputChecker(scanner, options, "Question number you want to modify?", "Invalid question number.");

        int questionNumber = Integer.valueOf(answer);

        System.out.println("Which one do you want to modify: ");
        System.out.println("1. The question.");
        System.out.println("2. The options.");
        String temp = "Which one do you want to modify: \n1. The question.\n2. The options.";

        answer = inputChecker(scanner, new String[]{"1", "2"}, temp, "Invalid input.");

        if (answer.equals("1")) {

            System.out.println("Type the new question: ");
            answer = scanner.nextLine();
            System.out.println(quiz.modifyAQuestion(questionNumber, answer));

        } else if (answer.equals("2")) {

            String[] newOptions = new String[4];
            for (int i = 0; i < newOptions.length; i++) {

                System.out.println("Option " + (i+1) + ":");
                newOptions[i] = (i+1) + ". " + scanner.nextLine() + "\n";
            }

            String[] correctAnswerOptions = {"1", "2", "3", "4"};

            System.out.println("What's the correct answer?");
            String correct = inputChecker(scanner, correctAnswerOptions, "What's the correct answer?",
                    "Correct answers should be from 1 to 4.");

            System.out.println(quiz.modifyOptionsOfAQuestion(questionNumber, newOptions, Integer.valueOf(correct)));
        }

        return true;


    }

    /**
     * Start a quiz by listing all the questions and options.
     * @param scanner = gets the input.
     * @param title = the title of the quiz you would like to run.
     * @param quizArchive = search for a particular quiz in the quizArchive.
     * @return a string arrayList containing the answers of a particular student, or
     * returns a message containing a description of the error.
     */
    public static void startAQuiz(Scanner scanner, String title, QuizArchive quizArchive, TimeStamp timeStamp) {

        var quizzes = quizArchive.getQuizzes();
        boolean check = false;
        ArrayList<Integer> studentAnswers = new ArrayList<>();
        Quiz quiz = null;
        String answer;

        for (int i=0; i < quizzes.size(); i++) {

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
                timeStamp.submissionTime();


            }

            if (check)
                break;

        }

        if (!check) {
            System.out.println("Couldn't start the quiz.");

        }

        quiz.setStudentAnswers(studentAnswers);

    }

    public static void creatingAQuiz(Scanner scanner, QuizArchive quizArchive) throws InvalidQuizException {

        String answer;

        Quiz temp = null;
        do {

            System.out.println("Do you want to add a quiz? (yes/no)");
            String[] standardChoices = {"Yes", "yes", "No", "no"};
            answer = inputChecker(scanner, standardChoices, "Do you want to add a quiz?", "Invalid input.");

            if (answer.equals("No") || answer.equals("no"))
                break;
            else {

                System.out.println("How many numbers of questions?");
                String[] questionNumberChoices = new String[121];
                for (int i=0; i < questionNumberChoices.length; i++) {
                    questionNumberChoices[i] = "" + i;
                }
                answer = inputChecker(scanner, questionNumberChoices, "How many numbers of questions?",
                        "Number of questions should be no more than 120.");

                if (Integer.valueOf(answer) == 0) {

                    do {

                        System.out.println("What's the quiz's title?");
                        answer = scanner.nextLine();

                    } while (answer.isEmpty() || answer.isBlank());

                    Quiz q1 = new Quiz(answer);
                    quizArchive.addQuizes(q1);

                    System.out.println("Do you want to add questions? (yes/no)");
                    answer = inputChecker(scanner, new String[]{"Yes", "yes", "No", "no"}, "Do you want to add questions?", "Invalid input.");
                    if (answer.equals("No") || answer.equals("no")) {
                        System.out.println("An empty quiz was created.");
                        break;
                    }
                    else {

                        System.out.println("Type STOP to stop adding questions.");

                        int numOfQuestions = 0;

                        while (true) {

                            System.out.println("Question " + (++numOfQuestions) + ":");

                            do {
                                answer = scanner.nextLine();
                                if (answer.isBlank() || answer.isEmpty()) {
                                    System.out.println("Type the question again:");
                                } else {
                                    break;
                                }
                            } while (true);


                            if (answer.equals("STOP") || answer.equals("Stop") || answer.equals("stop"))
                                break;

                            String question = answer;

                            String[] options = new String[4];
                            for (int i = 0; i < options.length; i++) {

                                System.out.println("Option " + (i+1) + ":");
                                String oneOption = "";
                                do {
                                    oneOption = scanner.nextLine();
                                    if (!oneOption.isBlank() && !oneOption.isEmpty())
                                        break;
                                    System.out.println("The option is empty.");
                                } while (true);

                                options[i] = (i+1) + ". " + oneOption + "\n";
                            }

                            String[] correctAnswerOptions = {"1", "2", "3", "4"};

                            System.out.println("What's the correct answer?");

                            String correct = inputChecker(scanner, correctAnswerOptions, "What's the correct answer?",
                                    "Correct answers should be from 1 to 4.");

                            q1.addOneQuestion(question, options, Integer.valueOf(correct));

                        }
                    }

                } else {

                    int numOfQuestions = Integer.valueOf(answer);

                    do {

                        System.out.println("What's the quiz's title?");
                        answer = scanner.nextLine();

                    } while (answer.isEmpty() || answer.isBlank());


                    Quiz q1 = new Quiz(answer, numOfQuestions);
                    temp = q1;
                    quizArchive.addQuizes(q1);


                    for (int i = 0; i < numOfQuestions; i++) {

                        String[] options = new String[4];

                        System.out.println("Question " + (i+1) + ":");

                        String question = "";
                        do {
                            question = scanner.nextLine();
                            if (question.isBlank() || question.isEmpty()) {
                                System.out.println("Type the question again:");
                            } else {
                                break;
                            }
                        } while (true);

                        for (int j = 0; j < options.length; j++) {

                            System.out.println("Option " + (j+1) + ":");

                            String oneOption = "";
                            do {
                                oneOption = scanner.nextLine();
                                if (!oneOption.isBlank() && !oneOption.isEmpty())
                                    break;
                                System.out.println("The option is empty.");
                            } while (true);

                            options[j] = (j+1) + ". " + oneOption + "\n";

                        }

                        System.out.println("What's the correct answer?");
                        String[] correctAnswerOptions = {"1", "2", "3", "4"};
                        answer = inputChecker(scanner, correctAnswerOptions, "What's the correct answer?",
                                "Correct answers should be from 1 to 4.");
                        q1.addOneQuestion(question, options, Integer.valueOf(answer));

                    }

                }
                Student.assignPointValues(temp, scanner);
            }

            break;

        }  while (true);

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
