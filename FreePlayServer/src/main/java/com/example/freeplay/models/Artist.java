package com.example.freeplay.models;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class Artist implements Serializable {
    private String artistId;
    private String artistName;
    private byte[] artistImage;

    public Artist() {}
    public Artist(String artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public String getArtistId() {
        return artistId;
    }
    public String getArtistName() {
        return artistName;
    }
    public byte[] getArtistImage() {
        return artistImage;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public void setArtistImage(MultipartFile artistImage) {
        try { this.artistImage = artistImage.getBytes(); }
        catch (IOException exception) { exception.printStackTrace(); }
    }
}
