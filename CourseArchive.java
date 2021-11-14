import java.util.ArrayList;

public class CourseArchive {

    private ArrayList<Course> courses;


    public CourseArchive() {
        courses = new ArrayList<>();
    }

    public CourseArchive(Course course) {

        this();
        courses.add(course);

    }

    public void addCourses(Course course) {
        courses.add(course);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void deleteACourse(String titleOfTheCourse) {

        for (int i=0; i<courses.size(); i++) {

            if (courses.get(i).getName().equals(titleOfTheCourse)) {
                courses.remove(i);
                break;
            }

        }
    }
}
