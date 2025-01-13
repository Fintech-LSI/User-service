package com.fintech.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(unique = true, nullable = false)
  @Email
  private String email;


  @Column(nullable = true)
  private Integer age;

  @Column(nullable = true)
  private Double salary;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = true)
  private OwnerShip homeOwnership;

  @Column(nullable = true)
  private Integer employmentMonth;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<FavoriteCurrencies> favoriteCurrencies = new HashSet<>();


  @ManyToOne
  @JoinColumn(name = "image_id")
  private Image image;

  @PrePersist
  public void setDefaultImage() {
    if (this.image == null) {
      this.image =  Image.builder()
        .name("default")
        .url("default_profile_picture.png")
        .build();
    }
  }

}
