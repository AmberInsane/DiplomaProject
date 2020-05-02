package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.service.MovieService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;


@Controller
public class LoadMovies {

    @Autowired
    private MovieService movieService;

    @GetMapping("/load")
    public String load() throws IOException {
        String URL = "https://kinogo.by/film/premie/page/";
        int numberPfPages = 5;

        List<Movie> movies = new LinkedList<>();

        for (int i = 2; i <= numberPfPages; i++) {
            String pageURL = URL + i + "/";
            Element content = getContentFromURL(pageURL);
            movies.addAll(getMoviesFromContent(content));
        }

        movies.forEach(System.out::println);
        return "/";
    }

    private static Element getContentFromURL(String pageURL) throws IOException {
        Document doc = Jsoup.connect(pageURL).get();
        return doc.getElementById("dle-content");
    }

    private List<Movie> getMoviesFromContent(Element content) {
        ArrayList<Movie> movies = new ArrayList<>();
        Elements movieElements = content.select("div.shortstory");
        movieElements.forEach(movieElement -> movies.add(createMovieFromElement(movieElement)));
        return movies;
    }

    private Movie createMovieFromElement(Element movieElement) {
        Movie movie = new Movie();

        String title = movieElement.select("h2.zagolovki").text();
        title = title.substring(0, title.indexOf(" ("));
        if (!title.equals("")) {
            movie.setTitle(title);
        }

        String year = movieElement.select("b:contains(Год выпуска:)").first().nextElementSibling().text();
        if (!year.equals("")) {
            movie.setYear(Short.parseShort(year));
        }
 /*       String fileUrl = "http://kinogo.by" + movieElement.select("img[alt^=" + title + "]").attr("src");
        ;
        String fileName = "temp_img.jpg";
        // File file = new File("/img/temp.jpg");*/

/*
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:temp_img.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            BufferedImage bufferedImage = ImageIO.read(file);
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", byteOutStream);

            movie.setImage(BlobProxy.generateProxy(byteOutStream.toByteArray()));

        } catch (IOException e) {
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(file);

                ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", byteOutStream);
                movie.setImage(BlobProxy.generateProxy(byteOutStream.toByteArray()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
*/


        String duration = getTextSibling(movieElement.select("b:contains(Продолжительность:)"));
        if (!duration.equals("")) {
            duration = duration.substring(0, duration.indexOf(" мин."));
            movie.setTimeLength(Short.parseShort(duration));
        }

        String description = movieElement.select("[id^=news-id]").text();
        if (!description.equals(""))
            movie.setDescription(description);

        List<String> genres = new ArrayList<>();
        Element genreTitle = movieElement.select("b:contains(Жанр:)").first();
        Element nextElem = genreTitle.nextElementSibling();
        while (!nextElem.text().equals("")) {
            genres.add(nextElem.text());
            nextElem = nextElem.nextElementSibling();
        }


        List<Genre> movieGenres = new ArrayList<>();
        for (String gengre : genres) {
            Optional<Genre> genreByName = movieService.findGenreByName(gengre);
            if (genreByName.isPresent()) {
                movieGenres.add(genreByName.get());
            } else {
                Genre newGenre = new Genre();
                newGenre.setName(gengre);
                movieService.saveOrUpdateGenre(newGenre);
                movieGenres.add(movieService.findGenreByName(gengre).get());
            }
        }

        if (movieGenres.size() > 0) {
            movie.setGenre(movieGenres);
        }
        if (!movieService.findByTitleYear(movie.getTitle(), movie.getYear()).isPresent())
            movieService.saveOrUpdate(movie);
        return movie;
    }

    private String getTextSibling(Elements element) {
        if (element.isEmpty()) {
            return "";
        } else {
            return element.first().nextSibling().toString().trim();
        }
    }
}

