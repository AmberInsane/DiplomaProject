package com.tms.stankevich.сontroller;

import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.service.MovieServiceImpl;
import com.tms.stankevich.validator.MovieFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/movie")
public class MovieController {

    final static Logger logger = Logger.getLogger(MovieController.class);

    @Autowired
    MovieFormValidator movieFormValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(movieFormValidator);
    }

    @Autowired
    private MovieServiceImpl movieService;

    @GetMapping("")
    public String showAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movie/all_movies";
    }

    @GetMapping("/add_movie")
    public String addMovie(Model model) {
        model.addAttribute("movieForm", new Movie());
        populateDefaultModel(model);
        return "movie/movie";
    }

    @PostMapping(value = "/add_movie")
    public String saveOrUpdateMovie(@ModelAttribute("movieForm") @Validated Movie movie,
                                    BindingResult result, Model model, final RedirectAttributes redirectAttributes) {

        logger.debug("saveOrUpdateMovie() : {}" + movie);

        if (result.hasErrors()) {
            populateDefaultModel(model);
            return "movie/movie";
        } else {

            redirectAttributes.addFlashAttribute("css", "success");
            if (movie.isNew()) {
                redirectAttributes.addFlashAttribute("msg", "User added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "User updated successfully!");
            }
            movieService.saveOrUpdate(movie);
            //return "redirect:/users/" + user.getId();
            return "redirect:/movie";
        }

    }

    private void populateDefaultModel(Model model) {

        List<String> genresList = new ArrayList<String>();
        genresList.add("Comedy");
        genresList.add("Drama");
        genresList.add("Horror");
        genresList.add("Romantic");
        genresList.add("Cartoon");
        genresList.add("Arthouse");
        model.addAttribute("genreList", genresList);

/*        Map<String, String> skill = new LinkedHashMap<String, String>();
        skill.put("Hibernate", "Hibernate");
        skill.put("Spring", "Spring");
        skill.put("Struts", "Struts");
        skill.put("Groovy", "Groovy");
        skill.put("Grails", "Grails");
        model.addAttribute("javaSkillList", skill);

        List<Integer> numbers = new ArrayList<Integer>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        model.addAttribute("numberList", numbers);

        Map<String, String> country = new LinkedHashMap<String, String>();
        country.put("US", "United Stated");
        country.put("CN", "China");
        country.put("SG", "Singapore");
        country.put("MY", "Malaysia");
        model.addAttribute("countryList", country);*/

    }
}
