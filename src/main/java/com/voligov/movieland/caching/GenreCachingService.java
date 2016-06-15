package com.voligov.movieland.caching;

import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class GenreCachingService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<Genre> genreCache = new CopyOnWriteArrayList<>();

    public Genre getById(int id) {
        for (Genre genre : genreCache) {
            if (genre.getId() == id) {
                return genre;
            }
        }
        log.warn("Genre with if = {} not found in cache", id);
        return null;
    }

    @Scheduled(fixedRate = 4 * 60 * 60 * 1000)
    public void updateGenreCache() {

    }
}
