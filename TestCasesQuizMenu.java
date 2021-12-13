/**
 * TestCases class
 * <p>
 * Tests functionality of the whole program by invoking methods
 *
 * @author Zuhair Almansouri, lab sec L16
 * @version December 13, 2021
 */

public class TestCasesQuizMenu {

    public static void main(String[] args) throws Exception {


        //These test cases examine the whole functionality for the quizzes. That is because it mimics the actual
        //quizzes menu

        Course course = new Course("quizTest", new Teacher("q", "q", "q", "q"),
                20);
        CourseArchive.addStaticCourses(course);

        //Creating a quiz. Create a quiz called "quizTest"

        TheQuizFunction.creatingAQuiz(new QuizArchive(), "quizTest");
        var allQuizzes = new QuizArchive().getQuizzes();
        if (allQuizzes.get(allQuizzes.size() - 1).getName().equals("quizTest")) {
            System.out.println(true);
        } else
            System.out.println(false);


        //Modify a quiz
        //Passed if no exception is thrown.
        var modifiedQuiz = searchForQuiz("quizTest");
        try {

            TheQuizFunction.modifyAQuiz("quizTest", new QuizArchive(), "quizTest");
            System.out.println(modifiedQuiz.getName());
            System.out.println(modifiedQuiz.getQuestions().toString());
            System.out.println(true);
        } catch (Exception e) {
            System.out.println(false);
        }


        //Randomize a quiz
        //Passed if no exception is thrown.
        try {
            TheQuizFunction.randomizeQuestions(modifiedQuiz.getName(), new QuizArchive());

            System.out.println(true);
        } catch (Exception e) {
            System.out.println(false);
        }


        //View Student Submissions
        //Passed if no exception is thrown.
        try {
            TheQuizFunction.viewStudentSubmissions(new QuizArchive(), "quizTest");
            System.out.println(true);
        } catch (Exception e) {
            System.out.println(false);
        }

        //List available quizzes to make sure they are added.
        try {
            for (int i = 0; i < new QuizArchive().getQuizzes().size(); i++) {
                Quiz quiz = new QuizArchive().getQuizzes().get(i);
                if (quiz.getName().equals("quizTest") || quiz.getName().equals(modifiedQuiz.getName())) {
                    System.out.println(true);
                }
            }

        } catch (Exception e) {
            System.out.println(false);
        }


        //Delete quiz
        Quiz quiz = new Quiz("toBeDeleted");
        new QuizArchive().addQuizzes(quiz);
        var q = new QuizArchive().getQuizzes();
        if (q.get(q.size() - 1).getName().equals("toBeDeleted")) {
            System.out.println(true);
        } else
            System.out.println(false);

        new QuizArchive().deleteAQuiz("toBeDeleted");

        var toBeTested = searchForQuiz("toBeDeleted");
        if (toBeTested == null) {
            System.out.println(true);
        } else
            System.out.println(false);


    }

    public static Quiz searchForQuiz(String name) {

        var allQuizzes = new QuizArchive().getQuizzes();

        for (int i = 0; i < allQuizzes.size(); i++) {
            if (allQuizzes.get(i).getName().equals(name))
                return allQuizzes.get(i);
        }

        return null;

    }
}
