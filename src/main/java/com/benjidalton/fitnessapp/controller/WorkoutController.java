package com.benjidalton.fitnessapp.controller;

import com.benjidalton.fitnessapp.dto.Requests.*;
import com.benjidalton.fitnessapp.dto.Responses.*;
import com.benjidalton.fitnessapp.model.*;
import com.benjidalton.fitnessapp.service.WorkoutService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WorkoutController.workoutPath)
public class WorkoutController {

    private final WorkoutService workoutService;
    public static final String workoutPath = "workouts";
    private static final String exercisesPath = "exercises";
    private static final String setsPath = "sets";

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

//    // =====================================================
//    // Muscle Group Endpoints
//    // =====================================================
//
//    @PostMapping("/muscle-groups")
//    public Object createMuscleGroup(
//            @Valid @RequestBody MuscleGroupRequest request) {
//
//        return workoutService.createMuscleGroup(request.name());
//    }
//
//    @GetMapping("/muscle-groups")
//    public Object getAllMuscleGroups() {
//        return workoutService.getAllMuscleGroups();
//    }
//
//    @GetMapping("/muscle-groups/volume/radar")
//    public Object getMuscleGroupVolumeRadar() {
//        return workoutService.getMuscleGroupVolumeRadarData();
//    }
//
//    @GetMapping(
//            "/muscle-groups/{muscleGroupId}/exercises-volume-over-time")
//    public Object getMuscleGroupExercisesVolumeOverTime(
//            @PathVariable Long muscleGroupId,
//            @RequestParam(required = false)
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//            LocalDate startDate,
//            @RequestParam(required = false)
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//            LocalDate endDate) {
//
//        return workoutService.getMuscleGroupExercisesVolumeOverTime(
//                muscleGroupId,
//                startDate,
//                endDate
//        );
//    }

    @PostMapping("/create")
    public WorkoutDto createWorkout(
            @Valid @RequestBody WorkoutCreate request) {

        return workoutService.createWorkout(request);
    }

    @GetMapping("/{workoutId}")
    public WorkoutDto getWorkout(@PathVariable Long workoutId) {
        return workoutService.getWorkout(workoutId);
    }

    @GetMapping("/all")
    public List<WorkoutDto> getAllWorkouts(
            @RequestParam(defaultValue = "100")
            @Min(1) @Max(500) int limit) {

        return workoutService.getAllWorkouts(limit);
    }

    @PutMapping("/update/{workoutId}")
    public WorkoutDto updateWorkout(
            @PathVariable Long workoutId,
            @RequestBody WorkoutUpdate request) {

        return workoutService.updateWorkout(workoutId, request);
    }

    @DeleteMapping("/{workoutId}")
    public void deleteWorkout(@PathVariable Long workoutId) {
        workoutService.delete(workoutId);
    }

    // =====================================================
    // Workout Exercise Endpoints
    // =====================================================

    @PostMapping("/{workoutId}/exercises")
    public WorkoutExerciseDto addExerciseToWorkout(
            @PathVariable Long workoutId,
            @Valid @RequestBody WorkoutExerciseCreate request) {

        return workoutService.addExerciseToWorkout(
                workoutId,
                request
        );
    }

    @DeleteMapping("/{workoutId}/" + exercisesPath + "/{workoutExerciseId}")
    public void removeExerciseFromWorkout(
            @PathVariable Long workoutId,
            @PathVariable Long workoutExerciseId) {

        workoutService.removeExerciseFromWorkout(
                workoutId,
                workoutExerciseId
        );
    }

    // =====================================================
    // Exercise Set Endpoints
    // =====================================================

    @PostMapping("/" + exercisesPath + "/{workoutExerciseId}/sets")
    public SetDto addSetToExercise(
            @PathVariable Long workoutExerciseId,
            @Valid @RequestBody SetCreate request) {

        return workoutService.addSet(workoutExerciseId, request);
    }

    @PutMapping("/" + setsPath + "/{setId}")
    public SetDto updateSet(
            @PathVariable Long setId,
            @RequestBody SetUpdate request) {

        return workoutService.updateSet(setId, request);
    }

    @DeleteMapping("/" + setsPath + "/{setId}")
    public void deleteSet(@PathVariable Long setId) {
         workoutService.deleteSet(setId);
    }

}
