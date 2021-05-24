package com.example.freeplay.dao;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import com.example.freeplay.models.Track;
import com.example.freeplay.models.Album;
import com.example.freeplay.models.Artist;
import com.example.freeplay.models.User;
import com.example.freeplay.models.Playlist;
import com.example.freeplay.models.UserRowMapper;
import com.example.freeplay.models.TrackRowMapper;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class TrackDAO implements ITrackDAO {
    private NamedParameterJdbcTemplate template;
   
    public TrackDAO(NamedParameterJdbcTemplate template) {  
        this.template = template;
    }  
    public Track getTrackById(String trackId) {
        String sql = String.format(
            "SELECT track.trackId, track.trackName, track.trackDataFormat, track.trackImageFormat," +
            " array_agg(artist) as artists, array_agg(album) as albums" + 
            " FROM track, album, artist, track_artist, track_album WHERE track.trackId='%s' AND" +
            " artist.artistId=track_artist.artistId AND track.trackId=track_artist.trackId AND" + 
            " album.albumId=track_album.albumId AND track.trackId=track_album.trackId GROUP BY track.trackId;", trackId);
        return template.query(sql, new TrackRowMapper()).get(0);
    }
    public List<Track> getAllTracks(String trackName, String albumName, String artistName) {

        ArrayList<String> filters = new ArrayList<>();
        if (trackName != null) { filters.add("LOWER(track.trackName) LIKE '%key%'".replace("key", trackName.toLowerCase())); }
        if (albumName != null) { filters.add("LOWER(album.albumName) LIKE '%key%'".replace("key", albumName.toLowerCase())); }
        if (artistName != null) { filters.add("LOWER(artist.artistName) LIKE '%key%'".replace("key", artistName.toLowerCase())); }
        String sql = String.format(
            "SELECT track.trackId, track.trackName, track.trackDataFormat, track.trackImageFormat," +
            " array_agg(artist) as artists, array_agg(album) as albums" +
            " FROM track, album, artist, track_artist, track_album WHERE %s" +
            " artist.artistId=track_artist.artistId AND track.trackId=track_artist.trackId AND" +
            " album.albumId=track_album.albumId AND track.trackId=track_album.trackId" +
            " GROUP BY track.trackId;", String.join(" AND ", filters) + (filters.isEmpty() ? "" : " AND "));
        return template.query(sql, new TrackRowMapper());

        /*
        ArrayList<String> filters = new ArrayList<>();
        if (trackName != null) { filters.add("LOWER(track.trackName) LIKE 'key'".replace("key", trackName.toLowerCase())); }
        if (albumName != null) { filters.add("LOWER(album.albumName) LIKE 'key'".replace("key", albumName.toLowerCase())); }
        if (artistName != null) { filters.add("LOWER(artist.artistName) LIKE 'key'".replace("key", artistName.toLowerCase())); }
        String sql = String.format("SELECT track.trackId, track.trackName, track.trackDataFormat, track.trackImageFormat " +
                                   "FROM track, album, artist%s", !filters.isEmpty() ? " WHERE " + String.join(" AND ", filters) : "");
        List<Track> tracks = template.query(sql, (rs, rows) -> {
            Track track = new Track();
            track.setTrackId(rs.getString("trackId"));
            track.setTrackName(rs.getString("trackName"));
            track.setTrackDataFormat(rs.getString("trackDataFormat"));
            track.setTrackImageFormat(rs.getString("trackImageFormat"));
            return track;
        });
        for (Track track : tracks) {
            sql = "SELECT album.albumId, album.albumName " +
                  "FROM album, track_album " +
                  "WHERE track_album.trackId='%s'";
            sql = String.format(sql, track.getTrackId());
            track.setAlbums(template.query(sql, (rs, rows) -> {
                Album album = new Album();
                album.setAlbumId(rs.getString("albumId"));
                album.setAlbumName(rs.getString("albumName"));
                return album;
            }));
            sql = "SELECT artist.artistId, artist.artistName " +
                  "FROM artist, track_artist " +
                  "WHERE track_artist.trackId='%s'";
            sql = String.format(sql, track.getTrackId());
            track.setArtists(template.query(sql, (rs, rows) -> {
                Artist artist = new Artist();
                artist.setArtistId(rs.getString("artistId"));
                artist.setArtistName(rs.getString("artistName"));
                return artist;
            }));
        }
        return tracks;
        */
    }
    public List<Track> getAllTracks(int limit) {
        String sql = "SELECT trackId, trackName, trackDataFormat, trackImageFormat FROM track ORDER BY RANDOM() LIMIT " + limit;
        List<Track> tracks = template.query(sql, (rs, rows) -> {
            Track track = new Track();
            track.setTrackId(rs.getString("trackId"));
            track.setTrackName(rs.getString("trackName"));
            track.setTrackDataFormat(rs.getString("trackDataFormat"));
            track.setTrackImageFormat(rs.getString("trackImageFormat"));
            return track;
        });
        for (Track track : tracks) {
            sql = "SELECT album.albumId, album.albumName " +
                  "FROM album, track_album " +
                  "WHERE track_album.trackId='%s' AND track_album.albumId=album.albumId";
            sql = String.format(sql, track.getTrackId());
            track.setAlbums(template.query(sql, (rs, rows) -> {
                Album album = new Album();
                album.setAlbumId(rs.getString("albumId"));
                album.setAlbumName(rs.getString("albumName"));
                return album;
            }));
            sql = "SELECT artist.artistId, artist.artistName " +
                  "FROM artist, track_artist " +
                  "WHERE track_artist.trackId='%s' AND track_artist.artistId=artist.artistId";
            sql = String.format(sql, track.getTrackId());
            track.setArtists(template.query(sql, (rs, rows) -> {
                Artist artist = new Artist();
                artist.setArtistId(rs.getString("artistId"));
                artist.setArtistName(rs.getString("artistName"));
                return artist;
            }));
        }
        return tracks;
    }

    public void insertArtist(Artist artist) {
        //artist.setArtistId(UUID.randomUUID().toString());
        String sql = "insert into artist(artistId, artistName, artistImage) values(:artistId, :artistName, :artistImage)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("artistId", artist.getArtistId())
            .addValue("artistName", artist.getArtistName())
            .addValue("artistImage", artist.getArtistImage());
        template.update(sql, parameters, keyHolder);
    }
    public void insertAlbum(Album album) {
        //album.setAlbumId(UUID.randomUUID().toString());
        String sql = "insert into album(albumId, albumName, albumImage) values(:albumId, :albumName, :albumImage)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("albumId", album.getAlbumId())
            .addValue("albumName", album.getAlbumName())
            .addValue("albumImage", album.getAlbumImage());
        template.update(sql, parameters, keyHolder);

        sql = "insert into album_artist(albumId, artistId) values(:albumId, :artistId)";
        for (Artist artist : album.getArtists()) {
            parameters = new MapSqlParameterSource()
                .addValue("albumId", album.getAlbumId())
                .addValue("artistId", artist.getArtistId());
            template.update(sql, parameters, keyHolder);
        }
    }
    public void insertTrack(Track track) {
        //track.setTrackId(UUID.randomUUID().toString());
        String sql = "insert into track(trackId, trackName, trackDataFormat, trackImageFormat, trackData, trackImage)" +
                     " values(:trackId, :trackName, :trackDataFormat, :trackImageFormat, :trackData, :trackImage)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("trackId", track.getTrackId())
            .addValue("trackName", track.getTrackName())
            .addValue("trackData", track.getTrackData())
            .addValue("trackImage", track.getTrackImage())
            .addValue("trackDataFormat", track.getTrackDataFormat())
            .addValue("trackImageFormat", track.getTrackImageFormat());
        template.update(sql, parameters, keyHolder);

        sql = "insert into track_album(trackId, albumId) values(:trackId, :albumId)";
        for (Album album : track.getAlbums()) {
            parameters = new MapSqlParameterSource()
                .addValue("trackId", track.getTrackId())
                .addValue("albumId", album.getAlbumId());
            template.update(sql, parameters, keyHolder);
        }
        sql = "insert into track_artist(trackId, artistId) values(:trackId, :artistId)";
        for (Artist artist : track.getArtists()) {
            parameters = new MapSqlParameterSource()
                .addValue("trackId", track.getTrackId())
                .addValue("artistId", artist.getArtistId());
            template.update(sql, parameters, keyHolder);
        }
    }
    public void insertUser(User user) {
        String sql = "insert into users(userId, userName, userEmail, userPassword)" +
                     " values(:userId, :userName, :userEmail, :userPassword)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("userId", user.getUserId())
            .addValue("userName", user.getUserName())
            .addValue("userEmail", user.getUserEmail())
            .addValue("userPassword", user.getUserPassword());
        template.update(sql, parameters, keyHolder);
    }
    public void insertFollower(String userId, String artistId) {
        String sql = String.format("select 1 from user_artist where userId = '%s' and artistId='%s' limit 1", userId, artistId);
        if (template.query(sql, (rs, rows) -> rs.getInt(1)).size() != 0) return;

        sql = "insert into user_artist(userId, artistId) values(:userId, :artistId)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("userId", userId)
            .addValue("artistId", artistId);
        template.update(sql, parameters, keyHolder);
    }
    public byte[] getTrackImageById(String trackId) {
        return template.query(
            "select trackImage from track where trackId='" + trackId + "'",
            (rs, rows) -> rs.getBytes(1)).get(0);
    }
    public byte[] getAlbumImageById(String albumId) {
        return template.query(
            "select albumImage from album where albumId='" + albumId + "'",
            (rs, rows) -> rs.getBytes(1)).get(0);
    }
    public byte[] getArtistImageById(String artistId) {
        return template.query(
            "select artistImage from artist where artistId='" + artistId + "'",
            (rs, rows) -> rs.getBytes(1)).get(0);
    }
    public byte[] getTrackDataById(String trackId) {
        return template.query(
            "select trackData from track where trackId='" + trackId + "'",
            (rs, rows) -> rs.getBytes(1)).get(0);
    }
    public List<Artist> getUserArtists(String userId) {
        String sql = String.format(
            "SELECT artist.artistId, artist.artistName FROM users, artist, user_artist WHERE" +
            " users.userId='%s' AND users.userId=user_artist.userId AND artist.artistId=user_artist.artistId", userId);
        return template.query(sql, (rs, rows) -> {
            Artist artist = new Artist();
            artist.setArtistId(rs.getString("artistId"));
            artist.setArtistName(rs.getString("artistName"));
            return artist;
         });
    }
    public List<Playlist> getUserPlaylists(String userId) {
        String sql = String.format(
            "SELECT playlist.userId, playlist.playlistId, playlist.playlistName FROM users, playlist WHERE" +
            " users.userId='%s' AND users.userId=playlist.userId", userId);
        return template.query(sql, (rs, rows) -> {
            Playlist playlist = new Playlist();
            playlist.setUserId(rs.getString("userId"));
            playlist.setPlaylistId(rs.getString("playlistId"));
            playlist.setPlaylistName(rs.getString("playlistName"));
            return playlist;
         });
    }
    public User getUserById(String userId) {
        String sql = String.format("SELECT * FROM users WHERE userId='%s'", userId);
        User user = template.query(sql, (rs, rows) -> {
            User USER = new User();
            USER.setUserId(rs.getString("userId"));
            USER.setUserName(rs.getString("userName"));
            USER.setUserEmail(rs.getString("userEmail"));
            USER.setUserPassword(rs.getString("userPassword"));
            return USER;
         }).get(0);
         user.setArtists(getUserArtists(userId));
         user.setPlaylists(getUserPlaylists(userId));
         return user;
    }
    public void insertPlaylist(Playlist playlist) {
        String sql = "insert into playlist(userId, playlistId, playlistName)" +
                     " values(:userId, :playlistId, :playlistName)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("userId", playlist.getUserId())
            .addValue("playlistId", playlist.getPlaylistId())
            .addValue("playlistName", playlist.getPlaylistName());
        template.update(sql, parameters, keyHolder);
    }
    public void addTrackInPlaylist(String trackId, String playlistId) {
        String sql = "insert into track_playlist(trackId, playlistId)" +
                     " values(:trackId, :playlistId)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("trackId", trackId)
            .addValue("playlistId", playlistId);
        template.update(sql, parameters, keyHolder);
    }
}
