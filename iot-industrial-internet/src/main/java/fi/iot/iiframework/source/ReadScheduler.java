/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.source;

import java.util.Date;

public interface ReadScheduler {

    public void schedule(int interval, Runnable method);

    public void scheduleAtSpecificInterval(final long interval, final Date startDate, final Date endDate, final Runnable runnable);

    public void scheduleOnlyOnce(final Date startDate, final Runnable runnable);

    public void cancel();
}
