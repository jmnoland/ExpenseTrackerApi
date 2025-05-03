package com.jmnoland.expensetrackerapi.interfaces.providers;

import java.util.Calendar;
import java.util.Date;

public interface DateProviderInterface {

    Calendar getDateNow();
    Calendar calendarDateFromParts(int year, int month, int date);
    Date addMonths(Date date, int months);
}
