import java.util.ArrayList;

public class Teacher {
    private final String teacherName;
    private final ArrayList<Course> courses;

    public Teacher(String teacherName, ArrayList<Course> courses) {
        this.teacherName = teacherName;
        this.courses = courses;
    }

    public void addCourse(String courseName, int enrollment) {
        Course course = new Course(courseName, enrollment);
        courses.add(course);
    }

    public void editCourse(String courseName, int newEnrollment) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).equals(courseName)) {
                courses.set(i, new Course(courseName, newEnrollment));
            }
        }
    }

    public void deleteCourse(String courseName) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).equals(courseName)) {
                courses.remove(i);
            }
        }
    }
}
