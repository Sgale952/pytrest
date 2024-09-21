package github.pytrest.routes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.pytrest.routes.services.CollectionsManagerService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;

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

    @GetMapping("/check-headers")
    public String checkHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
    
        StringBuilder headersList = new StringBuilder("Received headers:\n");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headersList.append(headerName).append(": ").append(headerValue).append("\n");
        }
        
        return headersList.toString();
    }
}