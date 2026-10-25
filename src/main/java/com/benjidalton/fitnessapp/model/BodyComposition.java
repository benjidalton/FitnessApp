package com.benjidalton.fitnessapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "body_composition")
public class BodyComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight_lbs", nullable = false)
    private double weightLbs;

    @Column(name = "body_fat_percentage")
    private Double bodyFatPercentage;

    private Double bmi;

    @Column(name = "lean_body_mass_lbs")
    private Double leanBodyMassLbs;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected BodyComposition() {}

    public BodyComposition(double weightLbs, Double bodyFatPercentage, Double bmi, Double leanBodyMassLbs) {
        this.weightLbs = weightLbs;
        this.bodyFatPercentage = bodyFatPercentage;
        this.bmi = bmi;
        this.leanBodyMassLbs = leanBodyMassLbs;
    }

    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

}
