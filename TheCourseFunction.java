import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
    public static boolean courseFunctionMenu(String username, Scanner scanner) throws InvalidCourseException, InvalidQuizException,
            IOException {
        String answer;
        String answer2;
        CourseArchive courseArchive = new CourseArchive();

        do {
            System.out.println("Available Courses:");
            int x = 0;
            for (Course course : CourseArchive.allCourses) {
                System.out.println((++x) + ". " + course.getName());
            }

            if (x == 0)
                System.out.println("None.");

            System.out.println("Select the action you want:");
            System.out.println("1. Create a course");
            System.out.println("2. Use quiz options for a course");
            System.out.println("3. Add students to a course");
            System.out.println("4. Modify course attributes-Course teacher/Course enrollment/Course name");
            System.out.println("5. Delete a course");
            System.out.println("6. Exit");
            String temp = "Select the action you want:\n1. Create a course\n2. Use quiz options for the course\n3. " +
                    "Add students to a course\n4. Modify course attributes-Course teacher/Course enrollment/" +
                    "Course name\n5. Delete a course\n6. Exit";
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

                Teacher assignedTeacher = null;

                for (int i = 0; i < Teacher.teachers.size(); i++) {
                    if (Teacher.teachers.get(i).getUsername().equals(username)) {
                        assignedTeacher = Teacher.teachers.get(i);
                        break;
                    }
                }

                int enrollmentCapacity = 0;
                do {

                    System.out.println("What's the course's enrollment capacity?");
                    try {
                        enrollmentCapacity = scanner.nextInt();
                        scanner.nextLine();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input!");
                        scanner.nextLine();
                    }
                } while (true);

                System.out.println("Course created!");

                Course course = new Course(answer, assignedTeacher, enrollmentCapacity);
                CourseArchive courseArchive1 = new CourseArchive();
                courseArchive1.addCourses(course);

                Teacher.writeCourses(CourseArchive.allCourses);


            } else if (answer.equals("2")) {
                boolean courseExists = false;
                System.out.println("What's the course's title?");
                answer = scanner.nextLine();

                ArrayList<Course> courses = courseArchive.getCourses();

                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).getName().equals(answer)) {
                        courseExists = true;
                        Course course = courses.get(i);
                        if (!(courses.get(i).getCourseTeacher().getUsername().equals(username)))
                            System.out.println("This course belongs to another teacher.");
                        else
                            course.callTheQuizFunction(answer);
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
                Teacher tempTeacher = null;
                for (int i = 0; i < Teacher.teachers.size(); i++) {
                    Teacher teacherToBeCompared = Teacher.teachers.get(i);
                    if (teacherToBeCompared.getUsername().equals(username)) {
                        tempTeacher = Teacher.teachers.get(i);
                        break;
                    }

                }
                boolean notAssignedTeacher = true;
                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).getName().equals(answer)) {
                        courseExists = true;
                        if (courses.get(i).getCourseTeacher().getUsername().equals(username)) {
                            notAssignedTeacher = false;
                            course = courses.get(i);
                            break;
                        }
                    }
                }
                if (notAssignedTeacher) {
                    System.out.println("You're not the assigned teacher for this course!");
                    break;
                }
                if (!courseExists) {
                    System.out.println("This course does not exist!\n");
                    break;
                }


                var allStudents = Student.getStudents();
                if (allStudents.size() == 0) {
                    System.out.println("No students available to be added to course! Please make sure students are " +
                            "available and then try again.\n");
                    break;
                }
                System.out.println("Select the student:");

                for (int i = 0; i < allStudents.size(); i++) {
                    System.out.println((i + 1) + ". " + allStudents.get(i).getName());
                }

                String studentNumber;
                boolean check = false;
                do {
                    check = false;
                    studentNumber = scanner.nextLine();
                    try {
                        int num = Integer.parseInt(studentNumber);
                        if (!(num >= 1 && num <= allStudents.size()))
                            check = true;
                    } catch (NumberFormatException e) {
                        check = true;
                    }
                } while (check);

                Student student = allStudents.get(Integer.valueOf(studentNumber) - 1);

                boolean checkDuplicateStudents = true;

                for (int i = 0; i < CourseArchive.allCourses.size(); i++) {
                    Course tempCourse = CourseArchive.allCourses.get(i);
                    if (tempCourse.getName().equals(answer)) {

                        var students = tempCourse.getStudentsInThisCourse();

                        for (int j = 0; j < students.size(); j++) {
                            if (students.get(j).equals(student)) {

                                System.out.println("This student is already added to the course!");
                                checkDuplicateStudents = false;
                                break;
                            }
                        }
                    }
                }

                if (checkDuplicateStudents) {
                    course.addAStudentToTheCourse(student);
                    Teacher.writeCourses(CourseArchive.allCourses);
                    System.out.println("Student added!");
                }

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

                do {


                    System.out.println("Select the action you want:");
                    System.out.println("1. Modify course name");
                    System.out.println("2. Change course teacher");
                    System.out.println("3. Modify course enrollment capacity");
                    System.out.println("4. Exit");
                    temp = "Select the action you want:\n1. Modify course name\n2. Change course teacher\n3. " +
                            "Modify course enrollment capacity\n4. Exit";
                    String[] options2 = {"1", "2", "3", "4"};
                    String teacherAssignedCourse = answer;
                    answer = inputChecker(scanner, options2, temp, "Invalid input.");


                    if (answer.equals("1")) {
                        boolean checkAssignedTeacher = assignedTeacherChecker(courses, course.getName(), username);
                        if (checkAssignedTeacher) {
                            System.out.println("What's the new course title?");
                            answer2 = scanner.nextLine();
                            course.setName(answer2);
                            System.out.println("Course name modified!");
                            Teacher.writeCourses(CourseArchive.allCourses);
                        }
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
                        Teacher.writeCourses(CourseArchive.allCourses);
                    } else if (answer.equals("3")) {
                        boolean checkAssignedTeacher = assignedTeacherChecker(courses, course.getName(), username);
                        if (checkAssignedTeacher) {

                            System.out.println("What's the new course enrollment capacity?");
                            answer2 = scanner.nextLine();
                            course.setEnrollmentCapacity(Integer.parseInt(answer2));
                            System.out.println("Course enrollment capacity modified!");
                            Teacher.writeCourses(CourseArchive.allCourses);
                        }
                    } else if (answer.equals("4")) {
                        break;
                    }

                } while (true);


            } else if (answer.equals("5")) {
                boolean courseExists = false;
                System.out.println("What's the course's title?");
                answer = scanner.nextLine();

                ArrayList<Course> courses = courseArchive.getCourses();



                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).getName().equals(answer)) {
                        courseExists = true;

                        boolean checkAssignedTeacher = assignedTeacherChecker(courses, answer, username);
                        if (!checkAssignedTeacher) {
                            break;
                        }

                        var allCourseQuizzes = new QuizArchive().getQuizzes();
                        for (int j = 0; j < allCourseQuizzes.size(); j++) {
                            if (allCourseQuizzes.get(j).getCourse().equals(courses.get(i).getName())) {
                                allCourseQuizzes.remove(j);
                                j--;
                            }
                        }

                        courseArchive.deleteACourse(answer);
                        PrintInformation.writeQuizQuestions(new QuizArchive());

                        try {
                            PrintWriter pw = new PrintWriter(new FileWriter("StudentQuizzes.txt"));
                            pw.print("");
                        } catch (IOException e) {
                            System.out.println("Couldn't modify the quiz.");
                        }

                        var allQuizzes = new QuizArchive().getQuizzes();

                        for (int j = 0; j < allQuizzes.size(); j++) {

                            if (!(allQuizzes.get(j).getRawScore().equals("NONE"))) {

                                StudentAnish.writeScores(allQuizzes.get(j),allQuizzes.get(j).getTimeStamp());
                            }

                        }


                        System.out.println("Course deleted!");
                        Teacher.writeCourses(CourseArchive.allCourses);
                        break;
                    }
                }
                if (!courseExists) {
                    System.out.println("The course to be deleted does not exist!\n");
                    break;
                }

            } else if (answer.equals("6")) {
                return false;
            }
        } while (true);
        return true;
    }
    //method for a person that is logged in as a teacher to create their own course to store quizzes
    // in which students access
    public static void creatingACourse(String answer, Teacher teacher,
                                       int enrollmentCapacity) throws InvalidCourseException, InvalidQuizException, FileNotFoundException {
        CourseArchive courseArchive = new CourseArchive();
        Course course = new Course(answer, teacher, enrollmentCapacity);
        courseArchive.addCourses(course);

    }

    public static boolean assignedTeacherChecker(ArrayList<Course> courses, String answer, String username) {

        Teacher tempTeacher = null;
        boolean notAssignedTeacher = true;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(answer)) {
                if (courses.get(i).getCourseTeacher().getUsername().equals(username)) {
                    notAssignedTeacher = false;

                    break;
                }
            }
        }
        if (notAssignedTeacher) {
            System.out.println("You're not the assigned teacher for this course!");
            return false;
        }

        return true;

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