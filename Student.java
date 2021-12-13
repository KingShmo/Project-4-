import java.io.*;
import java.util.ArrayList;

/**
 * Student class
 * <p>
 * Represents a student. Assigns name and account information.
 * Writes and reads from/to txt files.
 *
 * @author Troy, Artemii, Anushka
 * @version December 11, 2021
 */
public class Student {

    /**
     * sync threads
     */
    private static Object sync = new Object();

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    static ArrayList<Student> students = new ArrayList<>();
    private ArrayList<String> scores;


    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        scores = new ArrayList<>();
    }

    public Student(String firstName, String lastName, String username) {
        this(firstName, lastName);
        this.username = username;
    }

    public Student(String firstName, String lastName, String username, String password) {
        this(firstName, lastName);
        this.username = username;
        this.password = password;
        scores = new ArrayList<>();
    }

    public void addScore(String score, String modifiedScore, String quiz) {
        scores.add(score + "," + modifiedScore + "," + quiz);
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void addAStudent(Student student) {
        synchronized (sync) {
            students.add(student);
        }
    }


    public String getFirstName() { //Returns the first name of a student
        return firstName;
    }

    public String getLastName() { //Returns the last name of a student
        return lastName;
    }

    public String getName() { //Returns the full name of a teacher
        return getFirstName() + " " + getLastName();
    }

    public String getUsername() { //Returns the username of a student (use it to find out if username already exists)
        return username;
    }

    public String getPassword() { //Returns the password of a student
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //creates a student account by writing it
    public static void createAccount()
            throws FileNotFoundException {
        synchronized (sync) {
            //write to StudentAccounts.txt
            FileOutputStream fos = new FileOutputStream("StudentAccounts.txt");
            //create new print writer
            PrintWriter pw = new PrintWriter(fos);
            //write the name, username, and password of each account

            for (int i = 0; i < students.size(); i++) {

                String firstName = students.get(i).getFirstName();
                String lastName = students.get(i).getLastName();
                String username = students.get(i).getUsername();
                String password = students.get(i).getPassword();

                pw.println("Name: " + firstName + " " + lastName);
                pw.println("Username: " + username);
                pw.println("Password: " + password);
            }
            pw.flush();
            pw.close();
        }
    }


    //reads usernames and passwords
    public static ArrayList<String> getAllUsernamesAndPasswords() {
        ArrayList<String> fileContents = new ArrayList<>();
        ArrayList<String> allUsernames = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //add lines to arraylist
                fileContents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (fileContents.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < fileContents.size() / 4; i++) {
                //add usernames and passwords to an arraylist and return it
                String user = fileContents.get(1 + 4 * i);
                allUsernames.add(user.substring(10));
                String pass = fileContents.get(2 + 4 * i);
                allUsernames.add(pass.substring(10));
            }
        }
        return allUsernames;
    }

    public static String getSpecificPassword(String inputUsername) {
        ArrayList<String> fileContents = new ArrayList<>();
        ArrayList<String> allUsernames = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //add lines to arraylist
                fileContents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (fileContents.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < fileContents.size(); i++) {
                //find the input username and if username found, then retrive the password (next line in the txt file)
                if (fileContents.get(i).equals("Username: " + inputUsername)) {
                    return fileContents.get(i + 1).substring(10);
                }
            }
        }
        return null;
    }


    //if program has error, run function to save answers
    public static void writeUnfinishedQuizAnswersToFile(String firstName, String lastName, String course,
                                                        String quizName, ArrayList<Character> answersQuiz)
            throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream("StudentQuizzes.txt", true);
        PrintWriter pw = new PrintWriter(fos);
        //write the name of student
        pw.println("Name: " + firstName + " " + lastName);
        //write the quiz name and course
        pw.println("Answers for " + course + " quiz: " + quizName);
        //write the answers of the quizzes
        for (int i = 0; i < answersQuiz.size() - 1; i++) {
            pw.print(answersQuiz.get(i) + ";");
        }
        pw.println(answersQuiz.get(answersQuiz.size() - 1));
        //write that the quiz is unfinished and no score can be used
        pw.println("Unfinished Quiz...Score: N/A");
        pw.println();
        pw.flush();
    }

    //if user completes quiz and does not have any errors
    public static void writeFinishedQuizAnswersToFile(String firstName, String lastName, String course, String quizName,
                                                      ArrayList<Character> answersQuiz, int grade)
            throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream("StudentQuizzes.txt", true);
        PrintWriter pw = new PrintWriter(fos);
        //write the name of student
        pw.println("Name: " + firstName + " " + lastName);
        //write the quiz name and course
        pw.println("Answers for " + course + " quiz: " + quizName);
        //write the answers of the quizzes
        for (int i = 0; i < answersQuiz.size() - 1; i++) {
            pw.print(answersQuiz.get(i) + ";");
        }
        pw.println(answersQuiz.get(answersQuiz.size() - 1));
        //write the score of the quiz
        pw.println("Score: " + grade + "/100");
        pw.println();
        pw.flush();
    }

    /**
     * reads students from a file
     *
     * @throws IOException = when an error occurs while reading
     */
    public static void readStudents() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("StudentAccounts.txt"));

        String line = br.readLine();

        if (line == null)
            return;
        while (line != null) {

            String wholeName = line.substring(line.indexOf(":") + 2);
            String firstName = wholeName.substring(0, wholeName.indexOf(" "));
            String lastName = wholeName.substring(wholeName.indexOf(" ") + 1);

            line = br.readLine();

            String username = line.substring(line.indexOf(":") + 2);

            line = br.readLine();

            String password = line.substring(line.indexOf(":") + 2);

            Student student = new Student(firstName, lastName, username, password);

            student.addAStudent(student);

            line = br.readLine();

        }

        br.close();

    }

    /**
     * read scores
     */
    public static void readStudentsScores() {

        try (BufferedReader br = new BufferedReader(new FileReader("StudentQuizzes.txt"))) {

            String line = br.readLine();

            ArrayList<String> rawScores = new ArrayList<>();
            ArrayList<String> modifiedScores = new ArrayList<>();
            ArrayList<String> quizzes = new ArrayList<>();

            while (line != null) {

                rawScores.add(line.substring(0, line.indexOf(",")));
                line = line.substring(line.indexOf(",") + 1);
                modifiedScores.add(line.substring(0, line.indexOf(",")));
                line = line.substring(line.indexOf(",") + 1);
                quizzes.add(line);

                line = br.readLine();

            }

            var allCourses = CourseArchive.allCourses;

            for (int i = 0; i < allCourses.size(); i++) {

                var allQuizzes = allCourses.get(i).getQuizzes();


            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * read teachers from a file
     *
     * @param teachers = store the teachers
     * @throws IOException = when an error occurs while reading
     */
    public static void readTeachers(ArrayList<Teacher> teachers) throws IOException {

        File file = new File("TeacherAccounts.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = br.readLine();

        if (line == null)
            return;
        while (line != null) {

            //get the information needed to create a teacher object
            String wholeName = line.substring(line.indexOf(":") + 2);
            String firstName = wholeName.substring(0, wholeName.indexOf(" "));
            String lastName = wholeName.substring(wholeName.indexOf(" ") + 1);

            line = br.readLine();

            String username = line.substring(line.indexOf(":") + 2);

            line = br.readLine();

            String password = line.substring(line.indexOf(":") + 2);

            //create a new teacher and add the teacher to an arraylist

            Teacher teacher = new Teacher(firstName, lastName, username, password);
            Teacher.addATeacher(teacher);


            line = br.readLine();

        }
        br.close();

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

            quiz.addOneQuestion(allQuizQuestions.get(i).substring(3, allQuizQuestions.get(i).length() - 1),
                    allQuizOptions.get(i), allCorrectAnswers.get(i));

        }

        synchronized (sync) {
            quizArchive.addQuizzes(quiz);
        }

    }

    /**
     * Write questions and correct answers to a file
     *
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
     * Write finished answers to a file
     *
     * @param firstName   = student first name
     * @param lastName    = student last name
     * @param course      = their assigned course
     * @param quizArchive = retrieve the quizzes
     * @throws FileNotFoundException = thrown when the file is not found
     */
    public static void writeFinishedQuizAnswersToFile(String firstName, String lastName, String course,
                                                      QuizArchive quizArchive)
            throws FileNotFoundException {


        FileOutputStream fos = new FileOutputStream("StudentQuizzes.txt", true);
        PrintWriter pw = new PrintWriter(fos);

        var allQuizzes = quizArchive.getQuizzes();

        for (int j = 0; j < allQuizzes.size(); j++) {

            String quizName = allQuizzes.get(j).getName();
            var answersQuiz = allQuizzes.get(j).getStudentAnswers();
            var grade = allQuizzes.get(j).getScore();

            pw.println("Name: " + firstName + " " + lastName);
            pw.println("Answers for " + course + " quiz: " + quizName);
            for (int i = 0; i < answersQuiz.size() - 1; i++) {
                pw.print(answersQuiz.get(i) + ";");
            }
            pw.println(answersQuiz.get(answersQuiz.size() - 1));
            pw.println("Score: " + grade);
            pw.println();
            pw.flush();
        }
        pw.close();
    }

    /**
     * Writes student answers to a file
     *
     * @param firstName   = student first name
     * @param lastName    = student last name
     * @param course      = their assigned course
     * @param quizArchive = retrieve the quizzes
     * @throws FileNotFoundException = if the file is not found
     */
    public static void writeUnfinishedQuizAnswersToFile(String firstName, String lastName,
                                                        String course, QuizArchive quizArchive)
            throws FileNotFoundException {

        FileOutputStream fos = new FileOutputStream("StudentQuizzes.txt", true);
        PrintWriter pw = new PrintWriter(fos);

        var allQuizzes = quizArchive.getQuizzes();

        for (int j = 0; j < allQuizzes.size(); j++) {

            String quizName = allQuizzes.get(j).getName();

            var answersQuiz = allQuizzes.get(j).getStudentAnswers();

            pw.println("Name: " + firstName + " " + lastName);
            pw.println("Answers for " + course + " quiz: " + quizName);

            for (int i = 0; i < answersQuiz.size() - 1; i++) {
                pw.print(answersQuiz.get(i) + ";");
            }

            pw.println(answersQuiz.get(answersQuiz.size() - 1));
            pw.println("Unfinished Quiz...Score: N/A");
            pw.println();
            pw.flush();

        }
        pw.close();

    }

    public String toString() {

        return "Name: " + firstName + " " + lastName + " Username: " + username + " Password: " + password;

    }

    //Check equals student objects
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Student))
            return false;

        Student student = (Student) o;

        return getName().equals(((Student) o).getName()) && getUsername().equals(((Student) o).getUsername());

    }

    public static void main(String[] args) {
        ArrayList<String> usersAndPass = getAllUsernamesAndPasswords();

    }

    //Finds a specific student from the arraylist
    public static Student findStudent(String username) {
        return students.stream().filter(student -> student.getUsername().equals(username)).findFirst().orElse
                (null);
    }

}