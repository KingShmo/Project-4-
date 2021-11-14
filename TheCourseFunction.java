import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TheCourseFunction
 * <p>
 * Runs a course feature in a learning management system.
 *
 * @author Anushka Nilangekar
 * @version November 14, 2021
 */


public class TheCourseFunction {

    public static void main() throws InvalidCourseException, InvalidQuizException, FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        String answer;
        CourseArchive courseArchive = new CourseArchive();


        do {
            System.out.println("Select the action you want:");
            System.out.println("1. Create a course");
            System.out.println("2. Use quiz options for a course");
            System.out.println("3. Delete a course");
            System.out.println("4. Exit");
            String temp = "Select the action you want:\n1. Create a course\n2. Use quiz options for the course\n3. Delete a course\n4. Exit";
            String[] options = {"1", "2", "3", "4"};
            answer = inputChecker(scanner, options, temp, "Invalid input.");

            if (answer.equals("1")) {
                System.out.println("What's the course's title?");
                answer = scanner.nextLine();
                System.out.println("What's the full name of the course's assigned teacher?");
                String teacherName = scanner.nextLine();
                var allTeachers = Teacher.getTeachers();
                Teacher teacher = null;
                for (int i = 0; i < allTeachers.size(); i++) {
                    if (allTeachers.get(i).getName().equals(teacherName)) {
                        teacher = allTeachers.get(i);
                        break;
                    }
                }
                System.out.println("What's the course's enrollment capacity?");
                int enrollmentCapacity = scanner.nextInt();
                QuizArchive quizArchive = new QuizArchive();
                creatingACourse(scanner, answer, courseArchive, quizArchive, teacher, enrollmentCapacity);
                System.out.println("Course created!");
            } else if (answer.equals("2")) {
                System.out.println("What's the course's title?");
                answer = scanner.nextLine();
                ArrayList<Course> courses = courseArchive.getCourses();
                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).getName().equals(answer)) {
                        Course course = courses.get(i);
                        course.callTheQuizFunction();
                        break;
                    }
                }

            } else if (answer.equals("3")) {
                System.out.println("What's the course's title?");
                answer = scanner.nextLine();
                courseArchive.deleteACourse(answer);

            } else if (answer.equals("4"))
                break;

        } while (true);

        System.out.println("Thank you for using our course portal!");

    }

    public static void creatingACourse(Scanner scanner, String answer, CourseArchive courseArchive, QuizArchive quizArchive
            , Teacher teacher, int enrollmentCapacity) throws InvalidCourseException, InvalidQuizException {
        for (int i = 0; i < courseArchive.getCourses().size(); i++) {
            if (courseArchive.getCourses().get(i).getName().equals(answer))
                return;
        }

        Course course = new Course(answer, teacher, enrollmentCapacity);
        courseArchive.addCourses(course);

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
