package com.benjidalton.fitnessapp.repository;

import com.benjidalton.fitnessapp.model.Workout;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkoutRepository extends BaseRepository<Workout, Long> {

    long countByDateGreaterThanEqual(LocalDate date);

    /** Most recent workouts first; ties on date broken by id for a stable order. */
    @Query("select w from Workout w order by w.date desc, w.id desc")
    List<Workout> findRecent(Pageable pageable);

    @Query(
            """
            select coalesce(sum(s.reps * s.weight), 0.0)
            from ExerciseSet s
            where s.workoutExercise.workout.date >= :from
            """)
    double totalVolumeSince(@Param("from") LocalDate from);

    List<Workout> findTop10ByOrderByDateDesc();
}