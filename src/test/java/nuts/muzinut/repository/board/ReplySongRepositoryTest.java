package nuts.muzinut.repository.board;

import nuts.muzinut.domain.board.Comment;
import nuts.muzinut.domain.board.Reply;
import nuts.muzinut.domain.member.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReplySongRepositoryTest {

    @Autowired CommentRepository commentRepository;
    @Autowired ReplyRepository replyRepository;

    @Test
    void save() {

        //given
        User user = new User();

        Comment comment = new Comment();
        commentRepository.save(comment);

        Reply reply = new Reply();
        reply.addReply(comment, "reply content", user);

        //when
        replyRepository.save(reply);

        //then
        Optional<Reply> findReply = replyRepository.findById(reply.getId());
        assertThat(findReply.get()).isEqualTo(reply);
        assertThat(findReply.get().getComment()).isEqualTo(comment);
    }

    @Test
    void delete() {

        //given
        Reply reply = new Reply();
        replyRepository.save(reply);

        //when
        replyRepository.delete(reply);

        //then
        Optional<Reply> findReply = replyRepository.findById(reply.getId());
        assertThat(findReply.isEmpty()).isTrue();
    }
}