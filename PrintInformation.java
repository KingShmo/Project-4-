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


        //QuizArchive quizArchive = getQuizArchive();

        /*Placeholder to Anish's Student class.
        //Student answers should be written here
        */


        //writeQuizQuestions(quizArchive);


    }

    /**
     * Write questions and correct answers to a file
     * @param quizArchive = retrieve quizzes
     * @throws IOException = when an error occurs while writing or reading
     */
    public static void writeQuizQuestions(QuizArchive quizArchive) throws IOException {

        if (quizArchive.getQuizzes().size() == 0)
            return;
        var allQuizzes = quizArchive.getQuizzes();

        BufferedWriter bw = new BufferedWriter(new FileWriter("QuizQuestions.txt"));
        BufferedReader br = new BufferedReader(new FileReader("QuizQuestions.txt"));
        if (br.readLine() != null) {
            bw.write("\n");
        }

        for (int i = 0; i < allQuizzes.size(); i++) {


            var oneQuiz = allQuizzes.get(i);

            var correctAnswers = oneQuiz.getCorrectAnswers();
            var quizName = oneQuiz.getName();


            bw.write(quizName + "-" + oneQuiz.getCourse() + "\n");
            bw.write(oneQuiz.questionsPrinter());

            bw.write("Correct answers:\n");
            for (int j = 0; j < correctAnswers.size(); j++) {


                bw.write("Question " + (j + 1) + ":" + correctAnswers.get(j) + " ");

            }
            bw.write("\n");

            int[] temp = oneQuiz.getPointValues();

            for (int j = 0; j < oneQuiz.getPointValues().length; j++) {


                bw.write("" + temp[j]);

            }

            bw.write("\n");

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

        while (true) {

            String quizName = br.readLine();

            if (quizName == null) {
                br.close();
                return;
            }


            Quiz quiz = new Quiz(quizName.substring(0, quizName.indexOf("-")));
            quiz.assignCourse(quizName.substring(quizName.indexOf("-") + 1));

            ArrayList<String> allQuizQuestions = new ArrayList<>();
            ArrayList<String[]> allQuizOptions = new ArrayList<>();

            while (true) {

                String quizQuestion = br.readLine();
                if (quizQuestion == null) {
                    break;
                }
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


            while (true) {
                if (lastLine == null) {
                    break;
                }

                if ((lastLine.indexOf(":") + 3) > lastLine.length())
                    break;

                String answer = lastLine.substring(lastLine.indexOf(":") + 1, lastLine.indexOf(":") + 2);

                lastLine = lastLine.substring(lastLine.indexOf(":") + 3);

                allCorrectAnswers.add(Integer.valueOf(answer));

            }

            for (int i = 0; i < allQuizQuestions.size(); i++) {

                quiz.addOneQuestion(allQuizQuestions.get(i).substring(3, allQuizQuestions.get(i).length() - 1), allQuizOptions.get(i), allCorrectAnswers.get(i));

            }

            lastLine = br.readLine();

            int[] pointValues = new int[lastLine.length()];

            for (int i = 0; i < pointValues.length; i++) {
                pointValues[i] = Integer.valueOf(lastLine.substring(i, i + 1));
            }

            quiz.initializePointValues(pointValues);

            quizArchive.addQuizzes(quiz);
        }


    }


}