package com.blog.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Category {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer categoryId;
   @Column(name="title")
   private String categoryTitle;
   @Column(name="description")
   private String categoryDescription;
   @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
   private List<Post> posts = new ArrayList<>();
}
