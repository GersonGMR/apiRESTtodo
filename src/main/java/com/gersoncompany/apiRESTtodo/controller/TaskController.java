package com.gersoncompany.apiRESTtodo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gersoncompany.apiRESTtodo.DTO.TaskInDTO;
import com.gersoncompany.apiRESTtodo.persistence.entity.Task;
import com.gersoncompany.apiRESTtodo.persistence.entity.TaskStatus;
import com.gersoncompany.apiRESTtodo.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	private final TaskService taskService;
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@PostMapping
	public Task createTask(@RequestBody TaskInDTO taskInDTO) {
		return this.taskService.createTask(taskInDTO);
	}
	
	@GetMapping
	public List<Task> findAll(){
	    return this.taskService.findAll();
	}
	
	@GetMapping("/status/{status}")
	public List<Task> findAllbyStatus(@PathVariable("status") TaskStatus status){
		return this.taskService.findAllByTaskStatus(status);
	}
	
	@PatchMapping("/mark_as_finished/{id}")
	public ResponseEntity<Void> markAsFinished(@PathVariable("id") Long id) {
		this.taskService.updateTaskAsFinished(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		this.taskService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
