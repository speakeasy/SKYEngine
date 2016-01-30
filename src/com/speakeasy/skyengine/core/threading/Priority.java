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
public class Priority {

    private PriorityLevel level;

    public Priority() {
        ;
    }

    public Priority(PriorityLevel level) {
        this.level = level;
    }

    public PriorityLevel getPriorityLevel() {
        PriorityLevel thelevel = level;
        return thelevel;
    }

    public void setPriorityLevel(PriorityLevel level) {
        this.level = level;
    }
}
