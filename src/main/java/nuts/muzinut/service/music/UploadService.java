package nuts.muzinut.service.music;

import lombok.AllArgsConstructor;
import nuts.muzinut.domain.music.*;
import nuts.muzinut.dto.music.AlbumDto;
import nuts.muzinut.dto.music.SongDto;
import nuts.muzinut.repository.member.UserRepository;
import nuts.muzinut.repository.music.AlbumRepository;
import nuts.muzinut.repository.music.SongGenreRepository;
import nuts.muzinut.repository.music.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UploadService {

    private final UserRepository userRepository;
    private final SongRepository musicRepository;
    private final AlbumRepository albumRepository;
    private final FileUploadService fileUploadService;
    private final SongGenreRepository songGenreRepository;

    public AlbumDto createMusic(AlbumDto albumDto, MultipartFile albumImg, List<MultipartFile> songFiles) throws Exception {

        String albumImgName = fileUploadService.albumImgUpload(albumImg); // 이미지 파일 저장 및 uuid 값 추출
        albumDto.setAlbumImg(albumImgName); // uuid 추출 된 값을 dto 저장
        Album album = albumDto.toEntity();  // db 저장을 위한 엔티티 변환

        List<String> fileNames = new ArrayList<>();
        for (MultipartFile songFile : songFiles) {
            String fileName = fileUploadService.songFileUpload(songFile);
            fileNames.add(fileName);
        }

        List<Song> songList = new ArrayList<>();
        for (int i = 0; i < albumDto.getSongs().size(); i++) {
            SongDto songDto = albumDto.getSongs().get(i);
            songDto.setMusicFilename(fileNames.get(i));
            Song song = songDto.toEntity();
            song.setAlbum(album);
            album.addSongIntoAlbum(song);
            songList.add(song);

            // 장르 정보 변환
            List<SongGenre> genres = new ArrayList<>();
            for (String genreStr : songDto.getGenres()) {
                Genre genre = Genre.valueOf(genreStr.toUpperCase());
                SongGenre songGenre = new SongGenre(genre);
                songGenre.addMusicGenre(song);
                genres.add(songGenre);
            }
            song.setSongGenres(genres);

        }

        album = albumRepository.save(album);
        songList.forEach(musicRepository::save);
        songGenreRepository.saveAll(album.getSongList().stream()
                .flatMap(s -> s.getSongGenres().stream())
                .collect(Collectors.toList()));
        return null;
    }

}

