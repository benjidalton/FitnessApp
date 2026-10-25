package com.benjidalton.fitnessapp.controller;


import com.benjidalton.fitnessapp.service.ExerciseService;
import com.benjidalton.fitnessapp.dto.Requests.ExerciseCreate;
import com.benjidalton.fitnessapp.dto.Requests.ExerciseUpdate;
import com.benjidalton.fitnessapp.dto.Responses.ExerciseDto;
import com.benjidalton.fitnessapp.dto.Responses.ExerciseWithLastSetDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(ExerciseController.exercisesPath)
public class ExerciseController {

	private final ExerciseService exerciseService;
	public static final String exercisesPath = "exercises";

	public ExerciseController(ExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}


	@PostMapping("/create")
	public ExerciseDto createExercise(
			@Valid @RequestBody ExerciseCreate request) {

		return exerciseService.createExercise(request);
	}

	@GetMapping("/{exerciseId}")
	public ExerciseDto getExercise(@PathVariable Long exerciseId) {
		return exerciseService.getExercise(exerciseId);
	}

	@GetMapping("/")
	public List<ExerciseWithLastSetDto> getAllExercises() {
		return exerciseService.getAllExercises();
	}

	@PutMapping("/{exerciseId}")
	public ExerciseDto updateExercise(
			@PathVariable Long exerciseId,
			@RequestBody ExerciseUpdate request) {

		return exerciseService.updateExercise(
				exerciseId,
				request
		);
	}

	@DeleteMapping("/{exerciseId}")
	public void deleteExercise(@PathVariable Long exerciseId) {
		exerciseService.delete(exerciseId);
	}

//	@PostMapping("/"+ exercisesPath + "/{exerciseId}/volume-over-time")
//	public Object getExerciseVolumeOverTime(
//			@PathVariable Long exerciseId,
//			@RequestParam(required = false)
//			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//			LocalDate startDate,
//			@RequestParam(required = false)
//			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//			LocalDate endDate) {
//
//		return exerciseService.getExerciseVolumeOverTime(
//				exerciseId,
//				startDate,
//				endDate
//		);
//	}
}
