package com.example.cms_club_ver_1;

import android.net.Uri;

public class EventPOJO
{
    public String event_name;
    public String date;
    public Uri poster;
    public String description;
    public boolean isShrink = true;

    public EventPOJO(String event_name, String date, String description, Uri poster) {
        this.event_name = event_name;
        this.date = date;
        this.description = description;
        this.poster = poster;
    }

    public Uri getPoster() {
        return poster;
    }

    public void setPoster(Uri poster) {
        this.poster = poster;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
}
