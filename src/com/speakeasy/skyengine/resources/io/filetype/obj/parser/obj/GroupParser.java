package com.speakeasy.skyengine.resources.io.filetype.obj.parser.obj;

import com.speakeasy.skyengine.resources.io.filetype.obj.Group;
import com.speakeasy.skyengine.resources.io.filetype.obj.WavefrontObject;
import com.speakeasy.skyengine.resources.io.filetype.obj.parser.LineParser;

public class GroupParser extends LineParser {

    Group newGroup = null;

    @Override
    public void incoporateResults(WavefrontObject wavefrontObject) {

        if (wavefrontObject.getCurrentGroup() != null) {
            wavefrontObject.getCurrentGroup().pack();
        }

        wavefrontObject.getGroups().add(newGroup);
        wavefrontObject.getGroupsDirectAccess().put(newGroup.getName(), newGroup);

        wavefrontObject.setCurrentGroup(newGroup);
    }

    @Override
    public void parse() {

        String groupName = words[1];
        newGroup = new Group(groupName);
    }

}
