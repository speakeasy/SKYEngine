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

/**
 *
 * @author SpeaKeasY
 */
public enum PriorityLevel {

    MINIMUM(1.285714285714286f),
    VERY_LOW(2.571428571428571f),
    LOW(3.857142857142857f),
    MEDIUM(5.142857142857143f),
    HIGH(6.428571428571429f),
    VERY_HIGH(7.714285714285714f),
    MAXIMUM(9f);

    private final float priority;

    PriorityLevel(float priority) {
        this.priority = priority;
    }

    public float getPriority() {
        return priority;
    }
}
