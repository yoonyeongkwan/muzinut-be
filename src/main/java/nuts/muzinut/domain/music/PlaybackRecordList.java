package nuts.muzinut.domain.music;

import jakarta.persistence.*;

@Entity
@Table(name = "playback_record_list")
public class PlaybackRecordList {

    @Id @GeneratedValue
    @Column(name = "playback_record_list_id")
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinColumn(name = "playback_record_id")
    private PlaybackRecord playbackRecord;

    private Long music_id;

    //편의 메서드
    public void addRecord(PlaybackRecord playbackRecord) {
        this.playbackRecord = playbackRecord;
        playbackRecord.getPlaybackRecordLists().add(this);
    }
}
