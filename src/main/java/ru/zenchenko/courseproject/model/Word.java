package ru.zenchenko.courseproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "words")
public class Word {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int level;
    @Column
    private String rusWord;
    @Column
    private String engWord;
    @Column
    private String picUrl;
    @Column
    private String authorText;
    @Column
    private String sentence;

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public Word() {
    }

    public Word(int level, String rusWord, String engWord) {
        this.level = level;
        this.rusWord = rusWord;
        this.engWord = engWord;
    }

    public Word(int level, String rusWord, String engWord, String picUrl) {
        this.level = level;
        this.rusWord = rusWord;
        this.engWord = engWord;
        this.picUrl = picUrl;
    }

    public String getAuthorText() {
        return authorText;
    }

    public void setAuthorText(String authorText) {
        this.authorText = authorText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRusWord() {
        return rusWord;
    }

    public void setRusWord(String rusWord) {
        this.rusWord = rusWord;
    }

    public String getEngWord() {
        return engWord;
    }

    public void setEngWord(String engWord) {
        this.engWord = engWord;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String url) {
        this.picUrl = url;
    }
}
