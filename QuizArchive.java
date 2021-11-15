import java.util.ArrayList;

public class QuizArchive {

    private static ArrayList<Quiz> quizzes;

    public QuizArchive() {
        quizzes = new ArrayList<>();
    }

    public QuizArchive(Quiz quiz) {

        this();
        quizzes.add(quiz);

    }


    public void addQuizzes(Quiz quiz) {
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

    public String toString() {

        String toBeSaved = "Save this information:\n" + "Save the quizzes ArrayList.\n" +
                "Use the arraylist to get all the quizzes. In each quiz object, run the following get methods:\n" +
                "getName()\ngetQuestions()\ngetCorrectAnswers()\ngetStudentAnswers()";

        return toBeSaved;

    }
}