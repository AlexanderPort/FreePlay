package com.example.freeplay.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet result, int arg) throws SQLException {
        User user = new User();
        user.setUserId(result.getString("userId"));
        user.setUserName(result.getString("userName"));
        user.setUserEmail(result.getString("userEmail"));
        user.setUserPassword(result.getString("userPassword"));
        String aartists = result.getString("artists").replace("\"", "");
        String aplaylists = result.getString("playlists").replace("\"", "");
        aartists = aartists.substring(1, aartists.length() - 1).replace("\\", "");
        aplaylists = aplaylists.substring(1, aplaylists.length() - 1).replace("\\", "");
        String[] artists = aartists.split("\\),\\(");
        String[] playlists = aplaylists.split("\\),\\(");
        for (String artist : artists) {
            String[] parameters = artist.substring(1, artist.length() - 1).split(",");
            user.addArtist(new Artist(parameters[0], parameters[1]));
        }
        for (String playlist : playlists) {
            String[] parameters = playlist.substring(1, playlist.length() - 1).split(",");
            user.addPlaylist(new Playlist(parameters[0], parameters[1], parameters[2]));
        }
        return user;
    }
}

