package com.speakeasy.skyengine.entity.world;

import com.speakeasy.skyengine.entity.player.Frustum;
import com.speakeasy.skyengine.entity.world.heightmap.WorldChunkHeightMapGenerator;

public class WorldMesh {

    private static final int SIZE = 512;

    private static World world;
    private static WorldChunkHeightMapGenerator cmgen;

    private int[][] mesh = new int[SIZE][SIZE];

    public final int cx;
    public final int cy;

    public WorldMesh(int cx, int cy, World world, WorldChunkHeightMapGenerator cmgen) {
        this.cx = cx;
        this.cy = cy;
        this.world = world;
        this.cmgen = cmgen;
    }

    public WorldMesh(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;
    }

    public void init() {
        cmgen.generate();
    }

    public int getMeshPointHeight(int x, int y) {
        return mesh[x][y];
    }

    public void makeChunk(float cx, float cy, WorldChunkHeightMapGenerator cmgen) {
        cmgen.generate(cx, cy);
    }

    public int inFrustum() {
        return Frustum.cubeInFrustum((512 * cx) - 256, (512 * cy) - 256, 256, SIZE);
    }
}
