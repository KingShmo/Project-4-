import java.util.ArrayList;
import java.util.Objects;
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

    /**
     * Title of the quiz
     */
    private String name;
    /**
     * number of attempts for the quiz
     */
    private ArrayList<Integer> attempts;
    /**
     * All scores for the students
     */
    private ArrayList<Double> scores;
    private ArrayList<Student> students;
    private ArrayList<String> questions;
    private ArrayList<String> correctAnswers;
    private boolean quizIsReady;
    private int sizeOfQuiz;

    public Quiz(String name) {
        this.name = name;
        attempts = new ArrayList<>();
        scores = new ArrayList<>();
        students = new ArrayList<>();
        questions = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        quizIsReady = false;
        sizeOfQuiz = 0;

    }

    public Quiz(String name, int numberOfQuestions) throws InvalidQuizException {

        this(name);

        if (numberOfQuestions < 1)
            throw new InvalidQuizException("A quiz should have at least one question!");

        sizeOfQuiz = numberOfQuestions;


    }

    public void addOneQuestion(String question, String[] options) {
        if (questions == null)
            throw new NullPointerException("No question found.");

        // Question code: ^_^

        String completedQuestion = "";

        completedQuestion += question;
        completedQuestion += "^_^";

        for (int i = 0; i < options.length; i++) {
            completedQuestion += options[i];
            completedQuestion += "^_^";
        }

        questions.add(completedQuestion);

    }

    public void addQuestions(String[] questions) throws InvalidQuizException {

        if (questions == null || questions.length < 1)
            throw new InvalidQuizException("No questions found.");

        for (int i=0; i<questions.length; i++) {

            this.questions.add(questions[i]);
        }

    }

    public void addOptionsAndAnswers(String[] options) throws InvalidQuizException {

        if (options == null || options.length % 4 != 0)
            throw new InvalidQuizException("Multiple choice questions need four options each.");


    }






}
