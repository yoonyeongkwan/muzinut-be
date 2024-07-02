package nuts.muzinut.domain.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "song_genre")
@NoArgsConstructor
@Getter
@Setter
public class SongGenre {

    @Id @GeneratedValue
    @Column(name = "song_genre_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;

    public SongGenre(Genre genre) {
        this.genre = genre;
    }

    //연관 관계 메서드
    public void addMusicGenre(Song song) {
        this.song = song;
        song.getSongGenres().add(this);
    }
}
