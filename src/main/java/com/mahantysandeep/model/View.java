package com.mahantysandeep.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by smahanty on 3/31/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class View {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        return (getName() + ":" + getUrl()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof View) {
            View view = (View) obj;
            return (view.getName().equals(getName()) && (view.getUrl().equals(getUrl())));
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append(" name = " + name);
        stringBuffer.append(" url = " + url);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
