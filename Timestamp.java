import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class TimeStamp {

    private static final SimpleDateFormat yearMonthDaySpaceHoursMinutesSeconds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void main(String[] args) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(yearMonthDaySpaceHoursMinutesSeconds.format(timestamp));
    }
}
