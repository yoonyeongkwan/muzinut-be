package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "music_genre")
@NoArgsConstructor
@Getter
public class MusicGenre {

    @Id @GeneratedValue
    @Column(name = "music_genre_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;

    public MusicGenre(Genre genre) {
        this.genre = genre;
    }

    //연관 관계 메서드
    public void addMusicGenre(Music music) {
        this.music = music;
        music.getMusicGenres().add(this);
    }
}
