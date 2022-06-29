package com.example.cms_club_ver_1;

public class MainBoardPOJO
{
    public int img_profile;
    public String name;
    public String post;
    public String phone_no;
    public String linkedin_url;
    public String github_url;
    public String instagram_url;

    public MainBoardPOJO(int img_profile, String name, String post,String phone_no, String linkedin_url, String github_url, String instagram_url) {
        this.img_profile = img_profile;
        this.name = name;
        this.post = post;
        this.phone_no = phone_no;
        this.linkedin_url = linkedin_url;
        this.github_url = github_url;
        this.instagram_url = instagram_url;
    }


    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getLinkedin_url() {
        return linkedin_url;
    }

    public void setLinkedin_url(String linkedin_url) {
        this.linkedin_url = linkedin_url;
    }

    public String getGithub_url() {
        return github_url;
    }

    public void setGithub_url(String github_url) {
        this.github_url = github_url;
    }

    public String getInstagram_url() {
        return instagram_url;
    }

    public void setInstagram_url(String instagram_url) {
        this.instagram_url = instagram_url;
    }

    public int getImg_profile() {
        return img_profile;
    }

    public void setImg_profile(int img_profile) {
        this.img_profile = img_profile;
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
}
