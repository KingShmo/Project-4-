import java.util.Scanner;

/**
 * TheQuizFunction
 *
 * Runs a quiz feature in a learning management system.
 *
 * @author Zuhair Almansouri, lab sec L16
 *
 * @version November 8, 2021
 *
 */
public class TheQuizFunction {

    public static void main(String[] args) throws InvalidQuizException {

        Scanner scanner = new Scanner(System.in);
        String answer;
        QuizArchive quizArchive;

        do {

            System.out.println("Do you want to add a quiz?");
            String[] standardChoices = {"Yes", "yes", "No", "no"};
            answer = inputChecker(scanner, standardChoices, "Do you want to add a quiz?", "Invalid input.");

            if (answer.equals("No") || answer.equals("no"))
                break;
            else {

                System.out.println("How many numbers of questions?");
                String[] questionNumberChoices = new String[121];
                for (int i=0; i<questionNumberChoices.length; i++) {
                    questionNumberChoices[i] = "" + i;
                }
                answer = inputChecker(scanner, questionNumberChoices, "How many numbers of questions?",
                        "Number of questions should be no more than 120.");

                if (Integer.valueOf(answer) == 0) {

                    System.out.println("What's the quiz's title?");
                    answer = scanner.nextLine();
                    Quiz q1 = new Quiz(answer);
                    quizArchive = new QuizArchive(q1);

                    System.out.println("Do you want to add questions?");
                    answer = inputChecker(scanner, standardChoices, "Do you want to add questions?", "Invalid input.");

                    if (answer.equals("No") || answer.equals("no")) {
                        System.out.println("An empty quiz was created.");
                        break;
                    }
                    else {

                        System.out.println("Type STOP to stop adding questions.");

                        int numOfQuestions = 0;

                        while (true) {

                            System.out.println("Question " + (++numOfQuestions) + ":");
                            answer = scanner.nextLine();

                            if (answer.equals("STOP") || answer.equals("Stop") || answer.equals("stop"))
                                break;

                            String question = answer;

                            String[] options = new String[4];
                            for (int i = 0; i < options.length; i++) {

                                System.out.println("Option " + (i+1) + ":");
                                options[i] = (i+1) + ". " + scanner.nextLine() + "\n";
                            }

                            q1.addOneQuestion(question, options);

                        }
                    }

                } else {

                    int numOfQuestions = Integer.valueOf(answer);

                    System.out.println("What's the quiz'z title?");
                    answer = scanner.nextLine();

                    Quiz q1 = new Quiz(answer, Integer.valueOf(answer));
                    quizArchive = new QuizArchive(q1);



                    for (int i = 0; i < numOfQuestions; i++) {

                        String[] options = new String[4];

                        System.out.println("Question " + (i+1) + ":");
                        String question = scanner.nextLine();

                        for (int j = 0; j < options.length; j++) {

                            System.out.println("Option " + (i+1) + ":");
                            options[i] = (i+1) + ". " + scanner.nextLine() + "\n";

                        }

                        q1.addOneQuestion(question, options);
                    }

                }


                break;







            }

        } while (true);

        System.out.println("Thank you for using our quiz portal!");

    }

    public static String inputChecker(Scanner scanner, String[] choices, String question, String errorMessage) {

        do {

            String input = scanner.nextLine();

            if (input != null) {
                for (int i = 0; i < choices.length; i++) {
                    if (input.equals(choices[i]))
                        return input;
                }
            }
            System.out.println(errorMessage);
            System.out.println(question);

        } while (true);

    }


}
