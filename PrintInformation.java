import java.io.*;
import java.util.ArrayList;

/**
 * PrintInformation class
 * <p>
 * prints and writes quiz information to/from a notepad
 *
 * @author Zuhair Almansouri, lab sec L16
 * @version November 15, 2021
 */

public class PrintInformation {

    /**
     * instantiates quizArchive
     * @return quizArchive
     */
    public static QuizArchive getQuizArchive() {
        QuizArchive quizArchive = new QuizArchive();
        return quizArchive;
    }

    public static void main() throws IOException, InvalidQuizException {


        QuizArchive quizArchive = getQuizArchive();

        /*Placeholder to Anish's Student class.
        //Student answers should be written here
        */


        writeQuizQuestions(quizArchive);


    }

    /**
     * Write questions and correct answers to a file
     * @param quizArchive = retrieve quizzes
     * @throws IOException = when an error occurs while writing or reading
     */
    public static void writeQuizQuestions(QuizArchive quizArchive) throws IOException {

        var allQuizzes = quizArchive.getQuizzes();

        BufferedWriter bw = new BufferedWriter(new FileWriter("QuizQuestions.txt", true));
        BufferedReader br = new BufferedReader(new FileReader("QuizQuestions.txt"));
        if (br.readLine() != null) {
            bw.write("\n");
        }

        for (int i = 0; i < allQuizzes.size(); i++) {


            var oneQuiz = allQuizzes.get(i);

            var correctAnswers = oneQuiz.getCorrectAnswers();
            var quizName = oneQuiz.getName();


            bw.write(quizName + "\n");
            bw.write(oneQuiz.questionsPrinter());

            bw.write("Correct answers:\n");
            for (int j = 0; j < correctAnswers.size(); j++) {


                bw.write("Question " + (j + 1) + ":" + correctAnswers.get(j) + " ");

            }

        }

        bw.close();


    }

    /**
     * Reads the quizzes from a file
     * @param quizArchive = store the quizzes in quizeArchive
     * @throws IOException = When an error occurs while reading
     * @throws InvalidQuizException = if the quiz is invalid
     */
    public static void readQuizQuestions(QuizArchive quizArchive) throws IOException, InvalidQuizException {


        BufferedReader br = new BufferedReader(new FileReader("QuizQuestions.txt"));


        String quizName = br.readLine();

        Quiz quiz = new Quiz(quizName);

        ArrayList<String> allQuizQuestions = new ArrayList<>();
        ArrayList<String[]> allQuizOptions = new ArrayList<>();

        while (true) {

            String quizQuestion = br.readLine();

            if (quizQuestion.equals("Correct answers:"))
                break;

            String[] options = new String[4];

            for (int i = 0; i < 4; i++) {

                options[i] = br.readLine() + "\n";

            }

            allQuizQuestions.add(quizQuestion);
            allQuizOptions.add(options);


        }

        ArrayList<Integer> allCorrectAnswers = new ArrayList<>();

        String lastLine = br.readLine();
        br.close();

        while (true) {


            if ((lastLine.indexOf(":") + 3) > lastLine.length())
                break;

            String answer = lastLine.substring(lastLine.indexOf(":") + 1, lastLine.indexOf(":") + 2);

            lastLine = lastLine.substring(lastLine.indexOf(":") + 3);

            allCorrectAnswers.add(Integer.valueOf(answer));

        }

        for (int i = 0; i < allQuizQuestions.size(); i++) {

            quiz.addOneQuestion(allQuizQuestions.get(i).substring(3, allQuizQuestions.get(i).length() - 1), allQuizOptions.get(i), allCorrectAnswers.get(i));

        }

        quizArchive.addQuizzes(quiz);

    }


}
