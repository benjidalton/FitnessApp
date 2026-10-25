package com.benjidalton.fitnessapp.controller;

import com.benjidalton.fitnessapp.dto.Requests.MuscleGroupVolumeRequestDTO;
import com.benjidalton.fitnessapp.dto.Responses.MuscleGroupDto;
import com.benjidalton.fitnessapp.dto.Responses.MuscleGroupVolumeDto;
import com.benjidalton.fitnessapp.service.MuscleGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(MuscleGroupController.muscleGroupsPath)
public class MuscleGroupController {

	private final MuscleGroupService muscleGroupService;
	public static final String muscleGroupsPath = "muscle-groups";

	public MuscleGroupController(MuscleGroupService muscleGroupService) {
		this.muscleGroupService = muscleGroupService;
	}

	@PostMapping("/volume")
	public List<MuscleGroupVolumeDto> getMuscleGroupVolumes(
			@RequestBody(required = false)
			MuscleGroupVolumeRequestDTO request
	) {
		return muscleGroupService.getMuscleGroupVolumes(
				request != null ? request.startDate() : null,
				request != null ? request.endDate() : null
		);
	}

	@GetMapping("/")
	public List<MuscleGroupDto> getAllMuscleGroups() {
		return muscleGroupService.getAllMuscleGroups();
	}

	@PostMapping("/create")
	public MuscleGroupDto createMuscleGroup(String name) {
		return muscleGroupService.createMuscleGroup(name);
	}
}
