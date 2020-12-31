package com.example.tiku_b.bean;

public class BQY {

    /**
     * newstype : 时政
     */

    private String newstype;
    private boolean isPitchOn;

    public boolean isPitchOn() {
        return isPitchOn;
    }

    public void setPitchOn(boolean pitchOn) {
        isPitchOn = pitchOn;
    }

    public String getNewstype() {
        return newstype;
    }

    public void setNewstype(String newstype) {
        this.newstype = newstype;
    }
}
