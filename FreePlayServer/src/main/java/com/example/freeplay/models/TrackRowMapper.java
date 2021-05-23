package com.example.freeplay.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class TrackRowMapper implements RowMapper<Track> {
    @Override
    public Track mapRow(ResultSet result, int arg) throws SQLException {
        Track track = new Track();
        track.setTrackId(result.getString("trackId"));
        track.setTrackName(result.getString("trackName"));
        track.setTrackDataFormat(result.getString("trackDataFormat"));
        track.setTrackImageFormat(result.getString("trackImageFormat"));
        String aalbums = result.getString("albums").replace("\"", "");
        String aartists = result.getString("artists").replace("\"", "");
        aalbums = aalbums.substring(1, aalbums.length() - 1).replace("\\", "");
        aartists = aartists.substring(1, aartists.length() - 1).replace("\\", "");
        String[] albums = aalbums.split("\\),\\(");
        String[] artists = aartists.split("\\),\\(");
        for (String album : albums) {
            String[] parameters = album.substring(1, album.length() - 1).split(",");
            track.addAlbum(new Album(parameters[0], parameters[1]));
        }
        for (String artist : artists) {
            String[] parameters = artist.substring(1, artist.length() - 1).split(",");
            track.addArtist(new Artist(parameters[0], parameters[1]));
        }
        return track;
    }
}
