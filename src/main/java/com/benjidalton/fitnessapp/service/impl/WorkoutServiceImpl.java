package com.benjidalton.fitnessapp.service.impl;

import java.time.LocalDate;
import java.util.List;

import com.benjidalton.fitnessapp.exception.ApiException;
import com.benjidalton.fitnessapp.dto.Requests.*;
import com.benjidalton.fitnessapp.dto.Responses.*;
import com.benjidalton.fitnessapp.model.*;
import com.benjidalton.fitnessapp.repository.ExerciseRepository;
import com.benjidalton.fitnessapp.repository.ExerciseSetRepository;
import com.benjidalton.fitnessapp.repository.WorkoutExerciseRepository;
import com.benjidalton.fitnessapp.repository.WorkoutRepository;
import com.benjidalton.fitnessapp.service.WorkoutService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workouts;
    private final WorkoutExerciseRepository workoutExercises;
    private final ExerciseRepository exercises;
    private final ExerciseSetRepository sets;

    public WorkoutServiceImpl(
            WorkoutRepository workouts,
            WorkoutExerciseRepository workoutExercises,
            ExerciseRepository exercises,
            ExerciseSetRepository sets) {
        this.workouts = workouts;
        this.workoutExercises = workoutExercises;
        this.exercises = exercises;
        this.sets = sets;
    }

    // ----- workouts -----

    public WorkoutDto createWorkout(WorkoutCreate request) {
        Workout workout = new Workout(request.date() != null ? request.date() : LocalDate.now(), request.notes());
        if (request.exercises() != null) {
            for (WorkoutExerciseCreate item : request.exercises()) {
                workout.addExercise(buildWorkoutExercise(item));
            }
        }
        return WorkoutDto.from(workouts.create(workout));
    }

    @Transactional(readOnly = true)
    public WorkoutDto getWorkout(Long id) {
        return WorkoutDto.from(workouts.find(id));
    }

    @Transactional(readOnly = true)
    public List<WorkoutDto> getAllWorkouts(int limit) {
        return workouts.findRecent(PageRequest.of(0, limit)).stream().map(WorkoutDto::from).toList();
    }

    public WorkoutDto updateWorkout(Long id, WorkoutUpdate request) {
        Workout workout = workouts.find(id);
        if (request.notes() != null) workout.setNotes(request.notes());
        if (request.date() != null) workout.setDate(request.date());
        return WorkoutDto.from(workout);
    }

    public void delete(Long id) { workouts.deleteByIdOrThrow(id); }

    // ----- exercises within a workout -----

    public WorkoutExerciseDto addExerciseToWorkout(Long workoutId, WorkoutExerciseCreate request) {
        Workout workout = workouts.find(workoutId);
        WorkoutExercise workoutExercise = buildWorkoutExercise(request);
        workout.addExercise(workoutExercise);
        workouts.flush();
        return WorkoutExerciseDto.from(workoutExercise);
    }

    public void removeExerciseFromWorkout(Long workoutId, Long workoutExerciseId) {
        WorkoutExercise workoutExercise = workoutExercises
                .findByIdAndWorkoutId(workoutExerciseId, workoutId)
                .orElseThrow(() -> ApiException.notFound("Workout exercise not found"));
        workoutExercise.getWorkout().getExercises().remove(workoutExercise);
    }

    // ----- sets -----

    public SetDto addSet(Long workoutExerciseId, SetCreate request) {
        WorkoutExercise workoutExercise = workoutExercises.find(workoutExerciseId);
        ExerciseSet set = new ExerciseSet(request.reps(), request.weight(), request.restSeconds());
        workoutExercise.addSet(set);
        sets.saveAndFlush(set);
        return SetDto.from(set);
    }

    public SetDto updateSet(Long id, SetUpdate request) {
        ExerciseSet set = sets.find(id);
        if (request.reps() != null) set.setReps(request.reps());
        if (request.weight() != null) set.setWeight(request.weight());
        if (request.restSeconds() != null) set.setRestSeconds(request.restSeconds());
        return SetDto.from(set);
    }

    public void deleteSet(Long id) {
        ExerciseSet set = sets.find(id);
        set.getWorkoutExercise().getSets().remove(set);
    }

    // ----- helpers -----

    private WorkoutExercise buildWorkoutExercise(WorkoutExerciseCreate item) {
        Exercise exercise = exercises.find(item.exerciseId());
        WorkoutExercise workoutExercise =
                new WorkoutExercise(exercise, item.position() != null ? item.position() : 0, item.notes());
        if (item.sets() != null) {
            for (SetCreate s : item.sets()) {
                workoutExercise.addSet(new ExerciseSet(s.reps(), s.weight(), s.restSeconds()));
            }
        }
        return workoutExercise;
    }
}
