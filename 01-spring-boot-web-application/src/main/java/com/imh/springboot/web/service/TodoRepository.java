package com.imh.springboot.web.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imh.springboot.web.model.Todo;

public interface TodoRepository extends JpaRepository <Todo, Integer> {
	
	//This method service.retrieveTodos(name) is not provided by the TodoRepository; it's not necessary to implement
	List <Todo> findByUser(String user);

	//Provided by default by the TodoRepository; it's not necessary to implement
	//service.deleteTodo(id);
	//service.retrieveTodo(id);
	//service.updateTodo(todo);
	//service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(), false);
}
