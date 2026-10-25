package com.benjidalton.fitnessapp.repository;


import com.benjidalton.fitnessapp.model.BodyComposition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface BodyCompositionRepository extends BaseRepository<BodyComposition, Long> {

    @Query("select b from BodyComposition b order by b.createdAt desc, b.id desc")
    List<BodyComposition> findRecent(Pageable pageable);

    Optional<BodyComposition> findFirstByOrderByCreatedAtDescIdDesc();
}
