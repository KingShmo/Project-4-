import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * Prints information in notepads
 *
 */

public class PrintInformation {

    /**
     * Placeholder for Anushka's class
     * @return
     */
    public static QuizArchive getQuizArchive() {
        QuizArchive quizArchive = new QuizArchive();
        return quizArchive;
    }

    public static void main() throws IOException, InvalidQuizException {


        QuizArchive quizArchive = getQuizArchive();

        //Placeholder to Anish's Student class.
        Student student = new Student("placeholder", "placeholder", "placeholder", "placeholder");

        Student.writeUnfinishedQuizAnswersToFile(student.getFirstName(), student.getLastName(), "course name", quizArchive);

        Student.writeFinishedQuizAnswersToFile(student.getFirstName(), student.getLastName(), "course name", quizArchive);

        Student.writeQuizQuestions(quizArchive);



        Student.readQuizQuestions(quizArchive);



    }


}