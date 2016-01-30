package com.speakeasy.skyengine.resources.io.filetype.obj.parser.material;

import com.speakeasy.skyengine.resources.io.filetype.obj.Material;
import com.speakeasy.skyengine.resources.io.filetype.obj.WavefrontObject;
import com.speakeasy.skyengine.resources.io.filetype.obj.parser.LineParser;

public class NsParser extends LineParser {

    float ns;

    @Override
    public void incoporateResults(WavefrontObject wavefrontObject) {
        Material currentMaterial = wavefrontObject.getCurrentMaterial();
        currentMaterial.setShininess(ns);

    }

    @Override
    public void parse() {
        try {
            ns = Float.parseFloat(words[1]);
        } catch (Exception e) {
            throw new RuntimeException("VertexParser Error");
        }
    }

}
