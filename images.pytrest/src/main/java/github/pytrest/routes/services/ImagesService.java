package github.pytrest.routes.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import github.pytrest.routes.entities.ImagesEntity;
import github.pytrest.routes.models.ImageCategories;
import github.pytrest.routes.models.ImageData;
import github.pytrest.routes.repositories.ImagesRepo;

@Service
@Transactional
public class ImagesService {
    @Value("${pytrest.static.images_directory}")
    private String IMAGES_DIR;
    private final ImagesRepo imagesRepo;

    public ImagesService(ImagesRepo imagesRepo) {
        this.imagesRepo = imagesRepo;
    }

    public ResponseEntity<Resource> getImage(long id) {
        try {
            Path filePath = findFileById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));
            Resource resource = new UrlResource(filePath.toUri());

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName() + "\"")
                    .body(resource);
        }
        catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving image", e);
        }
    }

    public long createImage(String owner, ImageData data) {
        ImageCategories category = ImageCategories.toImageCategories(data.category());
        ImagesEntity newImageEntity = new ImagesEntity(owner, data.name(), category);
        long newImageId = imagesRepo.save(newImageEntity).getImageId();
        saveImageFile(data.file(), newImageId);
        return newImageId;
    }

    public void deleteImage(String owner, long imageId) {
        ImagesEntity deletingImage = checkOwner(owner, imageId);
        imagesRepo.delete(deletingImage);
        deleteImageFile(imageId);
    }

    public void renameImage(String owner, long imageId, String newName) {
        ImagesEntity renamingImage = checkOwner(owner, imageId);
        renamingImage.setName(newName);
        imagesRepo.save(renamingImage);
    }

    private Optional<Path> findFileById(long id) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(IMAGES_DIR), id + ".*")) {
            for (Path path : stream) {
                if (Files.isRegularFile(path)) {
                    return Optional.of(path);
                }
            }
        }
        return Optional.empty();
    }

    private void saveImageFile(MultipartFile file, long imageId) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Image file is empty!");
            }

            
            Path path = Paths.get(IMAGES_DIR + imageId);
            
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void deleteImageFile(long imageId) {
        try {
            Path filePath = Paths.get(IMAGES_DIR);

            Optional<File> fileToDelete = Files.list(filePath)
                .map(Path::toFile)
                .filter(file -> file.getName().startsWith(String.valueOf(imageId) + "."))
                .findFirst();
    
            if (fileToDelete.isPresent()) {
                Files.delete(fileToDelete.get().toPath());
            }
            else {
                throw new RuntimeException("Image file does not exist!");
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private ImagesEntity checkOwner(String owner, long imageId) {
        ImagesEntity image = imagesRepo.findById(imageId).orElseThrow();
        if (image.getOwner().equals(owner)) {
            return image;
        }

        throw new RuntimeException("User " + owner + " is not own this image");
    }
}
