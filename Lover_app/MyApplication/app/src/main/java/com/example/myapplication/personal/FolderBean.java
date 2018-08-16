package com.example.myapplication.personal;

/**
 * Created by 上官刀刀 on 2017/3/30.
 */

public class FolderBean {
    private String dir;

    public String getFirstImgPath() {
        return firstImgPath;
    }

    public void setFirstImgPath(String firstImgPath) {
        this.firstImgPath = firstImgPath;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf=this.dir.indexOf("/");
        this.name=this.dir.substring(lastIndexOf);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private String firstImgPath;
    private String name;
    private int count;
}
