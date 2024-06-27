package nuts.muzinut.repository.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nuts.muzinut.domain.member.Follow;
import nuts.muzinut.domain.member.Mailbox;
import nuts.muzinut.domain.member.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MailboxSongRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired UserRepository userRepository;
    @Autowired MailboxRepository mailboxRepository;
    @Autowired FollowRepository followRepository;

    @Test
    void save() {

        //given
        User user = new User("email", "password");
        userRepository.save(user);
        Mailbox mailbox = new Mailbox();
        mailbox.createMailbox(user);

        //when
        mailboxRepository.save(mailbox);

        //then
        Optional<Mailbox> findMailbox = mailboxRepository.findById(mailbox.getId());
        assertThat(findMailbox.get().getUser()).isEqualTo(user); //저장된 메일함의 멤버를 확인
        assertThat(findMailbox.get()).isEqualTo(mailbox); //저장된 메일함의 멤버를 확인
    }

    @Test
    void delete() {

        //given
        User user = new User("email", "password");
        userRepository.save(user);
        Mailbox mailbox = new Mailbox();
        mailbox.createMailbox(user);
        mailboxRepository.save(mailbox);

        //when
        mailboxRepository.delete(mailbox);

        //then
        Optional<Mailbox> findMailbox = mailboxRepository.findById(mailbox.getId());
        assertThat(findMailbox.isEmpty()).isTrue();
    }

    /**
     * 회원 삭제시 해당 회원의 우편함도 삭제한다..
     */
    @Test
    void delete_with_member() {

        //given
        User user = new User("email", "password");
        userRepository.save(user);
        Mailbox mailbox = new Mailbox();
        mailbox.createMailbox(user);
        mailboxRepository.save(mailbox);

        //when
        userRepository.delete(user);

        //then
        Optional<Mailbox> findMailbox = mailboxRepository.findById(mailbox.getId());
        assertThat(findMailbox.isEmpty()).isTrue(); //회원 삭제시 해당 회원의 메일함이 없다.
    }

    //유저가 읽지 않은 메일 수를 확인
    @Test
    void notReadMail() {

        //given
        User user = new User("userA", "password");
        userRepository.save(user);

        createMail(user); //userA의 메일 생성
        createMail(user);

        //when
        Long notReadMailCount = mailboxRepository.countNotReadMails(false, user);

        //then
        assertThat(notReadMailCount).isEqualTo(2); //읽지 않은 메일은 총 2개
    }

    //유저가 메일을 읽으면 모든 읽지 않은 메일들을 읽음 표시로 바뀐다
    @Test
    void readMails() {

        //given
        User user = createUser();
        createMail(user); //user 의 메일 생성
        createMail(user);

        //when
        mailboxRepository.readMails(true, user); //모든 메일은 읽음 처리된다.

        //then
        Long notReadMailCount = mailboxRepository.countNotReadMails(false, user);
        assertThat(notReadMailCount).isEqualTo(0); //모든 메일은 읽은 상태.
    }

    //특정 회원의 메일함 확인하기
    @Test
    void mailContent() {

        //given
        User user = createUser();
        createMail(user, "message1"); //user 의 메일 생성
        createMail(user, "message2"); //user 의 메일 생성

        //when
        List<String> findMails = mailboxRepository.myMails(user);

        //then
        assertThat(findMails.size()).isEqualTo(2); //user 가 받은 메시지는 2개
        assertThat(findMails)
                .containsExactly("message1", "message2"); //user 의 메시지 내용
    }

    //모든 회원들에게 공지사항을 전송
    @Test
    void sendNotice() {

        //given
        User user1 = createUser("userA");
        User user2 = createUser("userB");

        //when
        mailboxRepository.sendNotice("hello message"); //모든 회원들에게 메일 전송

        //then
        List<Mailbox> result = mailboxRepository.findAll();
        assertThat(result.size()).isEqualTo(2); //총 2명의 회원에게 공지사항이 전송된다
        assertThat(result)
                .extracting("message")
                .containsOnly("hello message"); //두 회원이 받은 메시지는 "hello .."
    }

    //팔로잉 한 아티스트가 음원을 낼 때, 팔로잉 한 회원의 우편함에 알림이 온다.
    @Test
    void musicUploadNotice() {

        //given
        List<User> users = createFollow(); //u1, u2가  u3를 팔로우 했음
        User artist = users.get(2); //음원을 업로드한 아티스트

        //when
        mailboxRepository.sendArtistMusicUploaded("music uploaded", artist.getId());

        //then
        List<Mailbox> result = mailboxRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result)
                .extracting("message")
                .containsOnly("music uploaded");
    }

    //팔로잉 한 아티스트가 음원을 낼 때, 팔로잉 한 회원의 우편함에 알림이 온다.
    //하지만 알림을 꺼놓은 회원에게는 우편함에 알림이 가지 않는다.
    @Test
    void turnOffNotification() {

        //given
        List<User> users = createFollow(); //u1, u2가  u3를 팔로우 했음
        User u1 = users.getFirst();
        User u3 = users.get(2); //음원을 업로드한 아티스트
        followRepository.updateNotificationStatus(false, u1, u3.getId()); //알람 끄기
        clearContext();

        //when
        mailboxRepository.sendArtistMusicUploaded("music uploaded", u3.getId());

        //then
        List<Mailbox> result = mailboxRepository.findAll();
        //u1은 u3 의 알람을 꺼놓았기 때문에 u2 만 알람이 간다.
        assertThat(result.size()).isEqualTo(1);
        assertThat(result)
                .extracting("message")
                .containsOnly("music uploaded");
    }

    private User createUser() {
        User user = new User("userA", "password");
        return userRepository.save(user);
    }

    private User createUser(String username) {
        User user = new User(username, "password");
        return userRepository.save(user);
    }

    private void createMail(User user) {
        Mailbox mail = new Mailbox();
        mail.createMailbox(user);
        mailboxRepository.save(mail);
    }

    private void createMail(User user, String message) {
        Mailbox mail = new Mailbox();
        mail.createMailbox(user, message);
        mailboxRepository.save(mail);
    }

    private List<User> createFollow() {
        User u1 = new User("m1@naver.com", "1234");
        User u2 = new User("m2@naver.com", "1234");
        User u3 = new User("m3@naver.com", "1234");

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);

        Follow follow1 = new Follow();
        Follow follow2 = new Follow();

        follow1.createFollowing(u1, u3); //u1 이 u3를 팔로우 했음
        follow2.createFollowing(u2, u3); //u2 이 u3를 팔로우 했음
        followRepository.save(follow1);
        followRepository.save(follow2);

        return List.of(u1, u2, u3);
    }

    private void clearContext() {
        em.flush();
        em.clear();
    }

}