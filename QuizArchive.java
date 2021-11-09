import java.util.ArrayList;

public class QuizArchive {

    private ArrayList<Quiz> quizzes;


    public QuizArchive() {
        quizzes = new ArrayList<>();
    }

    public QuizArchive(Quiz quiz) {

        this();
        quizzes.add(quiz);

    }

    public void addQuizes(Quiz quiz) {
        quizzes.add(quiz);
    }

}
