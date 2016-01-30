package com.speakeasy.skyengine.resources.io.filetype.obj.parser.material;

//import java.util.Hashtable;
import com.speakeasy.skyengine.resources.io.filetype.obj.LineParserFactory;
import com.speakeasy.skyengine.resources.io.filetype.obj.WavefrontObject;
import com.speakeasy.skyengine.resources.io.filetype.obj.parser.CommentParser;
//import com.obj.parser.DefaultParser;
//import com.obj.parser.LineParser;
import com.speakeasy.skyengine.resources.io.filetype.obj.parser.material.KdMapParser;
import com.speakeasy.skyengine.resources.io.filetype.obj.parser.material.KdParser;
import com.speakeasy.skyengine.resources.io.filetype.obj.parser.material.MaterialParser;

public class MtlLineParserFactory extends LineParserFactory {

    public MtlLineParserFactory(WavefrontObject object) {
        this.object = object;
        parsers.put("newmtl", new MaterialParser());
        parsers.put("Ka", new KaParser());
        parsers.put("Kd", new KdParser());
        parsers.put("Ks", new KsParser());
        parsers.put("Ns", new NsParser());
        parsers.put("map_Kd", new KdMapParser(object));
        parsers.put("#", new CommentParser());
    }

}
