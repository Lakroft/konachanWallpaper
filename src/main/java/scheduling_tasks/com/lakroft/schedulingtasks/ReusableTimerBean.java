package scheduling_tasks.com.lakroft.schedulingtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class ReusableTimerBean {
    private Timer timer;
    @Autowired
    private KonachanManager konachanManager;
    @Autowired
    private PropertiesLoader propertiesLoader;

    @PostConstruct
    public void timerStarter() {
        Long period = Long.parseLong(propertiesLoader.getProperty("delay", "60"))*1000;
        startTimer(1000L, period);
    }

    public void startTimer(Long delay, Long period) {
        if (timer != null) { stopTimer(); }
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
        @Override
        public void run() {
            konachanManager.getKonachan();
        }
    }
}
