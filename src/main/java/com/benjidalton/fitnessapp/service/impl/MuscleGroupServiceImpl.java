package com.benjidalton.fitnessapp.service.impl;

import com.benjidalton.fitnessapp.dto.Responses.MuscleGroupVolumeDto;
import com.benjidalton.fitnessapp.dto.Responses.MuscleGroupDto;
import com.benjidalton.fitnessapp.model.MuscleGroup;
import com.benjidalton.fitnessapp.repository.MuscleGroupRepository;
import com.benjidalton.fitnessapp.service.MuscleGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MuscleGroupServiceImpl implements MuscleGroupService {

	private final MuscleGroupRepository muscleGroups;

	public MuscleGroupServiceImpl(MuscleGroupRepository muscleGroups) {
		this.muscleGroups = muscleGroups;
	}

	public MuscleGroupDto createMuscleGroup(String name) {
		return MuscleGroupDto.from(muscleGroups.create(new MuscleGroup(name)));
	}

	@Transactional(readOnly = true)
	public List<MuscleGroupDto> getAllMuscleGroups() {
		return muscleGroups.findAll().stream().map(MuscleGroupDto::from).toList();
	}

	public List<MuscleGroupVolumeDto> getMuscleGroupVolumes(
			LocalDate startDate,
			LocalDate endDate
	) {
		return muscleGroups.volumeByMuscleGroup(
				startDate,
				endDate
		);
	}
}
