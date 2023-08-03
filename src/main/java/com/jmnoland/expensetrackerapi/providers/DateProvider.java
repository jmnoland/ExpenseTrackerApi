package com.jmnoland.expensetrackerapi.providers;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
public class DateProvider implements DateProviderInterface {

    DateProvider() {}
    
    public Calendar getDateNow() {
        return Calendar.getInstance();
    }
    public Calendar calendarDateFromParts(int year, int month, int date) {
        Calendar cal = new GregorianCalendar();
        cal.set(year, month - 1, date, 0, 0, 0);
        return cal;
    }
}
