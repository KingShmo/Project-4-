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

    public void addCourses(Course course) {
        courses.add(course);
        allCourses.add(course);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void deleteACourse(String titleOfTheCourse) {

        for (int i = 0; i < courses.size(); i++) {

            if (courses.get(i).getName().equals(titleOfTheCourse)) {
                courses.remove(i);
                break;
            }

        }
    }
}
