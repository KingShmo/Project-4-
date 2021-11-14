import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
/**
 * Quiz class
 *
 * A representation for one quiz that a certain number of students
 * can take.
 *
 * @author Zuhair Almansouri, lab sec L16
 *
 * @version November 12, 2021
 *
 */
public class Quiz {

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
    private ArrayList<Integer> studentAnswers;
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

        //question1:^_^option1^_^option2^_^option3^_^option4

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

    public String randomizeOptions(String question) {

        Random random = new Random();

        int[] num = new int[4];
        int counter = 0;


        while (true) {

            if (counter == num.length)
                break;
            boolean check = true;

            int temp = random.nextInt(5);

            if (temp != 0) {

                for (int i = 0; i < num.length; i++) {

                    if (num[i] == temp)
                        check = false;
                }

                if (check) {
                    num[counter++] = temp;
                }
            }

        }


        String collectQuestionParts = question.substring(0, question.indexOf("^_^") + 3); //question

        question = question.substring(question.indexOf("^_^") + 3);

        String[] options = new String[4];


        int countOptions = 1;

        for (int i = 0; i < options.length; i++) {

            if (question.indexOf("^_^") == -1) {
                options[i] = question + "^_^";
            }
            else {
                options[i] = question.substring(0, question.indexOf("^_^") + 3);
                question = question.substring(question.indexOf("^_^") + 3);
            }

        }


        for (int i = 0; i < options.length; i++) {

            collectQuestionParts += options[num[i] - 1];

        }


        return collectQuestionParts.substring(0, collectQuestionParts.length() - 3);


    }



    public String randomizeQuestions(Quiz quiz) {

        Random random = new Random();

        var oldQuestions = quiz.getQuestions();

        int[] questionsNumbers = new int[quiz.getQuestions().size() + 1];

        ArrayList<Integer> checkRepetitive = new ArrayList<>();
        boolean exit = true;
        for (int i=1 ; i<questionsNumbers.length; i++) {

            while (exit) {

                int temp = random.nextInt(questionsNumbers.length);

                boolean check = true;

                for (int k = 0; k < checkRepetitive.size(); k++) {

                    if (checkRepetitive.get(k) == temp) {
                        check = false;
                    }
                }

                if (check) {
                    checkRepetitive.add(temp);
                    if (temp != 0) {
                        questionsNumbers[i] = temp;

                        exit = false;
                    }
                }



            }
            exit = true;

        }

        ArrayList<String> newQuestions = new ArrayList<>();

        for (int i=1; i<questionsNumbers.length; i++) {

            String oneQuestion = oldQuestions.get(questionsNumbers[i] - 1);
            String modifiedOptions = randomizeOptions(oneQuestion);
            newQuestions.add(modifiedOptions);

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


    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public ArrayList<Integer> getStudentAnswers() {
        return studentAnswers;
    }
    public void setStudentAnswers(ArrayList<Integer> a) {
        studentAnswers = a;
    }


    public String getScore() {

        int count = 0;

        for (int i = 0; i < studentAnswers.size(); i++) {

            if (studentAnswers.get(i) == correctAnswers.get(i))
                count++;
        }

        return "" + count + "/" + studentAnswers.size();

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


    public String questionsPrinter() {

        int questionNum = 1;

        String toBePrinted = "";

        for (int j=0; j < questions.size(); j++) {

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


            toBePrinted += (questionNum++) + question.substring(1) + ":\n";
            toBePrinted += "1" + option1.substring(1) + "2" + option2.substring(1) + "3" + option3.substring(1) + "4" + option4.substring(1);



        }

        return toBePrinted;
    }


}