package com.project.ojt.projectojt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Movies {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer movieId;
    private String movieName;
    private String description;
    private Date releaseDate;
    private String genre;
    private String director;
    private Double averageGrade;
    private String image;

}
