package com.consultorio.core.services;

import java.util.List;

public interface CRUDService<T> {
	List<T> getAll();
	T save(T newElement);
	T getById(Long id);
	void deleteById(Long id);
	void delete(T element);
	void deleteAll();
	Boolean isExist(T element);
}
