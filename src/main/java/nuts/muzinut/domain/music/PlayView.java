package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
public class PlayView {

    @Id @GeneratedValue
    @Column(name = "play_view_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate playDt;
}
