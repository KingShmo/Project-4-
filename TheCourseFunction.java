import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TheCourseFunction
 * <p>
 * Runs a course feature in a learning management system.
 *
 * @author Anushka Nilangekar
 * @version November 15, 2021
 */


public class TheCourseFunction {

    public static void main() throws InvalidCourseException, InvalidQuizException, FileNotFoundException {
        Scanner scanner = new Scanner(System.in);


    }
//menu that only the teacher sees.
    public static boolean courseFunctionMenu(Scanner scanner) throws InvalidCourseException, InvalidQuizException, FileNotFoundException {
        String answer;
        String answer2;
        CourseArchive courseArchive = new CourseArchive();

        do {
            System.out.println("Select the action you want:");
            System.out.println("1. Create a course");
            System.out.println("2. Use quiz options for a course");
            System.out.println("3. Add students to a course");
            System.out.println("4. Modify course attributes-Course teacher/Course enrollment/Course name");
            System.out.println("5. Delete a course");
            System.out.println("6. Exit");
            String temp = "Select the action you want:\n1. Create a course\n2. Use quiz options for the course\n3. Add students to a course\n4. Modify course attributes-Course teacher/Course enrollment/Course name\n5. Delete a course\n6. Exit";
            String[] options = {"1", "2", "3", "4", "5", "6"};
            answer = inputChecker(scanner, options, temp, "Invalid input.");

            if (answer.equals("1")) {
                System.out.println("What's the course's title?");
                answer = scanner.nextLine();

                ArrayList<Course> courses = courseArchive.getCourses();
                boolean courseExists = false;
                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).getName().equals(answer)) {
                        courseExists = true;
                        break;
                    }
                }
                if (courseExists) {
                    System.out.println("This course already exists!\n");
                    break;
                }

                System.out.println("What's the full name of the course's assigned teacher?");
                String teacherName = scanner.nextLine();
                var allTeachers = Teacher.getTeachers();
                Teacher teacher = null;
                boolean found = false;
                if (allTeachers.size() == 0) {
                    System.out.println("No teachers available to be assigned! Please make sure teachers are available and then try again.\n");
                    break;
                } else {
                    for (int i = 0; i < allTeachers.size(); i++) {
                        if (allTeachers.get(i).getName().equals(teacherName)) {
                            found = true;
                            teacher = allTeachers.get(i);
                            break;
                        }
                    }
                }
                if (!found) {
                    System.out.println("The teacher entered does not exist in the database!\n");
                    break;
                }

                System.out.println("What's the course's enrollment capacity?");
                int enrollmentCapacity = scanner.nextInt();
                QuizArchive quizArchive = new QuizArchive();
                creatingACourse(scanner, answer, courseArchive, quizArchive, teacher, enrollmentCapacity);
                System.out.println("Course created!");

            } else if (answer.equals("2")) {
                boolean courseExists = false;
                System.out.println("What's the course's title?");
                answer = scanner.nextLine();

                ArrayList<Course> courses = courseArchive.getCourses();

                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).getName().equals(answer)) {
                        courseExists = true;
                        Course course = courses.get(i);
                        course.callTheQuizFunction();
                        break;
                    }
                }
                if (!courseExists) {
                    System.out.println("This course does not exist!\n");
                    break;
                }


            } else if (answer.equals("3")) {
                System.out.println("What's the title of the course to which you want to add the student?");
                answer = scanner.nextLine();
                Course course = null;
                ArrayList<Course> courses = courseArchive.getCourses();
                boolean courseExists = false;
                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).getName().equals(answer)) {
                        courseExists = true;
                        course = courses.get(i);
                        break;
                    }
                }
                if (!courseExists) {
                    System.out.println("This course does not exist!\n");
                    break;
                }

                System.out.println("What's the full name of the student you want to add?");
                String studentName = scanner.nextLine();
                var allStudents = Student.getStudents();
                Student student = null;
                if (allStudents.size() == 0) {
                    System.out.println("No students available to be added to course! Please make sure students are available and then try again.\n");
                    break;
                } else {
                    for (int i = 0; i < allStudents.size(); i++) {
                        if (allStudents.get(i).getName().equals(studentName)) {
                            student = allStudents.get(i);
                            break;
                        }
                    }
                }
                course.addAStudentToTheCourse(student);

            } else if (answer.equals("4")) {
                boolean courseExists = false;
                System.out.println("What's the title of the course whose details you want to modify?");
                answer = scanner.nextLine();
                Course course = null;
                ArrayList<Course> courses = courseArchive.getCourses();

                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).getName().equals(answer)) {
                        courseExists = true;
                        course = courses.get(i);
                        break;
                    }
                }
                if (!courseExists) {
                    System.out.println("This course does not exist!\n");
                    break;
                }

                System.out.println("Select the action you want:");
                System.out.println("1. Modify course name");
                System.out.println("2. Change course teacher");
                System.out.println("3. Modify course enrollment capacity");
                System.out.println("4. Exit");
                temp = "Select the action you want:\n1. Modify course name\n2. Change course teacher\n3. Modify course enrollment capacity\n4. Exit";
                String[] options2 = {"1", "2", "3", "4"};
                answer = inputChecker(scanner, options2, temp, "Invalid input.");

                if (answer.equals("1")) {
                    System.out.println("What's the new course title?");
                    answer2 = scanner.nextLine();
                    course.setName(answer2);
                    System.out.println("Course name modified!");
                } else if (answer.equals("2")) {
                    System.out.println("What's the new course teacher's full name?");
                    String teacherName = scanner.nextLine();
                    var allTeachers = Teacher.getTeachers();
                    Teacher teacher = null;
                    boolean found = false;
                    for (int i = 0; i < allTeachers.size(); i++) {
                        if (allTeachers.get(i).getName().equals(teacherName)) {
                            found = true;
                            teacher = allTeachers.get(i);
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("The teacher entered does not exist in the database!\n");
                        break;
                    }
                    course.setCourseTeacher(teacher);
                    System.out.println("Course teacher changed!");
                } else if (answer.equals("3")) {
                    System.out.println("What's the new course enrollment capacity?");
                    answer2 = scanner.nextLine();
                    course.setEnrollmentCapacity(Integer.parseInt(answer2));
                    System.out.println("Course enrollment capacity modified!");
                } else if (answer.equals("4")) {
                    break;
                }


            } else if (answer.equals("5")) {
                boolean courseExists = false;
                System.out.println("What's the course's title?");
                answer = scanner.nextLine();

                ArrayList<Course> courses = courseArchive.getCourses();

                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).getName().equals(answer)) {
                        courseExists = true;
                        courseArchive.deleteACourse(answer);
                        System.out.println("Course deleted!");
                        break;
                    }
                }
                if (!courseExists) {
                    System.out.println("The course to be deleted does not exist!\n");
                    break;
                }

            } else if (answer.equals("6"))
                return false;
        } while (true);
        return true;
    }
//method for a person that is logged in as a teacher to create their own course to store quizzes in which students access
    public static void creatingACourse(Scanner scanner, String answer, CourseArchive courseArchive, QuizArchive quizArchive
            , Teacher teacher, int enrollmentCapacity) throws InvalidCourseException, InvalidQuizException {

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
            } else {
                System.out.println(errorMessage);
                System.out.println(question);
            }


        } while (true);

    }


}
