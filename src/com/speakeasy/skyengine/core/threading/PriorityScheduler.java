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

import com.speakeasy.skyengine.core.timer.Timing;
import com.speakeasy.skyengine.entity.player.Camera;
import com.speakeasy.skyengine.utils.math.Vector3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Kevin Owen Burress <speakeasysky@gmail.com>
 */
public class PriorityScheduler extends Thread {

    private static ArrayList<Task> tasks;
    private static ArrayList<Task> thesetasks;
    private static int taskssize;

    private static Timing atiming;

    private static Vector3[] camera = new Vector3[2];

    private static Comparator comparator;

    private static ArrayList<Float> movingAverage = new ArrayList<Float>();
    private static int MAX_AVERAGE = 255;
    private static boolean average_max = false;

    private static float uavg;

    private static int ui;
    private static int ui2;
    private static float uf;

    public PriorityScheduler(ArrayList<Task> tasks, int tasksize) {
        this.comparator = (Comparator<Task>) (Task t1, Task t2) -> Float.compare(t1.priority, t2.priority);
        this.tasks = tasks;
        this.taskssize = tasksize;
        camera = Camera.getPositionRotation();
        this.setPriority(9);
    }

    // TODO Assign values to frustum and distance. Make near frustum and off screen (far) frustum.
    private void weighTasks() {
        ;
    }

    private void averageTasks() {
        updateTasks();
        ui2 = thesetasks.size();
        uavg = 0;
        for (ui = 0; ui < ui2; ui++) {
            uavg += ((Task) (thesetasks.get(ui))).priority;
        }
        uavg = uavg / ui2;
        ui2 = 0;
        ui = 0;
        calculateMovingAverage();
    }

    private void calculateMovingAverage() {
        ui = movingAverage.size();
        if (!average_max) {
            if (ui < MAX_AVERAGE) {
                movingAverage.add(uavg);
                ui++;
                if (ui == MAX_AVERAGE) {
                    average_max = true;
                }
            } else {
                average_max = true;
                ;
            }
            uf = 0;
            for (ui2 = 0; ui2 < ui; ui2++) {
                uf += movingAverage.get(ui2);

            }
            uf += uavg;
            movingAverage.add(uf / (movingAverage.size() + 1));
        } else {
            uf = 0;
            for (ui2 = 0; ui2 < ui; ui2++) {
                uf += movingAverage.get(ui2);

            }
            uf += uavg;
            movingAverage.add(uf / (movingAverage.size() + 1));
            movingAverage.remove(0);
        }
    }

    private void updateTasks() {
        thesetasks = (ArrayList<Task>) ThreadManager.tasks.clone();
    }

    private void sortTasks() {
        Collections.sort(thesetasks, comparator);
    }

}
