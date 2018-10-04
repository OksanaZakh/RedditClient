
package com.example.ozakharc.redditclient.api.response;

import java.util.List;

public class Data {

    private Integer dist;

    private List<Child> children = null;

    private String after;

    public List<Child> getChildren() {
        return children;
    }

    public String getAfter() {
        return after;
    }

}
