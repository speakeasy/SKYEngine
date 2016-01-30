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
package com.speakeasy.skyengine.core;

import com.speakeasy.skyengine.core.timer.Timing;
import java.util.HashMap;

/**
 *
 * @author SpeaKeasY
 */
public class GameTimer extends Thread {

    private static boolean running = false;
    private static GameTimer gametimer;
    protected static HashMap<Timing, Boolean> updates;
    private static HashMap<Integer, Timing> updatesidx;
    private static int updatesitr = 0;
    private static int updatessize = 0;
    private static Timing atiming;

    private GameTimer(boolean run) {
        updates = new HashMap();
        if (run) {
            gametimer.start();
        }
    }

    public static GameTimer newGameTimer() {
        gametimer = new GameTimer(false);
        return gametimer;
    }

    public static GameTimer newGameTimer(boolean run) {
        gametimer = new GameTimer(run);
        return gametimer;
    }

    @Override
    public void start() {
        running = true;
        this.setPriority(MAX_PRIORITY);
        loop();
    }

    private void loop() {
        long lastTime = System.nanoTime();

        final double ns = 1000000000.0 / (1000000 / 5); // Milliseconds.
        double delta = 0;
        long now;
        long ploss = 0;
        int sleep;

        while (running) {
            now = System.nanoTime();
            delta = delta + ((now - lastTime) / ns);
            lastTime = now;

            while (delta >= 1) {
                updateTimings();
                delta--;
            }

            try {
                if (ploss >= 1) {
                    ploss--;
                    now--;
                }
                Thread.sleep(0, (sleep = 5000 - (int) (ploss = System.nanoTime() - now)));
                ploss = ploss - sleep;
            } catch (InterruptedException ie) {
                ;
            }

            async();
        }
    }

    private void updateTimings() {
        updatesitr = 0;
        while (updatesitr <= updatessize) {
            atiming = updatesidx.get(updatesitr);
            if (atiming.update() > 0) {
                updates.put(atiming, true);
            }
        }
    }

    private void async() { // Called every 5 milliseconds, display refresh rate.
        ;
    }

    public void putTiming(Timing timing) {
        updates.put(timing, true);
        updatesidx.put(updatesidx.size(), timing);
    }

    public void addTiming(int times) {
        Timing timing = newTiming(getNanoSeconds(0, 0, 0, 0), times);
        putTiming(timing);
    }

    public void addTiming(long nanoseconds, int times) {
        Timing timing = new Timing(nanoseconds, times);
        putTiming(timing);
    }

    public void addTiming(int milliseconds, int times) {
        Timing timing = newTiming(getNanoSeconds(milliseconds, 0, 0, 0), times);
        putTiming(timing);
    }

    public void addTiming(int milliseconds, int seconds, int times) {
        Timing timing = newTiming(getNanoSeconds(milliseconds, seconds, 0, 0), times);
        putTiming(timing);
    }

    public void addTiming(int milliseconds, int seconds, int minutes, int times) {
        Timing timing = newTiming(getNanoSeconds(milliseconds, seconds, minutes, 0), times);
        putTiming(timing);
    }

    public void addTiming(int milliseconds, int seconds, int minutes, int hours, int times) {
        Timing timing = newTiming(getNanoSeconds(milliseconds, seconds, minutes, hours), times);
        putTiming(timing);
    }

    public Timing newTiming(long nanoseconds, int times) {
        Timing timing = new Timing(nanoseconds, times);
        return timing;
    }

    public long getNanoSeconds(int milliseconds, int seconds, int minutes, int hours) {
        return ((long) (((long) milliseconds * 1000000.0) + ((long) seconds * 1000000000.0) + ((long) minutes * 60000000000.0) + ((long) hours * 36000000000000.0)));
    }

    public GameTimer getGameTimer() {
        return gametimer;
    }

    public Timing removeTiming(Timing timing) {
        updatesitr = 0;
        while (updatesitr <= updatessize) {
            atiming = updatesidx.get(updatesitr);
            if (atiming.equals(timing)) {
                updates.remove(atiming);
                updatesidx.remove(updatesitr);
                updatessize = updatesidx.size();
                return atiming;
            }
        }
        return null;
    }
}
