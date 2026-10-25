package com.benjidalton.fitnessapp.service;

import com.benjidalton.fitnessapp.dto.Requests.*;
import com.benjidalton.fitnessapp.dto.Responses.*;

import java.util.List;

public interface WorkoutService {

	// ----- workouts -----
	WorkoutDto createWorkout(WorkoutCreate request);

	WorkoutDto getWorkout(Long id);

	List<WorkoutDto> getAllWorkouts(int limit);

	WorkoutDto updateWorkout(Long id, WorkoutUpdate request);

	void delete(Long id);

	// ----- exercises within a workout -----
	WorkoutExerciseDto addExerciseToWorkout(Long workoutId, WorkoutExerciseCreate request);

	void removeExerciseFromWorkout(Long workoutId, Long workoutExerciseId);

	// ----- sets -----
	SetDto addSet(Long workoutExerciseId, SetCreate request);

	SetDto updateSet(Long setId, SetUpdate request);

	void deleteSet(Long setId);

}
