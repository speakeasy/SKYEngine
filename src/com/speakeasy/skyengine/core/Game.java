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

import com.speakeasy.skyengine.core.timer.GameTimer;
import com.speakeasy.skyengine.entity.player.Camera;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 *
 * @author SpeaKeasY
 */
public class Game extends Thread {

    protected static GameTimer gametimer;
    public static Game game;
    protected static Camera camera;
    
    private final static int width = 800;
    private final static int height = 600;
    private final static int FRAME_RATE = 60;

    public Game(String title) {
        try {
            Display.setDisplayMode(new DisplayMode(width,height));
            Display.create();
        } catch (LWJGLException e) {
            System.out.println(e);
        }
        Display.setTitle(title);
        Game game = new Game();
        game.init();
        int delta = 0;
        
        gametimer = GameTimer.newGameTimer(true);
        camera = new Camera();
        Camera.init();
    }

}
