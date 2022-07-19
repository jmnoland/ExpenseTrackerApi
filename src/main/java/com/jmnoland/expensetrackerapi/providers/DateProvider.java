package com.jmnoland.expensetrackerapi.providers;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;

import java.util.Date;

public class DateProvider implements DateProviderInterface {

    DateProvider() {}
    
    public Date getDateNow() {
        long millis = System.currentTimeMillis();
        return new Date(millis);
    }
}
