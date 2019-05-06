package com.lakroft.spring.spring;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class ReusableTimerBean {
    private Timer timer;

    @PostConstruct
    public void timerStarter() {
        startTimer(500L, 1000L);
        try {
            Thread.sleep(1000L);
            startTimer(500L, 500L);
            Thread.sleep(1000L);
        } catch (InterruptedException ignored) {}
        stopTimer();
    }

    public void startTimer(Long delay, Long period) {
        if (timer != null) {
            stopTimer();
        }
        timer = new Timer("ReusableTimer");
        timer.scheduleAtFixedRate(new InnerTimerTask(), delay, period);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private class InnerTimerTask extends TimerTask {
        private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

        @Override
        public void run() {
            System.out.println("Reusable Timer: " + dateFormat.format(new Date()));
        }
    }
}
