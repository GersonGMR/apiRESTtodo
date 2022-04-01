package com.gersoncompany.apiRESTtodo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.gersoncompany.apiRESTtodo.DTO.TaskInDTO;
import com.gersoncompany.apiRESTtodo.exceptions.ToDoExceptions;
import com.gersoncompany.apiRESTtodo.mapper.TaskInDTOToTask;
import com.gersoncompany.apiRESTtodo.persistence.entity.Task;
import com.gersoncompany.apiRESTtodo.persistence.entity.TaskStatus;
import com.gersoncompany.apiRESTtodo.persistence.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class TaskService {
	
	private final TaskRepository repository;
	private final TaskInDTOToTask mapper;
    
	public TaskService(TaskRepository repository, TaskInDTOToTask mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	public Task createTask(TaskInDTO taskInDTO) {
		Task task = mapper.map(taskInDTO);
		return this.repository.save(task);
	}
	
	public List<Task> findAll() {
		return this.repository.findAll();
	}
	
	public List<Task> findAllByTaskStatus(TaskStatus status){
		return this.repository.findAllByTaskStatus(status);
	}
	
	@Transactional
	public void updateTaskAsFinished(Long id) {
		Optional<Task> optionalTask = this.repository.findById(id);
		if (optionalTask.isEmpty()) {
			throw new ToDoExceptions("Task not found", HttpStatus.NOT_FOUND);
		}
		this.repository.markTaskAsFinished(id);
	}
	
	public void deleteById(Long id) {
		Optional<Task> optionalTask = this.repository.findById(id);
		if (optionalTask.isEmpty()) {
			throw new ToDoExceptions("Task not found", HttpStatus.NOT_FOUND);
		}
		this.repository.deleteById(id);
	}
}
