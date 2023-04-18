package com.zinkworks.urlshorten.Dto;

import javax.persistence.Column;
import java.util.Date;

/* DTO (Data Transfer Object)  is an object that carries data between processes, facilitate communication
   without exposing sensitive information, contain data, not business logic.
 */
public class UrlDto {

    @Column(name= "SHORTURL")
    private String shortUrl;
    @Column(name= "LONGURL")
    private String longUrl;

    @Column(name= "CREATEDAT")
    private Date createdAt;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
