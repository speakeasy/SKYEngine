package com.speakeasy.skyengine.resources.io.filetype.obj.parser.material;

import com.speakeasy.skyengine.resources.io.filetype.obj.Vertex;
import com.speakeasy.skyengine.resources.io.filetype.obj.Material;
import com.speakeasy.skyengine.resources.io.filetype.obj.WavefrontObject;
import com.speakeasy.skyengine.resources.io.filetype.obj.parser.LineParser;

public class KdParser extends LineParser {

    Vertex kd = null;

    @Override
    public void incoporateResults(WavefrontObject wavefrontObject) {
        Material currentMaterial = wavefrontObject.getCurrentMaterial();
        currentMaterial.setKd(kd);

    }

    @Override
    public void parse() {
        kd = new Vertex();

        try {
            kd.setX(Float.parseFloat(words[1]));
            kd.setY(Float.parseFloat(words[2]));
            kd.setZ(Float.parseFloat(words[3]));
        } catch (Exception e) {
            throw new RuntimeException("VertexParser Error");
        }
    }

}
