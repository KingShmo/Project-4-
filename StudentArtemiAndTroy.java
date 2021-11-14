import java.io.*;
import java.util.ArrayList;

/*
This class contains all variables and methods that each student has
*/
public class StudentArtemiAndTroy {

    private static String firstName;
    private static String lastName;
    private static String username;
    private static String password;

    public StudentArtemiAndTroy(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() { //Returns the first name of a student
        return firstName;
    }

    public String getLastName() { //Returns the last name of a student
        return lastName;
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
        FileOutputStream fos = new FileOutputStream("StudentAccounts.txt", true);
        StringBuilder courses = new StringBuilder();
        PrintWriter pw = new PrintWriter(fos);
        pw.println("Name: " + firstName + " " + lastName);
        pw.println("Username: " + username);
        pw.println("Password: " + password);
        pw.println();
        pw.flush();
    }

    public static String deleteAccount(String username) throws FileNotFoundException {
        int deleteAcc = 0;
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + " \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContents = buffer.toString();
        if (buffer.isEmpty()) {
            return "No student accounts have been created.";
        } else {
            for (int i = 0; i < fileContents.length(); i++) {
                if (fileContents.contains(username)) {
                    fileContents = fileContents.replace("Username: " + username, "Username: deleteAccount");
                    deleteAcc = 1;
                }
            }
            if (deleteAcc != 1) {
                return "Please input correct password";
            }
            String [] splitContents = fileContents.split("\n");
            ArrayList<String> stringArrayList = new ArrayList<>();
            for (int i = 0; i < splitContents.length; i++) {
                stringArrayList.add(splitContents[i]);
            }
            for (int i = 0; i < stringArrayList.size(); i++) {
                if (stringArrayList.get(i).contains("Username: deleteAccount")) {
                    stringArrayList.remove(i+2);
                    stringArrayList.remove(i+1);
                    stringArrayList.remove(i);
                    stringArrayList.remove(i-1);
                }
            }
            FileOutputStream fos = new FileOutputStream("StudentAccounts.txt", false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < stringArrayList.size(); i++) {
                pw.println(stringArrayList.get(i));
            }
            pw.flush();
        }
        return "Your account has been deleted!";
    }

    public static ArrayList<String> getAllUsernamesAndPasswords(){
        ArrayList<String> fileContents = new ArrayList<>();
        ArrayList<String> allUsernames = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
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
                if (fileContents.get(i).equals("Username: " + inputUsername)) {
                    return fileContents.get(i + 1).substring(10);
                }
            }
        }
        return null;
    }

    public static String changeUsername(String oldUsername, String newUsername) throws FileNotFoundException {
        int usernameExist = 0;
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + " \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContents = buffer.toString();
        if (buffer.isEmpty()) {
            return "No student accounts have been created.";
        } else {
            for (int i = 0; i < fileContents.length(); i++) {
                if (fileContents.contains(oldUsername)) {
                    fileContents = fileContents.replace(oldUsername, newUsername);
                    usernameExist = 1;
                }
            }
            if (usernameExist != 1) {
                return "Your current username does not exist.";
            }
            String [] splitContents = fileContents.split("\n");
            FileOutputStream fos = new FileOutputStream("StudentAccounts.txt", false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < splitContents.length; i++) {
                pw.println(splitContents[i]);
            }
            pw.flush();
        }
        return "Your username has been changed!";
    }

    public static String changePassword(String username, String oldPassword, String newPassword) throws FileNotFoundException {
        int usernameExist = 0;
        int oldPasswordExist = 0;
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("StudentAccounts.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + " \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContents = buffer.toString();
        if (buffer.isEmpty()) {
            return "No student accounts have been created.";
        } else {
            for (int i = 0; i < fileContents.length(); i++) {
                if (fileContents.contains(username)) {
                    usernameExist = 1;
                    if (fileContents.contains("Password: " + oldPassword)) {
                        fileContents = fileContents.replace("Password: " + oldPassword, "Password: " + newPassword);
                        oldPasswordExist = 1;
                    }
                }
            }
            if (oldPasswordExist != 1) {
                return "This is not your current password. Please enter your current password correctly.";
            }
            if (usernameExist != 1) {
                return "Your current username does not exist.";
            }
            String [] splitContents = fileContents.split("\n");
            FileOutputStream fos = new FileOutputStream("StudentAccounts.txt", false);
            PrintWriter pw = new PrintWriter(fos);
            for (int i = 0; i < splitContents.length; i++) {
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
        StringBuilder courses = new StringBuilder();
        PrintWriter pw = new PrintWriter(fos);
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

    //if user completes quiz and does not have any errors
    public static void writeFinishedQuizAnswersToFile(String firstName, String lastName, String course, String quizName,
                                                      ArrayList<Character> answersQuiz, int grade)
            throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream("StudentQuizzes.txt", true);
        StringBuilder courses = new StringBuilder();
        PrintWriter pw = new PrintWriter(fos);
        pw.println("Name: " + firstName + " " + lastName);
        pw.println("Answers for " + course + " quiz: " + quizName);
        for (int i = 0; i < answersQuiz.size() - 1; i++) {
            pw.print(answersQuiz.get(i) + ";");
        }
        pw.println(answersQuiz.get(answersQuiz.size() - 1));
        pw.println("Score: " + grade + "/100");
        pw.println();
        pw.flush();
    }

    //Start of Zuhair's modification
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

        quizArchive.addQuizes(quiz);

    }

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


                bw.write("Question " + (j+1) + ":" + correctAnswers.get(j) + " ");

            }

        }

        bw.close();


    }

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