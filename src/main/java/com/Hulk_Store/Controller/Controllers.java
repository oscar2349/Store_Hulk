package com.Hulk_Store.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Hulk_Store.Entity.Inventario;
import com.Hulk_Store.Service.IHulk_Store;

@Controller
@SessionAttributes("inventario")
public class Controllers {

	private static final Logger log = LoggerFactory.getLogger(Controllers.class);

	@Autowired
	IHulk_Store Hulk_Store;

	/*====================Metodo Listar==================*/
	@GetMapping({ "/", "/listado" }) // metodo para mostrar todos
	public String Listar(Model model) {
		model.addAttribute("titulo", "Listado de Productos");
		model.addAttribute("Listado", Hulk_Store.findall());

		return "listado";

	}

	/*====================Metodo Crear==================*/
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {

		Inventario inventario = new Inventario();
		model.put("inventario", inventario);
		model.put("titulo", "Formulario Registro");
		return "form";
	}

	/*====================Metodo Ver==================*/
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {
		Inventario inventario = Hulk_Store.findOne(id);

		if (inventario == null) {
			flash.addFlashAttribute("El Producto no existe en la base de datos");
			return "redirect:listado";

		}
		model.put("inventario", inventario);
		model.put("titulo", "Detalle de producto");

		return "ver";

	}

	/*====================Metodo Guardar==================*/
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Inventario inventario, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Productos");
			return "form";
		}

		if (!foto.isEmpty()) {
			// Evita sobre escribir Fotos que tenga el mismo nombre
			String NewName = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
			Path rootpath = Paths.get("fotos").resolve(NewName);
			Path RutaCompleta = rootpath.toAbsolutePath();
			log.info("RutaCompleta: " + RutaCompleta);
			try {

				Files.copy(foto.getInputStream(), RutaCompleta);
				inventario.setFoto(NewName);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Hulk_Store.save(inventario);
		status.setComplete();
		return "redirect:listado";

	}

	
	/*====================Metodo Eliminar==================*/
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Integer id,RedirectAttributes flash) {

		if (id > 0) {
			Inventario inventario = Hulk_Store.findOne(id);
			Hulk_Store.delete(id);
			//Eliminar la Imagen Asociada al producto
			Path Path= Paths.get("fotos").resolve(inventario.getFoto()).toAbsolutePath();
			File archivo= Path.toFile();
			
			if (archivo.exists() && archivo.canRead()) {
				if (archivo.delete()) {
					flash.addFlashAttribute("info","Archivo "+ inventario.getFoto() +" Eliminado Correctamente");
					
				}
				
			}
			
			
		}
		return "redirect:/listado";
	}

	
	/* ====================Metodo Editar================== */
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Integer id, Map<String, Object> model) {

		Inventario inventario = null;

		if (id > 0) {
			inventario = Hulk_Store.findOne(id);
		} else {
			return "redirect:/listado";
		}
		model.put("inventario", inventario);
		model.put("titulo", "Editar Producto");
		return "form";
	}

}
