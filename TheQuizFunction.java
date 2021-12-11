import javax.swing.*;
import java.io.*;
import java.nio.charset.CoderResult;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * TheQuizFunction
 * <p>
 * Runs a quiz feature in a learning management system.
 *
 * @author Zuhair Almansouri, lab sec L16, Troy Tamura
 * All code is done by Zuhair except for specific lines that
 * has a comment on them stating the author who wrote them.
 * @version December 11, 2021
 */


public class TheQuizFunction {
    public static String answer = ""; // User's answers
    public static String fileAnswer = "";

    /**
     * sync threads
     */
    private static Object sync = new Object();

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


        do {

            String temp = "Select the action you want:\n1. Create a quiz\n2. Modify a quiz\n" +
                    "3. Randomize a quiz\n4. View Student Quiz Submissions\n" +
                    "5. List available quizzes\n6. Delete a quiz\n7. Import a quiz\n" +
                    "8. Exit"; //Collects the whole menu.

            String[] options = {"1", "2", "3", "4", "5", "6", "7", "8"};
            // Used for inputChecker method. To check the valid options.

            String answer = (String) JOptionPane.showInputDialog(null, temp, "Quiz Portal",
                    JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            //Assigns the valid input to "answer" variable
            if (answer == null) {
                return;
            } else if (answer.equals("1")) {
                //Creates a new Quiz object and saves it in QuizArchive
                creatingAQuiz(quizArchive, courseTitle);
                PrintInformation.writeQuizQuestions(quizArchive);
            } else if (answer.equals("2")) {
                String quizTitle = (String) JOptionPane.showInputDialog(null,
                        "What's the quiz title?",
                        "Quiz Portal",
                        JOptionPane.QUESTION_MESSAGE);
                if (quizTitle == null) {
                    return;
                }
                //Modifies a Quiz based on its title/name. Modifies the options, question, or name of the quiz.
                modifyAQuiz(quizTitle, quizArchive, courseTitle);
                PrintInformation.writeQuizQuestions(quizArchive);
            } else if (answer.equals("3")) {

                String quizName = JOptionPane.showInputDialog(null, "What's the quiz title?",
                        "Quiz Portal",
                        JOptionPane.QUESTION_MESSAGE);
                if (quizName == null) {
                    return;
                }
                boolean check = true;
                boolean quizExists = true;
                for (Quiz q : quizArchive.getQuizzes()) {
                    if (q.getName().equals(quizName)) {
                        quizExists = false;
                        if (!(q.getCourse().equals(courseTitle))) {
                            JOptionPane.showMessageDialog(null, "" +
                                            "This quiz is unavailable for this " +
                                            "course!", "Quiz Portal",
                                    JOptionPane.ERROR_MESSAGE);
                            check = false;
                        }

                    }
                }
                if (quizExists) {
                    JOptionPane.showMessageDialog(null, "" +
                                    "No such quiz with this name!", "Quiz Portal",
                            JOptionPane.ERROR_MESSAGE);
                }
                //Randomize options and questions for a given title of a quiz.
                if (check) {
                    for (int i = 0; i < quizArchive.getQuizzes().size(); i++) {
                        Quiz quiz = quizArchive.getQuizzes().get(i);
                        if (quiz.getName().equals(quizName)) {
                            if (quiz.getRandomize()) {
                                String question = "Questions are already randomized, do you want to toggle it off?";
                                int questionAnswer = JOptionPane.showConfirmDialog(null, question,
                                        "Quiz Portal",
                                        JOptionPane.YES_NO_OPTION);

                                if (questionAnswer == JOptionPane.CLOSED_OPTION ||
                                        questionAnswer == JOptionPane.CANCEL_OPTION) {
                                    return;
                                } else if (questionAnswer == JOptionPane.YES_OPTION) {
                                    quiz.toggleRandomization();
                                    JOptionPane.showMessageDialog(null, "Quiz randomization is " +
                                            "off.", "Quiz Portal", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } else {
                                quiz.toggleRandomization();
                                JOptionPane.showMessageDialog(null,
                                        "Quiz will be randomized for students in each attempt!",
                                        "Quiz Portal",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                }
                //randomizeQuestions(answer, quizArchive);

                PrintInformation.writeQuizQuestions(quizArchive);
            } else if (answer.equals("4")) {
                //Anushka's method. It lists the student answers for their quiz.
                viewStudentSubmissions(quizArchive, courseTitle);

            } else if (answer.equals("5")) {
                int x = 0;
                String allQuizzes = "";
                for (Quiz quiz : quizArchive.getQuizzes()) {
                    if (quiz.getCourse().equals(courseTitle))
                        allQuizzes += "" + (++x) + ". " + quiz.getName() + "\n";

                }

                JOptionPane.showMessageDialog(null, allQuizzes,
                        "Quiz Portal", JOptionPane.INFORMATION_MESSAGE);

                if (x == 0)
                    JOptionPane.showMessageDialog(null, "There are no quizzes available!",
                            "Quiz Portal", JOptionPane.INFORMATION_MESSAGE);
            } else if (answer.equals("6")) {
                String quizDelete = "Which quiz do you want to delete?\n";
                int x = 0;
                ArrayList<Quiz> quizzesToBeDeleted = new ArrayList<>();


                for (Quiz q : quizArchive.getQuizzes()) {
                    if (q.getCourse().equals(courseTitle)) {
                        quizDelete += "" + (++x) + ". " + q.getName() + "\n";
                        quizzesToBeDeleted.add(q);
                    }
                }


                String[] choices = new String[quizzesToBeDeleted.size()];
                for (int i = 0; i < choices.length; i++) {
                    choices[i] = "" + (i + 1);
                }

                String quizDeleted = (String) JOptionPane.showInputDialog(null, quizDelete,
                        "Quiz Portal",
                        JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
                if (quizDeleted == null) {
                    return;

                }

                Quiz quiz = quizzesToBeDeleted.get(Integer.valueOf(quizDeleted) - 1);

                for (int i = 0; i < CourseArchive.allCourses.size(); i++) {
                    Course course = CourseArchive.allCourses.get(i);
                    var courseQuizzes = course.getQuizzes();
                    for (int j = 0; j < courseQuizzes.size(); j++) {
                        if (courseQuizzes.get(j).getName().equals(quiz.getName())) {
                            courseQuizzes.remove(j);
                            i = CourseArchive.allCourses.size();
                            break;
                        }
                    }
                }

                for (int i = 0; i < quizArchive.getQuizzes().size(); i++) {
                    Quiz q = quizArchive.getQuizzes().get(i);
                    if (q.getName().equals(quiz.getName())) {
                        quizArchive.getQuizzes().remove(i);
                        break;
                    }
                }

                try {
                    PrintWriter pw = new PrintWriter(new FileWriter("StudentQuizzes.txt"));
                    pw.print("");
                } catch (IOException e) {
                    String noModify = "Couldn't modify the quiz.";
                    JOptionPane.showMessageDialog(null, noModify, "Quiz Portal",
                            JOptionPane.ERROR_MESSAGE);
                }

                var allQuizzes = quizArchive.getQuizzes();

                StudentAnish.writeScores(quizArchive);

                String quizRemoved = "Quiz removed!";
                JOptionPane.showMessageDialog(null, quizRemoved, "Quiz Portal",
                        JOptionPane.INFORMATION_MESSAGE);
                PrintInformation.writeQuizQuestions(quizArchive);

            } else if (answer.equals("7")) {

                String fileAnswer = JOptionPane.showInputDialog(null,
                        "The file should have the following format:\n Quiz Name\n1. Question:\n" +
                                "1. Option1\n2. Option2\n3. Option3\n4. Option4\n" +
                                "Correct Answers:\nQuestion 1:[numOfCorrectAnswer] Question 2:4\n" +
                                "[pointValue1],[pointValue2]\nFile path?", "Quiz Portal", JOptionPane.QUESTION_MESSAGE);
                if (fileAnswer == null) {
                    return;
                }


                try {

                    String imported = PrintInformation.readQuizQuestions(quizArchive, fileAnswer, courseTitle);
                    if (imported.equals("FileNotFound")) {
                        JOptionPane.showMessageDialog(null,
                                "Couldn't find file.", "Quiz Portal",
                                JOptionPane.INFORMATION_MESSAGE);

                    } else if (imported.equals("WrongFormat")) {
                        JOptionPane.showMessageDialog(null, "File was written in a wrong format.",
                                "Quiz Portal", JOptionPane.ERROR_MESSAGE);
                    } else if (imported.equals("Duplicate")) {
                        JOptionPane.showMessageDialog(null, "This quiz name already exists.",
                                "Quiz Portal", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Quiz Imported!", "Quiz Portal",
                                JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(null,
                            "Couldn't find file.", "Quiz Portal",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "File was written in a wrong format.",
                            "Quiz Portal", JOptionPane.ERROR_MESSAGE);
                }


            } else if (answer.equals("8"))
                break;

        } while (true);

        //Prints the quiz information in a notepad to save it before terminating the quiz portal.
        PrintInformation.writeQuizQuestions(quizArchive);


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
        if (quiz != null) {
            quiz.randomizeQuestions(quiz); //Randomizes the quiz
        } else
            JOptionPane.showMessageDialog(null, "No questions found!", "Quiz Portal",
                    JOptionPane.ERROR_MESSAGE);

    }

    /**
     * Modifies a quiz question, options, or title
     *
     * @param title       = title of the quiz to be modified.
     * @param quizArchive = the archive that contains the quizzes.
     * @return true if the modification was done successfully. Otherwise, false.
     * @throws InvalidQuizException if the quiz is not found, or the newOptions/newQuestion to be modified is not valid.
     */
    public static boolean modifyAQuiz(String title, QuizArchive quizArchive, String courseTitle) throws InvalidQuizException {

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
            String unavailableQuiz = "Unavailable quiz.";
            JOptionPane.showMessageDialog(null, unavailableQuiz, "Quiz Portal",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!(quiz.getCourse().equals(courseTitle))) {
            String wrongCourse = "This quiz is not in this course.";
            JOptionPane.showMessageDialog(null, wrongCourse, "Quiz Portal",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String changeTitle = "Do you want to change the quiz's title? (yes/no)";
        int yesOrNo = JOptionPane.showConfirmDialog(null, changeTitle, "Quiz Portal",
                JOptionPane.YES_NO_OPTION);
        if (yesOrNo == JOptionPane.CANCEL_OPTION) {
            return false;
        }
        if (yesOrNo == JOptionPane.YES_OPTION) {

            String newTitle = "Type the new title";
            answer = JOptionPane.showInputDialog(null, newTitle, "Quiz Portal",
                    JOptionPane.QUESTION_MESSAGE);
            quiz.setName(answer);
            var allQuizzes = quizArchive.getQuizzes();

            try {
                PrintWriter pw = new PrintWriter(new FileWriter("StudentQuizzes.txt"));
                pw.print("");
            } catch (IOException e) {
                String noModify = "Couldn't modify the quiz.";
                JOptionPane.showMessageDialog(null, noModify, "Quiz Portal",
                        JOptionPane.ERROR_MESSAGE);
            }


            StudentAnish.writeScores(quizArchive);
            JOptionPane.showMessageDialog(null, "Quiz name modified!", "Quiz Portal",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

        int size = quiz.getSizeOfQuiz();
        if (size == 0)
            return false;

        String questionNumberModify = "Question number you want to modify?";


        String[] options = new String[size];
        for (int i = 1; i <= size; i++) {

            options[i - 1] = "" + i;

        }
        answer = (String) JOptionPane.showInputDialog(null, questionNumberModify, "Quiz Portal",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (answer == null) {
            return false;
        }

        int questionNumber = Integer.valueOf(answer);


        String temp = "Which one do you want to modify: \n1. The question.\n2. The options.";
        String[] whatModify = {"1", "2"};
        answer = (String) JOptionPane.showInputDialog(null, temp, "Quiz Portal",
                JOptionPane.QUESTION_MESSAGE, null, whatModify, whatModify[0]);
        if (answer == null) {
            return false;
        }

        if (answer.equals("1")) {

            String newQuestion = "Type the new question:";
            answer = JOptionPane.showInputDialog(null, newQuestion, "Quiz Portal",
                    JOptionPane.QUESTION_MESSAGE);
            quiz.modifyAQuestion(questionNumber, answer);

        } else if (answer.equals("2")) {

            String[] newOptions = new String[4];
            for (int i = 0; i < newOptions.length; i++) {

                String optionNumber = "Option " + (i + 1) + ":";
                String newOption = JOptionPane.showInputDialog(null, optionNumber, "Quiz Portal",
                        JOptionPane.QUESTION_MESSAGE);
                if (newOption == null) {
                    return false;
                } else if (newOption.isBlank() || newOption.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Question cannot be blank!",
                            "Quiz Portal", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                newOptions[i] = (i + 1) + ". " + newOption + "\n";
            }

            String[] correctAnswerOptions = {"1", "2", "3", "4"};

            String correctAnswer = "What's the correct answer?";
            String correct = (String) JOptionPane.showInputDialog(null, correctAnswer, "Quiz" +
                            "Portal", JOptionPane.QUESTION_MESSAGE, null, correctAnswerOptions,
                    correctAnswerOptions[0]);
            if (correct == null) {
                return false;
            }

            quiz.modifyOptionsOfAQuestion(questionNumber, newOptions, Integer.valueOf(correct));
            JOptionPane.showMessageDialog(null, "Question modified!", "Quiz Portal",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        return true;


    }


    //Anushka's method


    //Zuhair's version
    public static void viewStudentSubmissions(QuizArchive quizArchive, String course) throws
            FileNotFoundException {

        boolean check = true;

        for (int i = 0; i < quizArchive.getQuizzes().size(); i++) {

            Quiz quiz = quizArchive.getQuizzes().get(i);
            if (quiz.getCourse().equals(course)) {

                if (quiz.isTaken()) {
                    String everything = "";
                    String allQuizzes = "Quiz name: " + quiz.getName();
                    everything += allQuizzes + "\n";

                    check = false;

                    int counter = 0;

                    for (int j = 0; j < quiz.getStudentAnswers().size(); j++) {

                        int ans = quiz.getStudentAnswers().get(j);
                        String studentAnswers = "Question " + (++counter) + " answer: " + ans;

                        everything += studentAnswers + "\n";

                    }
                    String questionsCorrect = "Questions correct: " + quiz.getRawScore() + "\n";
                    String score = "Score: " + quiz.getModifiedScore() + "\n";
                    String timestamp = "Timestamp: " + quiz.getTimeStamp();
                    JOptionPane.showMessageDialog(null, everything + questionsCorrect + score +
                            timestamp, "Quiz Portal", JOptionPane.INFORMATION_MESSAGE);
                }

            }

        }
        if (check)
            JOptionPane.showMessageDialog(null, "No student submissions yet!", "Quiz Portal",
                    JOptionPane.ERROR_MESSAGE);


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
     * @param quizArchive = extract a single Quiz from the QuizArchive
     * @throws InvalidQuizException = throws an exception whenever a quiz with the given information
     *                              can't possibly be created.
     */
    public static void creatingAQuiz(QuizArchive quizArchive, String courseTitle)
            throws InvalidQuizException {

        String answer;

        Quiz temp = null;
        do {

            String addQuiz = "Do you want to add a quiz? (yes/no)";

            int yesOrNo = JOptionPane.showConfirmDialog(null, addQuiz, "Quiz Portal",
                    JOptionPane.YES_NO_OPTION);

            if (yesOrNo == JOptionPane.NO_OPTION) {
                break;
            } else {

                String numberOfQuestions = "How many numbers of questions?";
                String[] questionNumberChoices = new String[120];
                for (int i = 0; i < questionNumberChoices.length; i++) {
                    questionNumberChoices[i] = "" + (i + 1);
                }
                answer = inputChecker(questionNumberChoices, "How many numbers of questions?",
                        "Number of questions should be no more than 120 or less than 1.");

                if (Integer.valueOf(answer) == 0) {

                    do {
                        answer = JOptionPane.showInputDialog(null, "What's the quiz's title?",
                                "Quiz Portal",
                                JOptionPane.QUESTION_MESSAGE);
                        var allQuizzes = quizArchive.getQuizzes();

                        for (Quiz quiz : allQuizzes) {
                            if (quiz.getName().equals(answer)) {
                                answer = "";
                                JOptionPane.showMessageDialog(null, "The quiz title already " +
                                                "exists!",
                                        "Quiz Portal",
                                        JOptionPane.ERROR_MESSAGE);

                            }
                        }

                    } while (answer.isEmpty() || answer.isBlank());

                    Quiz q1 = new Quiz(answer);
                    temp = q1;
                    synchronized (sync) {
                        quizArchive.addQuizzes(q1);
                    }

                    String addQuestions = "Do you want to add questions? (yes/no)";
                    int addNewQuestions = JOptionPane.showConfirmDialog(null, addQuestions,
                            "Quiz Portal", JOptionPane.YES_NO_OPTION);
                    if (addNewQuestions == JOptionPane.CLOSED_OPTION) {
                        return;
                    } else if (addNewQuestions == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(null, "An empty quiz was created!",
                                "Quiz Portal",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {

                        JOptionPane.showInputDialog(null, "Type STOP to stop adding questions!",
                                "Quiz Portal",
                                JOptionPane.INFORMATION_MESSAGE);

                        int numOfQuestions = 0;

                        while (true) {
                            do {

                                answer = JOptionPane.showInputDialog(null, "Question " +
                                                (++numOfQuestions) + ":",
                                        "Quiz Portal",
                                        JOptionPane.QUESTION_MESSAGE);

                                if (answer.isBlank() || answer.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Type the question " +
                                            "again!", "Quiz Portal", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    break;
                                }
                            } while (true);


                            if (answer.equalsIgnoreCase("STOP")) {
                                break;
                            }

                            String question = answer;

                            String[] options = new String[4];
                            for (int i = 0; i < options.length; i++) {

                                String oneOption = JOptionPane.showInputDialog(null,
                                        "Option " + (i + 1) + ":",
                                        "Quiz Portal", JOptionPane.QUESTION_MESSAGE);
                                while (oneOption.isBlank() || oneOption.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "The option is" +
                                            "empty!", "Quiz Portal", JOptionPane.ERROR_MESSAGE);
                                }

                                options[i] = (i + 1) + ". " + oneOption + "\n";
                            }

                            String[] correctAnswerOptions = {"1", "2", "3", "4"};

                            String correct = inputChecker(correctAnswerOptions, "What's the correct answer?",
                                    "Correct answers should be from 1 to 4.");

                            q1.addOneQuestion(question, options, Integer.valueOf(correct));

                        }
                    }

                } else {

                    int numOfQuestions = Integer.valueOf(answer);

                    do {

                        String quizTitle = "What's the quiz's title?";
                        answer = JOptionPane.showInputDialog(null, quizTitle, "Quiz Portal"
                                , JOptionPane.QUESTION_MESSAGE);
                        if (answer == null) {
                            return;
                        }
                        var allQuizzes = quizArchive.getQuizzes();
                        for (Quiz quiz : allQuizzes) {
                            if (quiz.getName().equals(answer)) {
                                answer = "";
                                JOptionPane.showMessageDialog(null, "The quiz title already " +
                                                "exists!",
                                        "Quiz Portal",
                                        JOptionPane.ERROR_MESSAGE);

                            }
                        }

                    } while (answer.isEmpty() || answer.isBlank());


                    Quiz q1 = new Quiz(answer, numOfQuestions);
                    temp = q1;
                    synchronized (sync) {
                        quizArchive.addQuizzes(q1);
                    }


                    for (int i = 0; i < numOfQuestions; i++) {

                        String[] options = new String[4];

                        String questionNumber = "Question " + (i + 1) + ":";

                        String question = "";
                        do {
                            question = JOptionPane.showInputDialog(null, questionNumber, "Quiz Portal",
                                    JOptionPane.QUESTION_MESSAGE);
                            if (question == null) {
                                return;

                            }
                            if (question.isBlank() || question.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Question cannot be blank!",
                                        "Quiz Portal", JOptionPane.ERROR_MESSAGE);
                            } else {
                                break;
                            }
                        } while (true);

                        for (int j = 0; j < options.length; j++) {

                            String optionNumber = "Option " + (j + 1) + ":";
                            String oneOption = "";
                            do {
                                oneOption = JOptionPane.showInputDialog(null, optionNumber,
                                        "Quiz Portal",
                                        JOptionPane.QUESTION_MESSAGE);
                                if (oneOption == null) {
                                    return;

                                }
                                if (!oneOption.isBlank() && !oneOption.isEmpty())
                                    break;
                                JOptionPane.showMessageDialog(null, "The option is empty!",
                                        "Quiz Portal",
                                        JOptionPane.ERROR_MESSAGE);
                            } while (true);

                            options[j] = (j + 1) + ". " + oneOption + "\n";

                        }

                        String correctAnswer = "What's the correct answer?";
                        String[] correctAnswerOptions = {"1", "2", "3", "4"};
                        answer = (String) JOptionPane.showInputDialog(null, correctAnswer, "Quiz Portal",
                                JOptionPane.QUESTION_MESSAGE, null, correctAnswerOptions, correctAnswerOptions[0]);
                        if (answer == null) {
                            return;

                        }
                        q1.addOneQuestion(question, options, Integer.valueOf(answer));

                    }

                    for (int i = 0; i < CourseArchive.allCourses.size(); i++) {

                        if (CourseArchive.allCourses.get(i).getName().equals(courseTitle)) {
                            CourseArchive.allCourses.get(i).addCourseQuiz(q1);
                            q1.assignCourse(courseTitle);
                        }

                    }

                    q1.initializePointValues(StudentAnish.assignPointValues(temp));
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
     * @param choices      = the valid choices
     * @param question     = reprints the question again in case the input was invalid.
     * @param errorMessage = prints the given error message if the input was invalid.
     * @return a String that includes a valid option chosen by the user.
     */
    public static String inputChecker(String[] choices, String question, String errorMessage) {

        do {

            String input = JOptionPane.showInputDialog(null, question, "Quiz Portal",
                    JOptionPane.QUESTION_MESSAGE);

            if (input != null) {
                for (int i = 0; i < choices.length; i++) {
                    if (input.equals(choices[i]))
                        return input;
                }
            }
            JOptionPane.showMessageDialog(null, errorMessage, "Quiz Portal",
                    JOptionPane.ERROR_MESSAGE);

        } while (true);

    }


}