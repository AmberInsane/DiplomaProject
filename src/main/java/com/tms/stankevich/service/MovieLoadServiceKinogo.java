package com.tms.stankevich.service;

import com.tms.stankevich.annotations.Loggable;
import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.exception.ConnectErrorException;
import com.tms.stankevich.exception.TimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("kinogo")
public class MovieLoadServiceKinogo implements MovieLoadService {

    private final Logger logger = LogManager.getLogger(MovieLoadService.class.getName());

    @Value("${kinigo.pages.sample.url}")
    private String URL;

    @Value("${kinigo.connect.timeout}")
    private int timeout;


    @Value("${kinigo.page.begin}")
    private int pageBegin;

    @Value("${kinigo.page.end}")
    private int pageEnd;

    @Autowired
    private MovieService movieService;

    @Loggable
    @Override
    public List<Movie> loadMovies() throws ConnectErrorException, TimeoutException {
        logger.debug("loadMovies pages from " + pageBegin + " to " + pageEnd);

        String URL = "https://kinogo.by/film/premie/page/";
        List<Movie> newMovies = new ArrayList<>();

        for (int i = pageBegin; i <= pageEnd; i++) {
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

    private Element getContentFromURL(String pageURL) throws IOException, ConnectErrorException, TimeoutException {
        try {
            Connection connect = Jsoup.connect(pageURL);
            connect.timeout(timeout);
            Document doc = connect.get();
            return doc.getElementById("dle-content");
        } catch (
                SocketException ex) {
            throw new ConnectErrorException("Kinogo connect error " + ex.getMessage());
        } catch (
                SocketTimeoutException ex) {
            throw new TimeoutException("Kinogo connect timeout error");
        }

    }

    public List<Movie> getMoviesFromContent(Element content) throws TimeoutException, ConnectErrorException {
        List<Movie> movies = new ArrayList<>();
        Elements movieElements = content.select("div.shortstory");

        for (Element movieElement : movieElements) {
            Movie movie = createMovieFromElement(movieElement);
            if (movie != null)
                movies.add(movie);
        }
        return movies;
    }

    private Movie createMovieFromElement(Element movieElement) throws ConnectErrorException, TimeoutException {
        try {
            String title = movieElement.select("h2.zagolovki").text();
            title = title.substring(0, title.indexOf(" ("));

            Short year = Short.parseShort(movieElement.select("b:contains(Год выпуска:)").first().nextElementSibling().text());

            if (!title.equals("") && !year.equals(0)) {
                if (!movieService.findByTitleYear(title, year).isPresent()) {
                    Movie movie = new Movie();
                    movie.setTitle(title);
                    movie.setYear(year);

                    String duration = getTextSibling(movieElement.select("b:contains(Продолжительность:)"));

                    if (!duration.equals("") && duration.indexOf(" мин.") > 0) {
                        duration = duration.substring(0, duration.indexOf(" мин."));
                        movie.setTimeLength(Short.parseShort(duration));
                        String movieUrl = movieElement.select("h2.zagolovki").select("[href]").attr("href");

                        if (!movieUrl.equals("")) {
                            Element moviePageElement = getContentFromURL(movieElement.select("h2.zagolovki").select("[href]").attr("href"));
                            List<Node> childNodes = moviePageElement.select("[class=fullimg]").select("[style=display:inline;]").get(0).childNodes();

                            StringBuilder description = new StringBuilder();
                            for (Node childNode : childNodes) {
                                if (childNode instanceof TextNode) {
                                    String text = ((TextNode) childNode).text();
                                    if (!text.equals("\n") && !text.equals("<br>") && !text.equals("") && (!text.equals(" "))) {
                                        description.append(text).append(" ");
                                    }
                                }
                            }

                            if (!description.toString().equals("")) {
                                movie.setDescription(description.toString());


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
                                        newGenre = movieService.saveOrUpdateGenre(newGenre);
                                        movieGenres.add(newGenre);
                                    }
                                }

                                if (movieGenres.size() > 0) {
                                    movie.setGenre(movieGenres);
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
