package com.fintech.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
