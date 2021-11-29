/**
 * InvalidCourseException
 * <p>
 * throws an exception whenever a course is invalid
 *
 * @author Anushka,
 * @version November 15, 2021
 */
public class InvalidCourseException extends Exception {

    /**
     * constructs the class
     */
    public InvalidCourseException() {
        super();
    }

    /**
     * constructs the class with a message
     */
    public InvalidCourseException(String message) {
        super(message);
    }
}