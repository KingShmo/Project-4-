import javax.xml.stream.events.Characters;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Student class
 * <p>
 * A class for student that has menus that only students see and has methods related to Quiz such as taking one,
 * viewing the grades for a specifics quiz, accessing courses, etc.
 *
 * @author Anish Ketha
 * @version November 15, 2021
 * All code written by me unless stated
 */

public class StudentAnish {
    int option;
    private static String firstName;
    private static String lastName;
    private static String username;
    private static String password;
    public ArrayList<Student> allStudents = new ArrayList<Student>();

    public StudentAnish(String firstName, String lastName) {
        StudentAnish.firstName = firstName;
        StudentAnish.lastName = lastName;
    }


    public static void main(String username) throws InvalidCourseException, IOException {

        CourseArchive listOfCourses = new CourseArchive();

        Scanner scanner = new Scanner(System.in);
        String option;
        QuizArchive quizArchive = new QuizArchive();

        //Below is a menu for only students which loops and handles any exceptions

        String course;
        int secondaryLoop = 0;
        Course accessedCourses;

        //start

        ArrayList<Course> enrolledCourses = new ArrayList<>();

        for (int i = 0; i < CourseArchive.allCourses.size(); i++) {

            var students = CourseArchive.allCourses.get(i).getStudentsInThisCourse();

            for (int j = 0; j < students.size(); j++) {

                if (students.get(j).getUsername().equals(username))
                    enrolledCourses.add(CourseArchive.allCourses.get(i));
            }

        }

        while (true) {

            String menu = "Which course would you like to access?";

            System.out.println("Welcome! Which course would you like to access?");
            int z;

            for (z = 0; z < enrolledCourses.size(); z++) {

                menu += (z + 1) + ". " + enrolledCourses.get(z).getName();
                System.out.println((z + 1) + ". " + enrolledCourses.get(z).getName());

            }
            if (z == 0) {
                System.out.println("No enrolled courses.");
                return;
            }
            System.out.println((++z) + ". Exit");
            menu += z + ". Exit";

            String[] options = new String[enrolledCourses.size() + 1];
            for (int i = 0; i < options.length; i++)
                options[i] = "" + (i + 1);

            String answer = inputChecker(scanner, options, menu,
                    "Invalid input.");
            int choice = Integer.valueOf(answer);
            if (choice == z) {
                System.out.println("Thank you for using the student portal.");
                return;
            } else {

                choice--;

                Course chosenCourse = enrolledCourses.get(choice);



                do {

                    String prompt = "Select the action you want:\n1. Take a quiz\n2. View Grades\n" +
                            "3. Exit the course";
                    System.out.println(prompt);

                    answer = inputChecker(scanner, new String[]{"1", "2", "3"}, prompt, "Invalid input.");

                    if (answer.equals("1")) {

                        System.out.println("Choose a quiz:");

                        var allQuizzes = chosenCourse.getQuizzes();
                        if (allQuizzes.size() == 0) {
                            System.out.println("There are no quizzes.");
                            continue;
                        }
                        String[] checkInput = new String[allQuizzes.size()];

                        for (int i = 0; i < allQuizzes.size(); i++) {
                            System.out.println((i + 1) + ". " + allQuizzes.get(i).getName());
                            checkInput[i] = "" + (i + 1);
                        }

                        answer = inputChecker(scanner, checkInput, "Choose a quiz:", "Invalid input.");

                        Quiz chosenQuiz = allQuizzes.get(Integer.valueOf(answer) - 1);

                        if (chosenQuiz.isTaken()) {
                            System.out.println("Quiz already taken.");
                        } else if (chosenQuiz.getStudentAnswers() != null) {
                            System.out.println("Quiz already taken.");
                        } else {
                            if (chosenQuiz.getRandomize()) {
                                TheQuizFunction.randomizeQuestions(chosenQuiz.getName(), quizArchive);
                            }
                            String question = "Do you want to attach a file for this quiz? (yes/no)";
                            System.out.println(question);
                            answer = inputChecker(scanner, new String[]{"Yes", "yes", "No", "no"}, question,
                                    "Invalid input.");
                            if (answer.equals("Yes") || answer.equals("yes")) {

                                int length = chosenQuiz.getQuestions().size();
                                System.out.println("The file should have " + length + " answers, following this format:");
                                System.out.println("[answerForQuestion1],[answerForQuestion2],3,4");

                                System.out.println("File path?");
                                answer = scanner.nextLine();

                                String response = readAttachedFile(answer, chosenQuiz, quizArchive);
                                if (response.equals("File was not found.")) {
                                    System.out.println("File was not found. Therefore, the quiz will start.");
                                    startAQuiz(scanner, chosenQuiz.getName(), quizArchive);
                                } else
                                    System.out.println(response);


                            } else
                                startAQuiz(scanner, chosenQuiz.getName(), quizArchive);
                        }


                    } else if (answer.equals("2")) {

                        for (int i = 0; i < enrolledCourses.size(); i++) {

                            Course c = enrolledCourses.get(i);
                            var quizzes = c.getQuizzes();

                            for (int j = 0; j < quizzes.size(); j++) {
                                System.out.println("Quiz name: " + quizzes.get(j).getName());
                                System.out.println("Raw score: " + quizzes.get(j).getRawScore());
                                System.out.println("Score with point values: " + quizzes.get(j).getModifiedScore());
                                System.out.println("Timestamp: " + quizzes.get(j).getTimeStamp());

                            }

                        }


                    } else
                        break;
                } while (true);
            }
        }
        //Finish
        /*do {
            secondaryLoop = 0;
            boolean check;
            do {
                check = false;
                for (int i = 0; i < CourseArchive.allCourses.size(); i++) {
                    if (CourseArchive.allCourses.size() == 0) {
                        System.out.println("There are no courses available!");
                        secondaryLoop = 1;
                    } else {
                        System.out.println((i + 1) + ". " + CourseArchive.allCourses.get(i).getName());
                    }
                }
                System.out.println(CourseArchive.allCourses.size() + 1 + ". Exit");

                String[] options = new String[CourseArchive.allCourses.size() + 1];
                for (int i = 0; i < options.length; i++)
                    options[i] = "" + (i + 1);
                course = inputChecker(scanner, options, "Which course would you like to access?",
                                             "Invalid input.");


                if (Integer.valueOf(course) == (listOfCourses.getCourses().size() + 1)) {
                    System.out.println("Thank you for using the student portal!");
                    return;
                } else if (Integer.valueOf(course) <= 1 || Integer.valueOf(course) > CourseArchive.allCourses.size() + 1) {
                    System.out.println("Invalid input! Please try again!");
                    check = true;
                }
            } while (check);
            int initialLoop;


            for (int i = 0; i < CourseArchive.allCourses.size(); i++) {
                if (CourseArchive.allCourses.get(i).getName().equals(course)) {
                    accessedCourse = CourseArchive.allCourses.get(i);
                    break;
                }
            }



            do {
                initialLoop = 0;
                System.out.println("Select the action you want:\n1. Take a quiz\n2. View Grades\n" +
                        "3. Exit the course");
                option = scanner.nextLine();
                if (option.equals("1")) {
                    if (quizArchive.getQuizzes().size() == 0) {
                        System.out.println("There are no available quizzes for this course!");
                        initialLoop = 1;
                    } else {
                        String title;
                        System.out.println("What is the name of the quiz?");
                        title = scanner.nextLine();
                        startAQuiz(scanner, title, quizArchive);
                        break;
                    }
                } else if (option.equals("2")) {
                    int loop = 0;
                    do {
                        loop = 0;
                        if (quizArchive.getQuizzes().size() == 0) {
                            System.out.println("There are no available Quiz Grades for this course!");
                            initialLoop = 1;
                        } else {
                            System.out.println("Which quiz do you want to view");
                            QuizArchive q = new QuizArchive();
                            ArrayList<Quiz> Quizzes = q.getQuizzes();
                            for (int i = 0; i < CourseArchive.allCourses.size(); i++) {
                                for (int j = 0; j < quizArchive.getQuizzes().size(); j++) {
                                    System.out.println(Quizzes.get(i).getName());
                                }
                            }
                            String quizName = "";
                            quizName = scanner.nextLine();
                            for (int i = 0; i < quizArchive.getQuizzes().size(); i++) {
                                if (Quizzes.get(i).getName().equals(quizName)) {
                                    System.out.println("Answers for " + course + " quiz: " + quizName);
                                    System.out.println("Questions Correct: " + Quizzes.get(i).getScore());
                                    System.out.println("Quiz Grade : " +
                                            Quizzes.get(i).getModifiedScore(assignPointValues(Quizzes.get(i), scanner),Quizzes.get(i)));
                                    //Check the line above
                                    //static method cannot be called from an object, only associated with the name of
                                    //the class
                                }
                            }
                            System.out.println("Do you want to view another quiz?");
                            System.out.println("1. Yes\n" +
                                    "2. No\n");
                            int studentOption;
                            do {
                                studentOption = scanner.nextInt();
                                if (studentOption == 1) {
                                    loop = 1;
                                } else if (studentOption == 2) {
                                    initialLoop = 1;
                                } else {
                                    System.out.println("Invalid Input!");
                                }
                            } while (studentOption != 1 && studentOption != 2);
                        }
                    } while (loop == 1);
                } else if (option.equals("3")) {
                    initialLoop = 2;
                } else {
                    System.out.println("Invalid option! Please try again!");
                    initialLoop = 1;
                }
            } while (initialLoop == 1);
        } while (secondaryLoop == 1);*/
    }

    //method that checks for input in a multiple choice question to make it more streamlined and efficient

    //Zuhair's method to read file imports
    public static String readAttachedFile(String path, Quiz quiz, QuizArchive quizArchive) {

        try (BufferedReader br = new BufferedReader(new FileReader(path))){

            String line = br.readLine();

            int[] answers = new int[quiz.getQuestions().size()];

            if (line == null)
                return "File was not found.";

            int i = 0;
            if (line.indexOf(",") == -1)
                answers[i] = Integer.valueOf(line);
            else {
                while (true) {

                    if (i + 1 > quiz.getQuestions().size())
                        return "File was not found.";

                    if (line.indexOf(",") == -1)
                        break;

                    String oneAnswer = line.substring(0, line.indexOf(","));
                    line = line.substring(line.indexOf(",") + 1);
                    answers[i++] = Integer.valueOf(oneAnswer);

                }

            }
            ArrayList<Integer> studentAnswers = new ArrayList<>();

            for (int j = 0; j < answers.length; j++) {
                studentAnswers.add(answers[j]);
            }
            quiz.setStudentAnswers(studentAnswers);

            String rawScore = getScore(quiz);
            String modifiedScore = getModifiedScore(quiz.getPointValues(), quiz);

            SimpleDateFormat yearMonthDaySpaceHoursMinutesSeconds =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String takeTimeStamp = yearMonthDaySpaceHoursMinutesSeconds.format(timestamp);



            writeScores(quiz, takeTimeStamp);
            PrintInformation.writeQuizQuestions(quizArchive);

            return "File attached!";


        } catch (FileNotFoundException e) {
            return "File was not found.";
        } catch (IOException e) {
            return "File was written in a wrong format.";
        }


    }

    //Zuhair's method to write student scores
    public static void writeScores(Quiz quiz, String timeStamp) {

        try(PrintWriter pw = new PrintWriter(new FileWriter("StudentQuizzes.txt", true))) {

            String rawScore = getScore(quiz);
            String modifiedScore = getModifiedScore(quiz.getPointValues(), quiz);

            quiz.setRawScore(rawScore);
            quiz.setModifiedScore(modifiedScore);
            quiz.setTimeStamp(timeStamp);
            pw.println(quiz.getName() + ";" + getScore(quiz) + ";" + getModifiedScore(quiz.getPointValues(), quiz)
                       + "," + timeStamp);

            for (int i = 0; i < quiz.getQuestions().size(); i++) {
                if (i + 1 == quiz.getStudentAnswers().size())
                    pw.print("" + quiz.getStudentAnswers().get(i) + ",");
            }
            quiz.toggleTaken();
            pw.println();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readStudentSubmissions(QuizArchive quizArchive) {

        var allQuizzes = quizArchive.getQuizzes();

        try(BufferedReader br = new BufferedReader(new FileReader("StudentQuizzes.txt"))) {

            String line = br.readLine();

            if (line == null)
                return;

            while (true) {

                String quizName = line.substring(0, line.indexOf(";"));
                line = line.substring(line.indexOf(";") + 1);
                String rawScore = line.substring(0, line.indexOf(";"));
                line = line.substring(line.indexOf(";") + 1);
                String modifiedScore = line.substring(0, line.indexOf(","));
                line = line.substring(line.indexOf(","));
                String timeStamp = line.substring(1);

                Quiz q = null;

                for (Quiz quiz : allQuizzes) {

                    if (quiz.getName().equals(quizName)) {

                        q = quiz;
                        quiz.setRawScore(rawScore);
                        quiz.setModifiedScore(modifiedScore);
                        quiz.setTimeStamp(timeStamp);
                        break;
                    }

                }

                line = br.readLine();


                ArrayList<Integer> studentAnswers = new ArrayList<>();



                while (true) {

                    if (line.indexOf(",") == -1)
                        break;

                    String oneAnswer = line.substring(0, line.indexOf(","));
                    line = line.substring(line.indexOf(",") + 1);
                    studentAnswers.add(Integer.valueOf(oneAnswer));

                }



                q.setStudentAnswers(studentAnswers);

                q.toggleTaken();

                line = br.readLine();

                if (line == null)
                    break;

            }


        } catch (IOException e) {
            e.printStackTrace();
        }



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
    //this is what the student uses if they want to see the grade for a quiz in a specific course
    public static void viewQuiz(Scanner scanner, String firstName, String lastName, String course, String quizName,
                                ArrayList<Character> answersQuiz, QuizArchive q, Quiz newStudent, int[] score) {
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
            System.out.println("Questions correct: " + StudentAnish.getScore(newStudent));
            System.out.println("Score: " + StudentAnish.getModifiedScore(score, newStudent));
            System.out.println("Do you want to view another quiz?");
            System.out.println("1. Yes\n" +
                    "2. No\n");
            int option;
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
    //method implemented when a student wants to take a quiz in a specific course
    public static void startAQuiz(Scanner scanner, String title, QuizArchive quizArchive) throws IOException {

        var quizzes = quizArchive.getQuizzes();
        boolean check = false;
        ArrayList<Integer> studentAnswers = new ArrayList<>();
        Quiz quiz = null;
        String answer;

        for (int i = 0; i < quizzes.size(); i++) {

            if (quizzes.get(i).getName().equals(title)) {



                check = true;

                quiz = quizzes.get(i);

                var questions = quiz.getQuestions();
                var correctAnswer = quiz.getCorrectAnswers();


                int questionNum = 1;

                for (int j = 0; j < questions.size(); j++) {

                    String wholeQuestion = questions.get(j);

                    String question = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option1 = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option2 = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option3 = wholeQuestion.substring(0, wholeQuestion.indexOf("^_^"));
                    wholeQuestion = wholeQuestion.substring(wholeQuestion.indexOf("^_^") + 3);

                    String option4 = wholeQuestion;

                    System.out.println((questionNum++) + question.substring(1) + ":");
                    System.out.print("1" + option1.substring(1) + "2" + option2.substring(1) + "3" + option3.substring(1) + "4" + option4.substring(1));

                    System.out.print("Your answer: ");
                    String[] options = {"1", "2", "3", "4"};
                    answer = inputChecker(scanner, options, "Your answer: ", "Answer should be from 1 to 4.");

                    studentAnswers.add(Integer.valueOf(answer));


                }

                SimpleDateFormat yearMonthDaySpaceHoursMinutesSeconds =
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String takeTimeStamp = yearMonthDaySpaceHoursMinutesSeconds.format(timestamp);
                System.out.println("Quiz completed: " + takeTimeStamp);

                quiz.setStudentAnswers(studentAnswers);

                writeScores(quiz, takeTimeStamp);
                PrintInformation.writeQuizQuestions(quizArchive);
                //Anish's code that prints a timestamp only when a student has COMPLETED a quiz which a student sees and which the teacher can access if needed.

            }

            if (check)
                break;

        }

        if (!check) {
            System.out.println("Couldn't start the quiz.");

        }

        if (quiz != null)
            quiz.setStudentAnswers(studentAnswers);

    }
    //method for student and teacher to recall their answers and view the point values for each question
    public static void getStudentAnswers(Quiz answers) {
        answers.getStudentAnswers();
    }

    //method implemented in Zuhair's class to allow a teacher to assign specific point values for each question when
    // assigning a quiz!
    public static int[] assignPointValues(Quiz temp, Scanner scanner) {

        if (temp.getQuestions().size() == 0) {
            System.out.println("The quiz has zero questions.");
            return null;
        }

        int[] pointValues = new int[temp.getSizeOfQuiz()];
        for (int i = 0; i < temp.getSizeOfQuiz(); i++) {
            System.out.println("How many points is question " + (i + 1) + " worth?");
            boolean check;
            int points = 0;
            do {
                check = false;
                try {
                    points = scanner.nextInt();
                    scanner.nextLine();

                } catch (Exception e) {
                    System.out.println("Enter a number.");
                    scanner.nextLine();
                    check = true;
                }
            } while (check);

            pointValues[i] = points;
        }
        return pointValues;
    }
    //this method shows the score the student got based on the point values assigned for each Question by the teacher
    public static String getModifiedScore(int[] pointValue, Quiz q) {

        int count = 0;

        if (q.getStudentAnswers() == null)
            return "Quiz was not taken.";

        for (int i = 0; i < q.getStudentAnswers().size(); i++) {

            if (q.getStudentAnswers().get(i) == q.getCorrectAnswers().get(i))
                count += pointValue[i];
        }
        int sum = 0;
        for (int i = 0; i < pointValue.length; i++) {
            sum = sum + pointValue[i];
        }

        return "" + count + "/" + sum;

    }
    //method for the teacher and student to get the number of questions correct out of total questions in a specific quiz in a specific course!
    public static String getScore(Quiz q) {

        int count = 0;
        if (q.getStudentAnswers() == null)
            return "Quiz was not taken.";
        for (int i = 0; i < q.getStudentAnswers().size(); i++) {


            if (q.getStudentAnswers().get(i) == q.getCorrectAnswers().get(i))
                count++;
        }

        return "" + count + "/" + q.getStudentAnswers().size();

    }

}