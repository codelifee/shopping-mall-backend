package com.shoppingmall.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingmall.mapper.CategoriesMapper;
import com.shoppingmall.model.Categories;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriesController {

	@Autowired
	private CategoriesMapper categoriesMapper;

	//모든 카테고리 목록을 보여줌
	@GetMapping("/all")
	public List<Categories> getAll(){
		return categoriesMapper.getAll();
	}
	
	//입력된 id와 매칭되는 카테고리 데이터 보여줌 
	@GetMapping("/{category_id}")
	public Categories get(@PathVariable("category_id")int category_id) {
		return categoriesMapper.getCategories(category_id);
	}
	
	//카테고리 데이터 모두 입력
	@PostMapping("")
	public Categories insert(@RequestBody Categories categories) {
		categoriesMapper.insertCategories(categories);
		return categories;
	}
	
	//입력된 id와 매칭되는 카테고리 데이터 모두 수정
	@PutMapping("/{category_id}")
	public void update(@RequestBody Categories categories) {
		categoriesMapper.updateCategories(categories);
	}
	
	//입력된 id와 매칭되는 카테고리 데이터 부분 수정
	@PatchMapping("/{category_id}")
	public @ResponseBody void patchCategory(@PathVariable int category_id, @RequestBody Map<Object, Object> fields) {
		Categories categories = categoriesMapper.getCategories(category_id);
		fields.forEach((k,v) -> {
			Field field = ReflectionUtils.findRequiredField(Categories.class, (String)k);
			ReflectionUtils.setField(field, categories, v);
		});
		categoriesMapper.updateCategories(categories);
	}
	
	//입력된 id와 매칭되는 카테고리 데이터 삭제
	@DeleteMapping("/{category_id}")
	public void delete(@PathVariable("category_id")int category_id){
		categoriesMapper.deleteCategories(category_id);
	}
}
