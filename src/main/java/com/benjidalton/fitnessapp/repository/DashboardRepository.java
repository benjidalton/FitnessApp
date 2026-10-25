package com.benjidalton.fitnessapp.repository;

import com.benjidalton.fitnessapp.model.BodyComposition;

import java.time.LocalDateTime;

public interface DashboardRepository extends BaseRepository<BodyComposition, Long> {

    long countByCreatedAtAfter(LocalDateTime createdAt);
}
