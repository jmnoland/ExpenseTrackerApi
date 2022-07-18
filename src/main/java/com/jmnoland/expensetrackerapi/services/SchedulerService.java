package com.jmnoland.expensetrackerapi.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SchedulerService {

    @Scheduled(fixedRate = 2000)
    public void scheduleTask()
    {

    }
}