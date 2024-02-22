package com.lipisak.vaadin.slidetab;

import java.util.Timer;

/**
 * The default {@link ScheduleStrategy} uses a static {@link Timer} instance for scheduling, and cancels any previously
 * scheduled task when a new task is scheduled.
 */
public class DefaultScheduleStrategy implements ScheduleStrategy {

    private SlideTab.TabTask currentTask;

    @Override
    public void schedule(SlideTab.TabTask tabTask, int delayMillis) {
        if (currentTask != null) {
            currentTask.cancel();
        }
        currentTask = tabTask;
        TimerHolder.INSTANCE.schedule(currentTask, delayMillis);
    }

    // Use a holder to defer initialization until TimerHolder.INSTANCE is accessed, in a thead-safe manner
    public static class TimerHolder {
        static final Timer INSTANCE = new Timer("slide-tab-timer", true);
    }
}