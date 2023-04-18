package com.zinkworks.urlshorten.service;

import com.zinkworks.urlshorten.model.Url;
import com.zinkworks.urlshorten.repository.UrlRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.net.URL;
import java.util.*;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String findLongUrl(String shortUrl) {
        Optional<Url> url = urlRepository.findById(shortUrl);
        if (url.isPresent()) {
            // https://stackoverflow.com/questions/39741102/how-to-beautifully-update-a-jpa-entity-in-spring-data
            // In above getOne(id) was used which is now replaced with getReferenceById()
            Url urlRecord = urlRepository.getReferenceById(shortUrl);
            // Calls method from Url model which increments by 1
            urlRecord.setTimesClicked();
            urlRepository.save(urlRecord);

            String longUrl = url.get().getLongUrl();
            return longUrl.matches("^(https?)://.*$") ? longUrl : String.format("https://%s", longUrl);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url not found");
    }

    public Url CreateShortUrl(String longUrl, String shortUrl, Date createdAt) {
        //used random to generate a random string for the short URL from long URL
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String urlShortner = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println(urlShortner);
        if (shortUrl == null) {
            shortUrl = urlShortner;
        }

        Url url = new Url(shortUrl, longUrl, createdAt);
        // When creating the short url it will call the setCreatedAt that sets the current date
        url.setCreatedAt();
        urlRepository.save(url);
        return url;
    }


    public static boolean validURL(String urlString) {
        try {
            new URL(urlString).toURI();
        } catch (Exception e) {
            System.out.println("404: Sorry, you entry an invalid URL!");
            return false;
        }
        return true;
    }

    public List<Url> getAllUrls() {
        // Here we use the findAll method which uses a Sort option as a parameter
        // https://www.baeldung.com/spring-data-sorting
        return urlRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }


    public String  CreateCustomUrl(String longUrl, String shortUrl) {

        if (urlRepository.existsByShortUrl(shortUrl)) {
            return shortUrl + " already exists, please try unique.";
        }else {
            Date firstDate = new Date();
            CreateShortUrl(longUrl, shortUrl, firstDate);
            return "URL Registered to system";
        }

    }

}

