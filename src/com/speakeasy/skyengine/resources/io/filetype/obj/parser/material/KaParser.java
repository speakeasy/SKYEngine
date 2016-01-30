package com.speakeasy.skyengine.resources.io.filetype.obj.parser.material;

import com.speakeasy.skyengine.resources.io.filetype.obj.Vertex;
import com.speakeasy.skyengine.resources.io.filetype.obj.Material;
import com.speakeasy.skyengine.resources.io.filetype.obj.WavefrontObject;
import com.speakeasy.skyengine.resources.io.filetype.obj.parser.LineParser;

public class KaParser extends LineParser {

    Vertex ka = null;

    @Override
    public void incoporateResults(WavefrontObject wavefrontObject) {
        Material currentMaterial = wavefrontObject.getCurrentMaterial();
        currentMaterial.setKa(ka);

    }

    @Override
    public void parse() {
        ka = new Vertex();

        try {
            ka.setX(Float.parseFloat(words[1]));
            ka.setY(Float.parseFloat(words[2]));
            ka.setZ(Float.parseFloat(words[3]));
        } catch (Exception e) {
            throw new RuntimeException("VertexParser Error");
        }
    }

}
