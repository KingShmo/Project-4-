import java.io.*;
import java.nio.charset.CoderResult;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TheQuizFunction
 * <p>
 * Runs a quiz feature in a learning management system.
 *
 * @author Zuhair Almansouri, lab sec L16, Troy Tamura
 * All code is done by Zuhair except for specific lines that
 * has a comment on them stating the author who wrote them.
 * @version November 15, 2021
 */


public class TheQuizFunction {

    /**
     * Runs a menu for Teacher's use.
     * Added a "Start quiz" method for the student to use in their class, so
     * they can take a quiz.
     *
     * @param quizArchive = includes all the quizzes. It is passes as a parameter
     *                    from the teacher who created the quizzes.
     * @throws InvalidQuizException = whenever a quiz can't be made, this is thrown.
     * @throws IOException          = whenever there is trouble writing quiz information into a file
     */
    public static void main(QuizArchive quizArchive, String courseTitle) throws InvalidQuizException, IOException {

        //Reads all the quizzes from a notepad to retrieve them.
        //PrintInformation.readQuizQuestions(quizArchive);


        Scanner scanner = new Scanner(System.in); // Scanner for input
        String answer; // User's answers

        do {
            System.out.println("Select the action you want:");
            System.out.println("1. Create a quiz");
            System.out.println("2. Modify a quiz");
            //System.out.println("3. Launch a quiz");
            System.out.println("3. Randomize a quiz");
            System.out.println("4. View Student Quiz Submissions");
            System.out.println("5. List available quizzes");
            System.out.println("6. Delete a quiz");
            System.out.println("7. Import a quiz");
            System.out.println("8. Exit");
            String temp = "Select the action you want:\n1. Create a quiz\n2. Modify a quiz\n" +
                    "3. Randomize a quiz\n4. View Student Quiz Submissions\n" +
                    "5. List available quizzes\n6. Delete a quiz\n7. Import a quiz\n" +
                    "8. Exit"; //Collects the whole menu.

            String[] options = {"1", "2", "3", "4", "5", "6", "7", "8"};
            // Used for inputChecker method. To check the valid options.

            answer = inputChecker(scanner, options, temp, "Invalid input.");
            //Assigns the valid input to "answer" variable

            if (answer.equals("1")) {
                //Creates a new Quiz object and saves it in QuizArchive
                creatingAQuiz(scanner, quizArchive, courseTitle);
                PrintInformation.writeQuizQuestions(quizArchive);
            } else if (answer.equals("2")) {
                System.out.println("What's the quiz title?");
                answer = scanner.nextLine();
                //Modifies a Quiz based on its title/name. Modifies the options, question, or name of the quiz.
                modifyAQuiz(scanner, answer, quizArchive, courseTitle);
                PrintInformation.writeQuizQuestions(quizArchive);
            } else if (answer.equals("3")) {

                System.out.println("What's the quiz title?");
                answer = scanner.nextLine();
                boolean check = true;
                for (Quiz q : quizArchive.getQuizzes()) {
                    if (q.getName().equals(answer)) {
                        if (!(q.getCourse().equals(courseTitle))) {
                            System.out.println("This quiz is unavailable for this course.");
                            check = false;
                        }

                    }
                }
                //Randomize options and questions for a given title of a quiz.
                if (check) {
                    for (int i = 0; i < quizArchive.getQuizzes().size(); i++) {
                        Quiz quiz = quizArchive.getQuizzes().get(i);
                        if (quiz.getName().equals(answer)) {
                            if (quiz.getRandomize()) {
                                String question = "Questions are already randomized, do you want to toggle it off? (y/n)";
                                System.out.println(question);
                                answer = inputChecker(scanner, new String[]{"Yes", "yes", "No", "no"},
                                        question, "Invalid input.");
                                if (answer.equals("Yes") || answer.equals("yes")) {
                                    quiz.toggleRandomization();
                                    System.out.println("Quiz will be randomized for students in each attempt!");
                                }
                            } else {
                                quiz.toggleRandomization();
                                System.out.println("Quiz will be randomized for students in each attempt!");
                            }
                        }
                    }
                }
                //randomizeQuestions(answer, quizArchive);

                //PrintInformation.writeQuizQuestions(quizArchive);
            } else if (answer.equals("4")) {
                //Anushka's method. It lists the student answers for their quiz.
                viewStudentSubmissions(scanner, quizArchive, courseTitle);

            } else if (answer.equals("5")) {
                int x = 0;
                for (Quiz quiz : quizArchive.getQuizzes()) {
                    if (quiz.getCourse().equals(courseTitle))
                        System.out.println((++x) + ". " + quiz.getName());
                }
            } else if (answer.equals("6")) {
                System.out.println("Quiz you want to delete:");
                int x = 0;
                ArrayList<Quiz> quizzesToBeDeleted = new ArrayList<>();
                for (Quiz q : quizArchive.getQuizzes()) {
                    if (q.getCourse().equals(courseTitle)) {
                        System.out.println((++x) + ". " + q.getName());
                        quizzesToBeDeleted.add(q);
                    }
                }
                String[] choices = new String[quizzesToBeDeleted.size()];
                for (int i = 0; i < choices.length; i++) {
                    choices[i] = "" + (i + 1);
                }
                answer = inputChecker(scanner, choices, "Quiz you want to delete:", "Invalid input");

                Quiz quiz = quizzesToBeDeleted.get(Integer.valueOf(answer) - 1);

                for (int i = 0; i < quizArchive.getQuizzes().size(); i++) {
                    Quiz q = quizArchive.getQuizzes().get(i);
                    if (q.getName().equals(quiz.getName())) {
                        quizArchive.getQuizzes().remove(i);
                        break;
                    }
                }

                System.out.println("Quiz removed!");

            } else if (answer.equals("7")) {

                System.out.println("The file should have the following format:");
                System.out.println("Quiz-Course\n1. Question:\n" +
                                   "1. Option1\n2. Option2\n3. Option3\n4. Option4\n" +
                                   "Correct Answers:\nQuestion 1:[numOfCorrectAnswer] Question 2:4\n" +
                                   "[pointValue1],[pointValue2]");

                System.out.println("\nFile path?");
                answer = scanner.nextLine();

                try {

                    PrintInformation.readQuizQuestions(quizArchive, answer);

                } catch (FileNotFoundException e) {
                    System.out.println("Couldn't find file.");
                } catch (IOException e) {
                    System.out.println("File was written in a wrong format.");
                }


            } else if (answer.equals("8"))
                break;

        } while (true);

        //Prints the quiz information in a notepad to save it before terminating the quiz portal.
        PrintInformation.writeQuizQuestions(quizArchive);

        System.out.println("Thank you for using our quiz portal!");

    }

    /**
     * Randomizes a quiz's questions and options based on its title.
     * The correct answer is also updated based on the randomized options.
     *
     * @param title       = the quiz that should be randomized
     * @param quizArchive = extract a single Quiz from the QuizArchive
     */
    public static void randomizeQuestions(String title, QuizArchive quizArchive) {

        var quizzes = quizArchive.getQuizzes(); // all quizzes

        Quiz quiz = null;

        for (int i = 0; i < quizzes.size(); i++) {

            if (quizzes.get(i).getName().equals(title)) {
                quiz = quizzes.get(i);
                break;
            }

        }
        if (quiz != null)
            System.out.println(quiz.randomizeQuestions(quiz)); //Randomizes the quiz
        else
            System.out.println("No questions found.");

    }


    /**
     * A teacher launches a quiz to make it available for students
     *
     * @param title       = the title of the quiz to be launched.
     * @param quizArchive = extract a single Quiz from the QuizArchive
     */
    public static void launchAQuiz(String title, QuizArchive quizArchive) {

        var quizzes = quizArchive.getQuizzes(); //all quizzes

        for (int i = 0; i < quizzes.size(); i++) {

            if (quizzes.get(i).getName().equals(title)) {
                quizzes.get(i).launchQuiz(); //sets quizIsReady to true
                System.out.println("Quiz launched!");
                return;
            }
        }

        System.out.println("Quiz not found.");

    }

    /**
     * Modifies a quiz question, options, or title
     *
     * @param scanner     = accepted user input
     * @param title       = title of the quiz to be modified.
     * @param quizArchive = the archive that contains the quizzes.
     * @return true if the modification was done successfully. Otherwise, false.
     * @throws InvalidQuizException if the quiz is not found, or the newOptions/newQuestion to be modified is not valid.
     */
    public static boolean modifyAQuiz(Scanner scanner, String title, QuizArchive quizArchive, String courseTitle) throws InvalidQuizException {

        String answer; // user answers
        var quizzes = quizArchive.getQuizzes(); //all quizzes
        Quiz quiz = null;
        boolean check = true; //checks quiz availability

        for (int i = 0; i < quizzes.size(); i++) {

            if (quizzes.get(i).getName().equals(title)) {
                quiz = quizzes.get(i);
                check = false;
            }
        }

        if (check) {
            System.out.println("Unavailable quiz.");
            return false;
        }

        if (!(quiz.getCourse().equals(courseTitle))) {
            System.out.println("This quiz is not in this course.");
            return false;
        }

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
        for (int i = 1; i <= size; i++) {

            options[i - 1] = "" + i;

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

                System.out.println("Option " + (i + 1) + ":");
                newOptions[i] = (i + 1) + ". " + scanner.nextLine() + "\n";
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
     * Start a quiz by listing all the questions, options, and accepting student answers
     *
     * @param scanner     = gets the input.
     * @param title       = the title of the quiz you would like to run.
     * @param quizArchive = search for a particular quiz in the quizArchive.
     * @return a string arrayList containing the answers of a particular student, or
     * returns a message containing a description of the error.
     */
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

                    String question = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option1 = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option2 = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option3 = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option4 = wholeQuestion;

                    System.out.println((questionNum++) + question.substring(1) + ":");
                    System.out.print("1" + option1.substring(1) + "2" + option2.substring(1) + "3" + option3.substring(1) + "4" + option4.substring(1));

                    System.out.print("Your answer: ");
                    String[] options = {"1", "2", "3", "4"};
                    answer = inputChecker(scanner, options, "Your answer: ", "Answer should be from 1 to 4.");

                    studentAnswers.add(Integer.valueOf(answer));


                }

                SimpleDateFormat yearMonthDaySpaceHoursMinutesSeconds =
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                System.out.println("Quiz completed: " + yearMonthDaySpaceHoursMinutesSeconds.format(timestamp));
                //Anish's code that prints a timestamp only when a student has COMPLETED a quiz which a student sees and which the teacher can access if needed.

            }

            if (check)
                break;

        }

        if (!check) {
            System.out.println("Couldn't start the quiz.");

        }

        if (quiz != null)
            quiz.setStudentAnswers(studentAnswers);

    }



    //Anushka's method
    public static void viewStudentSubmission(Scanner scanner, QuizArchive quizArchive) throws FileNotFoundException {
        String answer;
        System.out.println("Do you want to view the submissions according to 1- the quiz name or 2- the student name? (1/2)");
        String temp = "Do you want to view the submissions according to 1- the quiz name or 2- the student name? (1/2)";
        String[] options = {"1", "2"};
        answer = inputChecker(scanner, options, temp, "Invalid input.");
        if (answer.equals("2")) {
            System.out.println("What is the first name of the student? ");
            String firstName = scanner.nextLine();
            System.out.println("What is the last name of the student? ");
            String lastName = scanner.nextLine();
            readQuizByStudentName(firstName, lastName);
        } else if (answer.equals("1")) {
            System.out.println("What is the name of the quiz? ");
            String quizName = scanner.nextLine();
            readQuizByQuizName(quizName);
        }

    }

    //Zuhair's version
    public static void viewStudentSubmissions(Scanner scanner, QuizArchive quizArchive, String course) throws FileNotFoundException {

        boolean check = true;

        for (int i = 0; i < quizArchive.getQuizzes().size(); i++) {

            Quiz quiz = quizArchive.getQuizzes().get(i);
            if (quiz.getCourse().equals(course)) {

                if (quiz.isTaken()) {
                    System.out.println("Quiz name: " + quiz.getName());
                    check = false;

                    int counter = 0;

                    for (int j = 0; j < quiz.getStudentAnswers().size(); j++) {

                        int ans = quiz.getStudentAnswers().get(j);
                        System.out.println("Question " + (++counter) + " answer: " + ans);

                    }
                    System.out.println("Raw Score: " + quiz.getRawScore());
                    System.out.println("Modified Score: " + quiz.getModifiedScore());
                    System.out.println("Timestamp: " + quiz.getTimeStamp());
                }

            }

        }
        if (check)
            System.out.println("No submissions.");


    }

    //Troy's method
    //Create method to find all the students and results of their quizzes for one type of quiz
    public static ArrayList<String> readQuizByQuizName(String quizName) throws FileNotFoundException {
        ArrayList<String> allQuizInfo = new ArrayList<>();
        ArrayList<String> specificQuiz = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentQuizzes.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                allQuizInfo.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (allQuizInfo.size() == 0) {
            specificQuiz.add("No student has taken a quiz yet");
            return specificQuiz;
        } else {
            for (int i = 0; i < allQuizInfo.size(); i++) {
                if (allQuizInfo.get(i).equals(quizName)) {
                    specificQuiz.add(allQuizInfo.get(i));
                    specificQuiz.add(allQuizInfo.get(i + 1));
                    specificQuiz.add(allQuizInfo.get(i + 2));
                    specificQuiz.add(allQuizInfo.get(i + 3));
                    specificQuiz.add(" ");
                }
            }
            if (specificQuiz.size() == 0) {
                specificQuiz.add("No students have taken this particular quiz.");
            }
        }
        return specificQuiz;
    }

    //Troy's method
    //Create method to find all quizzes taken by a student by searching and using their name
    public static ArrayList<String> readQuizByStudentName(String firstName, String lastName) throws FileNotFoundException {
        ArrayList<String> allQuizInfo = new ArrayList<>();
        ArrayList<String> specificQuiz = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentQuizzes.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                allQuizInfo.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (allQuizInfo.size() == 0) {
            specificQuiz.add("No student has taken a quiz yet");
            return specificQuiz;
        } else {
            for (int i = 0; i < allQuizInfo.size(); i++) {
                if (allQuizInfo.get(i).equals("Name: " + firstName + " " + lastName)) {
                    specificQuiz.add(allQuizInfo.get(i));
                    specificQuiz.add(allQuizInfo.get(i + 1));
                    specificQuiz.add(allQuizInfo.get(i + 2));
                    specificQuiz.add(allQuizInfo.get(i + 3));
                    specificQuiz.add(" ");
                }
            }
            if (specificQuiz.size() == 0) {
                specificQuiz.add("This student has not taken a quiz yet");
            }
        }
        return specificQuiz;
    }

    /**
     * Creates a new quiz
     *
     * @param scanner     = accepts input from the user
     * @param quizArchive = extract a single Quiz from the QuizArchive
     * @throws InvalidQuizException = throws an exception whenever a quiz with the given information
     *                              can't possibly be created.
     */
    public static void creatingAQuiz(Scanner scanner, QuizArchive quizArchive, String courseTitle)
                                     throws InvalidQuizException {

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
                String[] questionNumberChoices = new String[120];
                for (int i = 0; i < questionNumberChoices.length; i++) {
                    questionNumberChoices[i] = "" + (i + 1);
                }
                answer = inputChecker(scanner, questionNumberChoices, "How many numbers of questions?",
                        "Number of questions should be no more than 120 or less than 1.");

                if (Integer.valueOf(answer) == 0) {

                    do {

                        System.out.println("What's the quiz's title?");
                        answer = scanner.nextLine();
                        var allQuizzes = quizArchive.getQuizzes();

                        for (Quiz quiz : allQuizzes) {
                            if (quiz.getName().equals(answer)) {
                                answer = "";
                                System.out.println("The quiz title already exists.");

                            }
                        }

                    } while (answer.isEmpty() || answer.isBlank());

                    Quiz q1 = new Quiz(answer);
                    temp = q1;
                    quizArchive.addQuizzes(q1);

                    System.out.println("Do you want to add questions? (yes/no)");
                    answer = inputChecker(scanner, new String[]{"Yes", "yes", "No", "no"}, "Do you want to add questions?", "Invalid input.");
                    if (answer.equals("No") || answer.equals("no")) {
                        System.out.println("An empty quiz was created.");
                        break;
                    } else {

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

                                System.out.println("Option " + (i + 1) + ":");
                                String oneOption = "";
                                do {
                                    oneOption = scanner.nextLine();
                                    if (!oneOption.isBlank() && !oneOption.isEmpty())
                                        break;
                                    System.out.println("The option is empty.");
                                } while (true);

                                options[i] = (i + 1) + ". " + oneOption + "\n";
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
                        var allQuizzes = quizArchive.getQuizzes();
                        for (Quiz quiz : allQuizzes) {
                            if (quiz.getName().equals(answer)) {
                                answer = "";
                                System.out.println("The quiz title already exists.");

                            }
                        }

                    } while (answer.isEmpty() || answer.isBlank());


                    Quiz q1 = new Quiz(answer, numOfQuestions);
                    temp = q1;
                    quizArchive.addQuizzes(q1);


                    for (int i = 0; i < numOfQuestions; i++) {

                        String[] options = new String[4];

                        System.out.println("Question " + (i + 1) + ":");

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

                            System.out.println("Option " + (j + 1) + ":");

                            String oneOption = "";
                            do {
                                oneOption = scanner.nextLine();
                                if (!oneOption.isBlank() && !oneOption.isEmpty())
                                    break;
                                System.out.println("The option is empty.");
                            } while (true);

                            options[j] = (j + 1) + ". " + oneOption + "\n";

                        }

                        System.out.println("What's the correct answer?");
                        String[] correctAnswerOptions = {"1", "2", "3", "4"};
                        answer = inputChecker(scanner, correctAnswerOptions, "What's the correct answer?",
                                "Correct answers should be from 1 to 4.");
                        q1.addOneQuestion(question, options, Integer.valueOf(answer));

                    }

                    for (int i = 0; i < CourseArchive.allCourses.size(); i++) {

                        if (CourseArchive.allCourses.get(i).getName().equals(courseTitle)) {
                            CourseArchive.allCourses.get(i).addCourseQuiz(q1);
                            q1.assignCourse(courseTitle);
                        }

                    }

                    q1.initializePointValues(StudentAnish.assignPointValues(temp, scanner));
                                            // Anish's method that asks a teacher the specific
                                            // value for each question when they create a quiz

                }



            }

            break;

        } while (true);

    }

    /**
     * Checks for user input and make sure it's valid.
     *
     * @param scanner      = accepts user input
     * @param choices      = the valid choices
     * @param question     = reprints the question again in case the input was invalid.
     * @param errorMessage = prints the given error message if the input was invalid.
     * @return a String that includes a valid option chosen by the user.
     */
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