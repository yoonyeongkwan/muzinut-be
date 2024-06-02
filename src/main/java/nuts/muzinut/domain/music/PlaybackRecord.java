package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import nuts.muzinut.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playback_record")
@Getter
public class PlaybackRecord {

    @Id @GeneratedValue
    @Column(name = "playback_record")
    private Long id;

    //Todo
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "playbackRecord")
    private List<PlaybackRecordList> playbackRecordLists = new ArrayList<>();
}
