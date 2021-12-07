import javax.swing.*;
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

    //If ever equal to 1, quit the program

    public static void main() throws InvalidCourseException, InvalidQuizException, FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

    }

    //menu that only the teacher sees.
    public static boolean courseFunctionMenu(String username) throws Exception {
        String answer;
        String answer2;
        String error;
        String info;
        String question;
        ArrayList<String> allStudentsArrayList = new ArrayList<>();
        CourseArchive courseArchive = new CourseArchive();

        do {
            int x = 0;
            String availableCourses = "";
            for (Course course : CourseArchive.allCourses) {
                availableCourses += (++x) + ". " + course.getName() + "\n";
            }

            if (x == 0)
                availableCourses = "None.";
            String teacherCourseMenu = "Select the action you want:\n1. Create a course\n2. " +
                    "Use quiz options for the course\n3. " +
                    "Add students to a course\n4. Modify course attributes-Course teacher/Course enrollment/" +
                    "Course name\n5. Delete a course\n6. Exit";
            String[] options = {"1", "2", "3", "4", "5", "6"};
            answer = inputChecker(availableCourses, teacherCourseMenu);
            if (answer == null) {
                JOptionPane.showMessageDialog(null, "Thank you for using the Teacher Portal!",
                        "Teacher Portal", JOptionPane.INFORMATION_MESSAGE);
                Application.quitProgram = 1;
                return false;
            }

            if (answer.equals("1")) {
                question = "What's the course's title?";
                answer = enterAnswerForCourseFunctionDialog(question);
                if (Application.quitProgram == 1) {
                    return false;
                }
                ArrayList<Course> courses = courseArchive.getCourses();
                boolean courseExists = false;
                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).getName().equals(answer)) {
                        courseExists = true;
                        break;
                    }
                }
                if (courseExists) {
                    JOptionPane.showMessageDialog(null, "This course already exists!",
                            "Courses", JOptionPane.ERROR_MESSAGE);
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
                    question = "What's the course's enrollment capacity?";
                    String enrollmentCapacityString = enterAnswerForCourseFunctionDialog(question);
                    if (Application.quitProgram == 1) {
                        return false;
                    }
                    try {
                        enrollmentCapacity = Integer.parseInt(enrollmentCapacityString);
                        break;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid Input",
                                "Courses", JOptionPane.ERROR_MESSAGE);
                    }
                } while (true);

                JOptionPane.showMessageDialog(null, "Course created!",
                        "Courses", JOptionPane.INFORMATION_MESSAGE);

                Course course = new Course(answer, assignedTeacher, enrollmentCapacity);
                CourseArchive courseArchive1 = new CourseArchive();
                courseArchive1.addCourses(course);

                Teacher.writeCourses(CourseArchive.allCourses);


            } else if (answer.equals("2")) {
                boolean courseExists = false;
                question = "What's the course's title?";
                answer = enterAnswerForCourseFunctionDialog(question);
                if (Application.quitProgram == 1) {
                    return false;
                }

                ArrayList<Course> courses = courseArchive.getCourses();

                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).getName().equals(answer)) {
                        courseExists = true;
                        Course course = courses.get(i);
                        if (!(courses.get(i).getCourseTeacher().getUsername().equals(username)))
                            JOptionPane.showMessageDialog(null, "This course belongs to " +
                                    "another teacher.", "Courses", JOptionPane.ERROR_MESSAGE);
                        else
                            course.callTheQuizFunction(answer);
                        break;
                    }
                }
                if (!courseExists) {
                    error = "This course does not exist!";
                    errorMessage(error);
                    break;
                }


            } else if (answer.equals("3")) {
                question = "What's the title of the course to which you want to add the student?";
                answer = enterAnswerForCourseFunctionDialog(question);
                if (Application.quitProgram == 1) {
                    return false;
                }
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
                    error = "You're not the assigned teacher for this course!";
                    errorMessage(error);
                    break;
                }
                if (!courseExists) {
                    error = "This course does not exist!";
                    errorMessage(error);
                    break;
                }


                var allStudents = Student.getStudents();
                if (allStudents.size() == 0) {
                    error = "No students available to be added to course! Please make sure students are " +
                            "available and then try again";
                    errorMessage(error);
                    break;
                }

                for (int i = 0; i < allStudents.size(); i++) {
                    allStudentsArrayList.add((i + 1) + ". " + allStudents.get(i).getName() + "\n");
                }

                String studentNumber;
                boolean check = false;
                do {
                    check = false;
                    studentNumber = selectStudent(allStudentsArrayList);
                    if (studentNumber == null) {
                        JOptionPane.showMessageDialog(null, "Thank you for " +
                                        "using the Teacher Portal!",
                                "Teacher Portal", JOptionPane.INFORMATION_MESSAGE);
                        Application.quitProgram = 1;
                    }
                    if (Application.quitProgram == 1) {
                        return false;
                    }
                    try {
                        int num = 0;
                        if (studentNumber != null) {
                            num = Integer.parseInt(studentNumber);
                        }
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
                                error = "This student is already added to the course!";
                                errorMessage(error);
                                checkDuplicateStudents = false;
                                break;
                            }
                        }
                    }
                }

                if (checkDuplicateStudents) {
                    course.addAStudentToTheCourse(student);
                    Teacher.writeCourses(CourseArchive.allCourses);
                    info = "Student added!";
                    informationMessage(info);
                }

            } else if (answer.equals("4")) {
                boolean courseExists = false;
                question = "What's the title of the course whose details you want to modify?";
                answer = enterAnswerForCourseFunctionDialog(question);
                if (Application.quitProgram == 1) {
                    return false;
                }
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
                    error = "This course does not exist!";
                    errorMessage(error);
                    break;
                }

                do {

                    String courseModificationMenu = "Select the action you want:\n1. Modify course name\n2. Change " +
                            "course teacher\n3. Modify course enrollment capacity\n4. Exit";
                    String teacherAssignedCourse = answer;
                    answer = courseModification(courseModificationMenu);
                    if (answer == null) {
                        JOptionPane.showMessageDialog(null, "Thank you for using the " +
                                        "Teacher Portal!",
                                "Teacher Portal", JOptionPane.INFORMATION_MESSAGE);
                        Application.quitProgram = 1;
                        return false;
                    }

                    if (answer.equals("1")) {
                        boolean checkAssignedTeacher = assignedTeacherChecker(courses, course.getName(), username);
                        if (checkAssignedTeacher) {
                            question = "What's the new course title?";
                            answer2 = enterAnswerForCourseFunctionDialog(question);
                            if (Application.quitProgram == 1) {
                                return false;
                            }
                            course.setName(answer2);
                            info = "Course name modified!";
                            informationMessage(info);
                            Teacher.writeCourses(CourseArchive.allCourses);
                        }
                    } else if (answer.equals("2")) {
                        question = "What's the new course teacher's full name?";
                        String teacherName = enterAnswerForCourseFunctionDialog(question);
                        if (Application.quitProgram == 1) {
                            return false;
                        }
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
                            error = "The teacher entered does not exist in the database!";
                            errorMessage(error);
                            break;
                        }
                        course.setCourseTeacher(teacher);
                        info = "Course teacher changed!";
                        informationMessage(info);
                        Teacher.writeCourses(CourseArchive.allCourses);
                    } else if (answer.equals("3")) {
                        boolean checkAssignedTeacher = assignedTeacherChecker(courses, course.getName(), username);
                        if (checkAssignedTeacher) {
                            int loop = 0;
                            do {
                                loop = 0;
                                question = "What's the new course enrollment capacity?";
                                answer2 = enterAnswerForCourseFunctionDialog(question);
                                if (Application.quitProgram == 1) {
                                    return false;
                                }
                                try {
                                    course.setEnrollmentCapacity(Integer.parseInt(answer2));
                                } catch (NumberFormatException e) {
                                    JOptionPane.showMessageDialog(null, "Invalid Input",
                                            "Courses", JOptionPane.ERROR_MESSAGE);
                                    loop = 1;
                                }
                            } while (loop == 1);
                            info = "Course enrollment capacity modified!";
                            informationMessage(info);
                            Teacher.writeCourses(CourseArchive.allCourses);
                        }
                    } else if (answer.equals("4")) {
                        break;
                    }

                } while (true);


            } else if (answer.equals("5")) {
                boolean courseExists = false;
                question = "What's the course's title?";
                answer = enterAnswerForCourseFunctionDialog(question);
                if (answer == null) {
                    Application.quitProgram = 1;
                    return false;
                }

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
                            error = "Couldn't modify the quiz.";
                            errorMessage(error);
                        }

                        var allQuizzes = new QuizArchive().getQuizzes();

                        for (int j = 0; j < allQuizzes.size(); j++) {

                            if (!(allQuizzes.get(j).getRawScore().equals("NONE"))) {

                                StudentAnish.writeScores(allQuizzes.get(j),allQuizzes.get(j).getTimeStamp());
                            }

                        }


                        info = "Course deleted!";
                        informationMessage(info);
                        Teacher.writeCourses(CourseArchive.allCourses);
                        break;
                    }
                }
                if (!courseExists) {
                    error = "The course to be deleted does not exist!";
                    errorMessage(error);
                    break;
                }

            } else if (answer.equals("6")) {
                Application.menuTeacher(username);
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
        String error;
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
            error = "You're not the assigned teacher for this course!";
            errorMessage(error);
            return false;
        }

        return true;

    }

    public static String inputChecker(String courses, String prompt) {
        do {
            String option;
            String[] optionList = {"1", "2","3","4","5","6"};
            option = (String) JOptionPane.showInputDialog(null, "Available Courses:\n"
                            + courses + prompt, "Teacher Portal", JOptionPane.QUESTION_MESSAGE, null,
                    optionList,
                    optionList[0]);
            if (option == null) {
                Application.quitProgram = 1;
                return null;
            } else {
                return option;
            }
        } while (true);
    }

    public static String courseModification(String prompt) {
        do {
            String option;
            String[] optionList = {"1", "2", "3", "4"};
            option = (String) JOptionPane.showInputDialog(null, prompt,
                    "Courses", JOptionPane.QUESTION_MESSAGE, null, optionList, optionList[0]);
            if (option == null) {
                Application.quitProgram = 1;
                return null;
            } else {
                return option;
            }
        } while (true);
    }

    public static String selectStudent(ArrayList <String> students) {
        do {
            String option;
            String optionNumber;
            String[] optionList = new String[students.size()];
            for (int i = 0; i < students.size(); i++) {
                optionList[i] = students.get(i);
            }
            option = (String) JOptionPane.showInputDialog(null, "Select the student:",
                    "Courses", JOptionPane.QUESTION_MESSAGE, null, optionList, optionList[0]);
            if (option == null) {
                Application.quitProgram = 1;
                return null;
            } else {
                optionNumber = option.substring(0, 1);
                return optionNumber;
            }
        } while (true);
    }

    public static void errorMessage(String error) {
        JOptionPane.showMessageDialog(null, error, "Courses", JOptionPane.ERROR_MESSAGE);
    }

    public static void informationMessage(String info) {
        JOptionPane.showMessageDialog(null, info, "Courses", JOptionPane.INFORMATION_MESSAGE);
    }

    public static String enterAnswerForCourseFunctionDialog(String question) {
        String answer;
        int loop = 0;
        do {
            loop = 0;
            answer = JOptionPane.showInputDialog(null, question,
                    "Courses", JOptionPane.QUESTION_MESSAGE);
            //if user wants to exit the program
            if (answer == null) {
                JOptionPane.showMessageDialog(null, "Thank you for using the Teacher Portal!",
                        "Teacher Portal", JOptionPane.INFORMATION_MESSAGE);
                Application.quitProgram = 1;
            } else if (answer.isBlank()) {
                JOptionPane.showMessageDialog(null, "Answer cannot be empty!",
                        "Courses", JOptionPane.ERROR_MESSAGE);
                loop = 1;
            }
        } while (loop == 1);
        return answer;
    }


}
