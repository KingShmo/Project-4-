/*
This class contains all variables and methods that each teacher has
*
I implemented some profile variables that you can see below; we can have more or less of those
*/
import java.util.*; 

public class Teacher {
    
    private static String firstName;
    private static String lastName;
    private static String username;
    private static String password;
    ArrayList<Course> courses = new ArrayList<Course>();

    public Teacher(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() { //Returns the first name of a teacher
        return firstName;
    }

    public String getLastName() { //Returns the last name of a teacher
        return lastName;
    }

    public String getUsername() { //Returns the username of a teacher (use it to find out if username already exists)
        return username;
    }
    
    public String getPassword() { //Returns the password of a teacher
        return password;
    }

    public ArrayList<Course> getCourses() { //Returns the list of Course objects that this teacher has
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    /*
    Adds a course to the ArrayList of courses
    *
    We also need to have an ArrayList of all courses (from different teachers)
    because students can access any course (should be created in the Course.java file)
    */

    public void addCourse(Course course) {  
        courses.add(course);
    }


}