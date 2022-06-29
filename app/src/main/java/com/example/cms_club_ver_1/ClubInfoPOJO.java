package com.example.cms_club_ver_1;

public class ClubInfoPOJO
{
    public String club_name;
    public String club_tag;
    public String club_aim;
    public String club_logo;
    public String creation_date;
    public String founder;


    public ClubInfoPOJO()
    {

    }

    public ClubInfoPOJO(String club_name, String club_tag, String club_aim, String club_logo, String creation_date, String founder) {
        this.club_name = club_name;
        this.club_tag = club_tag;
        this.club_aim = club_aim;
        this.club_logo = club_logo;
        this.creation_date = creation_date;
        this.founder = founder;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getClub_tag() {
        return club_tag;
    }

    public void setClub_tag(String club_tag) {
        this.club_tag = club_tag;
    }

    public String getClub_aim() {
        return club_aim;
    }

    public void setClub_aim(String club_aim) {
        this.club_aim = club_aim;
    }

    public String getClub_logo() {
        return club_logo;
    }

    public void setClub_logo(String club_logo) {
        this.club_logo = club_logo;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }
}
