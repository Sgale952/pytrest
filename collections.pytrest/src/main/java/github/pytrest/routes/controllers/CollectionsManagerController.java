package github.pytrest.routes.controllers;

import github.pytrest.routes.repositories.CollectionFillingRepo;
import github.pytrest.routes.repositories.CollectionRepo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collections")
public class CollectionsManagerController {
    private final CollectionRepo collectionRepo;
    private final CollectionFillingRepo collectionFillingRepo;

    public CollectionsManagerController(CollectionRepo collectionRepo, CollectionFillingRepo collectionFillingRepo) {
        this.collectionRepo = collectionRepo;
        this.collectionFillingRepo = collectionFillingRepo;
    }

    @PostMapping("/create")
    private ResponseEntity<String> create() {
        return ResponseEntity.ok("SUS");
    }
}