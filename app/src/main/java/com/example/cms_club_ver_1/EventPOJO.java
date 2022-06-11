package com.example.cms_club_ver_1;

public class EventPOJO
{
    public String event_name;
    public String date;
    public String discription;
    public boolean isShrink = true;

    public EventPOJO(String event_name, String date, String discription) {
        this.event_name = event_name;
        this.date = date;
        this.discription = discription;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
}
