package com.example.freeplay.models;

import java.io.Serializable;

public class Playlist implements Serializable {
    private String userId;
    private String playlistId;
    private String playlistName;

    public Playlist() {}

    public Playlist(String userId, String playlistId, String playlistName) {
        this.userId = userId;
        this.playlistId = playlistId;
        this.playlistName = playlistName;
    }

    public String getUserId() {
        return userId;
    }
    public String getPlaylistId() {
        return playlistId;
    }
    public String getPlaylistName() {
        return playlistName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
}
