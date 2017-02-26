package com.b2c.pojo;

/**
 * code
 * 200 is success
 * 500 is fail
 * @author james
 *
 */
public class PictureResult {
    private int code;
    private String url;
    
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
}
