package nuts.muzinut.service.artist;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.artist.HotArtistDto;
import nuts.muzinut.dto.page.PageDto;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.repository.artist.ArtistRepository;
import nuts.muzinut.service.encoding.EncodingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final EncodingService encodingService;
    @Value("${spring.file.dir}")
    private String fileDir;

    public PageDto<HotArtistDto> hotArtist(int pageable) throws IOException {
        PageRequest pageRequest = PageRequest.of(pageable-1, 10);
        Page<HotArtistDto> page = artistRepository.hotArtist(pageRequest);

        // 데이터가 빈 경우 Exception 발생
        if(page.isEmpty()) throw new NotFoundEntityException(pageable + " 페이지에 해당하는 데이터가 존재하지 않습니다.");

        // 이미지 Base64 형식으로 인코딩하여 dto에 다시 저장
        for(HotArtistDto dto : page.getContent()) {
            String img = dto.getImg();
            String imgPath = fileDir + img;
            File file = new File(imgPath);
            String encodingImg = encodingService.encodingBase64(file);
            dto.setImg(encodingImg);
        }

        PageDto<HotArtistDto> data = new PageDto<>(
                page.getContent(),
                page.getNumber(),
                page.getContent().size(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        return data;
    }
}
