package nuts.muzinut.service.encoding;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Service
@Transactional
@RequiredArgsConstructor
public class EncodingService {
    public String encodingBase64(File file) throws IOException {
        byte[] imageData = Files.readAllBytes(file.toPath());

        return Base64.getEncoder().encodeToString(imageData);
    }
}
