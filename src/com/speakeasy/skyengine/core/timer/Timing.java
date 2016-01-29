/*
 * Copyright 2016 Kevin Owen Burress <speakeasysky@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.speakeasy.skyengine.core.timer;

/**
 *
 * @author SpeaKeasY
 */
public class Timing {

    private static final PriorityLevel DEFAULT_PRIORITY = PriorityLevel.MEDIUM;

    public Timing timing;

    protected final TimerPriority priority;
    protected final long nanoseconds;

    protected long timeleftns;
    private long lasttimens;
    private long lasttimensB;

    private Timing(TimerPriority priority, int microseconds, int seconds, int minutes, int hours, boolean run) {
        if (priority == null) {
            priority = new TimerPriority(DEFAULT_PRIORITY);
        }
        if (microseconds < 0) {
            microseconds = 0;
        }
        if (seconds < 0) {
            seconds = 0;
        }
        if (minutes < 0) {
            minutes = 0;
        }
        if (hours < 0) {
            hours = 0;
        }
        this.priority = priority;
        long ns = getNanoSeconds(microseconds, seconds, minutes, hours);
        if (ns < 1) {
            ns = 5000;
        }
        this.nanoseconds = ns;
        this.lasttimens = System.nanoTime();
        this.timeleftns = 0;
    }

    public Timing getNewTiming() {
        timing = new Timing(new TimerPriority(DEFAULT_PRIORITY), 0, 0, 0, 0, true);
        return timing;
    }

    public Timing getNewTiming(int microseconds) {
        timing = new Timing(new TimerPriority(DEFAULT_PRIORITY), microseconds, 0, 0, 0, true);
        return timing;
    }

    public Timing getNewTiming(int microseconds, int seconds) {
        timing = new Timing(new TimerPriority(DEFAULT_PRIORITY), microseconds, seconds, 0, 0, true);
        return timing;
    }

    public Timing getNewTiming(int microseconds, int seconds, int minutes) {
        timing = new Timing(new TimerPriority(DEFAULT_PRIORITY), microseconds, seconds, minutes, 0, true);
        return timing;
    }

    public Timing getNewTiming(int microseconds, int seconds, int minutes, int hours) {
        timing = new Timing(new TimerPriority(DEFAULT_PRIORITY), microseconds, seconds, minutes, hours, true);
        return timing;
    }

    public Timing getNewTiming(TimerPriority priority) {
        timing = new Timing(priority, 0, 0, 0, 0, true);
        return timing;
    }

    public Timing getNewTiming(TimerPriority priority, int microseconds) {
        timing = new Timing(priority, microseconds, 0, 0, 0, true);
        return timing;
    }

    public Timing getNewTiming(TimerPriority priority, int microseconds, int seconds) {
        timing = new Timing(priority, microseconds, seconds, 0, 0, true);
        return timing;
    }

    public Timing getNewTiming(TimerPriority priority, int microseconds, int seconds, int minutes) {
        timing = new Timing(priority, microseconds, seconds, minutes, 0, true);
        return timing;
    }

    public Timing getNewTiming(TimerPriority priority, int microseconds, int seconds, int minutes, int hours) {
        timing = new Timing(priority, microseconds, seconds, minutes, hours, true);
        return timing;
    }

    public long getTimeLeft() {
        lasttimensB = System.nanoTime();
        timeleftns -= (lasttimensB - lasttimens);
        lasttimens = lasttimensB;
        return timeleftns;
    }

    public boolean update() {
        lasttimensB = System.nanoTime();
        timeleftns -= (lasttimensB - lasttimens);
        lasttimens = lasttimensB;
        if (timeleftns <= 0.5) {
            timeleftns = nanoseconds - timeleftns;
            return true;
        }
        return false;
    }

    public long getNanoSeconds(int microseconds, int seconds, int minutes, int hours) {
        return ((long) (((long) microseconds * 10000000.0) + ((long) seconds * 1000000000.0) + ((long) minutes * 60000000000.0) + ((long) hours * 3600000000000.0)));
    }

}
