package com.benjidalton.fitnessapp.service;

import com.benjidalton.fitnessapp.dto.Requests.*;
import com.benjidalton.fitnessapp.dto.Responses.*;


import java.util.List;

public interface ExerciseService {

	ExerciseDto createExercise(ExerciseCreate request);

	ExerciseDto getExercise(Long id);

	List<ExerciseWithLastSetDto> getAllExercises();

	ExerciseDto updateExercise(Long id, ExerciseUpdate request);

	void delete(Long id);

}
