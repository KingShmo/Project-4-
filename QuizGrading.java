import java.text.SimpleDateFormat;
import java.util.Scanner;

public class QuizGrading {
    int option;
    boolean isTeacher = false;

    public static void main(String[] args) {

    }

    public boolean equals(Object object) {
        if (object instanceof Teacher) {
            isTeacher = true;
        }
        return false;
    }

    public void editQuiz() {
    }

    public void viewQuiz() {
        Scanner scanner = new Scanner(System.in);
        int loop = 0;
        do {
            loop = 0;
            System.out.println("Which quiz do you want to edit");
            int deleteDigit = scanner.nextInt();
            quizzes.remove(deleteDigit);
            System.out.println("Quiz Removed!");
            System.out.println("Do you want to edit another quiz?");
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

    public void deleteQuiz() {
        Scanner scanner = new Scanner(System.in);
        int loop = 0;
        do {
            loop = 0;
            System.out.println("Which quiz do you want to delete?");
            int deleteDigit = scanner.nextInt();
            quizzes.remove(deleteDigit);
            System.out.println("Quiz Removed!");
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

