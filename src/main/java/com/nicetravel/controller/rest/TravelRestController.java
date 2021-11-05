package com.nicetravel.controller.rest;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Travel;
import com.nicetravel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travel")
public class TravelRestController {

    TravelService travelService;

    @Autowired
    public TravelRestController(TravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping()
    public List<Travel> getAllTravel(){
        return travelService.getAllTravel();
    }

    @GetMapping("{id}")
    public Travel getOne(@PathVariable("id") Integer id) {
        return travelService.findTravelById(id);
    }

    @GetMapping("/get/{slug}")
    public Travel getAccountByUsername(@PathVariable("slug") String slug) {
        return travelService.findTravelBySlug(slug);
    }

    @PostMapping()
    public Travel create(@RequestBody Travel travel) {
        return travelService.createTravel(travel);
    }

    @PutMapping("{id}")
    public Travel update(@PathVariable("id") Integer id,@RequestBody Travel travel) {
        return travelService.updateTravel(travel);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        travelService.deleteTravel(id);
    }
    
    
}
