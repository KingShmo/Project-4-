import java.io.*;
import java.util.ArrayList;

/**
 * Student class
 *
 * Represents a student. Assigns name and account information.
 * Writes and reads from/to txt files.
 *
 * @author Troy, Artemii, Anushka
 *
 *
 * @version November 15, 2021
 *
 */
public class Student {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    static ArrayList<Student> students = Application.students;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        students = new ArrayList<>();
        addAStudent(this);
    }

    public Student(String firstName, String lastName, String username, String password) {
        this(firstName, lastName);
        this.username = username;
        this.password = password;
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }


    public void addAStudent(Student student) {
        students.add(student);
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

    public static void createAccount(String firstName, String lastName, String username, String password)
            throws FileNotFoundException {
        //write to StudentAccounts.txt 
        FileOutputStream fos = new FileOutputStream("StudentAccounts.txt", true);
        //create new print writer
        PrintWriter pw = new PrintWriter(fos);
        //write the name, username, and password of each account
        pw.println("Name: " + firstName + " " + lastName);
        pw.println("Username: " + username);
        pw.println("Password: " + password);
        pw.flush();
    }

    public static String deleteAccount(String username) throws FileNotFoundException {
        //
        int deleteAcc = 0;
        //create string buffer 
        StringBuffer buffer = new StringBuffer();
        //read the lines within the file 
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //add the lines to a String Buffer
                buffer.append(line + " \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //convert the String buffer to a String
        String fileContents = buffer.toString();
        //if file empty, return
        if (buffer.isEmpty()) {
            return "No student accounts have been created.";
        } else {
            for (int i = 0; i < fileContents.length(); i++) {
                //if there is a username that is found that is the same, change username to delete account
                if (fileContents.contains(username)) {
                    fileContents = fileContents.replace("Username: " + username, "Username: deleteAccount");
                    deleteAcc = 1;
                }
            }
            //if no username found when trying to delete account, then username that was input was wrong
            if (deleteAcc != 1) {
                return "Please input correct username";
            }
            //split the fileContents string into an array by the "\n" which separateds different accounts
            String[] splitContents = fileContents.split("\n");
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (int i = 0; i < splitContents.length; i++) {
                //add contents of array to new arraylist so the arraylist size can be changed
                stringArrayList.add(splitContents[i]);
            }
            for (int i = 0; i < stringArrayList.size(); i++) {
                if (stringArrayList.get(i).contains("Username: deleteAccount")) {
                    //delete the information from that account
                    stringArrayList.remove(i + 2);
                    stringArrayList.remove(i + 1);
                    stringArrayList.remove(i);
                    stringArrayList.remove(i - 1);
                }
            }
            FileOutputStream fos = new FileOutputStream("StudentAccounts.txt", false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < stringArrayList.size(); i++) {
                //write the new array list to the file 
                pw.println(stringArrayList.get(i));
            }
            pw.flush();
        }
        return "Your account has been deleted!";
    }

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

    public static String changeUsername(String oldUsername, String newUsername) throws FileNotFoundException {
        int usernameExist = 0;
        //create string buffer to add lines from file
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //add lines to the buffer
                buffer.append(line + " \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //set the string buffer to a string 
        String fileContents = buffer.toString();
        if (buffer.isEmpty()) {
            return "No student accounts have been created.";
        } else {
            for (int i = 0; i < fileContents.length(); i++) {
                if (fileContents.contains(oldUsername)) {
                    //replace the old username with the new username in the file 
                    fileContents = fileContents.replace(oldUsername, newUsername);
                    usernameExist = 1;
                }
            }
            if (usernameExist != 1) {
                return "Your current username does not exist.";
            }
            //split the contents by the "\n" that separates different accounts 
            String[] splitContents = fileContents.split("\n");
            FileOutputStream fos = new FileOutputStream("StudentAccounts.txt", false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < splitContents.length; i++) {
                //rewrite all the new accounts 
                pw.println(splitContents[i]);
            }
            pw.flush();
        }
        return "Your username has been changed!";
    }

    public static String changePassword(String username, String oldPassword, String newPassword) throws FileNotFoundException {
        //will be updated to show if username exists
        int usernameExist = 0;
        //updated to show if old password is correct
        int oldPasswordExist = 0;
        //create string buffer
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //add line to string buffer
                buffer.append(line + " \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContents = buffer.toString();
        //if the txt file was empty
        if (buffer.isEmpty()) {
            return "No student accounts have been created.";
        } else {
            for (int i = 0; i < fileContents.length(); i++) {
                if (fileContents.contains(username)) {
                    //show that username exists and if it exists try look for old password
                    usernameExist = 1;
                    if (fileContents.contains("Password: " + oldPassword)) {
                        //now, if the old password exists, replace it with the new password
                        fileContents = fileContents.replace("Password: " + oldPassword, "Password: " + newPassword);
                        //show that the old password exists
                        oldPasswordExist = 1;
                    }
                }
            }
            //if old password not found
            if (oldPasswordExist != 1) {
                return "This is not your current password. Please enter your current password correctly.";
            }
            //if username is not found
            if (usernameExist != 1) {
                return "Your current username does not exist.";
            }
            String[] splitContents = fileContents.split("\n");
            FileOutputStream fos = new FileOutputStream("StudentAccounts.txt", false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < splitContents.length; i++) {
                //write the updated contents into a txt file 
                pw.println(splitContents[i]);
            }
            pw.flush();
        }
        return "Your password has been changed!";
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
     * @param students = store students
     * @throws IOException = when an error occurs while reading
     */
    public static void readStudents(ArrayList<Student> students) throws IOException {

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

            students.add(student);

            line = br.readLine();

        }


    }

    /**
     * read teachers from a file
     * @param teachers = store the teachers
     * @throws IOException = when an error occurs while reading
     */
    public static void readTeachers(ArrayList<Teacher> teachers) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("TeacherAccounts.txt"));

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
     * Write finished answers to a file
     * @param firstName = student first name
     * @param lastName = student last name
     * @param course = their assigned course
     * @param quizArchive = retrieve the quizzes
     * @throws FileNotFoundException = thrown when the file is not found
     */
    public static void writeFinishedQuizAnswersToFile(String firstName, String lastName, String course, QuizArchive quizArchive)
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
     * @param firstName = student first name
     * @param lastName = student last name
     * @param course = their assigned course
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


    public static void main(String[] args) {
        ArrayList<String> usersAndPass = getAllUsernamesAndPasswords();
        for (int i = 0; i < usersAndPass.size(); i++) {
            System.out.println(usersAndPass.get(i));
        }
    }

}
