package nuts.muzinut.controller.image;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.service.image.Base64TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class Base64TestController {

    private final Base64TestService service;

    @GetMapping("/base-64")
    public String testBase64(Model model, MultipartFile file) throws IOException {
        String encodedFile = service.encodeFileToBase64(file);
        model.addAttribute("img", encodedFile);
        return "base64-test.html";
    }
}
