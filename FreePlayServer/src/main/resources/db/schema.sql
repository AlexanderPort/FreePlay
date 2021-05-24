/*
DROP TABLE IF EXISTS artist CASCADE;
DROP TABLE IF EXISTS album CASCADE;
DROP TABLE IF EXISTS track CASCADE;
DROP TABLE IF EXISTS track_album CASCADE;
DROP TABLE IF EXISTS track_artist CASCADE;
DROP TABLE IF EXISTS album_artist CASCADE;
*/
/*
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS playlist CASCADE;
DROP TABLE IF EXISTS user_artist CASCADE;
DROP TABLE IF EXISTS user_playlist CASCADE;
DROP TABLE IF EXISTS track_playlist CASCADE;
*/



CREATE TABLE IF NOT EXISTS users 
(
    userId VARCHAR(36) NOT NULL,
    userName VARCHAR(128) NOT NULL,
    userEmail VARCHAR(128) NOT NULL,
    userPassword VARCHAR(128) NOT NULL,
    PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS artist
(
    artistId VARCHAR(36) NOT NULL,
    artistName VARCHAR(128) NOT NULL,
    artistImage BYTEA NOT NULL,
    PRIMARY KEY (artistId)
);

CREATE TABLE IF NOT EXISTS album
(
    albumId VARCHAR(36) NOT NULL,
    albumName VARCHAR(128) NOT NULL,
    albumImage BYTEA NOT NULL,
    PRIMARY KEY (albumId)
);

CREATE TABLE IF NOT EXISTS track
(
    trackId VARCHAR(36) NOT NULL,
    trackName VARCHAR(128) NOT NULL,
    trackData BYTEA NOT NULL,
    trackDataFormat VARCHAR(4) NOT NULL,
    trackImage BYTEA NOT NULL,
    trackImageFormat VARCHAR(4) NOT NULL,
    PRIMARY KEY (trackId)
);

CREATE TABLE IF NOT EXISTS track_artist
(
    trackId VARCHAR(36) NOT NULL,
    artistId VARCHAR(36) NOT NULL,
    FOREIGN KEY (trackId) REFERENCES track(trackId),
    FOREIGN KEY (artistId) REFERENCES artist(artistId)
);

CREATE TABLE IF NOT EXISTS track_album
(
    trackId VARCHAR(36) NOT NULL,
    albumId VARCHAR(36) NOT NULL,
    FOREIGN KEY (trackId) REFERENCES track(trackId),
    FOREIGN KEY (albumId) REFERENCES album(albumId)
);

CREATE TABLE IF NOT EXISTS album_artist
(
    albumId VARCHAR(36) NOT NULL,
    artistId VARCHAR(36) NOT NULL,
    FOREIGN KEY (albumId) REFERENCES album(albumId),
    FOREIGN KEY (artistId) REFERENCES artist(artistId)
);

CREATE TABLE IF NOT EXISTS playlist 
(
    userId VARCHAR(36) NOT NULL,
    playlistId VARCHAR(36) NOT NULL,
    playlistName VARCHAR(128) NOT NULL,
    PRIMARY KEY (playlistId),
    FOREIGN KEY (userId) REFERENCES users(userId)
);

CREATE TABLE IF NOT EXISTS user_artist 
(
    userId VARCHAR(36) NOT NULL,
    artistId VARCHAR(36) NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(userId),
    FOREIGN KEY (artistId) REFERENCES artist(artistId)
);

CREATE TABLE IF NOT EXISTS user_playlist 
(
    userId VARCHAR(36) NOT NULL,
    playlistId VARCHAR(36) NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(userId),
    FOREIGN KEY (playlistId) REFERENCES playlist(playlistId)
);

CREATE TABLE IF NOT EXISTS track_playlist 
(
    trackId VARCHAR(36) NOT NULL,
    playlistId VARCHAR(36) NOT NULL,
    FOREIGN KEY (trackId) REFERENCES track(trackId),
    FOREIGN KEY (playlistId) REFERENCES playlist(playlistId)
);


CREATE INDEX IF NOT EXISTS track_index ON track(trackId);
CREATE INDEX IF NOT EXISTS album_index ON album(albumId);
CREATE INDEX IF NOT EXISTS artist_index ON artist(artistId);
CREATE INDEX IF NOT EXISTS track_album_index ON track_album(trackId);
CREATE INDEX IF NOT EXISTS track_artist_index ON track_artist(trackId);

/*
DROP INDEX IF EXISTS track_index;
DROP INDEX IF EXISTS album_index;
DROP INDEX IF EXISTS artist_index;
DROP INDEX IF EXISTS track_album_index;
DROP INDEX IF EXISTS track_artist_index;
*/