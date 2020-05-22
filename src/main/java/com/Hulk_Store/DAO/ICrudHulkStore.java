package com.Hulk_Store.DAO;

import org.springframework.data.repository.CrudRepository;

import com.Hulk_Store.Entity.Inventario;

public interface ICrudHulkStore extends CrudRepository<Inventario, Integer> {

}
