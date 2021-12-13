import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * TimeStamp class
 * <p>
 * Prints a timestamp everytime a student has taken the quiz and allows a teacher to access the timestamp if needed
 *
 * @author Anish
 * @version December 11, 2021
 */
public class TimeStamp {

    //Simulates the date of quiz completion
    private static final SimpleDateFormat DATE = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");


    public static void main(String[] args) {
        //timestamp object
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    }
}