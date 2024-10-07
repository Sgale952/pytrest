package github.pytrest.routes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import github.pytrest.routes.services.CollectionImagesService;

public class CollectionImagesController {
    private final CollectionImagesService imagesService;

    public CollectionImagesController(CollectionImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @PostMapping("/{collectionId}/image-add")
    private void addImage(@RequestHeader("X-Authenticated-Username") String username, @PathVariable String collectionId, @RequestBody String imageId) {
        long collectionIdL = Long.valueOf(collectionId);
        long imageIdL = Long.valueOf(imageId);
        imagesService.addImamageToCollection(username, collectionIdL, imageIdL);
    }

    @DeleteMapping("/{collectionId}/image-remove")
    private void removeImage(@RequestHeader("X-Authenticated-Username") String username, @PathVariable String collectionId, @RequestBody String imageId) {
        long collectionIdL = Long.valueOf(collectionId);
        long imageIdL = Long.valueOf(imageId);
        imagesService.removeImamageFromCollection(username, collectionIdL, imageIdL);
    }

    @GetMapping("/{collectionId}/images")
    private ResponseEntity<?> getCollectionImages(@PathVariable String collectionId, @RequestBody String page) {
        long id = Long.valueOf(collectionId);
        int pageI = Integer.valueOf(page);
        return ResponseEntity.ok(imagesService.getImages(id, pageI));
    }
}
