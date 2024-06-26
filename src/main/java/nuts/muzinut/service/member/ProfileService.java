package nuts.muzinut.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuts.muzinut.service.board.*;
import nuts.muzinut.service.security.UserService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserService userService;
    private final FollowService followService;
    private final AdminBoardService adminBoardService;
    private final LoungeService loungeService;
    private final FreeBoardService freeBoardService;
    private final RecruitBoardService recruitBoardService;
    private final CommentService commentService;

}
