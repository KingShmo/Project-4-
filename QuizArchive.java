import java.util.ArrayList;

/**
 * PrintInformation class
 * <p>
 * Stores quizzes
 *
 * @author Zuhair Almansouri, Anish Ketha lab sec L16
 * @version November 15, 2021
 */
public class QuizArchive {

    private static ArrayList<Quiz> quizzes = new ArrayList<>(); //All quizzes



    /**
     * instantiates the quizzes arrayList
     */

    public QuizArchive() {

    }

    /**
     * calls the default constructor and adds a quiz
     *
     * @param quiz = adds a quiz
     */
    public QuizArchive(Quiz quiz) {

        this();
        quizzes.add(quiz);

    }

    /**
     * Add Quizzes
     *
     * @param quiz = the quiz to be added
     */
    public void addQuizzes(Quiz quiz) {
        quizzes.add(quiz);
    }

    /**
     * returns quizzes arrayList
     *
     * @return quizzes arrayList
     */
    public ArrayList<Quiz> getQuizzes() {
        return quizzes;
    }

    /**
     * deletes a quiz
     *
     * @param titleOfTheQuiz = the quiz to be deleted
     */
    public void deleteAQuiz(String titleOfTheQuiz) {

        for (int i = 0; i < quizzes.size(); i++) {

            if (quizzes.get(i).getName().equals(titleOfTheQuiz)) {
                quizzes.remove(i);
                break;
            }

        }
    }

    /**
     * Used to list all things that need to be written to a file
     *
     * @return = the string that has the information needed to be saved
     */
    public String toString() {

        String toBeSaved = "Save this information:\n" + "Save the quizzes ArrayList.\n" +
                "Use the arraylist to get all the quizzes. In each quiz object, run the following get methods:\n" +
                "getName()\ngetQuestions()\ngetCorrectAnswers()\ngetStudentAnswers()";

        return toBeSaved;

    }
}
