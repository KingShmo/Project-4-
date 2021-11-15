/**
 * InvalidQuizException class
 * <p>
 * throws an exception whenever a quiz is invalid
 *
 * @author Zuhair Almansouri, lab sec L16
 * @version November 10, 2021
 */
public class InvalidQuizException extends Exception {

    public InvalidQuizException() {
        super();
    }

    public InvalidQuizException(String message) {
        super(message);
    }
}