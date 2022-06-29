package com.example.cms_club_ver_1;

import java.io.Serializable;

public class ClubMemberPOJO implements Serializable
{
    public String profile_image;
    public String name;
    public String post;
    public String phone_no;
    public String linkedin_account;
    public String github_account;
    public String instagram_account;
    public String cms_id;


    public ClubMemberPOJO()
    {

    }


    public ClubMemberPOJO(String profile_image, String name, String post, String phone_no, String linkedin_account, String github_account, String instagram_account, String cms_id) {
        this.profile_image = profile_image;
        this.name = name;
        this.post = post;
        this.phone_no = phone_no;
        this.linkedin_account = linkedin_account;
        this.github_account = github_account;
        this.instagram_account = instagram_account;
        this.cms_id = cms_id;
    }


    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getLinkedin_account() {
        return linkedin_account;
    }

    public void setLinkedin_account(String linkedin_account) {
        this.linkedin_account = linkedin_account;
    }

    public String getGithub_account() {
        return github_account;
    }

    public void setGithub_account(String github_account) {
        this.github_account = github_account;
    }

    public String getInstagram_account() {
        return instagram_account;
    }

    public void setInstagram_account(String instagram_account) {
        this.instagram_account = instagram_account;
    }

    public String getCms_id() {
        return cms_id;
    }

    public void setCms_id(String cms_id) {
        this.cms_id = cms_id;
    }
}
