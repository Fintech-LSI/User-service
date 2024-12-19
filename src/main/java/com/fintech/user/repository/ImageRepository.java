package com.fintech.user.repository;

import com.fintech.user.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
  Optional<Image> findByName(String name);
}
