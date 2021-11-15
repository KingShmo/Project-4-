import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/**
 * TimeStamp class
 * 
 * Prints a timestamp for a quiz
 *
 * @author Anish 
 * 
 * @version November 15, 2021
 */
public class TimeStamp {

    private static final SimpleDateFormat yearMonthDaySpaceHoursMinutesSeconds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void main(String[] args) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(yearMonthDaySpaceHoursMinutesSeconds.format(timestamp));
    }
}
