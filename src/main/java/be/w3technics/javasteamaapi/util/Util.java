
package be.w3technics.javasteamaapi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {
    
    public static Date getToday() {
        Calendar today = Calendar.getInstance();
                
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND,0);
        
        return today.getTime();
    }
    
    public static Date getStartOfDay(Date date) {
        Calendar lastMinute = Calendar.getInstance();
        lastMinute.setTime(date);
        lastMinute.set(Calendar.HOUR_OF_DAY,0);
        lastMinute.set(Calendar.MINUTE,0);
        lastMinute.set(Calendar.SECOND, 1);
        return lastMinute.getTime();
    }
    
    public static Date getEndOfDay(Date date) {
        Calendar lastMinute = Calendar.getInstance();
        lastMinute.setTime(date);
        lastMinute.set(Calendar.HOUR_OF_DAY,23);
        lastMinute.set(Calendar.MINUTE,59);
        lastMinute.set(Calendar.SECOND, 59);
        return lastMinute.getTime();
    }
    
    public static String removeTimestamp(Date toParse) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(toParse);
    }
    
    public static String dateToISO8601Format(Date today) {
        DateFormat df = new SimpleDateFormat(Settings.STEAMA_DATE_FORMAT);
        return df.format(today.getTime());
    }
}
