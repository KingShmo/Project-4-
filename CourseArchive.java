import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CourseArchive {

    private ArrayList<Course> courses;

    private ArrayList<Course> coursesAfterReading;

    private static ArrayList<Course> allCourses;


    public CourseArchive() throws InvalidCourseException {
        coursesAfterReading = Teacher.readCourses();
        if (coursesAfterReading != null) {
            courses = coursesAfterReading;
        } else {
            courses = new ArrayList<>();
        }
    }

    public CourseArchive(Course course) throws InvalidCourseException, FileNotFoundException {

        this();
        courses.add(course);
        Teacher.writeCourses(courses);
        allCourses.add(course);
    }
//adds a course to the courseArchive
    public void addCourses(Course course) {
        courses.add(course);
        allCourses.add(course);
    }
//method that allows teacher to see all the courses
    public ArrayList<Course> getCourses() {
        return courses;
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
