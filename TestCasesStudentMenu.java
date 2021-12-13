/**
 * TestCases class
 * <p>
 * Tests functionality of the whole program by invoking methods
 *
 * @author Zuhair Almansouri, lab sec L16
 * @version December 13, 2021
 */

public class TestCasesStudentMenu {

    public static void main(String[] args) throws Exception {

        //These test cases examine the whole functionality for the student. Create a quiz called "quizTest" to test it.


        //Student taking a quiz

        //Create a quiz called "quizTest"
        TheQuizFunction.creatingAQuiz(new QuizArchive(), "quizTest");

        try {
            //Take the quiz with the title "quizTest"
            StudentAnish.startAQuiz("quizTest", new QuizArchive());
            System.out.println(true);
        } catch (Exception e) {
            System.out.println(false);
        }

        //Timestamp, raw score, modified score
        var allQuizzes = new QuizArchive().getQuizzes();
        var quiz = allQuizzes.get(allQuizzes.size() - 1);
        System.out.println(quiz.getTimeStamp()); //timestamp
        System.out.println(quiz.getRawScore()); //score
        System.out.println(quiz.getModifiedScore()); //modified score (with point values)


    }
}
