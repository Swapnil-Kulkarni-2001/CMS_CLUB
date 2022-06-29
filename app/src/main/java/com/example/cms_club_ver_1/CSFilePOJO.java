package com.example.cms_club_ver_1;


public class CSFilePOJO
{
    public String key;
    public String file_name;
    public String file_size;
    public String file_url;


    public CSFilePOJO()
    {

    }

    public CSFilePOJO(String file_name, String file_size, String file_url) {
        this.file_name = file_name;
        this.file_size = file_size;
        this.file_url = file_url;
    }


    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
