package com.benjidalton.fitnessapp.service.impl;

import com.benjidalton.fitnessapp.dto.Requests.*;
import com.benjidalton.fitnessapp.dto.Responses.*;
import com.benjidalton.fitnessapp.model.Exercise;
import com.benjidalton.fitnessapp.repository.ExerciseRepository;
import com.benjidalton.fitnessapp.repository.ExerciseSetRepository;
import com.benjidalton.fitnessapp.repository.MuscleGroupRepository;
import com.benjidalton.fitnessapp.service.ExerciseService;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exercises;
    private final MuscleGroupRepository muscleGroups;
    private final ExerciseSetRepository sets;

    public ExerciseServiceImpl(ExerciseRepository exercises, MuscleGroupRepository muscleGroups, ExerciseSetRepository sets) {
        this.exercises = exercises;
        this.muscleGroups = muscleGroups;
        this.sets = sets;
    }

    public ExerciseDto createExercise(ExerciseCreate request) {
        Exercise exercise = new Exercise(request.name(), request.description());
        if (request.muscleGroupIds() != null && !request.muscleGroupIds().isEmpty()) {
            exercise.getMuscles().addAll(muscleGroups.findAllById(request.muscleGroupIds()));
        }
        return ExerciseDto.from(exercises.save(exercise));
    }

    @Transactional(readOnly = true)
    public ExerciseDto getExercise(Long id) {
        return ExerciseDto.from(exercises.find(id));
    }

    @Transactional(readOnly = true)
    public List<ExerciseWithLastSetDto> getAllExercises() {
        return exercises.findAll().stream()
                .map(e -> ExerciseWithLastSetDto.from(
                        e, sets.findFirstByWorkoutExerciseExerciseIdOrderByCreatedAtDescIdDesc(e.getId()).orElse(null)))
                .toList();
    }

    public ExerciseDto updateExercise(Long id, ExerciseUpdate request) {
        Exercise exercise = exercises.find(id);
        if (request.name() != null && !request.name().isBlank()) {
            exercise.setName(request.name());
        }
        if (request.description() != null) {
            exercise.setDescription(request.description());
        }
        return ExerciseDto.from(exercise);
    }

    public void delete(Long id) {
        exercises.deleteByIdOrThrow(id);
    }
}
