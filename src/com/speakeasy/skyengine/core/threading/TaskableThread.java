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

import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskableThread extends Thread {

    Task task;
    private PriorityLevel plevel;
    private float priority = 5;
    int index;
    
    private ThreadManager threadmanager;

    TaskableThread(int index, ThreadManager threadmanager) {
        this.setPriority(NORM_PRIORITY);
        this.index = index;
        this.threadmanager = threadmanager;
    }

    @Override
    public void start() {
        this.priority = (int) plevel.getPriority();
        task.executeTask();
        threadmanager.taskFinished(index);
    }

    public void assignTask(Task task) {
        this.task = task;
        this.start();
    }
    
    @Override
    public void finalize() {
        try {
            threadmanager.taskFinished(this.index);
            threadmanager = null;
        } finally {
            try {
                super.finalize();
            } catch (Throwable ex) {
                Logger.getLogger(TaskableThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
