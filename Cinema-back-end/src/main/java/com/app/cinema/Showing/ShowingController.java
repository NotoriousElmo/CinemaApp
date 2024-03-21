package com.app.cinema.Showing;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/showings")
public class ShowingController {
    private final ShowingService showingService;

    public ShowingController(ShowingService showingService) {
        this.showingService = showingService;
    }

    @GetMapping
    public List<Showing> listShowings() {
        return showingService.getShowings();
    }

    @GetMapping("{id}")
    public Showing getShowingId(@PathVariable("id") Integer id) {
        return showingService.getShowing(id);
    }

    @PostMapping
    public void addShowing(@RequestBody Showing showing) {
        showingService.addNewShowing(showing);
    }

    @DeleteMapping("{id}")
    public void deleteShowing(@PathVariable("id") Integer id) {
        showingService.deleteShowing(id);
    }
}
