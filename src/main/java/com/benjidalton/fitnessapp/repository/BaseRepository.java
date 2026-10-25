package com.benjidalton.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

	T create(T entity);

	T find(ID id);

	void deleteByIdOrThrow(ID id);
}