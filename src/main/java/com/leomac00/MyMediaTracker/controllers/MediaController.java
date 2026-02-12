package com.leomac00.MyMediaTracker.controllers;

import com.leomac00.MyMediaTracker.data.dtos.MediaDto;
import com.leomac00.MyMediaTracker.models.Media;
import com.leomac00.MyMediaTracker.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("api/media/v1")
public class MediaController {
    @Autowired
    private MediaService service;

    @GetMapping("/{id}")
    public ResponseEntity<Media> getById(@PathVariable(name="id") Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Media>> getAll(){
        List<Media> allMedia = service.findAll();
        return ResponseEntity.ok(allMedia);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Media> create(@RequestBody MediaDto mediaTobeCreated) {
        Media newMedia = service.create(mediaTobeCreated);
        return ResponseEntity.ok(newMedia);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable(name="id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Media> update(
            @PathVariable(name = "id") Long id,
            @RequestBody MediaDto newInfo) {
        Media updatedMedia = service.update(id, newInfo);

        return ResponseEntity.ok(updatedMedia);
    }
}
