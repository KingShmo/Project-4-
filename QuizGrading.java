import java.util.ArrayList;
import java.util.Scanner;
/**
 * QuizGrading
 * <p>
 * Runs methods for teacher that allows them to delete and view quiz and as well as assign points to each
 * individual question
 * in a learning management system.
 *
 * @author Anish Ketha
 * @version November 14, 2021
 */


public class QuizGrading {
    int option;

    //prints a quiz to the console
    public void viewQuiz(String firstName, String lastName, String course, String quizName,
                         ArrayList<Character> answersQuiz, int grade, QuizArchive q) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Quiz> Quizzes = q.getQuizzes();
        int loop = 0;
        do {
            loop = 0;
            System.out.println("Which quiz do you want to view");
            int viewDigit = scanner.nextInt();
            Quizzes.get(viewDigit);
            System.out.println("Name: " + firstName + " " + lastName);
            System.out.println("Answers for " + course + " quiz: " + quizName);
            for (int i = 0; i < answersQuiz.size() - 1; i++) {
                System.out.println(answersQuiz.get(i) + ";");
            }
            System.out.println(answersQuiz.get(answersQuiz.size() - 1));
            System.out.println("Score: " + grade + "/100");
            System.out.println("Do you want to view another quiz?");
            System.out.println("1. Yes\n" +
                    "2. No\n");
            option = scanner.nextInt();
            do {
                if (option == 1) {
                    loop = 1;
                } else if (option == 2) {
                    break;
                } else {
                    System.out.println("Invalid Input!");
                }
            } while (option != 1 && option != 2);
        } while (loop == 1);
    }

    //deletes a quiz
    public void deleteQuiz(QuizArchive q, String nameOfQuiz) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Quiz> allQuizzes = q.getQuizzes();
        int loop = 0;
        do {
            loop = 0;
            System.out.println("Which quiz do you want to delete?");
            int deleteDigit = scanner.nextInt();
            if (allQuizzes.get(deleteDigit).getName().equals(nameOfQuiz)) {
                allQuizzes.remove(deleteDigit);
                System.out.println("Quiz Removed!");
            } else {
                System.out.println("Quiz does not exist!");
            }
            System.out.println("Do you want to remove another quiz?");
            System.out.println("1. Yes\n" +
                    "2. No\n");
            option = scanner.nextInt();
            do {
                if (option == 1) {
                    loop = 1;
                } else if (option == 2) {
                    break;
                } else {
                    System.out.println("Invalid Input!");
                }
            } while (option != 1 && option != 2);
        } while (loop == 1);
    }
}