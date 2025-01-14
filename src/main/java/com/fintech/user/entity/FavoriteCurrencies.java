package com.fintech.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favorite_currencies")
public class FavoriteCurrencies implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long currencyId; // References the ID in the wallet service

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @ToString.Exclude // Exclude from toString
  @EqualsAndHashCode.Exclude // Exclude from hashCode and equals
  private User user;
}
