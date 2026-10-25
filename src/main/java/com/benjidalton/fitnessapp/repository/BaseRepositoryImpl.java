package com.benjidalton.fitnessapp.repository;

import com.benjidalton.fitnessapp.exception.ApiException;

import jakarta.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

	private final JpaEntityInformation<T, ?> entityInformation;

	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
	}

	@Override
	@Transactional
	public T create(T entity) {
		return saveAndFlush(entity);
	}

	@Override
	public T find(ID id) {
		return findById(id)
				.orElseThrow(() -> ApiException.notFound(
						humanize(entityInformation.getJavaType().getSimpleName())
								+ " " + id + " not found"
				));
	}

	@Override
	@Transactional
	public void deleteByIdOrThrow(ID id) {
		delete(find(id));
	}

	private String humanize(String name) {
		return name.replaceAll("([a-z])([A-Z])", "$1 $2");
	}
}