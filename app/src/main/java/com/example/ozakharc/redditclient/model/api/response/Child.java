
package com.example.ozakharc.redditclient.model.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child {

    @SerializedName("data")
    @Expose
    private ChildData childDta;

    public ChildData getChildDta() {
        return childDta;
    }

    public void setChildDta(ChildData childDta) {
        this.childDta = childDta;
    }
}
