//package com.tbless.inventoryManagementApp.data.models;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Table(name = "products")
//public class Product {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ElementCollection
//    private List<String> imageUrl;
//    private String emailAddress;
//    private String name;
//    private BigDecimal price;
//    private int stock;
//    private String description;
//}



package com.tbless.inventoryManagementApp.data.models;

import org.hibernate.annotations.Cascade;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<String> imageUrl;
    private String emailAddress;
    private String name;
    private BigDecimal price;
    private int stock;
    private String description;
}
