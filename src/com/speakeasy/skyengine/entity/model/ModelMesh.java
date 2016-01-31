package com.speakeasy.skyengine.entity.model;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;

public class ModelMesh {

    String _name;
    ModelMaterial _material;
    ArrayList<Vector3f> _vertexList;
    ArrayList<Vector3f> _texcoordList;
    ArrayList<Vector3f> _normalList;
    ArrayList<ModelFace> _faceList;

    public ModelMesh() {
        _vertexList = new ArrayList<Vector3f>();
        _texcoordList = new ArrayList<Vector3f>();
        _normalList = new ArrayList<Vector3f>();
        _faceList = new ArrayList<ModelFace>();

        _material = new ModelMaterial();
    }

    public void setName(String n) {
        _name = n;
    }

    public void addVertex(Vector3f v) {
        _vertexList.add(v);
    }

    public void addTexCoord(Vector3f v) {
        _texcoordList.add(v);
    }

    public void addNormal(Vector3f v) {
        _normalList.add(v);
    }

    // Add faces
    public void addFace(ModelFace f) {
        _faceList.add(f);
    }

    public void addFace(int a, int b, int c) {
        _faceList.add(new ModelFace(a, b, c));
    }

    public String getName() {
        return _name;
    }

    public ArrayList<Vector3f> getVertexList() {
        return _vertexList;
    }

    public ArrayList<Vector3f> getNormalList() {
        return _normalList;
    }

    public ArrayList<Vector3f> getTexCoordList() {
        return _texcoordList;
    }

    public ArrayList<ModelFace> getFaceList() {
        return _faceList;
    }

}
