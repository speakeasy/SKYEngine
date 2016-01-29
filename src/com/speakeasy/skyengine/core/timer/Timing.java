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

    protected Timing timing;

    protected final long nanoseconds;
    protected int times;

    protected long timeleftns;
    private long lasttimens;
    private long lasttimensB;

    private Timing(int microseconds, int seconds, int minutes, int hours, int times) {
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

        long ns = getNanoSeconds(microseconds, seconds, minutes, hours);
        if (ns < 5000) {
            ns = 5000;
        }
        this.nanoseconds = ns;
        this.lasttimens = System.nanoTime();
        this.timeleftns = 0;
        if (times == 0) {
            times--;
        }

        this.times = times;
    }

    public Timing newTiming(int times) {
        timing = new Timing(0, 0, 0, 0, times);
        return timing;
    }

    public Timing newTiming(int microseconds, int times) {
        timing = new Timing(microseconds, 0, 0, 0, times);
        return timing;
    }

    public Timing newTiming(int microseconds, int seconds, int times) {
        timing = new Timing(microseconds, seconds, 0, 0, times);
        return timing;
    }

    public Timing newTiming(int microseconds, int seconds, int minutes, int times) {
        timing = new Timing(microseconds, seconds, minutes, 0, times);
        return timing;
    }

    public Timing newTiming(int microseconds, int seconds, int minutes, int hours, int times) {
        timing = new Timing(microseconds, seconds, minutes, hours, times);
        return timing;
    }

    public long getTimeRemaining() {
        lasttimensB = System.nanoTime();
        timeleftns -= (lasttimensB - lasttimens);
        lasttimens = lasttimensB;
        return timeleftns;
    }

    public int update() {
        lasttimensB = System.nanoTime();
        timeleftns -= (lasttimensB - lasttimens);
        lasttimens = lasttimensB;
        if (timeleftns <= 0.5) {
            if (times > 0 || times == -1) {
                timeleftns = nanoseconds - timeleftns;
                if (times-- > -1) {
                    if (times > 0) {
                        return times;
                    }
                }
                times++;
                return 1;
            }
        }
        return 0;
    }

    public final long getNanoSeconds(int microseconds, int seconds, int minutes, int hours) {
        return ((long) (((long) microseconds * 10000000.0) + ((long) seconds * 1000000000.0) + ((long) minutes * 60000000000.0) + ((long) hours * 3600000000000.0)));
    }

}
