import javax.swing.*;
import java.io.*;
import java.lang.annotation.Target;
import java.util.ArrayList;

/**
 * PrintInformation class
 * <p>
 * prints and writes quiz information to/from a notepad
 *
 * @author Zuhair Almansouri, lab sec L16
 * @version December 11, 2021
 */

public class PrintInformation {

    /**
     * sync threads
     */
    private static Object sync = new Object();

    /**
     * instantiates quizArchive
     *
     * @return quizArchive
     */
    public static QuizArchive getQuizArchive() {
        QuizArchive quizArchive = new QuizArchive();
        return quizArchive;
    }

    public static void main() throws IOException, InvalidQuizException {


    }

    /**
     * Write questions and correct answers to a file
     *
     * @param quizArchive = retrieve quizzes
     * @throws IOException = when an error occurs while writing or reading
     */
    public static void writeQuizQuestions(QuizArchive quizArchive) throws IOException {

        synchronized (sync) {

            if (quizArchive.getQuizzes().size() == 0) {
                BufferedWriter bw = new BufferedWriter(new FileWriter("QuizQuestions.txt"));
                bw.write("");
                return;
            }
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

                //Not taken '-', taken '--'
                String taken = "-";
                if (oneQuiz.isTaken())
                    taken = "--";

                bw.write(quizName + "-" + oneQuiz.getCourse() + taken + "\n");
                if (oneQuiz.getRandomize()) {
                    bw.write("+\n");
                }
                bw.write(oneQuiz.questionsPrinter());

                bw.write("Correct answers:\n");
                for (int j = 0; j < correctAnswers.size(); j++) {


                    bw.write("Question " + (j + 1) + ":" + correctAnswers.get(j) + " ");

                }
                bw.write("\n");

                int[] temp = oneQuiz.getPointValues();

                for (int j = 0; j < oneQuiz.getPointValues().length; j++) {


                    bw.write("" + temp[j] + ",");

                }

                bw.write("\n");

            }

            bw.close();
        }


    }

    /**
     * Write questions and correct answers to a file
     *
     * @param quiz = quiz to be written
     * @throws IOException = when an error occurs while writing or reading
     */
    public static void writeImportedQuizQuestions(Quiz quiz, String path) throws IOException {


        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        BufferedReader br = new BufferedReader(new FileReader(path));
        if (br.readLine() != null) {
            bw.write("\n");
        }

        var oneQuiz = quiz;

        var correctAnswers = oneQuiz.getCorrectAnswers();
        var quizName = oneQuiz.getName();

        //Not taken '-', taken '--'
        String taken = "-";
        if (oneQuiz.isTaken())
            taken = "--";

        bw.write(quizName + "-" + oneQuiz.getCourse() + taken + "\n");
        bw.write(oneQuiz.questionsPrinter());

        bw.write("Correct answers:\n");
        for (int j = 0; j < correctAnswers.size(); j++) {


            bw.write("Question " + (j + 1) + ":" + correctAnswers.get(j) + " ");

        }
        bw.write("\n");

        int[] temp = oneQuiz.getPointValues();

        for (int j = 0; j < oneQuiz.getPointValues().length; j++) {


            bw.write("" + temp[j] + ",");

        }
        bw.write("\n");


        bw.close();


    }

    /**
     * Reads the quizzes from a file
     *
     * @param quizArchive = store the quizzes in quizeArchive
     * @throws IOException          = When an error occurs while reading
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


            String temp = quizName;

            if (quizName.substring(quizName.length() - 1).equals("-")) {
                quizName = quizName.substring(0, quizName.length() - 1);
                if (quizName.substring(quizName.length() - 1).equals("-"))
                    quizName = quizName.substring(0, quizName.length() - 1);
            }

            Quiz quiz = new Quiz(quizName.substring(0, quizName.indexOf("-")));
            quiz.assignCourse(quizName.substring(quizName.indexOf("-") + 1));


            /*if (temp.indexOf("--") != -1) {
                quiz.toggleTaken();
            }*/


            ArrayList<String> allQuizQuestions = new ArrayList<>();
            ArrayList<String[]> allQuizOptions = new ArrayList<>();

            while (true) {

                String quizQuestion = br.readLine();
                if (quizQuestion.equals("+")) {
                    quiz.toggleRandomization();
                    quizQuestion = br.readLine();
                }
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


            if (!(lastLine.substring(lastLine.length() - 1).equals(" "))) {
                lastLine += " ";
            }

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

            ArrayList<Integer> pointValues = new ArrayList<>();

            if (lastLine.indexOf(",") == -1)
                pointValues.add(Integer.valueOf(lastLine));
            else {
                while (true) {

                    if (lastLine.indexOf(",") == -1)
                        break;

                    String onePointValue = lastLine.substring(0, lastLine.indexOf(","));
                    lastLine = lastLine.substring(lastLine.indexOf(",") + 1);
                    pointValues.add(Integer.valueOf(onePointValue));

                }

            }

            int[] p = new int[pointValues.size()];
            for (int i = 0; i < p.length; i++)
                p[i] = pointValues.get(i);
            quiz.initializePointValues(p);

            synchronized (sync) {
                quizArchive.addQuizzes(quiz);
            }
        }


    }

    //Reads imported quizzes externally
    public static String readQuizQuestions(QuizArchive quizArchive, String path, String course) throws IOException,
            InvalidQuizException, InvalidCourseException {

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            return "FileNotFound";
        }

        String quizName = br.readLine();

        if (quizName == null || quizName.contains("-")) {
            br.close();
            return "WrongFormat";
        }

        var allQuizzes = quizArchive.getQuizzes();

        for (int i = 0; i < allQuizzes.size(); i++) {
            if (allQuizzes.get(i).getName().equals(quizName)) {
                return "Duplicate";
            }
        }

        Quiz quiz = new Quiz(quizName);
        quiz.assignCourse(course);


        /*if (quizName.substring(quizName.length() - 1).equals("-")) {
            quizName = quizName.substring(0, quizName.length() - 1);
            if (quizName.substring(quizName.length() - 1).equals("-"))
                quizName = quizName.substring(0, quizName.length() - 1);
        }*/




        /*if (temp.indexOf("--") != -1) {
            quiz.toggleTaken();
        }*/

        ArrayList<String> allQuizQuestions = new ArrayList<>();
        ArrayList<String[]> allQuizOptions = new ArrayList<>();

        while (true) {

            String quizQuestion = br.readLine();
            if (quizQuestion.equals("+")) {
                quiz.toggleRandomization();
                quizQuestion = br.readLine();
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


        if (!(lastLine.substring(lastLine.length() - 1).equals(" "))) {
            lastLine += " ";
        }

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

            quiz.addOneQuestion(allQuizQuestions.get(i).substring(3, allQuizQuestions.get(i).length() - 1),
                    allQuizOptions.get(i), allCorrectAnswers.get(i));

        }

        lastLine = br.readLine();

        ArrayList<Integer> pointValues = new ArrayList<>();

        if (lastLine.indexOf(",") == -1)
            pointValues.add(Integer.valueOf(lastLine));
        else {
            while (true) {

                if (lastLine.indexOf(",") == -1)
                    break;

                String onePointValue = lastLine.substring(0, lastLine.indexOf(","));
                lastLine = lastLine.substring(lastLine.indexOf(",") + 1);
                pointValues.add(Integer.valueOf(onePointValue));

            }

        }

        int[] p = new int[pointValues.size()];
        for (int i = 0; i < p.length; i++)
            p[i] = pointValues.get(i);
        quiz.initializePointValues(p);


        for (int i = 0; i < CourseArchive.allCourses.size(); i++) {

            if (CourseArchive.allCourses.get(i).getName().equals(course)) {
                CourseArchive.allCourses.get(i).addCourseQuiz(quiz);
            }

        }

        synchronized (sync) {
            quizArchive.addQuizzes(quiz);
        }

        br.close();

        return "Done";


    }


}