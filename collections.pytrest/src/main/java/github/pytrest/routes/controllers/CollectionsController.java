package github.pytrest.routes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.pytrest.routes.services.CollectionsService;

@RestController
@RequestMapping("/collections")
public class CollectionsController {
    private final CollectionsService collectionManagerService;
    

    public CollectionsController(CollectionsService collectionManagerService) {
        this.collectionManagerService = collectionManagerService;
    }

    @PostMapping("/create")
    private ResponseEntity<?> create(@RequestHeader("X-Authenticated-Username") String username, @RequestBody String collectionName) {
        return ResponseEntity.ok(collectionManagerService.create(username, collectionName));
    }

    @PostMapping("/{collectionId}/delete")
    private void delete(@RequestHeader("X-Authenticated-Username") String username, @PathVariable String collectionId) {
        long id = Long.valueOf(collectionId);
        collectionManagerService.delete(username, id);
    }

    @PatchMapping("/{collectionId}/change-name")
    private void changeName(@RequestHeader("X-Authenticated-Username") String username, @PathVariable String collectionId, @RequestBody String newName) {
        long id = Long.valueOf(collectionId);
        collectionManagerService.changeName(username, id, newName);
    }

    @GetMapping("/{collectionId}/collection")
    private ResponseEntity<?> getCollection(@PathVariable String collectionId) {
        long id = Long.valueOf(collectionId);
        return ResponseEntity.ok(collectionManagerService.getCollection(id));
    }

    @GetMapping("/{username}/collections")
    private ResponseEntity<?> getAllUserCollections(@PathVariable String username) {
        return ResponseEntity.ok(collectionManagerService.getUserCollections(username));
    }
}