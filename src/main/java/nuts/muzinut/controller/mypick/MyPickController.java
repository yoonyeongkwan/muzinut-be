package nuts.muzinut.controller.mypick;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.mypick.FindArtistDto;
import nuts.muzinut.dto.mypick.MyPickCommentDto;
import nuts.muzinut.dto.mypick.VoteDto;
import nuts.muzinut.service.mypick.MyPickService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mypick")
@RequiredArgsConstructor
public class MyPickController {
    private final MyPickService myPickService;
    @GetMapping("/findArtist/{findNickname}")
    public List<FindArtistDto> findArtist(@Validated @PathVariable("findNickname") @NotNull String findNickname) throws IOException {
        return myPickService.findArtist(findNickname);
    }

    @GetMapping("/findOneArtist/{findNickname}")
    public FindArtistDto findOneArtist(@Validated @PathVariable("findNickname") @NotNull Long findArtistId) throws IOException {
        return myPickService.findOneArtist(findArtistId);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/remainVote")
    public ResponseEntity<Map<String,Integer>> remainVote() {
        Integer remainVote = myPickService.getRemainVote();
        Map<String, Integer> body = new HashMap<>();
        body.put("remainVote", remainVote);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/vote")
    public ResponseEntity<String> vote(@Valid @RequestBody VoteDto voteDto) {
        Long userId = myPickService.getCurrentUserId();
        Long voteUserId = voteDto.getVoteUserId();
        Integer voteAmount = voteDto.getVoteAmount();
        myPickService.vote(userId, voteUserId, voteAmount);

        return new ResponseEntity<>("vote success", HttpStatus.OK);
    }

    @GetMapping("/ranking")
    public ResponseEntity<Map<String,Object>> ranking() throws IOException {
        // 랭킹 3위까지 데이터 출력
        Map<String, Object> body = myPickService.ranking();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/comment")
    public List<MyPickCommentDto> comment(
            @RequestParam(value = "lastNum", defaultValue = "0") Long lastNum,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        PageRequest pageable = PageRequest.ofSize(limit);
        List<MyPickCommentDto> comment = myPickService.getComment();
        return comment;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/createComment")
    public ResponseEntity<String> writeComment(@Valid @RequestBody MyPickCommentDto myPickCommentDto) {
        String comment = myPickCommentDto.getComment();
        myPickService.createComment(comment);
        return new ResponseEntity<>("create success", HttpStatus.OK);
    }
}
