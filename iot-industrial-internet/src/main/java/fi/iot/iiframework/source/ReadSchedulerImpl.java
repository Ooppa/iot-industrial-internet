/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.source;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ReadSchedulerImpl implements ReadScheduler {

    Timer timer;

    @Override
    public void schedule(final int interval, final Runnable runnable) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runnable.run();
            }

        }, 0, interval);

    }

    @Override
    public void scheduleAtSpecificInterval(final long interval, final Date startDate, final Date endDate, final Runnable runnable) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (endDate != null && new Date().after(endDate)) {
                    timer.cancel();
                }
                runnable.run();
            }

        }, startDate, interval);

    }

    @Override
    public void scheduleOnlyOnce(final Date startDate, final Runnable runnable) {
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                runnable.run();
            }

        }, startDate);

    }

    @Override
    public void cancel() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }

}
