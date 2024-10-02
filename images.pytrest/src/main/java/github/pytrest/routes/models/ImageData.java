package github.pytrest.routes.models;

import org.springframework.web.multipart.MultipartFile;

public record ImageData(MultipartFile file, String name, String category) {
}
