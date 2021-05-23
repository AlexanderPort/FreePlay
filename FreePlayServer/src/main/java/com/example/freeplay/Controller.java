package com.example.freeplay;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.freeplay.dao.TrackDAO;
import com.example.freeplay.models.Track;
import com.example.freeplay.models.User;
import com.example.freeplay.models.Album;
import com.example.freeplay.models.Artist;


@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api")
public class Controller {
    @Resource TrackDAO trackService;

    @GetMapping(value="/users/{id}")
    public User getUser(@PathVariable("id") String userId) {
        return trackService.getUserById(userId);
    }
    @GetMapping(value="/tracks/meta")
    public List<Track> getAllTracks(@RequestParam(required=false) String trackName, 
                                    @RequestParam(required=false) String albumName, 
                                    @RequestParam(required=false) String artistName) {
        return trackService.getAllTracks(trackName, albumName, artistName);
    }
    @GetMapping(value="/tracks/meta/{id}")
    public Track getTracks(@PathVariable("id") String trackId) {
        return trackService.getTrackById(trackId);
    }
    @GetMapping(value="/tracks/images/{id}", headers="Accept=image/jpeg, image/jpg, image/png, image/gif")
    public @ResponseBody byte[] getTrackImageById(@PathVariable("id") String trackId) {
        return trackService.getTrackImageById(trackId);
    }
    @GetMapping(value="/albums/images/{id}", headers="Accept=image/jpeg, image/jpg, image/png, image/gif")
    public @ResponseBody byte[] getAlbumImageById(@PathVariable("id") String albumId) {
        return trackService.getAlbumImageById(albumId);
    }
    @GetMapping(value="/artists/images/{id}", headers="Accept=image/jpeg, image/jpg, image/png, image/gif")
    public @ResponseBody byte[] getArtistsImageById(@PathVariable("id") String artistId) {
        return trackService.getArtistImageById(artistId);
    }
    @GetMapping(value="/tracks/data/{id}", headers="Accept=audio/mp3")
    public @ResponseBody byte[] getTrackDataById(@PathVariable("id") String trackId) {
        return trackService.getTrackDataById(trackId);
    }

    @PostMapping(value="/users")
    public void createUser(@ModelAttribute User user) {
        trackService.insertUser(user);
    }
    @PostMapping(value="/followers")
    public void createFollower(@RequestParam(required=true) String userId, 
                               @RequestParam(required=true) String artistId) {
        trackService.insertFollower(userId, artistId);
    }
    @PostMapping(value="/tracks")
    public void createTrack(@ModelAttribute Track track) {
        trackService.insertTrack(track);
    }
    @PostMapping(value="/albums")
    public void createTrack(@ModelAttribute Album album) {
        trackService.insertAlbum(album);
    }
    @PostMapping(value="/artists")
    public void createTrack(@ModelAttribute Artist artist) {
        trackService.insertArtist(artist);
    }
}