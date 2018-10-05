
package com.example.ozakharc.redditclient.api.response;

import java.util.List;

public class Data {

    private List<Child> children = null;

    private String after;

    public List<Child> getChildren() {
        return children;
    }

    public String getAfter() {
        return after;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public void setAfter(String after) {
        this.after = after;
    }
}
