package ar.edu.unju.fi.sistematodolist.service.interfaces;

import ar.edu.unju.fi.sistematodolist.dto.TaskDTO;

import java.util.List;
import java.util.Optional;

public interface ITaskService {

    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO updateTask(Long id, TaskDTO taskDTO);
    void deleteTask(Long id);
    List<TaskDTO> findAllTasks();
    Optional<TaskDTO> findById(Long id);
}
