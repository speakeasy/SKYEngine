package com.speakeasy.skyengine.core;

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
 /*package com.speakeasy.skyengine.core;

import com.speakeasy.skyengine.core.timer.GameTimer;
import com.speakeasy.skyengine.entity.player.Camera;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 *
 * @author SpeaKeasY
 */
 /*public class Game extends Thread {

    protected static GameTimer gametimer;
    public static Game game;
    protected static Camera camera;

    private static int WINDOW_WIDTH = 800;
    private static int WINDOW_HEIGHT = 600;
    private static int FRAME_RATE = 60;

    public Game(String title) {
        if (glfwInit() != true) {
            System.err.println("Error initializing GLFW");
            System.exit(1);
        }

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        glfwWindowHint(GLFW_SAMPLES, 4);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        long windowID = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Terrain App - LWJGL3", NULL, NULL);

        if (windowID == NULL) {
            System.err.println("Error creating a window");
            System.exit(1);
        }
        game = this;
        gametimer = GameTimer.newGameTimer(true);
        camera = new Camera();
        Camera.init();
    }

}
 */
import com.speakeasy.skyengine.core.timer.GameTimer;
import com.speakeasy.skyengine.entity.player.Camera;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class Game {

    protected long windowID;
    public static Game game;

    protected static GameTimer gametimer;
    public static Camera camera;

    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public static int FRAME_RATE = 60;

    public Game() {
        if (!glfwInit()) {
            System.err.println("Error initializing GLFW");
            System.exit(1);
        }

        // Window Hints for OpenGL context
        glfwWindowHint(GLFW_SAMPLES, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        windowID = glfwCreateWindow(640, 480, "Terrain App - Test LWJGL3", NULL, NULL);

        if (windowID == NULL) {
            System.err.println("Error creating a window");
            System.exit(1);
        }

        glfwMakeContextCurrent(windowID);
        GL.createCapabilities();

        glfwSwapInterval(1);
    }

    public void init() {
        gametimer = GameTimer.newGameTimer(false);
        gametimer.start();
        camera = new Camera(windowID);
        camera.init();
    }

    public void update(float delta) {
    }

    public void render(float delta) {
    }

    public void dispose() {
    }

    public void start() {
        float now, last, delta;

        last = 0;

        // Initialise the Game
        init();

        // Loop continuously and render and update
        while (!glfwWindowShouldClose(windowID)) {
            // Get the time
            now = (float) glfwGetTime();
            delta = now - last;
            last = now;

            // Update and render
            update(delta);
            render(delta);

            // Poll the events and swap the buffers
            glfwPollEvents();
            glfwSwapBuffers(windowID);
        }

        // Dispose the game
        dispose();

        // Destroy the window
        glfwDestroyWindow(windowID);
        glfwTerminate();

        System.exit(0);
    }

    public static void main(String[] args) {
        game = new Game();
        game.start();
    }
}
