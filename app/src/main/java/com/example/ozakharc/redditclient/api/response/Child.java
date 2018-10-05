
package com.example.ozakharc.redditclient.api.response;

import com.google.gson.annotations.SerializedName;

public class Child {

    @SerializedName("data")
    private ChildData childDta;

    public ChildData getChildDta() {
        return childDta;
    }

    public void setChildDta(ChildData childDta) {
        this.childDta = childDta;
    }
}
