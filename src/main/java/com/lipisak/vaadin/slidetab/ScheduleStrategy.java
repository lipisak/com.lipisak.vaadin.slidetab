package com.lipisak.vaadin.slidetab;

/**
 * Implementations of this class are responsible for scheduling expand/collapse/toggle commands for the {@link SlideTab}.
 *
 * @see DefaultScheduleStrategy
 */
public interface ScheduleStrategy {

    /**
     * Schedules the given {@link SlideTab.TabTask} after the given delay.
     */
    void schedule(SlideTab.TabTask tabTask, int delayMillis);
}