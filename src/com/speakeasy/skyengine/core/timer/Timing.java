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

    protected final long nanoseconds;
    protected int times;

    protected long timeleftns;
    private long lasttimens;
    private long lasttimensB;

    public Timing(long nanoseconds, int times) {
        if (nanoseconds < 5000) {
            nanoseconds = 0;
        }
        this.nanoseconds = nanoseconds;
        this.lasttimens = System.nanoTime();
        this.timeleftns = 0;
        if (times == 0) {
            times--;
        }

        this.times = times;
    }



    public long getTimeRemaining() {
        lasttimensB = System.nanoTime();
        timeleftns -= (lasttimensB - lasttimens);
        lasttimens = lasttimensB;
        return timeleftns;
    }
    
    public int timesLeft() {
        return times;
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

    public void setTimes(int times) {
        this.times = times;
    }
    
    

}
