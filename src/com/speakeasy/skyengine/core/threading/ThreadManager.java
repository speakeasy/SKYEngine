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
package com.speakeasy.skyengine.core.threading;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kevin Owen Burress <speakeasysky@gmail.com>
 */
public class ThreadManager extends Thread implements Observer {

    private static final int MAX_THREADS = 255;
    private static final int START_THREADS = 64;
    protected static ArrayList<TaskableThread> threads = new ArrayList<>();
    protected static int threadssize = 0;
    protected static Boolean[] waitingthreads = new Boolean[MAX_THREADS];
    protected static int threadswaiting = 0;

    private static TaskableThread athread;

    static ArrayList<Task> tasks = new ArrayList<>();
    static int taskssize = 0;

    protected static ArrayList<Task> usedtasks = new ArrayList<>(); // Recycling buffer.
    protected static int usedtaskssize = 0;
    
    private static PriorityScheduler scheduler;

    static int ui = 0;
    static boolean ub = false;

    static boolean lock = false;

    public ThreadManager() {
        this.setPriority(MAX_PRIORITY);

        for (; ui < MAX_THREADS; ui++) {
            waitingthreads[ui] = false;
        }
        ui = 0;
        for (; ui < START_THREADS; ui++) {
            threads.add(new TaskableThread(threadssize++, this));
            waitingthreads[ui] = true;
        }
        threadswaiting = START_THREADS;
        ui = 0;
        scheduler = new PriorityScheduler(tasks, taskssize);
    }

    /*@Override
    public void start(Object... o) {
        ;
    }*/

    private void assignTasks() {
        while(lock) {
            try {
                Thread.sleep(0, 10);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (; ui < threadssize; ui++) {
            if (waitingthreads[ui]) {
                if (taskssize >= 0) {
                    threads.get(ui).assignTask(tasks.remove(taskssize--));
                    waitingthreads[ui] = false;
                    threadswaiting--;
                } else {
                    ui = 0;
                    return;
                }
            }
        }
        ui = 0;
    }

    private void addThreads(int amount) {
        if (MAX_THREADS >= (amount + threadssize)) {
            amount = (amount + threadssize) - MAX_THREADS;
        }
        for (; amount > 0; amount--) {
            threads.add(new TaskableThread(threadssize++, this));
            waitingthreads[threadssize] = true;
            threadswaiting++;
        }
    }

    public void taskFinished(int threadindex) {
        athread = threads.get(threadindex);
        usedtasks.add(athread.task);
        athread.task.setNotExecuted();
        usedtaskssize++;
        athread.task = null;
        athread = null;
        waitingthreads[threadindex] = true;
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void finalize() {
        try {
            for (; ui < threadssize; ui++) {
                threads.remove(ui).finalize();
            }
            for (ui = 0; ui < taskssize; ui++) {
                tasks.remove(ui).finalize();
            }
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static PriorityScheduler getPriorityScheduler() {
        return ThreadManager.scheduler;
    }

}
