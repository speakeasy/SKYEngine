package com.speakeasy.skyengine.model;

public class ModelMaterial {

    float[] _ambient;
    float[] _diffuse;
    float[] _specular;
    float _shininess;
    int _texId;

    public ModelMaterial() {
        //_ambient = new float[3];
        //_diffuse = new float[3];
        //_specular = new float[3];
        _texId = -1;
    }

}
