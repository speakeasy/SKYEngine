package com.speakeasy.skyengine.resources.io.filetype.obj.parser.obj;

//import java.util.Hashtable;
import com.speakeasy.skyengine.resources.io.filetype.obj.LineParserFactory;
import com.speakeasy.skyengine.resources.io.filetype.obj.NormalParser;
import com.speakeasy.skyengine.resources.io.filetype.obj.WavefrontObject;
import com.speakeasy.skyengine.resources.io.filetype.obj.parser.CommentParser;
import com.speakeasy.skyengine.resources.io.filetype.obj.parser.material.MaterialFileParser;

public class ObjLineParserFactory extends LineParserFactory {

    public ObjLineParserFactory(WavefrontObject object) {
        this.object = object;
        parsers.put("v", new VertexParser());
        parsers.put("vn", new NormalParser());
        parsers.put("vp", new FreeFormParser());
        parsers.put("vt", new TextureCooParser());
        parsers.put("f", new FaceParser(object));
        parsers.put("#", new CommentParser());
        parsers.put("mtllib", new MaterialFileParser(object));
        parsers.put("usemtl", new MaterialParser());
        parsers.put("g", new GroupParser());
    }

}
