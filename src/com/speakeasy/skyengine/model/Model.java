package com.speakeasy.skyengine.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

import com.obj.Face;
import com.obj.Group;
import com.obj.Material;
import com.obj.Texture;
import com.obj.TextureLoader;
import com.obj.TextureCoordinate;
import com.obj.Vertex;
import com.obj.WavefrontObject;

/**
 * @author Oskar
 */
public class Model {

    private int _callListID;
    private boolean _callListCompiled;

    private final ArrayList<Vector3f> _vertexList;
    private final ArrayList<Vector3f> _normalList;
    private final ArrayList<Vector3f> _texcoordList;
    private final ArrayList<ModelMesh> _meshList;
    private final HashMap<String, ModelMaterial> _materials = new HashMap<String, ModelMaterial>();

    private TextureLoader textureLoader;

    private WavefrontObject _obj;

    private boolean enableSmoothShading = true;

    /*
     private final List<Vector3f> vertices = new ArrayList<Vector3f>();
     private final List<Vector2f> textureCoordinates = new ArrayList<Vector2f>();
     private final List<Vector3f> normals = new ArrayList<Vector3f>();
     private final List<Face> faces = new ArrayList<Face>();
     private final HashMap<String, Material> materials = new HashMap<String, Material>();
     private boolean enableSmoothShading = true;
     */
    public Model() {
        _vertexList = new ArrayList<Vector3f>();
        _normalList = new ArrayList<Vector3f>();
        _texcoordList = new ArrayList<Vector3f>();
        _meshList = new ArrayList<ModelMesh>();
        _obj = null;
        textureLoader = TextureLoader.instance();
    }

    public void load(String name) {
        load(name, 1, 1, 1);
    }

    public void load(String name, float s) {
        load(name, s, s, s);
    }

    public void load(String name, float sx, float sy, float sz) {
        _obj = new WavefrontObject(name, sx, sy, sz);

        ArrayList<Group> groups = _obj.getGroups();
        for (int gi = 0; gi < groups.size(); gi++) {
            Group g = groups.get(gi);
            Material gm = g.getMaterial();

            ModelMesh mesh = new ModelMesh();
            mesh.setName(g.getName());

            if (gm != null) {
                if (gm.getKa() != null) {
                    mesh._material._ambient = new float[]{gm.getKa().getX(), gm.getKa().getY(), gm.getKa().getZ()};
                } else {
                    mesh._material._diffuse = new float[]{0.0f, 0.0f, 0.0f};
                }
                if (gm.getKd() != null) {
                    mesh._material._diffuse = new float[]{gm.getKd().getX(), gm.getKd().getY(), gm.getKd().getZ()};
                } else {
                    mesh._material._diffuse = new float[]{0.5f, 0.5f, 0.5f};
                }
                if (gm.getKs() != null) {
                    mesh._material._specular = new float[]{gm.getKs().getX(), gm.getKs().getY(), gm.getKs().getZ()};
                } else {
                    mesh._material._specular = new float[]{0, 0, 0};
                }
                mesh._material._shininess = gm.getShininess();

                if (gm.texName != null && gm.texName.length() > 0) {
                    Texture _tex = textureLoader.loadTexture(gm.texName);

                    mesh._material._texId = _tex.getTextureID();
                    //System.out.println( "mesh._material._texId: " + mesh._material._texId  );
                }
            } else {
                // default material
                mesh._material._ambient = new float[]{0, 0, 0, 0};
                mesh._material._diffuse = new float[]{0.5f, 0.5f, 0.5f, 1};
                mesh._material._specular = new float[]{1, 1, 1, 1};
                mesh._material._shininess = 64.0f;
                mesh._material._texId = 0;
            }

            for (int fi = 0; fi < g.getFaces().size(); fi++) {
                Face f = g.getFaces().get(fi);
                int[] idx = f.vertIndices;
                int[] nidx = f.normIndices;
                int[] tidx = f.texIndices;

                if (f.getType() == Face.GL_TRIANGLES) {
                    ModelFace face = new ModelFace();
                    face._a = idx[0];
                    face._b = idx[1];
                    face._c = idx[2];
                    face._na = nidx[0];
                    face._nb = nidx[1];
                    face._nc = nidx[2];
                    face._ta = tidx[0];
                    face._tb = tidx[1];
                    face._tc = tidx[2];
                    mesh.addFace(face);
                } else if (f.getType() == Face.GL_QUADS) {
                    ModelFace face = new ModelFace();
                    ModelFace face2 = new ModelFace();

                    face._a = idx[0];
                    face._b = idx[1];
                    face._c = idx[2];
                    face._na = nidx[0];
                    face._nb = nidx[1];
                    face._nc = nidx[2];
                    face._ta = tidx[0];
                    face._tb = tidx[1];
                    face._tc = tidx[2];

                    face2._a = idx[0];
                    face2._b = idx[2];
                    face2._c = idx[3];
                    face2._na = nidx[0];
                    face2._nb = nidx[2];
                    face2._nc = nidx[3];
                    face2._ta = tidx[0];
                    face2._tb = tidx[2];
                    face2._tc = tidx[3];

                    mesh.addFace(face);
                    mesh.addFace(face2);
                }
            }

            for (int vi = 0; vi < _obj.getVertices().size(); vi++) {
                Vertex v = (Vertex) _obj.getVertices().get(vi);
                _vertexList.add(new Vector3f(v.getX(), v.getY(), v.getZ()));
            }

            for (int vi = 0; vi < _obj.getNormals().size(); vi++) {
                Vertex v = (Vertex) _obj.getNormals().get(vi);
                _normalList.add(new Vector3f(v.getX(), v.getY(), v.getZ()));
            }

            for (int vi = 0; vi < _obj.getTextures().size(); vi++) {
                TextureCoordinate tc = (TextureCoordinate) _obj.getTextures().get(vi);
                _texcoordList.add(new Vector3f(tc.getU(), tc.getV(), tc.getW()));
            }

            /*      for( int vi=0; vi<g.indices.size(); vi++ )
             {
             int tc = (int)g.indices.get( vi );
             mesh.addVertex( new Vector3( tc.getX(), tc.getY(), tc.getZ()) );
             }

             for( int vi=0; vi<g.vertices.size(); vi++ )
             {
             Vertex tc = (Vertex)g.vertices.get( vi );
             mesh.addVertex( new Vector3( tc.getX(), tc.getY(), tc.getZ()) );
             }

             for( int vi=0; vi<g.normals.size(); vi++ )
             {
             Vertex tc = (Vertex)g.normals.get( vi );
             mesh.addNormal( new Vector3( tc.getX(), tc.getY(), tc.getZ()) );
             }

             for( int vi=0; vi<g.texcoords.size(); vi++ )
             {
             TextureCoordinate tc = (TextureCoordinate)g.texcoords.get( vi );
             mesh.addTexCoord( new Vector3( tc.getU(), tc.getV(), tc.getW()) );
             }*/
            // Finally add mesh to scene
            addMesh(mesh);
        }
    }

    public void addMesh(ModelMesh mesh) {
        _meshList.add(mesh);
    }

    public void enableStates() {
        if (hasTextureCoordinates()) {
            glEnable(GL_TEXTURE_2D);
        }
        if (isSmoothShadingEnabled()) {
            glShadeModel(GL_SMOOTH);
        } else {
            glShadeModel(GL_FLAT);
        }
    }

    public boolean hasTextureCoordinates() {
        return getTextureCoordinates().size() > 0;
    }

    public boolean hasNormals() {
        return getNormals().size() > 0;
    }

    public ArrayList<Vector3f> getVertices() {
        return _vertexList;
    }

    public ArrayList<Vector3f> getTextureCoordinates() {
        return _texcoordList;
    }

    public ArrayList<Vector3f> getNormals() {
        return _normalList;
    }

    public boolean isSmoothShadingEnabled() {
        return enableSmoothShading;
    }

    public void setSmoothShadingEnabled(boolean smoothShadingEnabled) {
        this.enableSmoothShading = smoothShadingEnabled;
    }

    public HashMap<String, ModelMaterial> getMaterials() {
        return _materials;
    }

    public ModelMesh getMeshByIdx(int i) {
        return _meshList.get(i);
    }

    public ModelMesh getMeshByName(String name) {
        for (int i = 0; i < _meshList.size(); i++) {
            ModelMesh m = _meshList.get(i);
            if (m._name.equals(name)) {
                return _meshList.get(i);
            }
        }

        return null;
    }

}
