/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.source;

import fi.iot.iiframework.application.Application;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class ReadSchedulerImpl implements ReadScheduler {

    Timer timer;

    @Override
    public void schedule(final int interval, final Runnable runnable) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Application.logger.log(
                        Level.INFO, 
                        "Task was run. Currently software has {0} threads.",
                        Thread.activeCount()
                );
                runnable.run();
            }

        }, 0, interval);

    }

    @Override
    public void cancel() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }

}
