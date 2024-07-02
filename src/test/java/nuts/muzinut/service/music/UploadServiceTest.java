//package nuts.muzinut.service.music;
//
//import jakarta.persistence.EntityManager;
//import nuts.muzinut.domain.music.Album;
//import nuts.muzinut.domain.music.Genre;
//import nuts.muzinut.domain.music.Song;
//import nuts.muzinut.domain.music.SongGenre;
//import nuts.muzinut.dto.music.AlbumDto;
//import nuts.muzinut.dto.music.SongDto;
//import nuts.muzinut.repository.music.AlbumRepository;
//import nuts.muzinut.repository.music.SongGenreRepository;
//import nuts.muzinut.repository.music.SongRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//
//@SpringBootTest
//@Transactional
//class UploadServiceTest {
//
//    @Autowired
//    private AlbumService albumService;
//
//    @Autowired
//    private AlbumRepository albumRepository;
//
//    @Autowired
//    private SongRepository musicRepository;
//
//    @Autowired
//    private SongGenreRepository songGenreRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Test
//    void createMusic() throws Exception {
//        // Given
//        AlbumDto albumDto = new AlbumDto();
//        albumDto.setAlbumName("Test Album");
//        albumDto.setAlbumBio("This is a test album");
//
//        SongDto songDto1 = new SongDto();
//        songDto1.setSongName("Test Song 1");
//        songDto1.setLyricist("Test Lyricist 1");
//        songDto1.setComposer("Test Composer 1");
//        songDto1.setLyrics("Test Lyrics 1");
//        songDto1.setGenres(List.of("KPOP", "POP"));
//        songDto1.setOriginFilename("test_song1.mp3");
//
//        SongDto songDto2 = new SongDto();
//        songDto2.setSongName("Test Song 2");
//        songDto2.setLyricist("Test Lyricist 2");
//        songDto2.setComposer("Test Composer 2");
//        songDto2.setLyrics("Test Lyrics 2");
//        songDto2.setGenres(List.of("BALLAD", "RNB"));
//        songDto2.setOriginFilename("test_song2.mp3");
//
//        List<SongDto> songDtos = new ArrayList<>();
//        songDtos.add(songDto1);
//        songDtos.add(songDto2);
//        albumDto.setSongs(songDtos);
//
//        MockMultipartFile albumImg = new MockMultipartFile("albumImg", "test_album_img.jpg", "image/jpeg", "test_album_img_data".getBytes());
//        MockMultipartFile songFile1 = new MockMultipartFile("songFile1", "test_song1.mp3", "audio/mpeg", "test_song1_data".getBytes());
//        MockMultipartFile songFile2 = new MockMultipartFile("songFile2", "test_song2.mp3", "audio/mpeg", "test_song2_data".getBytes());
//        List<MultipartFile> songFiles = new ArrayList<>();
//        songFiles.add(songFile1);
//        songFiles.add(songFile2);
//
//        // When
//        String storeAlbumImg = albumService.saveAlbumImg(albumImg);
//        AlbumDto storeAlbumData = albumService.saveSongs(songFiles, albumDto);
//        albumService.saveAlbumData(storeAlbumData, storeAlbumImg);
////        albumService.createMusic(albumDto, albumImg, songFiles);
////        em.flush();
////        em.clear();
////
////        // Then
////        Optional<Album> savedAlbum = albumRepository.findByName("Test Album");
////        Album album = savedAlbum.get();
////        assertThat(album).isNotNull();
////        assertThat(album.getName()).isEqualTo("Test Album");
////        assertThat(album.getIntro()).isEqualTo("This is a test album");
////        assertThat(album.getAlbumImg()).isNotNull();
//
////        List<Song> savedSongs = musicRepository.findAllByAlbum(album);
////        assertThat(savedSongs).hasSize(2);
//
////        Song savedSong1 = savedSongs.get(0);
////        assertThat(savedSong1.getName()).isEqualTo("Test Song 1");
////        assertThat(savedSong1.getComposer()).isEqualTo("Test Composer 1");
////        assertThat(savedSong1.getLyricist()).isEqualTo("Test Lyricist 1");
////        assertThat(savedSong1.getLyrics()).isEqualTo("Test Lyrics 1");
////        assertThat(savedSong1.getMusicFilename()).isEqualTo("test_song1.mp3");
////
////        Song savedSong2 = savedSongs.get(1);
////        assertThat(savedSong2.getName()).isEqualTo("Test Song 2");
////        assertThat(savedSong2.getComposer()).isEqualTo("Test Composer 2");
////        assertThat(savedSong2.getLyricist()).isEqualTo("Test Lyricist 2");
////        assertThat(savedSong2.getLyrics()).isEqualTo("Test Lyrics 2");
////        assertThat(savedSong2.getMusicFilename()).isEqualTo("test_song2.mp3");
////
////
////        List<SongGenre> savedSongGenres = songGenreRepository.findAll();
////        assertThat(savedSongGenres).hasSize(4);
////        assertThat(savedSongGenres.stream().anyMatch(sg -> sg.getGenre() == Genre.KPOP && sg.getSong().equals(savedSong1))).isTrue();
////        assertThat(savedSongGenres.stream().anyMatch(sg -> sg.getGenre() == Genre.POP && sg.getSong().equals(savedSong1))).isTrue();
////        assertThat(savedSongGenres.stream().anyMatch(sg -> sg.getGenre() == Genre.BALLAD && sg.getSong().equals(savedSong2))).isTrue();
////        assertThat(savedSongGenres.stream().anyMatch(sg -> sg.getGenre() == Genre.RNB && sg.getSong().equals(savedSong2))).isTrue();
//    }
//
//
//}
