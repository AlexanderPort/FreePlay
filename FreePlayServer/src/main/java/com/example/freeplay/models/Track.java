package com.example.freeplay.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;


public class Track implements Serializable {
    private String trackId;
    private String trackName;
    private byte[] trackData;
    private byte[] trackImage;
    private String trackDataFormat;
    private String trackImageFormat;
    private List<Album> albums = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();
    
    public Track() {}

    public String getTrackId() {
        return trackId;
    }
    public String getTrackName() {
        return trackName;
    }
    public byte[] getTrackData() {
        return trackData;
    }
    public byte[] getTrackImage() {
        return trackImage;
    }
    public List<Album> getAlbums() {
        return albums;
    }
    public List<Artist> getArtists() {
        return artists;
    }
    public String getTrackDataFormat() {
        return trackDataFormat;
    }
    public String getTrackImageFormat() {
        return trackImageFormat;
    }
    
    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }
    public void setTrackData(MultipartFile trackData) {
        try { this.trackData = trackData.getBytes(); }
        catch (IOException exception) { exception.printStackTrace(); }
    }
    public void setTrackImage(MultipartFile trackImage) {
        try { this.trackImage = trackImage.getBytes(); }
        catch (IOException exception) { exception.printStackTrace(); }
    }
    public void setAlbums(Object object) {
        if (object instanceof String) {
            String albums = ((String) object);
            ObjectMapper mapper = new ObjectMapper();
            try { this.albums = Arrays.asList(
                mapper.readValue(albums, Album[].class)); }
            catch (Exception exception) { exception.printStackTrace(); }
        } else if (object instanceof List<?>) {
            this.albums = ((List<Album>)object);
        }
    }
    public void setArtists(Object object) {
        if (object instanceof String) {
            String artists = ((String) object);
            ObjectMapper mapper = new ObjectMapper();
            try { this.artists = Arrays.asList(
                mapper.readValue(artists, Artist[].class)); }
            catch (Exception exception) { exception.printStackTrace(); }
        } else if (object instanceof List<?>) {
            this.artists = ((List<Artist>)object);
        }
    }
    /*
    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
    */
    public void setTrackDataFormat(String trackDataFormat) {
        this.trackDataFormat = trackDataFormat;
    }
    public void setTrackImageFormat(String trackImageFormat) {
        this.trackImageFormat = trackImageFormat;
    }
     @Override
     public String toString() {
         return String.format("Track(trackName=%s)", trackName);
     }
     public void addAlbum(Album album) {
        albums.add(album);
    }
     public void addArtist(Artist artist) {
         artists.add(artist);
     }
}
