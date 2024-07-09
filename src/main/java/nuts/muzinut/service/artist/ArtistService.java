package nuts.muzinut.service.artist;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.artist.HotArtistDto;
import nuts.muzinut.dto.page.PageDto;
import nuts.muzinut.repository.artist.ArtistRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
@Service
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;
    @Value("${spring.file.dir}")
    private String fileDir;


    public MultiValueMap<String, Object> hotArtist(int pageable) throws IOException {
        PageRequest pageRequest = PageRequest.of(pageable-1, 10);
        Page<HotArtistDto> page = artistRepository.hotArtist(pageRequest);
        PageDto<HotArtistDto> data = new PageDto<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalPages()
        );

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add("data", data);

        for(HotArtistDto dto : page.getContent()) {
            String img = dto.getImg();
            Path filePath = new File(fileDir + "/profileImg/" + img).toPath();
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));
            body.add(img, resource);
        }

        return body;
    }
}
