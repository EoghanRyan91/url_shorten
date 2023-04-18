package com.zinkworks.urlshorten.model;
import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "url")
public class Url {
    @Id
    @Column(name= "SHORTURL")
    private String shortUrl;
    @Column(name= "LONGURL")
    private String longUrl;

    @Column(name= "CREATEDAT")
    private Date createdAt;

    @Column(name = "TIMESCLICKED")
    private Integer timesClicked;

    public Url() {

    }
    public Url(String shortUrl, String longUrl, Date createdAt) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.createdAt = createdAt;
        this.timesClicked = 0;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
    public String getShortUrl() {
        return shortUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    //Checks if created at is empty and sets it with the current date
    public void setCreatedAt() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
    }

    public Integer getTimesClicked() {
        return timesClicked;
    }

    // Increments the timesClicked variable by 1
    public void setTimesClicked() {
        this.timesClicked = timesClicked + 1;
    }
}