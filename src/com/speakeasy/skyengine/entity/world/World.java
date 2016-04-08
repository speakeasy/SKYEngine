package com.speakeasy.skyengine.entity.world;

import com.speakeasy.skyengine.entity.world.heightmap.WorldChunkHeightMapGenerator;
import com.speakeasy.skyengine.utils.math.FloatArray;
import java.io.IOException;

public class World {

    private static int WORLD_SIZE_X = 1;
    private static int WORLD_SIZE_Y = 1;
    private static WorldMesh[][] meshes;
    private WorldChunkHeightMapGenerator cmgen;

    public World(int x, int y) {
        meshes = new WorldMesh[WORLD_SIZE_X][WORLD_SIZE_Y];
        createWorld();
    }

    public WorldMesh getChunk(int i, int j) {
        if (i >= 0 && j >= 0 && i < meshes.length && j < meshes[0].length) {
            return meshes[i][j];
        }
        return null;
    }

    public int getHeight(int cx, int cy, int x, int y) {
        return meshes[cx][cy].getMeshPointHeight(x, y);
    }

    public void createWorld() {
        for (int i = 0; i < meshes.length; i++) {
            for (int j = 0; j < meshes[0].length; j++) {
                meshes[i][j] = new WorldMesh(i, j, this, cmgen);
            }
        }

        for (int i = 0; i < meshes.length; i++) {
            for (int j = 0; j < meshes[0].length; j++) {
                meshes[i][j].init();
            }
        }
    }

    public void render(FloatArray vertices, FloatArray colorVertices) throws IOException {
        for (int i = 0; i < meshes.length; i++) {
            for (int j = 0; j < meshes[0].length; j++) {
                int points = meshes[i][j].inFrustum();
                if (points == 2) {
                    // WorldMesh is entirely within frustum.
                    meshes[i][j].renderNoCheck(vertices, colorVertices);
                } else if (points == 0) {
                    // WorldMesh is entirely outside frustum.
                } else {
                    // WorldMesh is partial.
                    meshes[i][j].render(vertices, colorVertices);
                }
            }
        }
    }

    public int getWidth() {
        return meshes.length;
    }
}
