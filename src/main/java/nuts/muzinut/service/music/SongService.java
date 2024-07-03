package nuts.muzinut.service.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.dto.mainpage.MainTotalDto;
import nuts.muzinut.dto.music.SongPageDto;
import nuts.muzinut.dto.page.PageDto;
import nuts.muzinut.repository.music.SongRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class SongService {

    @Value("${spring.file.dir}")
    private String fileDir;

    private final SongRepository songRepository;

    public MultiValueMap<String, Object> getNewSongs(int pageable) {
        pageable = pageable - 1;
        PageRequest pageRequest = PageRequest.of(pageable,20);
        Page<SongPageDto> page = songRepository.new100Song(pageRequest);
        List<SongPageDto> content = page.getContent();
        List<FileSystemResource> imgfiles = getAlbumImg(content);
        PageDto<SongPageDto> totalData = (PageDto<SongPageDto>) new PageDto<>(
                page.getContent(),
                page.getNumber(),
                page.getContent().size(),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return saveMultipartFormData(totalData, imgfiles);
    }

    public MultiValueMap<String, Object> getHotTOP100Songs(int pageable) {
        pageable = pageable - 1;
        PageRequest pageRequest = PageRequest.of(pageable, 20);
        Page<SongPageDto> page = songRepository.new100Song(pageRequest);
        List<SongPageDto> content = page.getContent();
        List<FileSystemResource> imgfiles = getAlbumImg(content);
        PageDto<SongPageDto> totalData = (PageDto<SongPageDto>) new PageDto<>(
                page.getContent(),
                page.getNumber(),
                page.getContent().size(),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return saveMultipartFormData(totalData, imgfiles);
    }

    public MultiValueMap<String, Object> saveMultipartFormData(PageDto<SongPageDto> totalData, List<FileSystemResource> imgfiles){
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PageDto<SongPageDto>> totalDtoEntity = new HttpEntity<>(totalData, jsonHeaders);
        body.add("totaldata", totalDtoEntity);
        for (int i = 0; i < imgfiles.size(); i++) {
            body.add("albumImg" + i, imgfiles.get(i));
        }
        return body;
    }

    public List<FileSystemResource> getAlbumImg(List<SongPageDto> content) {
        List<FileSystemResource> fileSystemResourceList = new ArrayList<>();
        for (SongPageDto songPageDto : content) {
            FileSystemResource fileSystemResource = new FileSystemResource
                    (fileDir + "/albumImg/" + songPageDto.getAlbumImg() + ".png");
            fileSystemResourceList.add(fileSystemResource);
        }
        return fileSystemResourceList;
    }
}
