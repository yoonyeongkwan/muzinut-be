package nuts.muzinut.repository.member;

import nuts.muzinut.domain.member.Mailbox;
import nuts.muzinut.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MailboxRepository extends JpaRepository<Mailbox, Long> {

    /**
     * @param isChecked: 읽지 않은 메일을 가져오는 것으므로 항상 false
     * @param user: 메일을 읽지 않은 유저
     * @return: 읽지 않은 메일의 수
     */
    @Query("select count(m) from Mailbox m where m.isChecked = :isChecked and m.user = :user")
    Long countNotReadMails(@Param("isChecked") boolean isChecked, @Param("user") User user); //읽지 않은 메일 수

    /**
     * @param isChecked: 메일을 읽은 상태를 True 로 해야 하므로 항상 true
     * @param user: 메일을 읽은 유저
     */
    @Modifying
    @Query("update Mailbox m set m.isChecked = :isChecked where m.user = :user")
    void readMails(@Param("isChecked") boolean isChecked, @Param("user") User user);

    @Query("select m.message from Mailbox m where m.user = :user")
    List<String> myMails(@Param("user") User user); //특정 유저의 메일함을 확인

    /**
     * 어드민이 공지사항 작성시 모든 회원들에게 우편함 알림이 간다.
     * @param message: 회원들에게 보낼 메시지
     */
    @Modifying
    @Query(
            value = "insert into mailbox (user_id, message, is_checked) " +
                    "select user_id, ?1, false " +
                    "from users", nativeQuery = true
    )
    void sendNotice(String message);



    /**
     * 팔로잉 한 아티스트가 음원을 낼 때, 팔로잉 한 회원의 우편함에 알림이 온다.
     * @param message: 우편함 보낼 메시지
     * @param followingMemberId: 팔로우 한 회원 (음원을 낸 아티스트)
     */
    @Modifying
    @Query(
            value = "insert into mailbox (user_id, message) " +
                    "select user_id, ?1 " +
                    "from follow as f where f.FOLLOWING_MEMBER_ID = ?2 and f.NOTIFICATION = true;", nativeQuery = true
    )
    void sendArtistMusicUploaded(String message, Long followingMemberId);

}
