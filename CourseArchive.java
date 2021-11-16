import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * CourseArchive
 *
 * saves all courses
 *
 * @author Anushka
 *
 * @version November 15, 2021
 *
 */

public class CourseArchive {
    /**
     * all courses
     */
    private ArrayList<Course> courses;
    /**
     * courses that are read
     */
    private ArrayList<Course> coursesAfterReading;
    /*
     * all courses (static)
     */
    private static ArrayList<Course> allCourses;

    /**
     * contructs courses
     * @throws InvalidCourseException = thrown when appropriate
     */
    public CourseArchive() throws InvalidCourseException {
        allCourses = new ArrayList<>();
        coursesAfterReading = new ArrayList<>();
        courses = new ArrayList<>();
        coursesAfterReading = Teacher.readAllCourses();
        if (coursesAfterReading != null) {
            courses = coursesAfterReading;
        } else {
            courses = new ArrayList<>();
        }
    }
    /**
     * contructs courses
     * @param course = to be added
     * @throws InvalidCourseException = thrown when appropriate
     */
    public CourseArchive(Course course) throws InvalidCourseException, FileNotFoundException {

        this();
        courses.add(course);
        Teacher.writeCourses(courses);
        allCourses.add(course);
    }
    //adds a course to the courseArchive
    public void addCourses(Course course) {
        allCourses.add(course);
    }
    //method that allows teacher to see all the courses
    public ArrayList<Course> getCourses() {
        return allCourses;
    }
    //method which allows a teacher to delete an unwanted course
    public void deleteACourse(String titleOfTheCourse) {

        for (int i = 0; i < courses.size(); i++) {

            if (courses.get(i).getName().equals(titleOfTheCourse)) {
                courses.remove(i);
                break;
            }

        }
    }
}
