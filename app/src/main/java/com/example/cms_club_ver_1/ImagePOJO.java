package com.example.cms_club_ver_1;

public class ImagePOJO
{
    public String key;
    public String imgURL;

    public ImagePOJO()
    {

    }

    public ImagePOJO(String imgURL, String key)
    {
        this.imgURL = imgURL;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ImagePOJO(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
