package es.unizar.webeng.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Component that processes client requests.
 * <p>
 * The annotation @Controller is used so that the Spring container can detect, create and configure this component
 * when automatic configuration and wiring is used.
 */
@Controller
public class TextController {

    @Autowired
    private TextRepository repo;
    /*
     * This <code>@Bean</code> populates the JPA repository with some generic text.
     */
    @Bean
    public CommandLineRunner populate(TextRepository repository) {
        return (args) -> {
            // some data to display
            repository.save(new Text("Jack"));
            repository.save(new Text("Chloe"));
            repository.save(new Text("Kim"));
            repository.save(new Text("David"));
            repository.save(new Text("Michelle"));
        };
    }
    /*
     * Method that gets the JPA data stored in our repository and allow to display it in the view.
     * <p>
     * The data is passed to the view by <code>FlashAttribute</code>, which is related to the use of redirection
     * to show the View.
     */
    @GetMapping("/showRepo")
    public ModelAndView newMessage(RedirectAttributes redir){
        List<Text> textList = (List<Text>) repo.findAll();
        ModelAndView model = new ModelAndView("redirect:/");
        redir.addFlashAttribute("list",textList);
        return model;
    }
}
