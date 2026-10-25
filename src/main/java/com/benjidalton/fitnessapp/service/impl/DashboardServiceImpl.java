package com.benjidalton.fitnessapp.service.impl;

import com.benjidalton.fitnessapp.dto.Responses;
import com.benjidalton.fitnessapp.repository.DashboardRepository;
import com.benjidalton.fitnessapp.repository.ExerciseSetRepository;
import com.benjidalton.fitnessapp.repository.WorkoutRepository;
import com.benjidalton.fitnessapp.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.benjidalton.fitnessapp.dto.Responses.DashboardMetricsDto;
import com.benjidalton.fitnessapp.dto.Responses.WorkoutHistoryDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

@Slf4j
@Service
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;
    private final ExerciseSetRepository exerciseSetRepository;
    private final WorkoutRepository workoutRepository;

	public DashboardServiceImpl(DashboardRepository dashboardRepository, ExerciseSetRepository exerciseSetRepository, WorkoutRepository workoutRepository) {
		this.dashboardRepository = dashboardRepository;
		this.exerciseSetRepository = exerciseSetRepository;
		this.workoutRepository = workoutRepository;
	}

	@Transactional(readOnly = true)
    public DashboardMetricsDto getDashboardMetrics() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekAgo = now.minusDays(7);

        // Workouts created in the past 7 days
        long workoutsThisWeek = dashboardRepository.countByCreatedAtAfter(weekAgo);

        // Total volume: sum of reps * weight
        Double totalVolume = exerciseSetRepository.sumVolumeSinceDate(weekAgo.toLocalDate());

        // Last 10 workouts, newest first
        List<WorkoutHistoryDto> history =
                workoutRepository.findTop10ByOrderByDateDesc()
                        .stream()
                        .map(workout -> {
                            TreeSet<String> muscleGroups = new TreeSet<>();

                            workout.getExercises().forEach(workoutExercise ->
                                    workoutExercise.getExercise()
                                            .getMuscles()
                                            .forEach(muscle ->
                                                    muscleGroups.add(muscle.getName())
                                            )
                            );

                            return new WorkoutHistoryDto(
                                    workout.getId(),
                                    workout.getDate(),
                                    List.copyOf(muscleGroups)
                            );
                        })
                        .toList();

        return new DashboardMetricsDto(
                workoutsThisWeek,
                totalVolume != null ? totalVolume : 0.0,
                history
        );
    }
}