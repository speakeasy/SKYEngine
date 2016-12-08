package com.speakeasy.skyengine.entity.player;

import com.speakeasy.skyengine.core.Game;
import com.speakeasy.skyengine.utils.math.Vector3;
import java.util.Observable;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
//import net.java.games.input.Controller;
import org.lwjgl.opengl.GL11;

public class Camera extends Observable {

    private Vector3 position = new Vector3();
    private Vector3 oldPosition = new Vector3();

    private Vector3 rotation = new Vector3();
    private Vector3 oldRotation = new Vector3();

    private final float speed = 0.05f;
    //private Controller controller;
    private Vector3[] positionPS = new Vector3[2];

    private GLFWKeyCallback keyCallback;
    private GLFWErrorCallback errorCallback;
    
    private long windowID;
    
    public Camera(long windowID) {
        this.windowID = windowID;
    }

    public float getCamX() {
        return position.getX();
    }

    public float getCamY() {
        return position.getY();
    }

    public float getCamZ() {
        return position.getZ() + 2;
    }

    public float getDistance(float x, float y, float z) {
        return new Vector3(x - position.getX(), y - position.getY(), z - position.getZ()).getMagnitude();
    }

    public float getFacingHoriz() {
        return (270 + rotation.getZ()) % 360;
    }

    public float getRelativeDirectionHoriz(int x, int y) {
        return (float) (getFacingHoriz() - Math.atan2(position.getY() - y, position.getX() - x));
    }

    public float getFacingVert() {
        return -rotation.getX();
    }

    public void init() {
        /*if (ControllerEnvironment.getDefaultEnvironment().getControllers().length > 0) {
         for (Controller controller : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
         if (controller.getType() == Controller.Type.GAMEPAD) {
         Camera.controller = controller;
         break;
         }
         }
         } else { */

        positionPS[0] = position;
        positionPS[1] = rotation;
        glfwSetInputMode(windowID , GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        //}
    }

    public boolean hasNotMoved() {
        return oldPosition.equals(position) && rotation.equals(oldRotation);
    }

    public Vector3[] getPositionRotation() {
        return positionPS;
    }

    public String update(int delta) {
        oldPosition.set(position);
        oldRotation.set(rotation);
        updateRotation(delta);
        updatePosition(delta);
        updatePerspective();
        glfwSetCursorPos(windowID, Game.WINDOW_WIDTH * 0.5f, Game.WINDOW_HEIGHT * 0.5f);
        return position.toString();
    }

    public void updatePosition(int delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.setX(position.getX() - (float) (Math.sin(-rotation.getZ() * Math.PI / 180) * speed * delta));
            position.setY(position.getY() - (float) (Math.cos(-rotation.getZ() * Math.PI / 180) * speed * delta));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.setX(position.getX() + (float) (Math.sin(-rotation.getZ() * Math.PI / 180) * speed * delta));
            position.setY(position.getY() + (float) (Math.cos(-rotation.getZ() * Math.PI / 180) * speed * delta));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.setX(position.getX() + (float) (Math.sin((-rotation.getZ() - 90) * Math.PI / 180) * speed * delta));
            position.setY(position.getY() + (float) (Math.cos((-rotation.getZ() - 90) * Math.PI / 180) * speed * delta));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.setX(position.getX() + (float) (Math.sin((-rotation.getZ() + 90) * Math.PI / 180) * speed * delta));
            position.setY(position.getY() + (float) (Math.cos((-rotation.getZ() + 90) * Math.PI / 180) * speed * delta));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            position.setZ(position.getZ() + speed * delta);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            position.setZ(position.getZ() - speed * delta);
        }
    }

    public void updateRotation(int delta) {
        //Mouse Input for looking around...
        if (Mouse.isGrabbed()) {
            float mouseDX = Mouse.getDX() * 0.128f;
            float mouseDY = Mouse.getDY() * 0.128f;

            if (rotation.getZ() + mouseDX >= 360) {
                rotation.setZ(rotation.getZ() + mouseDX - 360);
            } else if (rotation.getZ() + mouseDX < 0) {
                rotation.setZ(rotation.getZ() + mouseDX + 360);
            } else {
                rotation.setZ(rotation.getZ() + mouseDX);
            }

            if (rotation.getX() - mouseDY >= -89 && rotation.getX() - mouseDY <= 89) {
                rotation.setX(rotation.getX() - mouseDY);
            } else if (rotation.getX() - mouseDY < -89) {
                rotation.setX(-89);
            } else if (rotation.getX() - mouseDY > 89) {
                rotation.setX(89);
            }
        }
    }

    public void updatePerspective() {
        GL11.glRotatef(rotation.getX(), 1, 0, 0);
        GL11.glRotatef(rotation.getY(), 0, 0, 1);
        GL11.glRotatef(rotation.getZ(), 0, 1, 0);
        GL11.glTranslatef(-position.getX(), -position.getZ() - 2.0f, -position.getY());
    }
}