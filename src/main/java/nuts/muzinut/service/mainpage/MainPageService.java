package nuts.muzinut.service.mainpage;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.FreeBoard;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.dto.mainpage.*;
import nuts.muzinut.repository.mainpage.MainPageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nuts.muzinut.domain.board.QBoard.board;
import static nuts.muzinut.domain.board.QFreeBoard.freeBoard;
import static nuts.muzinut.domain.board.QRecruitBoard.recruitBoard;

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
        List<NewBoardDto> newBoards = findMainNewBoard();
        NewBoardDto newBoard = newBoards.get(0);
        MainTotalDto mainTotalDto = new MainTotalDto(top10Songs, newSongs ,top5Artists, hotBoards, newBoard);
        List<FileSystemResource> top10Imgs = top10Img(top10Songs);
        List<FileSystemResource> newSongImgs = newSongImg(newSongs);
        List<FileSystemResource> top5ArtistImgs = top5ArtistImg(top5Artists);
        return saveMultipartFormData(mainTotalDto, top10Imgs, newSongImgs, top5ArtistImgs);
    }

    public MultiValueMap<String, Object> saveMultipartFormData(MainTotalDto mainTotalDto, List<FileSystemResource> top10Imgs,
                                      List<FileSystemResource> newSongImgs, List<FileSystemResource> top5ArtistImgs){
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("totalData",mainTotalDto);
        body.add("top10Imgs", top10Imgs);
        body.add("newSongImgs", newSongImgs);
        body.add("top5ArtistImgs", top5ArtistImgs);
        return body;
    }

    public List<FileSystemResource> top10Img(List<HotSongDto> top10Songs) {
        List<String> albumImgs = new ArrayList<>();
        for (HotSongDto top10Song : top10Songs) {
            albumImgs.add(top10Song.getAlbumImg());
        }
        List<FileSystemResource> top10Imgs = new ArrayList<>();
        for (String albumImg : albumImgs) {
            FileSystemResource top10SongImg = new FileSystemResource(fileDir + "/albumImg/" + albumImg);
        }
        return top10Imgs;
    }

    public List<FileSystemResource> newSongImg(List<NewSongDto> newSongs) {
        List<String> albumImgs = new ArrayList<>();
        for (NewSongDto newSong : newSongs) {
            albumImgs.add(newSong.getAlbumImg());
        }
        List<FileSystemResource> newSongImgs = new ArrayList<>();
        for (String albumImg : albumImgs) {
            FileSystemResource newSongImg = new FileSystemResource(fileDir + "/albumImg/" + albumImg);
        }
        return newSongImgs;
    }
    public List<FileSystemResource> top5ArtistImg(List<HotArtistDto> top5Artists) {
        List<String> profileImgs = new ArrayList<>();
        for (HotArtistDto top5Artist : top5Artists) {
            profileImgs.add(top5Artist.getProfileImg());
        }
        List<FileSystemResource> top5ArtistImgs = new ArrayList<>();
        for (String profileImg : profileImgs) {
            FileSystemResource newSongImg = new FileSystemResource(fileDir + profileImg);
        }
        return top5ArtistImgs;
    }

    public List<HotBoardDto> findMainHotBoard(){
        List<Tuple> results = mainPageRepository.findHotBoard();
        List<HotBoardDto> hotBoardDtos = new ArrayList<>();
        for (Tuple tuple : results) {
            Board b = tuple.get(board);
            FreeBoard fb = tuple.get(freeBoard);
            RecruitBoard rb = tuple.get(recruitBoard);

            if (fb != null) {
                hotBoardDtos.add(new HotBoardDto(
                        fb.getId(),
                        fb.getTitle(),
                        b.getUser().getNickname(),
                        fb.getView(),
                        fb.getClass().getSimpleName()
                ));
            } else if (rb != null) {
                hotBoardDtos.add(new HotBoardDto(
                        rb.getId(),
                        rb.getTitle(),
                        b.getUser().getNickname(),
                        rb.getView(),
                        rb.getClass().getSimpleName()
                ));
            }
        }
        return hotBoardDtos;
    }

    public List<NewBoardDto> findMainNewBoard() {
        List<Tuple> results = mainPageRepository.findNewBoard();

        List<NewFreeBoardDto> freeBoardDtos = new ArrayList<>();
        List<NewRecruitBoardDto> recruitBoardDtos = new ArrayList<>();

        for (Tuple tuple : results) {
            Board b = tuple.get(board);
            FreeBoard fb = tuple.get(freeBoard);
            RecruitBoard rb = tuple.get(recruitBoard);

            if (fb != null) {
                freeBoardDtos.add(new NewFreeBoardDto(
                        fb.getId(),
                        fb.getTitle(),
                        b.getUser().getNickname()
                ));
            }

            if (rb != null) {
                recruitBoardDtos.add(new NewRecruitBoardDto(
                        rb.getId(),
                        rb.getTitle(),
                        b.getUser().getNickname()
                ));
            }
        }


        int freeBoardSize = freeBoardDtos.size();
        int recruitBoardSize = recruitBoardDtos.size();

        return Arrays.asList(
                new NewBoardDto(freeBoardDtos.subList(0, Math.min(4, freeBoardSize)), recruitBoardDtos.subList(0, Math.min(4, recruitBoardSize))),
                new NewBoardDto(freeBoardDtos.subList(Math.max(0, freeBoardSize - 4), freeBoardSize), recruitBoardDtos.subList(Math.max(0, recruitBoardSize - 4), recruitBoardSize))
        );
    }
}
