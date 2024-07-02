package nuts.muzinut.service.board;

import nuts.muzinut.domain.board.Board;
import nuts.muzinut.domain.board.Bookmark;
import nuts.muzinut.domain.board.Like;
import nuts.muzinut.domain.member.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class DetailCommonTest {

    @Test
    void isLike() {
        DetailCommon detailCommon = new DetailCommon();

        User u1 = new User();
        Board board = new Board();
        Like like = new Like();
        like.addLike(u1, board);

        assertThat(detailCommon.isLike(u1, board)).isTrue();
    }

    @Test
    void isBookmark() {
        DetailCommon detailCommon = new DetailCommon();

        User u1 = new User();
        Board board = new Board();
        Bookmark bookmark = new Bookmark();
        bookmark.addBookmark(u1, board);

        assertThat(detailCommon.isBookmark(u1, board)).isTrue();
    }
}