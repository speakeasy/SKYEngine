package com.speakeasy.skyengine.utils.math;

//import processing.*;
import java.nio.*;

public class Matrix4 {

    Matrix4() {
        _M = new float[16];
        identity();
    }

    Matrix4(Matrix4 m) {
        copy(m);
    }

    void copy(Matrix4 m) {
        _M[0] = m._M[0];
        _M[4] = m._M[4];
        _M[8] = m._M[8];
        _M[12] = m._M[12];
        _M[1] = m._M[1];
        _M[5] = m._M[5];
        _M[9] = m._M[9];
        _M[13] = m._M[13];
        _M[2] = m._M[2];
        _M[6] = m._M[6];
        _M[10] = m._M[10];
        _M[14] = m._M[14];
        _M[3] = m._M[3];
        _M[7] = m._M[7];
        _M[11] = m._M[11];
        _M[15] = m._M[15];
    }

    FloatBuffer getFloatBuffer() {
        FloatBuffer fb = FloatBuffer.wrap(_M);
        return fb;
    }

    void identity() {
        _M[0] = 1;
        _M[4] = 0;
        _M[8] = 0;
        _M[12] = 0;
        _M[1] = 0;
        _M[5] = 1;
        _M[9] = 0;
        _M[13] = 0;
        _M[2] = 0;
        _M[6] = 0;
        _M[10] = 1;
        _M[14] = 0;
        _M[3] = 0;
        _M[7] = 0;
        _M[11] = 0;
        _M[15] = 1;
    }

    void scale(float s) {
        _M[0] *= s;
        _M[4] = 0;
        _M[8] = 0;
        _M[12] = 0;
        _M[1] = 0;
        _M[5] *= s;
        _M[9] = 0;
        _M[13] = 0;
        _M[2] = 0;
        _M[6] = 0;
        _M[10] *= s;
        _M[14] = 0;
        _M[3] = 0;
        _M[7] = 0;
        _M[11] = 0;
        _M[15] = 1;
    }

    Matrix4 translate(float X, float Y, float Z) {
        Matrix4 m = new Matrix4();
        m.identity();
        m._M[12] = X;
        m._M[13] = Y;
        m._M[14] = Z;
        return m;
    }

    void translate2(float X, float Y, float Z) {
        _M[12] = X;
        _M[13] = Y;
        _M[14] = Z;
    }

    public void rotateX(float a) {
        //identity();

        float ca = (float) Math.cos(a);
        float sa = (float) Math.sin(a);

        _M[0] = 1;
        _M[4] = 0;
        _M[8] = 0;
        _M[12] = 0;
        _M[1] = 0;
        _M[5] = ca;
        _M[9] = sa;
        _M[13] = 0;
        _M[2] = 0;
        _M[6] = -sa;
        _M[10] = ca;
        _M[14] = 0;
        _M[3] = 0;
        _M[7] = 0;
        _M[11] = 0;
        _M[15] = 1;
    }

    public void rotateY(float a) {
        //identity();

        float ca = (float) Math.cos(a);
        float sa = (float) Math.sin(a);

        _M[0] = ca;
        _M[4] = 0;
        _M[8] = sa;
        _M[12] = 0;
        _M[1] = 0;
        _M[5] = 1;
        _M[9] = 0;
        _M[13] = 0;
        _M[2] = -sa;
        _M[6] = 0;
        _M[10] = ca;
        _M[14] = 0;
        _M[3] = 0;
        _M[7] = 0;
        _M[11] = 0;
        _M[15] = 1;
    }

    public void rotateZ(float a) {
        //identity();

        float ca = (float) Math.cos(a);
        float sa = (float) Math.sin(a);

        _M[0] = ca;
        _M[4] = -sa;
        _M[8] = 0;
        _M[12] = 0;
        _M[1] = sa;
        _M[5] = ca;
        _M[9] = 0;
        _M[13] = 0;
        _M[2] = 0;
        _M[6] = 0;
        _M[10] = 0;
        _M[14] = 0;
        _M[3] = 0;
        _M[7] = 0;
        _M[11] = 0;
        _M[15] = 1;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // compute matrix based on 3 axis angles
    //////////////////////////////////////////////////////////////////////////////////////////////
    Matrix4 rotate(float Yaw, float Pitch, float Roll) {
        Matrix4 m = new Matrix4();
        float sinY, cosY, sinP, cosP, sinR, cosR;
        float ux, uy, uz, vx, vy, vz, nx, ny, nz;

        sinY = (float) Math.sin(Yaw);
        cosY = (float) Math.cos(Yaw);

        sinP = (float) Math.sin(Pitch);
        cosP = (float) Math.cos(Pitch);

        sinR = (float) Math.sin(Roll);
        cosR = (float) Math.cos(Roll);

        ux = cosY * cosR + sinY * sinP * sinR;
        uy = sinR * cosP;
        uz = cosY * sinP * sinR - sinY * cosR;

        vx = sinY * sinP * cosR - cosY * sinR;
        vy = cosR * cosP;
        vz = sinR * sinY + cosR * cosY * sinP;

        nx = cosP * sinY;
        ny = -sinP;
        nz = cosP * cosY;

        m._M[0] = ux;
        m._M[1] = uy;
        m._M[2] = uz;
        m._M[3] = 0.0f;
        m._M[4] = vx;
        m._M[5] = vy;
        m._M[6] = vz;
        m._M[7] = 0.0f;
        m._M[8] = nx;
        m._M[9] = ny;
        m._M[10] = nz;
        m._M[11] = 0.0f;
        m._M[12] = 0.0f;
        m._M[13] = 0.0f;
        m._M[14] = 0.0f;
        m._M[15] = 1.0f;

        return m;
    }

    public void add(Matrix4 m) {
        _M[0] += m._M[0];
        _M[4] += m._M[1];
        _M[8] += m._M[2];
        _M[12] += m._M[3];
        _M[1] += m._M[4];
        _M[5] += m._M[5];
        _M[9] += m._M[6];
        _M[13] += m._M[7];
        _M[2] += m._M[8];
        _M[6] += m._M[9];
        _M[10] += m._M[10];
        _M[14] += m._M[11];
        _M[3] += m._M[12];
        _M[7] += m._M[13];
        _M[11] += m._M[14];
        _M[15] += m._M[15];
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // row for column concatenation
    //////////////////////////////////////////////////////////////////////////////////////////////
    Matrix4 mul(Matrix4 m) {
        Matrix4 mat = new Matrix4();

        mat._M[0] = _M[0] * m._M[0] + _M[4] * m._M[1] + _M[8] * m._M[2] + _M[12] * m._M[3];
        mat._M[1] = _M[0] * m._M[4] + _M[4] * m._M[5] + _M[8] * m._M[6] + _M[12] * m._M[7];
        mat._M[2] = _M[0] * m._M[8] + _M[4] * m._M[9] + _M[8] * m._M[10] + _M[12] * m._M[11];
        mat._M[3] = _M[0] * m._M[12] + _M[4] * m._M[13] + _M[8] * m._M[15] + _M[12] * m._M[15];

        mat._M[4] = _M[1] * m._M[0] + _M[5] * m._M[1] + _M[9] * m._M[2] + _M[13] * m._M[3];
        mat._M[5] = _M[1] * m._M[4] + _M[5] * m._M[5] + _M[9] * m._M[6] + _M[13] * m._M[7];
        mat._M[6] = _M[1] * m._M[8] + _M[5] * m._M[9] + _M[9] * m._M[10] + _M[13] * m._M[11];
        mat._M[7] = _M[1] * m._M[12] + _M[5] * m._M[13] + _M[9] * m._M[15] + _M[13] * m._M[15];

        mat._M[8] = _M[2] * m._M[0] + _M[6] * m._M[1] + _M[10] * m._M[2] + _M[14] * m._M[3];
        mat._M[9] = _M[2] * m._M[4] + _M[6] * m._M[5] + _M[10] * m._M[6] + _M[14] * m._M[7];
        mat._M[10] = _M[2] * m._M[8] + _M[6] * m._M[9] + _M[10] * m._M[10] + _M[14] * m._M[11];
        mat._M[11] = _M[2] * m._M[12] + _M[6] * m._M[13] + _M[10] * m._M[15] + _M[14] * m._M[15];

        mat._M[12] = _M[3] * m._M[0] + _M[7] * m._M[1] + _M[11] * m._M[2] + _M[15] * m._M[3];
        mat._M[13] = _M[3] * m._M[4] + _M[7] * m._M[5] + _M[11] * m._M[6] + _M[15] * m._M[7];
        mat._M[14] = _M[3] * m._M[8] + _M[7] * m._M[9] + _M[11] * m._M[10] + _M[15] * m._M[11];
        mat._M[15] = _M[3] * m._M[12] + _M[7] * m._M[13] + _M[11] * m._M[15] + _M[15] * m._M[15];

        /*
         mat._M[0] = _M[0] * m._M[0] + _M[4] * m._M[1] + _M[8] * m._M[2] +  _M[12] * m._M[3];
         mat._M[4] = _M[0] * m._M[4] + _M[4] * m._M[5] + _M[8] * m._M[6] +  _M[12] * m._M[7];
         mat._M[8] = _M[0] * m._M[8] + _M[4] * m._M[9] + _M[8] * m._M[10] + _M[12] * m._M[11];
         mat._M[12] = _M[0] * m._M[12] + _M[4] * m._M[13] + _M[8] * m._M[15] + _M[12] * m._M[15];

         mat._M[1] = _M[1] * m._M[0] + _M[5] * m._M[1] + _M[9] * m._M[2] +  _M[13] * m._M[3];
         mat._M[5] = _M[1] * m._M[4] + _M[5] * m._M[5] + _M[9] * m._M[6] +  _M[13] * m._M[7];
         mat._M[9] = _M[1] * m._M[8] + _M[5] * m._M[9] + _M[9] * m._M[10] + _M[13] * m._M[11];
         mat._M[13] = _M[1] * m._M[12] + _M[5] * m._M[13] + _M[9] * m._M[15] + _M[13] * m._M[15];

         mat._M[2] = _M[2] * m._M[0] + _M[6] * m._M[1] + _M[10] * m._M[2] +  _M[14] * m._M[3];
         mat._M[6] = _M[2] * m._M[4] + _M[6] * m._M[5] + _M[10] * m._M[6] +  _M[14] * m._M[7];
         mat._M[10] = _M[2] * m._M[8] + _M[6] * m._M[9] + _M[10] * m._M[10] + _M[14] * m._M[11];
         mat._M[14] = _M[2] * m._M[12] + _M[6] * m._M[13] + _M[10] * m._M[15] + _M[14] * m._M[15];

         mat._M[3] = _M[3] * m._M[0] + _M[7] * m._M[1] + _M[11] * m._M[2] +  _M[15] * m._M[3];
         mat._M[7] = _M[3] * m._M[4] + _M[7] * m._M[5] + _M[11] * m._M[6] +  _M[15] * m._M[7];
         mat._M[11] = _M[3] * m._M[8] + _M[7] * m._M[9] + _M[11] * m._M[10] + _M[15] * m._M[11];
         mat._M[15] = _M[3] * m._M[12] + _M[7] * m._M[13] + _M[11] * m._M[15] + _M[15] * m._M[15];
         */
        return mat;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // column for row concatenation
    //////////////////////////////////////////////////////////////////////////////////////////////
    Matrix4 mul2(Matrix4 m) {
        Matrix4 mat = new Matrix4();

        mat._M[0] = _M[0] * m._M[0] + _M[1] * m._M[4] + _M[2] * m._M[8] + _M[3] * m._M[12];
        mat._M[1] = _M[0] * m._M[1] + _M[1] * m._M[5] + _M[2] * m._M[9] + _M[3] * m._M[13];
        mat._M[2] = _M[0] * m._M[2] + _M[1] * m._M[6] + _M[2] * m._M[10] + _M[3] * m._M[14];
        mat._M[3] = _M[0] * m._M[3] + _M[1] * m._M[7] + _M[2] * m._M[11] + _M[3] * m._M[15];

        mat._M[4] = _M[4] * m._M[0] + _M[5] * m._M[4] + _M[6] * m._M[8] + _M[7] * m._M[12];
        mat._M[5] = _M[4] * m._M[1] + _M[5] * m._M[5] + _M[6] * m._M[9] + _M[7] * m._M[13];
        mat._M[6] = _M[4] * m._M[2] + _M[5] * m._M[6] + _M[6] * m._M[10] + _M[7] * m._M[14];
        mat._M[7] = _M[4] * m._M[3] + _M[5] * m._M[7] + _M[6] * m._M[11] + _M[7] * m._M[15];

        mat._M[8] = _M[8] * m._M[0] + _M[9] * m._M[4] + _M[10] * m._M[8] + _M[11] * m._M[12];
        mat._M[9] = _M[8] * m._M[1] + _M[9] * m._M[5] + _M[10] * m._M[9] + _M[11] * m._M[13];
        mat._M[10] = _M[8] * m._M[2] + _M[9] * m._M[6] + _M[10] * m._M[10] + _M[11] * m._M[14];
        mat._M[11] = _M[8] * m._M[3] + _M[9] * m._M[7] + _M[10] * m._M[11] + _M[11] * m._M[15];

        mat._M[12] = _M[12] * m._M[0] + _M[13] * m._M[4] + _M[14] * m._M[8] + _M[15] * m._M[12];
        mat._M[13] = _M[12] * m._M[1] + _M[13] * m._M[5] + _M[14] * m._M[9] + _M[15] * m._M[13];
        mat._M[14] = _M[12] * m._M[2] + _M[13] * m._M[6] + _M[14] * m._M[10] + _M[15] * m._M[14];
        mat._M[15] = _M[12] * m._M[3] + _M[13] * m._M[7] + _M[14] * m._M[11] + _M[15] * m._M[15];

        return mat;
    }

    public void mul(float s) {
        _M[0] *= s;
        _M[4] *= s;
        _M[8] *= s;
        _M[12] *= s;
        _M[1] *= s;
        _M[5] *= s;
        _M[9] *= s;
        _M[13] *= s;
        _M[2] *= s;
        _M[6] *= s;
        _M[10] *= s;
        _M[14] *= s;
        _M[3] *= s;
        _M[7] *= s;
        _M[11] *= s;
        _M[15] *= s;
    }

    public void transpose() {
        Matrix4 m = new Matrix4();
        m.copy(this);
        _M[0] = m._M[0];
        _M[4] = m._M[1];
        _M[8] = m._M[2];
        _M[12] = m._M[3];
        _M[1] = m._M[4];
        _M[5] = m._M[5];
        _M[9] = m._M[6];
        _M[13] = m._M[7];
        _M[2] = m._M[8];
        _M[6] = m._M[9];
        _M[10] = m._M[10];
        _M[14] = m._M[11];
        _M[3] = m._M[12];
        _M[7] = m._M[13];
        _M[11] = m._M[14];
        _M[15] = m._M[15];
    }

    public Vector3 transform(Vector3 v) {
        float xx = (v.x * _M[0] + v.y * _M[4] + v.z * _M[8] + _M[12]);
        float yy = (v.x * _M[1] + v.y * _M[5] + v.z * _M[9] + _M[13]);
        float zz = (v.x * _M[2] + v.y * _M[6] + v.z * _M[10] + _M[14]);

        return new Vector3(xx, yy, zz);
    }

    /*  public void debug( PApplet p )
     {
     p.println( _M[ 0] + ", " + _M[ 1] + ", " + _M[ 2] + ", " + _M[ 3] );
     p.println( _M[ 4] + ", " + _M[ 5] + ", " + _M[ 6] + ", " + _M[ 7] );
     p.println( _M[ 8] + ", " + _M[ 9] + ", " + _M[10] + ", " + _M[11] );
     p.println( _M[12] + ", " + _M[13] + ", " + _M[14] + ", " + _M[15] );
     }*/
    public static Vector3 transform(Vector3 v, float[] m) {
        float xx = (v.x * m[0] + v.y * m[4] + v.z * m[8] + m[12]);
        float yy = (v.x * m[1] + v.y * m[5] + v.z * m[9] + m[13]);
        float zz = (v.x * m[2] + v.y * m[6] + v.z * m[10] + m[14]);

        return new Vector3(xx, yy, zz);
    }

    float[] _M;
};
