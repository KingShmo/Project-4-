import java.io.*;
import java.util.ArrayList;

public class Student {

    public static void createAccount(String firstName, String lastName, String username, String password)
            throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream("StudentsAccounts.txt", true);
        StringBuilder courses = new StringBuilder();
        PrintWriter pw = new PrintWriter(fos);
        pw.println("Name: " + firstName + " " + lastName);
        pw.println("Username: " + username);
        pw.println("Password: " + password);
        pw.println();
        pw.flush();
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
        createAccount("Whats up", "Yall", "hey", "cool");
        writeUnfinishedQuizAnswersToFile("Troy", "Tamura", courseName, "Strings", unfinishedAnswers);
        writeFinishedQuizAnswersToFile("Troy", "Tamura", courseName, "test", unfinishedAnswers, 90);
        writeUnfinishedQuizAnswersToFile("Hello", "World", courseName, "Hello", unfinishedAnswers);
    }
}

//student answers are in an arraylist