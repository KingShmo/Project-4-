import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * Course class
 * <p>
 * A representation for one course that a certain number of students
 * can access.
 *
 * @author Anushka Nilangekar
 * @version November 15, 2021
 */
public class Course {
    /**
     * Title of the course
     */
    private String name;
    /**
     * Teacher assigned to this course
     */
    private Teacher courseTeacher;
    /**
     * Enrollment capacity of the course
     */
    private int enrollmentCapacity;
    /**
     * students enrolled
     */
    private ArrayList<Student> studentsInThisCourse;
    /**
     * QuizArchive with quizzes
     */
    QuizArchive quizArchive = new QuizArchive();

    /**
     * get students
     */
    public ArrayList<Student> getStudentsInThisCourse() {
        return this.studentsInThisCourse;
    }

    /**
     * sets students
     * @param studentsInThisCourse = students to be set
     */
    public void setStudentsInThisCourse(ArrayList<Student> studentsInThisCourse) {
        this.studentsInThisCourse = studentsInThisCourse;
    }

    /**
     * Allocates a new course object with its title, assigned teacher and enrollment count.
     *
     * @param name = course title.
     * @param enrollmentCapacity = Enrollment capacity of the course
     * @param teacher = teacher to be assigned
     * @throws InvalidCourseException = throws an exception when there is 0 enrollment capacity for the course.
     */
    public Course(String name, Teacher teacher, int enrollmentCapacity) throws InvalidCourseException {
        this.name = name;
        this.courseTeacher = teacher;
        this.enrollmentCapacity = enrollmentCapacity;
        studentsInThisCourse = new ArrayList<>();
        if (enrollmentCapacity < 1)
            throw new InvalidCourseException("A course should have a minimum student enrollment of 1!");
    }

    /**
     * Allocates a new course object with its title, assigned teacher and enrollment count.
     *
     * @param name = course title.
     * @param enrollmentCapacity = Enrollment capacity of the course
     * @param teacher = teacher to be assigned
     * @param studentsInThisCourse = students to be added for the course
     * @throws InvalidCourseException = throws an exception when there is 0 enrollment capacity for the course.
     */
    public Course(String name, Teacher teacher, int enrollmentCapacity, ArrayList<Student> studentsInThisCourse) throws InvalidCourseException {
        this(name, teacher, enrollmentCapacity);
        this.studentsInThisCourse = studentsInThisCourse;
        if (enrollmentCapacity < 1)
            throw new InvalidCourseException("A course should have a minimum student enrollment capacity of 1!");
    }

    /**
     * adds a student
     * @param student = student to be added
     */
    public void addAStudentToTheCourse(Student student) {
        studentsInThisCourse.add(student);
    }

    /**
     * get name
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * get courseTeacher
     * @return courseTeacher
     */
    public Teacher getCourseTeacher() {
        return this.courseTeacher;
    }

    /**
     * get enrollmentCapacity
     * @return enrollmentCapacity
     */
    public int getEnrollmentCapacity() {
        return this.enrollmentCapacity;
    }

    /**
     * sets courseTeacher
     * @param newCourseTeacher = new teacher
     */
    public void setCourseTeacher(Teacher newCourseTeacher) {
        this.courseTeacher = newCourseTeacher;
    }

    /**
     * sets enrollmentCapacity
     * @param newEnrollmentCapacity = new enrollmentCapacity
     */
    public void setEnrollmentCapacity(int newEnrollmentCapacity) {
        this.enrollmentCapacity = newEnrollmentCapacity;
    }

    /**
     * sets name
     * @param newName = new name
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * calls the quiz menu
     * @throws InvalidQuizException = thrown when appropriate
     * @throws FileNotFoundException = thrown when appropriate
     */
    public void callTheQuizFunction() throws InvalidQuizException, FileNotFoundException {
        TheQuizFunction.main(quizArchive);

    }
}
