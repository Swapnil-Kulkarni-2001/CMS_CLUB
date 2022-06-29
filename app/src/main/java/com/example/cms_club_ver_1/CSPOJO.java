package com.example.cms_club_ver_1;

import java.io.Serializable;

public class CSPOJO implements Serializable
{

    public String cms_id;
    public String cs_topic;
    public String cs_date;
    public String cs_description;
    public String cs_poster;
    public boolean isShrink = true;

    public CSPOJO()
    {

    }

    public CSPOJO(String cms_id, String cs_topic, String cs_date, String cs_description, String cs_poster) {
        this.cms_id = cms_id;
        this.cs_topic = cs_topic;
        this.cs_date = cs_date;
        this.cs_description = cs_description;
        this.cs_poster = cs_poster;
    }

    public String getCms_id() {
        return cms_id;
    }

    public void setCms_id(String cms_id) {
        this.cms_id = cms_id;
    }

    public String getCs_topic() {
        return cs_topic;
    }

    public void setCs_topic(String cs_topic) {
        this.cs_topic = cs_topic;
    }

    public String getCs_date() {
        return cs_date;
    }

    public void setCs_date(String cs_date) {
        this.cs_date = cs_date;
    }

    public String getCs_description() {
        return cs_description;
    }

    public void setCs_description(String cs_description) {
        this.cs_description = cs_description;
    }

    public String getCs_poster() {
        return cs_poster;
    }

    public void setCs_poster(String cs_poster) {
        this.cs_poster = cs_poster;
    }

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
}
