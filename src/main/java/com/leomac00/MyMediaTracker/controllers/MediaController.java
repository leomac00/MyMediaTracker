package com.leomac00.MyMediaTracker.controllers;

import com.leomac00.MyMediaTracker.data.dtos.MediaDto;
import com.leomac00.MyMediaTracker.models.Media;
import com.leomac00.MyMediaTracker.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("api/media/v1")
public class MediaController {
    @Autowired
    private MediaService service;

    @GetMapping("/{id}")
    public Media findById(@PathVariable(name="id") Long id){
        return service.findById(id);
    }

    @PostMapping("/create")
    public Media create(@RequestBody MediaDto mediaTobeCreated) {return service.create(mediaTobeCreated); }
}
