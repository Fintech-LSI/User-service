package com.fintech.user.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/users/public/images")
public class ImageController {

  private static final String IMAGE_DIRECTORY = "public/images";

  @GetMapping("/{filename}")
  public ResponseEntity<Resource> getImage(@PathVariable String filename) {
    try {
      Path filePath = Paths.get(IMAGE_DIRECTORY).resolve(filename).normalize();
      Resource resource = new UrlResource(filePath.toUri());

      if (!resource.exists() || !resource.isReadable()) {
        return ResponseEntity.notFound().build();
      }
      // Determine content type (image/jpeg, image/png, etc.)
      String contentType = "application/octet-stream"; // Default content type
      try {
        contentType = resource.getURL().openConnection().getContentType();
      } catch (Exception e) {
        e.printStackTrace();
      }

      return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))  // Set the content type
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")  // Optional: Inline for viewing in browser
        .body(resource);
    } catch (MalformedURLException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
