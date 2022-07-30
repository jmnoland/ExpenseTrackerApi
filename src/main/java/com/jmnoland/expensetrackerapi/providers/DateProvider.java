package com.jmnoland.expensetrackerapi.providers;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class DateProvider implements DateProviderInterface {

    DateProvider() {}
    
    public Calendar getDateNow() {
        return Calendar.getInstance();
    }
}
