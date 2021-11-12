import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Student {

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

    public static ArrayList<String> getAllUsernames(){
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
            }
        }
        return allUsernames;
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
                    if (fileContents.contains(oldPassword)) {
                        fileContents = fileContents.replace(oldPassword, newPassword);
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
                System.out.println(splitContents[i]);
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

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer> courses = new ArrayList<>();
        ArrayList<Character> unfinishedAnswers = new ArrayList<>();
        ArrayList<Character> finishedAnswers = new ArrayList<>();
        String courseName = "CS180";
        for (int i = 0; i < 10; i++) {
            courses.add(i * 3);
        }
        char a = 67;
        for (int i = 0; i < 9; i++) {
            unfinishedAnswers.add(a);
            a += 2;
        }
        char b = 92;
        for (int i = 0; i < 9; i++) {
            finishedAnswers.add(b);
            b += 2;
        }
        /**
        createAccount("First", "Last", "hey", "cool");
        writeUnfinishedQuizAnswersToFile("Troy", "Tamura", courseName, "Strings", unfinishedAnswers);
        writeFinishedQuizAnswersToFile("Troy", "Tamura", courseName, "test", unfinishedAnswers, 90);
        writeUnfinishedQuizAnswersToFile("Hello", "World", courseName, "Hello", unfinishedAnswers);
        //createAccount("Whats up", "Yall", "hey", "cool");
        */
        createAccount("Artemii", "IS-Cool", "hey", "nice");
        //System.out.println(changeUsername("i'm", "bruh"));
        //System.out.println(changePassword("bruh", "dope", "cool"));
        ArrayList<String> users = getAllUsernames();
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
        }
    }
}

//student answers are in an arraylist

//need to get list of usernames to make sure that they aren't the same
