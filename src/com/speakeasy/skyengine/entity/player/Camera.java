package com.speakeasy.skyengine.entity.player;

import com.speakeasy.skyengine.core.Game;
import com.speakeasy.skyengine.utils.math.Vector3;
import java.nio.DoubleBuffer;
import java.util.Observable;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
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
    private boolean mouseLocked;
    private float newX;
    private float newY;
    private float deltaX;
    private float deltaY;

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
        glfwSetInputMode(windowID, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
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
        glfwSetCursorPos(windowID, Game.WIDTH * 0.5f, Game.HEIGHT * 0.5f);
        return position.toString();
    }

    public void updatePosition(int delta) {
        glfwPollEvents();
        if (glfwGetKey(windowID, GLFW_KEY_W) == 1) {
            position.setX(position.getX() - (float) (Math.sin(-rotation.getZ() * Math.PI / 180) * speed * delta));
            position.setY(position.getY() - (float) (Math.cos(-rotation.getZ() * Math.PI / 180) * speed * delta));
        } else {
        }
        if (glfwGetKey(windowID, GLFW_KEY_S) == 1) {
            position.setX(position.getX() + (float) (Math.sin(-rotation.getZ() * Math.PI / 180) * speed * delta));
            position.setY(position.getY() + (float) (Math.cos(-rotation.getZ() * Math.PI / 180) * speed * delta));
        }
        if (glfwGetKey(windowID, GLFW_KEY_A) == 1) {
            position.setX(position.getX() + (float) (Math.sin((-rotation.getZ() - 90) * Math.PI / 180) * speed * delta));
            position.setY(position.getY() + (float) (Math.cos((-rotation.getZ() - 90) * Math.PI / 180) * speed * delta));
        }
        if (glfwGetKey(windowID, GLFW_KEY_D) == 1) {
            position.setX(position.getX() + (float) (Math.sin((-rotation.getZ() + 90) * Math.PI / 180) * speed * delta));
            position.setY(position.getY() + (float) (Math.cos((-rotation.getZ() + 90) * Math.PI / 180) * speed * delta));
        }
        if (glfwGetKey(windowID, GLFW_KEY_SPACE) == 1) {
            position.setZ(position.getZ() + speed * delta);
        }
        if (glfwGetKey(windowID, GLFW.GLFW_KEY_LEFT_SHIFT) == 1) {
            position.setZ(position.getZ() - speed * delta);
        }
    }

    public void updateRotation(int delta) {
        //Mouse Input for looking around...
        if (glfwGetMouseButton(windowID, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
            glfwSetCursorPos(windowID, 800 / 2, 600 / 2);
            mouseLocked = true;
        }
        if (mouseLocked) {
            DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

            glfwGetCursorPos(windowID, x, y);

            x.rewind();
            y.rewind();

            newX = (float) x.get();
            newY = (float) y.get();

            deltaX = (float) (newX - (Game.WIDTH * 0.5));
            deltaY = (float) (newY - (Game.HEIGHT * 0.5));

            if (rotation.getZ() + deltaX >= 360) {
                rotation.setZ(rotation.getZ() + deltaX - 360);
            } else if (rotation.getZ() + deltaX < 0) {
                rotation.setZ(rotation.getZ() + deltaX + 360);
            } else {
                rotation.setZ(rotation.getZ() + deltaX);
            }

            if (rotation.getX() - deltaY >= -89 && rotation.getX() - deltaY <= 89) {
                rotation.setX(rotation.getX() - deltaY);
            } else if (rotation.getX() - deltaY < -89) {
                rotation.setX(-89);
            } else if (rotation.getX() - deltaY > 89) {
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
