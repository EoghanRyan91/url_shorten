package com.zinkworks.urlshorten.RestController;

import com.zinkworks.urlshorten.Dto.UrlDto;
import com.zinkworks.urlshorten.model.Url;
import com.zinkworks.urlshorten.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins= "http://localhost:3000")
@RestController
public class Controller {
    @Autowired
    UrlService urlService;

    @GetMapping("/shorturl/{shortUrl}")
    @ResponseBody
    public void redirectController(HttpServletResponse response, @PathVariable String shortUrl) throws IOException {

        String myLongUrl = urlService.findLongUrl(shortUrl);
        response.sendRedirect(myLongUrl);
    }

    // Returns a list of all the url records
    @GetMapping("/shorturl")
    public List<Url> getAll() {
        return urlService.getAllUrls();
    }

    @PostMapping("/create-url")
    public ResponseEntity<Object> saveUrl(@RequestBody UrlDto urlDto) {
        return ResponseEntity.status(201).body(urlService.CreateShortUrl(urlDto.getLongUrl(), urlDto.getShortUrl(), urlDto.getCreatedAt()));
    }

    @PostMapping("/customUrl")
    public ResponseEntity<Object> createCustomUrl(@RequestBody UrlDto urlDto) {
        if (urlDto.getShortUrl().length() != 6 ||  !urlDto.getShortUrl().matches("^[a-zA-Z0-9]*$")) {
                return ResponseEntity.status(406).body("Short Url format invalid, Supported formats : Alphanumerics, length: 6");
        }
        return ResponseEntity.status(200).body(urlService.CreateCustomUrl(urlDto.getLongUrl(), urlDto.getShortUrl()));
    }
}


