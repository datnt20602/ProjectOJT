package com.project.ojt.projectojt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "movieId", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

    @Column(columnDefinition = "TEXT")
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private String genre;
    private String director;
    private Double averageGrade;
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Tính điểm trung bình
    public void calculateAverageGrade() {
        if (feedbacks == null || feedbacks.isEmpty()) {
            averageGrade = 0.0; // Hoặc giá trị mặc định khác tùy vào yêu cầu của bạn
            return;
        }

        double totalGrade = 0.0;
        int feedbackCount = 0;

        for (Feedback feedback : feedbacks) {
            if (feedback.getGrade() != null) {
                totalGrade += feedback.getGrade();
                feedbackCount++;
            }
        }

        if (feedbackCount == 0) {
            averageGrade = 0.0; // Hoặc giá trị mặc định khác tùy vào yêu cầu của bạn
        } else {
            averageGrade = totalGrade / feedbackCount;
        }
    }

}
