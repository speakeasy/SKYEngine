package com.speakeasy.skyengine.resources.io.filetype.obj.parser.material;

import com.speakeasy.skyengine.resources.io.filetype.obj.Vertex;
import com.speakeasy.skyengine.resources.io.filetype.obj.Material;
import com.speakeasy.skyengine.resources.io.filetype.obj.WavefrontObject;
import com.speakeasy.skyengine.resources.io.filetype.obj.parser.LineParser;

public class KsParser extends LineParser {

    Vertex ks = null;

    @Override
    public void incoporateResults(WavefrontObject wavefrontObject) {
        Material currentMaterial = wavefrontObject.getCurrentMaterial();
        currentMaterial.setKs(ks);

    }

    @Override
    public void parse() {
        ks = new Vertex();

        try {
            ks.setX(Float.parseFloat(words[1]));
            ks.setY(Float.parseFloat(words[2]));
            ks.setZ(Float.parseFloat(words[3]));
        } catch (Exception e) {
            throw new RuntimeException("VertexParser Error");
        }
    }

}
