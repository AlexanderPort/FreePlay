package com.example.freeplay.models;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.io.Serializable;


public class Album implements Serializable {
    private String albumId;
    private String albumName;
    private byte[] albumImage;
    private List<Artist> artists = new ArrayList<>();

    public Album() {}
    public Album(String albumId, String albumName) {
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public String getAlbumId() {
        return albumId;
    }
    public String getAlbumName() {
        return albumName;
    }
    public List<Artist> getArtists() {
        return artists;
    }
    public byte[] getAlbumImage() {
        return albumImage;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    public void setArtists(String artists) {
        ObjectMapper mapper = new ObjectMapper();
        try { this.artists = Arrays.asList(
            mapper.readValue(artists, Artist[].class)); }
        catch (Exception exception) { exception.printStackTrace(); }
    }
    public void setAlbumImage(MultipartFile albumImage) {
        try { this.albumImage = albumImage.getBytes(); }
        catch (IOException exception) { exception.printStackTrace(); }
    }
    /*
    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
    */

}
