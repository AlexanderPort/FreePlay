package com.example.freeplay.models;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class User implements Serializable{
    private String userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private List<Artist> artists = new ArrayList<>();
    private List<Playlist> playlists = new ArrayList<>();

    public User() {}

    public String getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public List<Artist> getArtists() {
        return artists;
    }
    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void addArtist(Artist artist) {
        artists.add(artist);
    }
    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }
}
