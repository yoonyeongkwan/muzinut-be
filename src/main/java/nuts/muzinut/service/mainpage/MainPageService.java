package nuts.muzinut.service.mainpage;


import lombok.RequiredArgsConstructor;

import nuts.muzinut.dto.mainpage.*;
import nuts.muzinut.repository.mainpage.MainPageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.ArrayList;

import java.util.List;



@RequiredArgsConstructor
@Service
public class MainPageService {

    private final MainPageRepository mainPageRepository;

    @Value("${spring.file.dir}")
    private String fileDir;

    public MultiValueMap<String, Object> findMainTotalData(){
        List<HotSongDto> top10Songs = mainPageRepository.findTOP10Song();
        List<NewSongDto> newSongs = mainPageRepository.findNewSong();
        List<HotArtistDto> top5Artists = mainPageRepository.findTOP5Artist();
        List<HotBoardDto> hotBoards = findMainHotBoard();
        NewBoardDto newBoard = findMainNewBoard();
        MainTotalDto mainTotalDto = new MainTotalDto(top10Songs, newSongs ,top5Artists, hotBoards, newBoard);
        List<FileSystemResource> top10Imgs = top10Img(top10Songs);
        List<FileSystemResource> newSongImgs = newSongImg(newSongs);
        List<FileSystemResource> top5ArtistImgs = top5ArtistImg(top5Artists);

        return saveMultipartFormData(mainTotalDto, top10Imgs, newSongImgs, top5ArtistImgs);
    }
    public MultiValueMap<String, Object> saveMultipartFormData(MainTotalDto mainTotalDto, List<FileSystemResource> top10Imgs,
                                      List<FileSystemResource> newSongImgs, List<FileSystemResource> top5ArtistImgs){
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MainTotalDto> mainTotalDtoEntity = new HttpEntity<>(mainTotalDto, jsonHeaders);

        body.add("totalData", mainTotalDtoEntity);
        for (int i = 0; i < top10Imgs.size(); i++) {
            body.add("top10Img" + i, top10Imgs.get(i));
        }
        for (int i = 0; i < newSongImgs.size(); i++) {
            body.add("newSongImg" + i, newSongImgs.get(i));
        }
        for (int i = 0; i < top5ArtistImgs.size(); i++) {
            body.add("top5ArtistImg" + i, top5ArtistImgs.get(i));
        }
        return body;
    }

    public List<FileSystemResource> top10Img(List<HotSongDto> top10Songs) {
        List<FileSystemResource> top10Imgs = new ArrayList<>();
        for (HotSongDto top10Song : top10Songs) {
            FileSystemResource top10SongImg = new FileSystemResource
                    (fileDir + "/albumImg/" + top10Song.getAlbumImg() + ".png");
            top10Imgs.add(top10SongImg);
        }
        return top10Imgs;
    }

    public List<FileSystemResource> newSongImg(List<NewSongDto> newSongs) {
        List<FileSystemResource> newSongImgs = new ArrayList<>();
        for (NewSongDto newSong : newSongs) {
            FileSystemResource newSongImg = new FileSystemResource
                    (fileDir + "/albumImg/" + newSong.getAlbumImg() + ".png");
            newSongImgs.add(newSongImg);
        }
        return newSongImgs;
    }
    public List<FileSystemResource> top5ArtistImg(List<HotArtistDto> top5Artists) {
        List<FileSystemResource> top5ArtistImgs = new ArrayList<>();
        for (HotArtistDto top5Artist : top5Artists) {
            FileSystemResource top5ArtistImg = new FileSystemResource(fileDir + top5Artist.getProfileImg());
            top5ArtistImgs.add(top5ArtistImg);
        }

        return top5ArtistImgs;
    }

    public List<HotBoardDto> findMainHotBoard(){
        List<Object[]> resultList = mainPageRepository.findHotBoard();
        List<HotBoardDto> hotBoardDtos = new ArrayList<>();
        for (Object[] objects : resultList) {

            Long boardId = (Long) objects[0];
            String title = (String) objects[1];
            String nickname = (String) objects[2];
            int view = (int) objects[3];
            String dtype = (String) objects[4];
            HotBoardDto hotBoardDto = new HotBoardDto(boardId, title, nickname, view, dtype);
            hotBoardDtos.add(hotBoardDto);
        }
        return hotBoardDtos;
    }

    public NewBoardDto findMainNewBoard() {
        List<Object[]> results = mainPageRepository.findNewBoard();

        List<NewFreeBoardDto> freeBoardDtos = new ArrayList<>();
        List<NewRecruitBoardDto> recruitBoardDtos = new ArrayList<>();

        for (Object[] objects : results) {
            Long boardId = (Long) objects[0];
            String title = (String) objects[1];
            String nickname = (String) objects[2];
            String dtype = (String) objects[3];
            if (dtype.equals("FreeBoard")){
                NewFreeBoardDto newFreeBoardDto = new NewFreeBoardDto(boardId, title, nickname);
                freeBoardDtos.add(newFreeBoardDto);
            } else if (dtype.equals("RecruitBoard")) {
                NewRecruitBoardDto newRecruitBoardDto = new NewRecruitBoardDto(boardId, title, nickname);
                recruitBoardDtos.add(newRecruitBoardDto);
            }
        }
        return new NewBoardDto(freeBoardDtos,recruitBoardDtos);
    }
}
