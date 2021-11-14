import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
/**
 * Course class
 *
 * A representation for one course that a certain number of students
 * can access.
 *
 * @author Anushka Nilangekar
 *
 * @version November 13, 2021
 *
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

    private ArrayList<Student> students;

    public Course(String name, Teacher teacher, int enrollmentCapacity) throws InvalidCourseException {
        this.name = name;
        this.courseTeacher = teacher;
        this.enrollmentCapacity = enrollmentCapacity;
        students = new ArrayList<>();
        if (enrollmentCapacity < 1)
            throw new InvalidCourseException("A course should have a minimum student enrollment of 1!");
    }

    /*
     * Allocates a new course object with its title, assigned teacher and enrollment count.
     *
     * @param name = course title.
     * @param courseTeacher = Teacher assigned to this course.
     * @param enrollmentCapacity = Enrollment capacity of the course
     * @param students = Students enrolled in the course
     * @throws InvalidCourseException = throws an exception when there is 0 enrollment capacity for the course.
     */


    public void addAStudent(Student student) {
        students.add(student);
    }

    public String getName() {
        return this.name;
    }

    public Teacher getCourseTeacher() {
        return this.courseTeacher;
    }

    public int getEnrollmentCapacity() {
        return this.enrollmentCapacity;
    }

    public void setCourseTeacher(Teacher newCourseTeacher) {
        this.courseTeacher = newCourseTeacher;
    }

    public void setEnrollmentCapacity(String newEnrollmentCapacity) {
        this.name = newEnrollmentCapacity;
    }

    public void setName(String newName) {
        this.name = newName;
    }

}
