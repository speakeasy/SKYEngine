package com.speakeasy.skyengine.resources.io.filetype.obj.parser;

import com.speakeasy.skyengine.resources.io.filetype.obj.WavefrontObject;

public abstract class LineParser {

    protected String[] words = null;

    public void setWords(String[] words) {
        this.words = words;
    }

    public abstract void parse();

    public abstract void incoporateResults(WavefrontObject wavefrontObject);

}
