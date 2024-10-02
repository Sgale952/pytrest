package github.pytrest.routes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import github.pytrest.routes.models.ImageData;
import github.pytrest.routes.services.ImagesService;

@RestController
@RequestMapping("/images")
public class ImagesController {
    private final ImagesService imagesService;

    public ImagesController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @GetMapping("/{imageId}/image")
    private ResponseEntity<?> getImage(@PathVariable String imageId) {
        long id = Long.valueOf(imageId);
        return ResponseEntity.ok(imagesService.getImage(id));
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public long createImage(
            @RequestHeader("X-Authenticated-Username") String owner,
            @RequestParam("file") MultipartFile file, 
            @RequestParam("name") String name, 
            @RequestParam("category") String category) {
        
        ImageData imageData = new ImageData(file, name, category);
        return imagesService.createImage(owner, imageData);
    }

    @DeleteMapping("/{imageId}/delete")
    private void delete(@RequestHeader("X-Authenticated-Username") String owner, @PathVariable String imageId) {
        long id = Long.valueOf(imageId);
        imagesService.deleteImage(owner, id);
    }

    @PatchMapping("/{imageId}/change-name")
    private void changeName(@RequestHeader("X-Authenticated-Username") String owner, @PathVariable String imageId, @RequestBody String newName) {
        long id = Long.valueOf(imageId);
        imagesService.renameImage(owner, id, newName);
    }
}