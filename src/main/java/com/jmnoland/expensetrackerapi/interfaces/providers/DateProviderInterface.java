package com.jmnoland.expensetrackerapi.interfaces.providers;

import java.util.Calendar;

public interface DateProviderInterface {

    Calendar getDateNow();
    Calendar calendarDateFromParts(int year, int month, int date);
}
