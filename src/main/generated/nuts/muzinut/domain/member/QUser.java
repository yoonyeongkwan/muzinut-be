package nuts.muzinut.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1567562901L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> accountNumber = createNumber("accountNumber", Integer.class);

    public final BooleanPath activated = createBoolean("activated");

    public final ListPath<nuts.muzinut.domain.board.AdminBoard, nuts.muzinut.domain.board.QAdminBoard> adminBoards = this.<nuts.muzinut.domain.board.AdminBoard, nuts.muzinut.domain.board.QAdminBoard>createList("adminBoards", nuts.muzinut.domain.board.AdminBoard.class, nuts.muzinut.domain.board.QAdminBoard.class, PathInits.DIRECT2);

    public final SetPath<Authority, QAuthority> authorities = this.<Authority, QAuthority>createSet("authorities", Authority.class, QAuthority.class, PathInits.DIRECT2);

    public final ListPath<nuts.muzinut.domain.board.Board, nuts.muzinut.domain.board.QBoard> boards = this.<nuts.muzinut.domain.board.Board, nuts.muzinut.domain.board.QBoard>createList("boards", nuts.muzinut.domain.board.Board.class, nuts.muzinut.domain.board.QBoard.class, PathInits.DIRECT2);

    public final ListPath<nuts.muzinut.domain.board.Bookmark, nuts.muzinut.domain.board.QBookmark> bookmarks = this.<nuts.muzinut.domain.board.Bookmark, nuts.muzinut.domain.board.QBookmark>createList("bookmarks", nuts.muzinut.domain.board.Bookmark.class, nuts.muzinut.domain.board.QBookmark.class, PathInits.DIRECT2);

    public final ListPath<nuts.muzinut.domain.chat.ChatMember, nuts.muzinut.domain.chat.QChatMember> chatMembers = this.<nuts.muzinut.domain.chat.ChatMember, nuts.muzinut.domain.chat.QChatMember>createList("chatMembers", nuts.muzinut.domain.chat.ChatMember.class, nuts.muzinut.domain.chat.QChatMember.class, PathInits.DIRECT2);

    public final ListPath<nuts.muzinut.domain.board.CommentLike, nuts.muzinut.domain.board.QCommentLike> commentLikes = this.<nuts.muzinut.domain.board.CommentLike, nuts.muzinut.domain.board.QCommentLike>createList("commentLikes", nuts.muzinut.domain.board.CommentLike.class, nuts.muzinut.domain.board.QCommentLike.class, PathInits.DIRECT2);

    public final ListPath<nuts.muzinut.domain.board.Comment, nuts.muzinut.domain.board.QComment> comments = this.<nuts.muzinut.domain.board.Comment, nuts.muzinut.domain.board.QComment>createList("comments", nuts.muzinut.domain.board.Comment.class, nuts.muzinut.domain.board.QComment.class, PathInits.DIRECT2);

    public final NumberPath<Integer> declaration = createNumber("declaration", Integer.class);

    public final ListPath<Follow, QFollow> followings = this.<Follow, QFollow>createList("followings", Follow.class, QFollow.class, PathInits.DIRECT2);

    public final ListPath<nuts.muzinut.domain.board.FreeBoard, nuts.muzinut.domain.board.QFreeBoard> freeBoards = this.<nuts.muzinut.domain.board.FreeBoard, nuts.muzinut.domain.board.QFreeBoard>createList("freeBoards", nuts.muzinut.domain.board.FreeBoard.class, nuts.muzinut.domain.board.QFreeBoard.class, PathInits.DIRECT2);

    public final ListPath<Friend, QFriend> friends = this.<Friend, QFriend>createList("friends", Friend.class, QFriend.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    public final ListPath<nuts.muzinut.domain.board.Like, nuts.muzinut.domain.board.QLike> likeList = this.<nuts.muzinut.domain.board.Like, nuts.muzinut.domain.board.QLike>createList("likeList", nuts.muzinut.domain.board.Like.class, nuts.muzinut.domain.board.QLike.class, PathInits.DIRECT2);

    public final ListPath<nuts.muzinut.domain.board.Lounge, nuts.muzinut.domain.board.QLounge> lounges = this.<nuts.muzinut.domain.board.Lounge, nuts.muzinut.domain.board.QLounge>createList("lounges", nuts.muzinut.domain.board.Lounge.class, nuts.muzinut.domain.board.QLounge.class, PathInits.DIRECT2);

    public final ListPath<Mailbox, QMailbox> mailboxes = this.<Mailbox, QMailbox>createList("mailboxes", Mailbox.class, QMailbox.class, PathInits.DIRECT2);

    public final ListPath<nuts.muzinut.domain.chat.Message, nuts.muzinut.domain.chat.QMessage> messages = this.<nuts.muzinut.domain.chat.Message, nuts.muzinut.domain.chat.QMessage>createList("messages", nuts.muzinut.domain.chat.Message.class, nuts.muzinut.domain.chat.QMessage.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Integer> nuts = createNumber("nuts", Integer.class);

    public final ListPath<nuts.muzinut.domain.nuts.NutsUsageHistory, nuts.muzinut.domain.nuts.QNutsUsageHistory> nutsUsageHistories = this.<nuts.muzinut.domain.nuts.NutsUsageHistory, nuts.muzinut.domain.nuts.QNutsUsageHistory>createList("nutsUsageHistories", nuts.muzinut.domain.nuts.NutsUsageHistory.class, nuts.muzinut.domain.nuts.QNutsUsageHistory.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final ListPath<nuts.muzinut.domain.nuts.PaymentHistory, nuts.muzinut.domain.nuts.QPaymentHistory> paymentHistories = this.<nuts.muzinut.domain.nuts.PaymentHistory, nuts.muzinut.domain.nuts.QPaymentHistory>createList("paymentHistories", nuts.muzinut.domain.nuts.PaymentHistory.class, nuts.muzinut.domain.nuts.QPaymentHistory.class, PathInits.DIRECT2);

    public final nuts.muzinut.domain.music.QPlaylist playlist;

    public final ListPath<nuts.muzinut.domain.music.PlayNut, nuts.muzinut.domain.music.QPlayNut> playNutList = this.<nuts.muzinut.domain.music.PlayNut, nuts.muzinut.domain.music.QPlayNut>createList("playNutList", nuts.muzinut.domain.music.PlayNut.class, nuts.muzinut.domain.music.QPlayNut.class, PathInits.DIRECT2);

    public final StringPath profileBannerImgFilename = createString("profileBannerImgFilename");

    public final StringPath profileImgFilename = createString("profileImgFilename");

    public final NumberPath<Integer> receiveVote = createNumber("receiveVote", Integer.class);

    public final ListPath<nuts.muzinut.domain.board.RecruitBoard, nuts.muzinut.domain.board.QRecruitBoard> recruitBoards = this.<nuts.muzinut.domain.board.RecruitBoard, nuts.muzinut.domain.board.QRecruitBoard>createList("recruitBoards", nuts.muzinut.domain.board.RecruitBoard.class, nuts.muzinut.domain.board.QRecruitBoard.class, PathInits.DIRECT2);

    public final ListPath<nuts.muzinut.domain.board.Reply, nuts.muzinut.domain.board.QReply> replies = this.<nuts.muzinut.domain.board.Reply, nuts.muzinut.domain.board.QReply>createList("replies", nuts.muzinut.domain.board.Reply.class, nuts.muzinut.domain.board.QReply.class, PathInits.DIRECT2);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final ListPath<nuts.muzinut.domain.music.Song, nuts.muzinut.domain.music.QSong> songList = this.<nuts.muzinut.domain.music.Song, nuts.muzinut.domain.music.QSong>createList("songList", nuts.muzinut.domain.music.Song.class, nuts.muzinut.domain.music.QSong.class, PathInits.DIRECT2);

    public final ListPath<nuts.muzinut.domain.nuts.SupportMsg, nuts.muzinut.domain.nuts.QSupportMsg> supportMsgs = this.<nuts.muzinut.domain.nuts.SupportMsg, nuts.muzinut.domain.nuts.QSupportMsg>createList("supportMsgs", nuts.muzinut.domain.nuts.SupportMsg.class, nuts.muzinut.domain.nuts.QSupportMsg.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public final NumberPath<Integer> vote = createNumber("vote", Integer.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.playlist = inits.isInitialized("playlist") ? new nuts.muzinut.domain.music.QPlaylist(forProperty("playlist"), inits.get("playlist")) : null;
    }

}

