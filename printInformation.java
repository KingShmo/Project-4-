import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * Prints information in notepads
 *
 */

public class printInformation {

    /**
     * Placeholder for Anushka's class
     * @return
     */
    public static QuizArchive getQuizArchive() {
        QuizArchive quizArchive = new QuizArchive();
        return quizArchive;
    }

    public static void main(String[] args) throws IOException, InvalidQuizException {


        QuizArchive quizArchive = getQuizArchive();

        //Placeholder to Anish's Student class.
        Student student = new Student("placeholder", "placeholder", "placeholder", "placeholder");

        //For test purposes
        Student.writeUnfinishedQuizAnswersToFile(student.getFirstName(), student.getLastName(), "course name", quizArchive);
        //For test purposes
        Student.writeFinishedQuizAnswersToFile(student.getFirstName(), student.getLastName(), "course name", quizArchive);
        //For test purposes
        Student.writeQuizQuestions(quizArchive);
        //For test purposes
        Student.readQuizQuestions(quizArchive);



    }


}
