package com.Hulk_Store.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Hulk_Store.DAO.ICrudHulkStore;
import com.Hulk_Store.Entity.Inventario;

@Service
public class ImpleHulkStore implements IHulk_Store {

	@Autowired
	ICrudHulkStore impHulkStore;

	@Override
	@Transactional
	public List<Inventario> findall() {

		return (List<Inventario>) impHulkStore.findAll();
	}

	@Override
	@Transactional
	public void save(Inventario inventario) {
		impHulkStore.save(inventario);
	}

	@Override
	@Transactional(readOnly = true)
	public Inventario findOne(Integer id) {
		// TODO Auto-generated method stub
		return impHulkStore.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		impHulkStore.deleteById(id);

	}
}
