package com.mahantysandeep.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by smahanty on 3/31/17.
 */
public class Home {
    private String mode;

    @SerializedName("nodeName")
    private String jenkinsNodeName;

    @SerializedName("nodeDescription")
    private String jenkinsNodeDescription;

    private int numExecutors;

    private String description;

    private List<Job> jobs;

    private View primaryView;

    private List<View> views;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getJenkinsNodeName() {
        return jenkinsNodeName;
    }

    public void setJenkinsNodeName(String jenkinsNodeName) {
        this.jenkinsNodeName = jenkinsNodeName;
    }

    public String getJenkinsNodeDescription() {
        return jenkinsNodeDescription;
    }

    public void setJenkinsNodeDescription(String jenkinsNodeDescription) {
        this.jenkinsNodeDescription = jenkinsNodeDescription;
    }

    public int getNumExecutors() {
        return numExecutors;
    }

    public void setNumExecutors(int numExecutors) {
        this.numExecutors = numExecutors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public View getPrimaryView() {
        return primaryView;
    }

    public void setPrimaryView(View primaryView) {
        this.primaryView = primaryView;
    }

    public List<View> getViews() {
        return views;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }
}
