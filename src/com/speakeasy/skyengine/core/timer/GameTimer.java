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

import java.util.HashMap;

/**
 *
 * @author SpeaKeasY
 */
public class GameTimer extends Thread {

    private static int updatess = 100; // Number of updates per second.
    private static boolean running = false;
    public static GameTimer gametimer;
    protected static HashMap<Timing, Boolean> updates; 
    
    private GameTimer(int updatess, boolean run) {
        GameTimer.updatess = updatess;
        if (run) {
            gametimer.start();
        }
    }

    public static GameTimer newGameTimer() {
        gametimer = new GameTimer(updatess, false);
        return gametimer;
    }

    public static GameTimer newGameTimer(boolean run) {
        gametimer = new GameTimer(updatess, run);
        return gametimer;
    }

    public static GameTimer newGameTimer(int updatess) {
        gametimer = new GameTimer(updatess, false);
        return gametimer;
    }

    public static GameTimer newGameTimer(int updatess, boolean run) {
        gametimer = new GameTimer(updatess, run);
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

        final double ns = 1000000000.0 / (10000000 / 5); // Microsecond.
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
        ;
    }

    private void async() {
        ;
    }

    public void addTiming() {
        ;
    }
    
}
