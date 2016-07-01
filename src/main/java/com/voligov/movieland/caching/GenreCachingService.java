package com.voligov.movieland.caching;

import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class GenreCachingService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<Genre> genreCache = new CopyOnWriteArrayList<>();

    @Autowired
    private GenreService genreService;

    public Genre getById(int id) {
        for (Genre genre : genreCache) {
            if (genre.getId() == id) {
                return copy(genre);
            }
        }
        log.warn("Genre with id = {} not found in cache", id);
        return null;
    }

    public Genre getByName(String name) {
        for (Genre genre : genreCache) {
            if (genre.getName().equals(name)) {
                return copy(genre);
            }
        }
        log.warn("Genre with name = {} not found in cache", name);
        return null;
    }

    @Scheduled(fixedRate = 4 * 60 * 60 * 1000)
    public void updateGenreCache() {
        List<Genre> genresFromDb = genreService.getAll();
        if (genreCache.isEmpty()) {
            genreCache.addAll(genresFromDb);
        } else {
            for (Genre genre : genresFromDb) {
                int genreIndex = genreCache.indexOf(genre);
                if (genreIndex == -1) {
                    genreCache.add(genre);
                } else {
                    Genre genreInCache = genreCache.get(genreIndex);
                    if (!genreInCache.getName().equals(genre.getName())) {
                        genreCache.remove(genreIndex);
                        genreCache.add(genre);
                    }
                }
            }
            for (Genre genre : genreCache) {
                int genreIndex = genresFromDb.indexOf(genre);
                if (genreIndex == -1) {
                    genreCache.remove(genreIndex);
                }
            }
        }
    }

    private Genre copy(Genre genre) {
        Genre genreCopy = new Genre();
        genreCopy.setId(genre.getId());
        genreCopy.setName(genre.getName());
        return genreCopy;
    }
}
