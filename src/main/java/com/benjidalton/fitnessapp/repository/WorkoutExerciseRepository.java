package com.benjidalton.fitnessapp.repository;

import com.benjidalton.fitnessapp.model.WorkoutExercise;
import java.util.Optional;

public interface WorkoutExerciseRepository extends BaseRepository<WorkoutExercise, Long> {

    Optional<WorkoutExercise> findByIdAndWorkoutId(Long id, Long workoutId);
}
