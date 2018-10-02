
package com.example.ozakharc.redditclient.model.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child {

    @SerializedName("data")
    private ChildData childDta;

    public ChildData getChildDta() {
        return childDta;
    }
}
