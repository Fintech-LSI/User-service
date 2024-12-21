package com.fintech.user.service;

import com.fintech.user.entity.Image;
import com.fintech.user.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {
  @Value("${image.folder}")
  private String IMAGE_FOLDER ;

  @Autowired
  private ImageRepository imageRepository;

  public Image saveImage(MultipartFile file) throws IOException {
    // Ensure the folder exists
    Path folderPath = Paths.get(IMAGE_FOLDER);
    if (!Files.exists(folderPath)) {
      Files.createDirectories(folderPath);
    }

    // Generate a unique file name
    String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    Path filePath = folderPath.resolve(uniqueFileName);

    // Save the file to the folder
    Files.copy(file.getInputStream(), filePath);

    // Save the image metadata in the database
    Image image = Image.builder()
      .name(uniqueFileName)
      .url(IMAGE_FOLDER + uniqueFileName) // Public URL path
      .build();
    return imageRepository.save(image);
  }

  public Image getDefaultImage() {
    return imageRepository.findByName("default").orElseGet(() -> {
      Image defaultImage = Image.builder()
        .name("default")
        .url("/images/default_profile_picture.png")
        .build();
      return imageRepository.save(defaultImage);
    });
  }



  /**
   * Delete an image file from the server and remove its metadata from the database.
   *
   * @param imageId the ID of the image to delete
   * @return true if the image was successfully deleted, false otherwise
   */
  public boolean deleteImage(Long imageId) {
    Optional<Image> optionalImage = imageRepository.findById(imageId);
    if (optionalImage.isPresent()) {
      Image image = optionalImage.get();
      Path filePath = Paths.get(IMAGE_FOLDER).resolve(image.getName());

      try {
        // Delete the file from the server
        if (Files.exists(filePath)) {
          Files.delete(filePath);
        }
        // Delete the image metadata from the database
        imageRepository.delete(image);
        return true;
      } catch (IOException e) {
        e.printStackTrace(); // Log the exception (could use a logger in a real application)
        return false;
      }
    }
    return false; // Image not found
  }
}
