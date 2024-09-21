package github.pytrest.routes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.pytrest.routes.services.CollectionsManagerService;

@RestController
@RequestMapping("/collections")
public class CollectionsManagerController {
    private final CollectionsManagerService collectionManagerService;

    public CollectionsManagerController(CollectionsManagerService collectionManagerService) {
        this.collectionManagerService = collectionManagerService;
    }

    @PostMapping("/create")
    private ResponseEntity<?> create(@RequestHeader("X-Authenticated-Username") String username, @RequestBody String collectionName) {
        return ResponseEntity.ok(collectionManagerService.create(username, collectionName));
    }
}