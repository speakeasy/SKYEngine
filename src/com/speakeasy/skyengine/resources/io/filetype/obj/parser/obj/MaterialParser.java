package com.speakeasy.skyengine.resources.io.filetype.obj.parser.obj;

import com.speakeasy.skyengine.resources.io.filetype.obj.Material;
import com.speakeasy.skyengine.resources.io.filetype.obj.WavefrontObject;
import com.speakeasy.skyengine.resources.io.filetype.obj.parser.LineParser;

public class MaterialParser extends LineParser {

    String materialName = "";

    @Override
    public void parse() {
        materialName = words[1];
    }

    @Override
    public void incoporateResults(WavefrontObject wavefrontObject) {
        Material newMaterial = wavefrontObject.getMaterials().get(materialName);
        wavefrontObject.getCurrentGroup().setMaterial(newMaterial);

    }

}
