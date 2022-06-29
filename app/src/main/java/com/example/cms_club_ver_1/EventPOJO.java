package com.example.cms_club_ver_1;

import java.io.Serializable;

public class EventPOJO implements Serializable
{
    public String cms_id;
    public String event_name;
    public String event_date;
    public String event_poster;
    public String event_description;
    public boolean isShrink = true;

    public EventPOJO()
    {

    }

    public EventPOJO(String event_name, String event_date, String event_description, String event_poster,String cms_id) {
        this.event_name = event_name;
        this.event_date = event_date;
        this.event_description = event_description;
        this.event_poster = event_poster;
        this.cms_id = cms_id;
    }

    public String getCms_id() {
        return cms_id;
    }

    public void setCms_id(String cms_id) {
        this.cms_id = cms_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_poster() {
        return event_poster;
    }

    public void setEvent_poster(String event_poster) {
        this.event_poster = event_poster;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
}
