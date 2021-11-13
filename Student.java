/*
This class contains all variables and methods that each student has
*/
public class Student {
    
    private static String firstName;
    private static String lastName;
    private static String username;
    private static String password;

    public Student(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() { //Returns the first name of a student
        return firstName;
    }

    public String getLastName() { //Returns the last name of a student
        return lastName;
    }

    public String getUsername() { //Returns the username of a student (use it to find out if username already exists)
        return username;
    }
    
    public String getPassword() { //Returns the password of a student
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
