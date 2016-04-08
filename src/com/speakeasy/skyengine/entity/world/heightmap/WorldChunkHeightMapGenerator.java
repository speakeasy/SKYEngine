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
package com.speakeasy.skyengine.entity.world.heightmap;

import com.speakeasy.skyengine.utils.math.OpenSimplexNoise;

/**
 *
 * @author Kevin Owen Burress <speakeasysky@gmail.com>
 */
public class WorldChunkHeightMapGenerator {

    private long Seed = (long) Math.random();
    private int Size = 512;
    private static double featureSize = 100; // for OpenSimplexNoise
    private ChunkHeightMapArray heightmap;
    private OpenSimplexNoise noise;

    public WorldChunkHeightMapGenerator(int size, long seed) {
        Size = size;
        Seed = seed;

        // Init HeightMap
        heightmap = new ChunkHeightMapArray(Size);

        // Init OpenSimplexNoise
        noise = new OpenSimplexNoise(Seed);

    }

    public void generate() {

        // Generate base height map from noise
        for (int y = 0; y < Size; y++) {
            for (int x = 0; x < Size; x++) {
                heightmap.heightmap[x][y] = noise.eval(x / featureSize, y / featureSize, 0.0);
            }
        }
        // Perturb
        Perturb(16, 16);

        // Erode
        for (int i = 0; i < 30; i++) {
            Erode(18.0);
        }

        // Smooth
        Smooth();

        // Normalize
        Normalize();
    }

    public void generate(float chx, float chy) {

        // Generate base height map from noise
        for (int y = 0; y < Size; y++) {
            for (int x = 0; x < Size; x++) {
                heightmap.heightmap[x][y] = noise.eval((x + chx) / featureSize, (y + chy) / featureSize, 0.0);
            }
        }
        // Perturb
        Perturb(16, 16);

        // Erode
        for (int i = 0; i < 30; i++) {
            Erode(18.0);
        }

        // Smooth
        Smooth();

        // Normalize
        Normalize();
    }

    public double[][] getHeightMap() {
        return heightmap.heightmap;
    }

    private void Perturb(double f, double d) {
        int u, v;
        double temp[][] = new double[Size][Size];

        for (int i = 0; i < Size; ++i) {
            for (int j = 0; j < Size; ++j) {
                u = i + (int) (noise.eval(f * i / (double) Size, f * j / (double) Size, 0) * d);
                v = j + (int) (noise.eval(f * i / (double) Size, f * j / (double) Size, 1) * d);
                if (u < 0) {
                    u = 0;
                }
                if (u >= Size) {
                    u = Size - 1;
                }
                if (v < 0) {
                    v = 0;
                }
                if (v >= Size) {
                    v = Size - 1;
                }
                temp[i][j] = heightmap.heightmap[u][v];
            }
        }
        heightmap.setHeightMap(temp);
    }

    private void Erode(double smoothness) {
        for (int i = 1; i < Size - 1; i++) {
            for (int j = 1; j < Size - 1; j++) {
                double d_max = 0.0f;
                int match[] = {0, 0};

                for (int u = -1; u <= 1; u++) {
                    for (int v = -1; v <= 1; v++) {
                        if (Math.abs(u) + Math.abs(v) > 0) {
                            double d_i = heightmap.heightmap[i][j] - heightmap.heightmap[i + u][j + v];
                            if (d_i > d_max) {
                                d_max = d_i;
                                match[0] = u;
                                match[1] = v;
                            }
                        }
                    }
                }

                if (0 < d_max && d_max <= (smoothness / (double) Size)) {
                    double d_h = 0.5f * d_max;
                    heightmap.heightmap[i][j] -= d_h;
                    heightmap.heightmap[i + match[0]][j + match[1]] += d_h;
                }
            }
        }
    }

    private void Smooth() {
        for (int i = 1; i < Size - 1; ++i) {
            for (int j = 1; j < Size - 1; ++j) {
                double total = 0.0;

                for (int u = -1; u <= 1; u++) {
                    for (int v = -1; v <= 1; v++) {
                        total += heightmap.heightmap[i + u][j + v];
                    }
                }

                heightmap.heightmap[i][j] = total / 9.0;
            }
        }
    }

    private void Normalize() {
        for (int y = 0; y < Size; y++) {
            for (int x = 0; x < Size; x++) {
                heightmap.heightmap[x][y] = (heightmap.heightmap[x][y] - -1) / (1 - -1);
            }
        }
    }
}
