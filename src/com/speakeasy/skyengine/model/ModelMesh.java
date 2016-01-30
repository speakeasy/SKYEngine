package com.speakeasy.skyengine.model;

import java.util.ArrayList;


public class ModelMesh 
{
	public ModelMesh()
	  {
	    _vertexList = new ArrayList<float[]>();
	    _texcoordList = new ArrayList<float[]>();
	    _normalList = new ArrayList<float[]>();
	    _faceList = new ArrayList<ModelFace>();
	    
	    _material = new ModelMaterial();
	  }
	  
	public void setName( String n )
	  {
	    _name = n;
	  }
	  
	public void addVertex( float[] v )
	  {
	    _vertexList.add( v );
	  }

	public void addTexCoord( float[] v )
	  {
	    _texcoordList.add( v );
	  }

	public void addNormal( float[] v )
	  {
	    _normalList.add( v );
	  }

	  // Add faces
	public void addFace( ModelFace f )
	  {
	    _faceList.add( f );
	  }
	public void addFace( int a, int b, int c )
	  {
	    _faceList.add( new ModelFace(a, b, c) );
	  }
	
	
	public String getName()
	{
		return _name;
	}
	
	public ArrayList<float[]> getVertexList()
	{
		return _vertexList;
	}

	
	public ArrayList<float[]> getNormalList()
	{
		return _normalList;
	}

	public ArrayList<float[]> getTexCoordList()
	{
		return _texcoordList;
	}

	public ArrayList<ModelFace> getFaceList()
	{
		return _faceList;
	}

	  
	String _name;
	ModelMaterial _material;  
	ArrayList<float[]> _vertexList;
	ArrayList<float[]> _texcoordList;
	ArrayList<float[]> _normalList;
	ArrayList<ModelFace> _faceList; 
}
