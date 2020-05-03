package com.tms.stankevich.service;

import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.domain.movie.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("kinogo")
public class MovieLoadServiceKinogo implements MovieLoadService {

    private final Logger logger = LogManager.getLogger(MovieLoadService.class.getName());

    @Value("${kinigo.pages.sample.url}")
    private String URL;

    @Value("${kinigo.page.begin}")
    private int pageBegin;

    @Value("${kinigo.page.end}")
    private int pageEnd;

    @Autowired
    private MovieService movieService;

    @Override
    public List<Movie> load() {
        String URL = "https://kinogo.by/film/premie/page/";
        int numberPfPages = 12;

        List<Movie> newMovies = new ArrayList<>();

        for (int i = 1; i <= numberPfPages; i++) {
            String pageURL = URL + i + "/";
            try {
                Element content = getContentFromURL(pageURL);
                newMovies.addAll(getMoviesFromContent(content));
            } catch (IOException e) {
                logger.error("Error during loading from kinogo: " + e.getMessage());
            }
        }
        return newMovies;
    }

    private static Element getContentFromURL(String pageURL) throws IOException {
        Document doc = Jsoup.connect(pageURL).get();
        return doc.getElementById("dle-content");
    }

    private List<Movie> getMoviesFromContent(Element content) {
        List<Movie> movies = new ArrayList<>();
        Elements movieElements = content.select("div.shortstory");
        movieElements.forEach(movieElement -> {
            Movie movie = createMovieFromElement(movieElement);
            if (movie != null)
                movies.add(movie);
        });
        return movies;
    }

    private Movie createMovieFromElement(Element movieElement) {
        try {
            Movie movie = new Movie();

            String title = movieElement.select("h2.zagolovki").text();
            title = title.substring(0, title.indexOf(" ("));
            if (!title.equals("")) {
                movie.setTitle(title);

                String year = movieElement.select("b:contains(Год выпуска:)").first().nextElementSibling().text();
                if (!year.equals("")) {
                    movie.setYear(Short.parseShort(year));
                    String duration = getTextSibling(movieElement.select("b:contains(Продолжительность:)"));
                    if (!duration.equals("") && duration.indexOf(" мин.") > 0) {
                        duration = duration.substring(0, duration.indexOf(" мин."));
                        movie.setTimeLength(Short.parseShort(duration));

                        String description = movieElement.select("[id^=news-id]").text();
                        if (!description.equals("")) {
                            movie.setDescription(description);
                            List<String> genres = new ArrayList<>();
                            Element genreTitle = movieElement.select("b:contains(Жанр:)").first();
                            Element nextElem = genreTitle.nextElementSibling();
                            while (!nextElem.text().equals("")) {
                                genres.add(nextElem.text());
                                nextElem = nextElem.nextElementSibling();
                            }

                            List<Genre> movieGenres = new ArrayList<>();
                            for (String genre : genres) {
                                Optional<Genre> genreByName = movieService.findGenreByName(genre);
                                if (genreByName.isPresent()) {
                                    movieGenres.add(genreByName.get());
                                } else {
                                    Genre newGenre = new Genre();
                                    newGenre.setName(genre);
                                    movieService.saveOrUpdateGenre(newGenre);
                                    movieGenres.add(movieService.findGenreByName(genre).get());
                                }
                            }

                            if (movieGenres.size() > 0) {
                                movie.setGenre(movieGenres);
                                if (!movieService.findByTitleYear(movie.getTitle(), movie.getYear()).isPresent()) {
                                    movieService.saveOrUpdate(movie);
                                    return movie;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private String getTextSibling(Elements element) {
        if (element.isEmpty()) {
            return "";
        } else {
            return element.first().nextSibling().toString().trim();
        }
    }
}
