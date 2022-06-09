package com.example.cms_club_ver_1;

public class AssistantBoardPOJO
{
    public int img_profile;
    public String name;
    public String post;

    public AssistantBoardPOJO(int img_profile, String name, String post) {
        this.img_profile = img_profile;
        this.name = name;
        this.post = post;
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
