package ar.edu.unju.fi.sistematodolist.controllers;

import ar.edu.unju.fi.sistematodolist.dto.TaskDTO;
import ar.edu.unju.fi.sistematodolist.exceptions.CustomeException;
import ar.edu.unju.fi.sistematodolist.service.interfaces.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final ITaskService taskService;

    @Autowired
    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        try{
            return ResponseEntity.ok(taskService.createTask(taskDTO));
        } catch (Exception e) {
            throw new CustomeException("Error al crear la tarea");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        try{
            return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
        } catch (Exception e) {
            throw new CustomeException("Error al actualizar la tarea");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id){
        try{
            taskService.deleteTask(id);
        }catch (Exception e){
            throw new CustomeException("Error al eliminar la tarea");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        try{
            List<TaskDTO> tasks = taskService.findAllTasks();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            throw new CustomeException("Error al obtener las tareas");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
        try {
            return taskService.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw new CustomeException("Error al obtener la tarea");
        }
    }
}
