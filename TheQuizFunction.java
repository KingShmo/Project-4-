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

    public static void main(String[] args) throws InvalidQuizException {

        Scanner scanner = new Scanner(System.in);
        String answer;
        QuizArchive quizArchive = new QuizArchive();

        do {
            System.out.println("Select the action you want:");
            System.out.println("1. Create a quiz");
            System.out.println("2. Start a quiz");
            System.out.println("3. Modify a quiz");
            System.out.println("4. Launch a quiz");
            System.out.println("5. Randomize a quiz");
            System.out.println("6. Exit");
            String temp = "Select the action you want:\n1. Create a quiz\n2. Start a quiz\n3. Exit";
            String[] options = {"1", "2", "3", "4"};
            answer = inputChecker(scanner, options, temp, "Invalid input.");

            if (answer.equals("1"))
                creatingAQuiz(scanner, quizArchive);
            else if (answer.equals("2")) {
                System.out.println("What's the quizz's title?");
                answer = scanner.nextLine();
                if (startAQuiz(scanner, answer, quizArchive) == null) {
                    System.out.println("Couldn't start the quiz.");
                }
            } else if (answer.equals("3")) {
                System.out.println("What's the quizz's title?");
                answer = scanner.nextLine();
                modifyAQuiz(scanner, answer, quizArchive);
            }
            else if (answer.equals("4")) {
                System.out.println("What's the quizz's title?");
                answer = scanner.nextLine();
                launchAQuiz(answer, quizArchive);
            } else if (answer.equals("5")) {

                System.out.println("Randomization in maintenance...");

            } else if (answer.equals("6"))
                break;

        } while (true);

        System.out.println("Thank you for using our quiz portal!");

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



        System.out.println("Question number you want to modify?");

        int size = quiz.getSizeOfQuiz();
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
     * returns null if the quiz's title is not found in the quizArchive or the quiz is not ready to be launched.
     */
    public static ArrayList<String> startAQuiz(Scanner scanner, String title, QuizArchive quizArchive) {

        var quizzes = quizArchive.getQuizzes();
        boolean check = false;
        ArrayList<String> studentAnswers = new ArrayList<>();

        String answer;

        for (int i=0; i<quizzes.size(); i++) {

            if (quizzes.get(i).getName().equals(title)) {

                if (!quizzes.get(i).isQuizIsReady())
                    return null;

                check = true;

                Quiz quiz = quizzes.get(i);

                var questions = quiz.getQuestions();
                var correctAnswer = quiz.getCorrectAnswers();

                for (int j=0; j<questions.size(); j++) {

                    String wholeQuestion = questions.get(i);

                    String quesiton = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option1 = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option2 = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option3 = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option4 = wholeQuestion;

                    System.out.println(quesiton);
                    System.out.print(option1 + option2 + option3 + option4);

                    System.out.print("Your answer: ");
                    String[] options = {"1", "2", "3", "4"};
                    answer = inputChecker(scanner, options, "Your answer: ", "Answer should be from 1 to 4.");

                    studentAnswers.add(answer);


                }

            }

            if (check)
                break;

        }

        if (!check) {
            return null;
        }
        return studentAnswers;

    }

    public static void creatingAQuiz(Scanner scanner, QuizArchive quizArchive) throws InvalidQuizException {

        String answer;


        do {

            System.out.println("Do you want to add a quiz?");
            String[] standardChoices = {"Yes", "yes", "No", "no"};
            answer = inputChecker(scanner, standardChoices, "Do you want to add a quiz?", "Invalid input.");

            if (answer.equals("No") || answer.equals("no"))
                break;
            else {

                System.out.println("How many numbers of questions?");
                String[] questionNumberChoices = new String[121];
                for (int i=0; i<questionNumberChoices.length; i++) {
                    questionNumberChoices[i] = "" + i;
                }
                answer = inputChecker(scanner, questionNumberChoices, "How many numbers of questions?",
                        "Number of questions should be no more than 120.");

                if (Integer.valueOf(answer) == 0) {

                    System.out.println("What's the quiz's title?");
                    answer = scanner.nextLine();
                    Quiz q1 = new Quiz(answer);
                    quizArchive.addQuizes(q1);

                    System.out.println("Do you want to add questions?");

                    if (answer.equals("No") || answer.equals("no")) {
                        System.out.println("An empty quiz was created.");
                        break;
                    }
                    else {

                        System.out.println("Type STOP to stop adding questions.");

                        int numOfQuestions = 0;

                        while (true) {

                            System.out.println("Question " + (++numOfQuestions) + ":");
                            answer = scanner.nextLine();

                            if (answer.equals("STOP") || answer.equals("Stop") || answer.equals("stop"))
                                break;

                            String question = answer;

                            String[] options = new String[4];
                            for (int i = 0; i < options.length; i++) {

                                System.out.println("Option " + (i+1) + ":");
                                options[i] = (i+1) + ". " + scanner.nextLine() + "\n";
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

                    System.out.println("What's the quiz's title?");
                    answer = scanner.nextLine();

                    Quiz q1 = new Quiz(answer, numOfQuestions);
                    quizArchive.addQuizes(q1);


                    for (int i = 0; i < numOfQuestions; i++) {

                        String[] options = new String[4];

                        System.out.println("Question " + (i+1) + ":");
                        String question = scanner.nextLine();

                        for (int j = 0; j < options.length; j++) {

                            System.out.println("Option " + (j+1) + ":");
                            options[j] = (j+1) + ". " + scanner.nextLine() + "\n";

                        }

                        System.out.println("What's the correct answer?");
                        String[] correctAnswerOptions = {"1", "2", "3", "4"};
                        answer = inputChecker(scanner, correctAnswerOptions, "What's the correct answer?",
                                "Correct answers should be from 1 to 4.");
                        q1.addOneQuestion(question, options, Integer.valueOf(answer));

                    }

                }

            }

            break;

        } while (true);

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
