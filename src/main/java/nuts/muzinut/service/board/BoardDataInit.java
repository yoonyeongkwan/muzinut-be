package nuts.muzinut.service.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import nuts.muzinut.domain.board.FreeBoard;
import nuts.muzinut.domain.board.RecruitBoard;
import nuts.muzinut.domain.member.User;
import nuts.muzinut.repository.member.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardDataInit {

    private final UserRepository userRepository;
    @PersistenceContext
    EntityManager em;

    public void boardData() {
        for(int i=1; i<=100; i++){
            Optional<User> optional1 = userRepository.findById(1L);
            Optional<User> optional2 = userRepository.findById(2L);
            Optional<User> optional3 = userRepository.findById(3L);
            User user1 = optional1.get();
            User user2 = optional2.get();
            User user3 = optional3.get();
            if(i <= 30){
                FreeBoard freeBoard = new FreeBoard("프리게시판 제목"+i);
                freeBoard.addBoard(user1);
                RecruitBoard recruitBoard = new RecruitBoard("모집게시판 제목"+i);
                recruitBoard.addBoard(user1);
                em.persist(freeBoard);
                em.persist(recruitBoard);
            } else if (i <= 70) {
                FreeBoard freeBoard = new FreeBoard("프리게시판 제목"+i);
                freeBoard.addBoard(user2);
                RecruitBoard recruitBoard = new RecruitBoard("모집게시판 제목"+i);
                recruitBoard.addBoard(user2);
                if (i == 35){
                    freeBoard.addView();
                } else if (i == 50) {
                    recruitBoard.incrementView();recruitBoard.incrementView();
                }
                em.persist(freeBoard);
                em.persist(recruitBoard);
            } else {
                FreeBoard freeBoard = new FreeBoard("프리게시판 제목"+i);
                freeBoard.addBoard(user3);
                RecruitBoard recruitBoard = new RecruitBoard("모집게시판 제목"+i);
                recruitBoard.addBoard(user3);
                if (i == 75) {
                    freeBoard.addView();freeBoard.addView();freeBoard.addView();
                } else if (i== 88) {
                    recruitBoard.incrementView();recruitBoard.incrementView();recruitBoard.incrementView();recruitBoard.incrementView();
                } else if (i == 97) {
                    freeBoard.addView();freeBoard.addView();freeBoard.addView();freeBoard.addView();freeBoard.addView();
                }
                em.persist(freeBoard);
                em.persist(recruitBoard);
            }
        }
    }
}
