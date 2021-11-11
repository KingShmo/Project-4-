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

    public ArrayList<Quiz> getQuizzes() {
        return quizzes;
    }

    public void deleteAQuiz(String titleOfTheQuiz) {

        for (int i=0; i<quizzes.size(); i++) {

            if (quizzes.get(i).getName().equals(titleOfTheQuiz)) {
                quizzes.remove(i);
                break;
            }

        }
    }
}
