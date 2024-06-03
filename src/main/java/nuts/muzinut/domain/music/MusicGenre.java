package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "music_genre")
@Getter
public class MusicGenre {

    @Id @GeneratedValue
    @Column(name = "music_genre_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

    private String genre;

    //연관 관계 메서드
    public void addMusicGenre(Music music) {
        this.music = music;
        music.getMusicGenres().add(this);
    }
}
