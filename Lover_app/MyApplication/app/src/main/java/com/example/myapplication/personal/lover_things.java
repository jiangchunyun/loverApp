package com.example.myapplication.personal;

import java.util.List;

/**
 * Created by 上官刀刀 on 2017/3/19.
 */

public class lover_things {

    private String id;
    private String name;
    private String lovername;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getX() {
        return x;
    }
    public void setX(String x) {
        this.x = x;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLovername() {
        return lovername;
    }

    public void setLovername(String lovername) {
        this.lovername = lovername;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getThings() {
        return things;
    }

    public void setThings(String things) {
        this.things = things;
    }

    private String x;
    private String y;
    private String things;
    private String location;

    public String getTimedate() {
        return timedate;
    }

    public void setTimedate(String timedate) {
        this.timedate = timedate;
    }

    private String timedate;

    public List<com.example.myapplication.personal.comment> getComment() {
        return comment;
    }

    public void setComment(List<com.example.myapplication.personal.comment> comment) {
        this.comment = comment;
    }
    private List<comment> comment;

    public List<lover_pictures> getPortrait() {
        return portrait;
    }

    public void setPortrait(List<lover_pictures> portrait) {
        this.portrait = portrait;
    }


    public List<lover_thing_picture> getPicture() {
        return picture;
    }

    public void setPicture(List<lover_thing_picture> picture) {
        this.picture = picture;
    }

    private List<lover_thing_picture> picture;
    private List<lover_pictures> portrait;
}
