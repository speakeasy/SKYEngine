/*
 * Copyright 2016 SpeaKeasY.
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
public class GameTimer extends Thread {

    private static int updatess = 1000; // Number of updates per second.
    private static boolean running = false;
    private static GameTimer gametimer;
    
    // for second and minute timer statistics.
    private long lastSecond;
    private long lastMinute;

    private GameTimer() {
        gametimer = this;
    }

    private GameTimer(boolean run) {
        gametimer = this;
        if (run) {
            gametimer.start();
        }
    }

    private GameTimer(int nupdates) {
        gametimer = this;
        updatess = nupdates;
    }

    private GameTimer(int nupdates, boolean run) {
        gametimer = this;
        updatess = nupdates;
        if (run) {
            gametimer.start();
        }
    }

    @Override
    public void start() {
        loop();
        
        
    }

    private void loop() {
        long lastTime = this.lastSecond = this.lastMinute = System.nanoTime();
        
        final double ns = 1000000000.0 / updatess;
        int nupdatessec = 0;
        int nupdatesmin = 0;
        double delta = 0;
        
        while (running) {
            long now = System.nanoTime();
            delta = delta + ((now - lastTime) / ns);
            lastTime = now;
            while (delta >= 1)// n times a second
            {
                doLogic(); // All time restricted logic.
                if(nupdatessec++ >= updatess) {
                    nupdatessec = 0;
                    secondTimer();
                    if(nupdatesmin >= 60) {
                        nupdatesmin = 0;
                        minuteTimer();
                    }
                }
                delta--;
            }
            render();//displays to the screen unrestricted time
        }
    }

    private void doLogic() {
        ;
    }

    private void render() {
        ;
    }

    private void secondTimer() {
        ;
    }

    private void minuteTimer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
