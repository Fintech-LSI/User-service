package com.fintech.user.repository;

import com.fintech.user.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
  Optional<Image> findByName(String name);
}
