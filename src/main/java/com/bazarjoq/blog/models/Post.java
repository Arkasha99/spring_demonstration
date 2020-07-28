package com.bazarjoq.blog.models;

import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String announce;
    private String text;
    private Integer count;

    public Post(){    }
    public Post(String name, String announce, String text) {
        this.name = name;
        this.announce = announce;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
