package com.benjidalton.fitnessapp.service;

import com.benjidalton.fitnessapp.dto.Responses.MuscleGroupDto;
import com.benjidalton.fitnessapp.dto.Responses.MuscleGroupVolumeDto;

import java.time.LocalDate;
import java.util.List;

public interface MuscleGroupService {

	MuscleGroupDto createMuscleGroup(String name);

	List<MuscleGroupDto> getAllMuscleGroups();

	List<MuscleGroupVolumeDto> getMuscleGroupVolumes(LocalDate startDate, LocalDate endDate);

}
