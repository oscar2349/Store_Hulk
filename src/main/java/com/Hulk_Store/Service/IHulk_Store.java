package com.Hulk_Store.Service;

import java.util.List;

import com.Hulk_Store.Entity.Inventario;

public interface IHulk_Store {
	
	public List<Inventario> findall();

	public void save(Inventario inventario);

	public Inventario findOne(Integer id);

	public void delete(Integer id);

}
