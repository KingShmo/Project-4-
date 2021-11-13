import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
/**
 * Quiz class
 *
 * A representation for one quiz that a certian number of students
 * can take.
 *
 * @author Zuhair Almansouri, lab sec L16
 *
 * @version November 7, 2021
 *
 */
public class Quiz {
    int option;

    /**
     * Title of the quiz
     */
    private String name;
    /**
     * number of attempts for the quiz. Each index represents one student.
     */
    private ArrayList<Integer> attempts;
    /**
     * All scores for the students. Each index represents the highest score for
     * a particular student.
     */
    private ArrayList<Double> scores;
    /**
     * Students who took the quiz
     */
    private ArrayList<Student> students;
    /**
     * Students answers
     */
    private ArrayList<Integer[]> studentAnswers;
    /**
     * Quiz questions
     *
     * Each index has the whole question including its options.
     * the question and the options are seperated by "^_^".
     * For example:
     *
     * index 0: "Do you love coding?^_^1.Yes^_^2.No^_^3.Maybe^_^4.IDK"
     */
    private ArrayList<String> questions;
    /**
     * Correct answers for each question.
     *
     * Each index corresponding to the same index on the questions arraylist
     * include the correct answer.
     *
     * For example:
     * index 0 in questions arraylist that has the whole question, will have its
     * correct answer in index 0 of the correctAnswers arraylist.
     *
     */
    private ArrayList<Integer> correctAnswers;
    /**
     * Whether or not the quiz is ready to be launched.
     */
    private boolean quizIsReady;
    /**
     * size of the quiz
     */
    private int sizeOfQuiz;
    /**
     * quiz questions counter
     */
    private int questionNum;
    /**
     * Allocates a new quiz object with its title.
     * Used in-case the teacher doesn't have questions to put, or
     * is not sure how many questions to put.
     *
     * @param name = the title of the quiz
     */
    public Quiz(String name) {

        this.name = name;
        attempts = new ArrayList<>();
        scores = new ArrayList<>();
        students = new ArrayList<>();
        questions = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        quizIsReady = false;
        sizeOfQuiz = 0;
        questionNum = 1;

    }

    /**
     * Allocates a new quiz object with its title and number of questions.
     * Normally, this is the constructor to be used. It is when the teacher has a
     * specified number of questions.
     *
     * @param name = quiz title.
     * @param numberOfQuestions = number of questions in the quiz.
     * @throws InvalidQuizException = throws an exception when there are no questions for the quiz.
     */
    public Quiz(String name, int numberOfQuestions) throws InvalidQuizException {

        this(name);

        if (numberOfQuestions < 1)
            throw new InvalidQuizException("A quiz should have at least one question!");

        sizeOfQuiz = numberOfQuestions;


    }

    /**
     * Adds one question associated with its options.
     * @param question = the question to be added.
     * @param options = the options for that question.
     * @throws InvalidQuizException = an exception is thrown when no questions found or the options
     * are not four.
     */

    public void addOneQuestion(String question, String[] options, int correctAnswer) throws InvalidQuizException {

        if (sizeOfQuiz > 120)
            throw new InvalidQuizException("The quiz is 120 questions, and can't accept more questions.");

        if (questions == null)
            throw new NullPointerException("No question found.");
        if (options == null || options.length != 4)
            throw new InvalidQuizException("A question should have four options.");

        // Question code that separates question parts: ^_^
        boolean check = false;
        for (int i=0; i < options.length; i++)
            if (options[i].indexOf("^_^") != -1)
                check = true;

        if (question.indexOf("^_^") != -1 || check)
            throw new InvalidQuizException("A quiz cannot have this \"^_^\" expression in it.");

        String completedQuestion = "" + (questionNum++) + ". ";

        completedQuestion += question;
        completedQuestion += "^_^";

        for (int i = 0; i < options.length; i++) {
            completedQuestion += options[i];
            if (i + 1 != options.length)
                completedQuestion += "^_^";
        }

        questions.add(completedQuestion);
        correctAnswers.add(correctAnswer);
        sizeOfQuiz = questions.size();

    }

    /**
     * A get method
     * @return = returns a quiz object
     */
    public Quiz createQuiz() {
        return this;
    }

    /**
     * modified a question.
     * @param questionNumber = the number of the question to be modified.
     * @param newQuestion = the new question that will replace the old question.
     * @return a string representing whether or not a question was modified successfully.
     * @throws InvalidQuizException when having a question number less than one or (empty/blank/null) question.
     */
    public String modifyAQuestion(int questionNumber, String newQuestion) throws InvalidQuizException {

        if (questionNumber < 1)
            throw new InvalidQuizException("Question numbers should be greater than 1.");
        if (newQuestion == null || newQuestion.isBlank() || newQuestion.isEmpty())
            throw new InvalidQuizException("Question not found.");

        for (int i=0; i<questions.size(); i++) {

            int temp = Integer.valueOf(questions.get(i).substring(0, 1));
            if (temp == questionNumber) {

                String oldQuestion = questions.get(i);
                oldQuestion = oldQuestion.substring(oldQuestion.indexOf("^_^"));
                String modifiedQuestion = "" + questionNumber + ". " + newQuestion + oldQuestion;
                questions.set(i, modifiedQuestion);
                return "Question modified!";
            }
        }

        return "Question not found.";

    }

    public String modifyOptionsOfAQuestion(int questionNumber, String[] newOptions, int correctAnswer) throws InvalidQuizException {

        if (questionNumber < 1)
            throw new InvalidQuizException("Question numbers should be greater than 1.");
        if (newOptions == null || newOptions.length != 4)
            throw new InvalidQuizException("There should be four options for the quiz.");

        for (int i=0; i<questions.size(); i++) {

            int temp = Integer.valueOf(questions.get(i).substring(0, 1));
            if (temp == questionNumber) {

                String oldQuestion = questions.get(i);
                oldQuestion = oldQuestion.substring(0, oldQuestion.indexOf("^_^"));
                oldQuestion += "^_^";
                String completedQuestion = "";
                for (int j = 0; j < newOptions.length; j++) {
                    completedQuestion += newOptions[j];
                    if (j + 1 != newOptions.length)
                        completedQuestion += "^_^";
                }

                String newQestion = oldQuestion + completedQuestion;
                questions.set(i, newQestion);
                correctAnswers.set(i, correctAnswer);

                return "Options modified!";
            }

        }

        return "Question not found.";

    }

    public String randomizeQuestions(Quiz quiz) {

        Random random = new Random();

        var oldQuestions = quiz.getQuestions();

        int[] questionsNumbers = new int[quiz.getQuestions().size() + 1];

        for (int i=1 ; i<questionsNumbers.length; i++) {

            while (true) {

                int temp = random.nextInt(questionsNumbers.length);
                if (questionsNumbers[temp] == 0 && temp != 0) {
                    questionsNumbers[i] = temp;
                    break;
                }

            }

        }

        ArrayList<String> newQuestions = new ArrayList<>();

        for (int i=1; i<questionsNumbers.length; i++) {

            newQuestions.add(oldQuestions.get(questionsNumbers[i]));

        }

        quiz.setQuestions(newQuestions);

        return "Questions randomized!";

    }

    public void addAStudent(Student student) {
        students.add(student);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getAttempts() {
        return attempts;
    }

    public ArrayList<Double> getScores() {
        return scores;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }



    public ArrayList<Integer> getCorrectAnswers() {
        return correctAnswers;
    }

    public boolean isQuizIsReady() {
        return quizIsReady;
    }

    public void launchQuiz() {
        quizIsReady = true;
    }

    public int getSizeOfQuiz() {
        return sizeOfQuiz;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public void setName(String newName) {
        this.name = newName;
    }
    
    public void deleteQuiz(QuizArchive q, String nameOfQuiz) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Quiz> allQuizzes = q.getQuizzes();
        int loop = 0;
        do {
            loop = 0;
            System.out.println("Which quiz do you want to delete?");
            int deleteDigit = scanner.nextInt();
            if (allQuizzes.get(deleteDigit).getName().equals(nameOfQuiz)) {
                allQuizzes.remove(deleteDigit);
                System.out.println("Quiz Removed!");
            } else {
                System.out.println("Quiz does not exist!");
            }
            System.out.println("Do you want to remove another quiz?");
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
}
