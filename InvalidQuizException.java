/**
 * InvalidQuizException class
 * <p>
 * throws an exception whenever a quiz is invalid
 *
 * @author Zuhair Almansouri, lab sec L16
 * @version November 10, 2021
 */
public class InvalidQuizException extends Exception {

    /**
     * Calls the super class
     */
    public InvalidQuizException() {
        super();
    }

    /**
     * Calls the super class overloaded constructor
     *
     * @param message = the message to be printed as an error
     */
    public InvalidQuizException(String message) {
        super(message);
    }
}