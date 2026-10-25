package com.benjidalton.fitnessapp.repository;

import com.benjidalton.fitnessapp.dto.Responses.MuscleGroupVolumeDto;
import com.benjidalton.fitnessapp.model.MuscleGroup;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MuscleGroupRepository extends BaseRepository<MuscleGroup, Long> {

    Optional<MuscleGroup> findByName(String name);

    /** Total volume (reps * weight) per muscle group; groups with no sets report 0. */
    @Query(
            """
            select new com.benjidalton.fitnessapp.dto.Responses$MuscleGroupVolumeDto(
                    m.id,
                    m.name,
                    cast(coalesce(sum(s.reps * s.weight), 0.0) as double)
                )
            from MuscleGroup m
             left join m.exercises e
             left join e.workoutExercises we
             left join we.se ts s
             left join we.workout w
                 on (:startDate is null or w.date >= :startDate)
                 and (:endDate is null or w.date <= :endDate)
            group by m.id, m.name
            order by m.id
            """)
    List<MuscleGroupVolumeDto> volumeByMuscleGroup(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

}