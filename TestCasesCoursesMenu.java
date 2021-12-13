/**
 * TestCases class
 * <p>
 * Tests functionality of the whole program by invoking methods
 *
 * @author Zuhair Almansouri, lab sec L16
 * @version December 13, 2021
 */

public class TestCasesCoursesMenu {

    public static void main(String[] args) throws Exception {

        //These test cases examine the whole functionality for the courses. That's because it mimics the original menu.

        //Adding courses
        Course expected = new Course("test", new Teacher("a", "a", "a", "a"),
                10);
        CourseArchive.addStaticCourses(expected);
        var allCourses = CourseArchive.allCourses;

        if (allCourses.get(allCourses.size() - 1).getName().equals(expected.getName())) {
            System.out.println(true);
        } else
            System.out.println(false);

        //Use quiz options
        try {
            TheQuizFunction.main(new QuizArchive(), "test");
            passed(); //passed since no exception is thrown and a GUI popped up
        } catch (Exception e) {
            System.out.println(false);
        }

        //Adding students
        expected.addAStudentToTheCourse(new Student("test", "test", "test", "test"));
        var courseStu = expected.getStudentsInThisCourse();
        if (courseStu.get(courseStu.size() - 1).getUsername().equals("test")) {
            System.out.println(true);
        } else
            System.out.println(false);

        //Modify course name
        expected.setName("newTest");
        if (expected.getName().equals("newTest")) {
            System.out.println(true);
        } else
            System.out.println(false);

        //Assign a new teacher
        expected.setCourseTeacher(new Teacher("new", "new", "new", "new"));
        if (expected.getCourseTeacher().getUsername().equals("new")) {
            System.out.println(true);
        } else
            System.out.println(false);

        //Change enrollment capacity
        expected.setEnrollmentCapacity(12345);
        if (expected.getEnrollmentCapacity() == 12345) {
            System.out.println(true);
        } else
            System.out.println(false);

        //Delete a course
        CourseArchive courseArchive = new CourseArchive();
        Course toBeDeleted = new Course("del",
                new Teacher("b", "b", "b", "b"), 2);
        courseArchive.deleteACourse("del");
        var toBeCompared = CourseArchive.allCourses;

        for (int i = 0; i < toBeCompared.size(); i++) {
            if (toBeCompared.get(i).getName().equals(toBeDeleted.getName())) {
                System.out.println(false);
            }
            if (i == toBeCompared.size() - 1) {
                System.out.println(true);
            }
        }


    }

    public static void passed() {
        System.out.println(true);
    }


}
