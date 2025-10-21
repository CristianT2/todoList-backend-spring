package ar.edu.unju.fi.sistematodolist.service.implementations;

import ar.edu.unju.fi.sistematodolist.dto.TaskDTO;
import ar.edu.unju.fi.sistematodolist.entities.Task;
import ar.edu.unju.fi.sistematodolist.exceptions.CustomeException;
import ar.edu.unju.fi.sistematodolist.mappers.TaskMapper;
import ar.edu.unju.fi.sistematodolist.repository.TaskRepository;
import ar.edu.unju.fi.sistematodolist.service.interfaces.ITaskService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class taskServiceImp implements ITaskService {


    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public taskServiceImp(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }


    /**
     * Crea una Tarea
    */
    @Transactional
    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        try{
            if (taskRepository.existsByTitle(taskDTO.getTitle())){
                throw new CustomeException("La terea ya existe");
            }
            Task task = taskMapper.toEntity(taskDTO);
            log.info("Creando task {}", task);
            return taskMapper.toDTO(taskRepository.save(task));
        } catch (Exception e){
            log.error("Error al crear la tarea", e.getMessage());
            throw new CustomeException("Error al crear la tarea");
        }
    }

    /**
     * Actualiza una tarea
     * @param id
     * @param taskDTO
     * @return
     */
    @Override
    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        log.info("Actualizando Tarea con id: {}", id);
        try {
            taskRepository.findById(id).orElseThrow(() -> new CustomeException("La tarea no existe"));

            if (taskRepository.existsByTitleAndIdNot(taskDTO.getTitle(), id)){
                throw new CustomeException("La tarea ya existe");
            }

            Task task = taskMapper.toEntity(taskDTO);
            task.setId(id);

            log.info("La tarea se actualizo correctamente");
            return taskMapper.toDTO(taskRepository.save(task));
        } catch (Exception e) {
            log.error("Error al actualizar la tarea", e.getMessage());
            throw new CustomeException("Error al actualizar la tarea");
        }
    }

    /**
     * Elimina una tarea
     * @param id
     */
    @Transactional
    @Override
    public void deleteTask(Long id) {
        log.info("Elimininando tarea con id: {}", id);
        try {
            taskRepository.findById(id).orElseThrow(() -> new CustomeException("La tarea no existe"));
            taskRepository.deleteById(id);
            log.info("Tarea eliminada correctamente");
        } catch (Exception e) {
            log.error("Error al eliminar la tarea", e.getMessage());
            throw new CustomeException("Error al eliminar la tarea");
        }
    }

    /**
     * Obtiene tada las tareas registradas
     * @return
     */
    @Override
    public List<TaskDTO> findAllTasks() {
        log.info("Obteniendo todas las tareas");
        try {
            List<Task> tasks = taskRepository.findAll();
            return  tasks.stream()
                    .map(taskMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obteniendo tareas", e.getMessage());
            throw new CustomeException("Error al obtener las tareas");
        }
    }

    /**
     * Obtiene una tarea por su id
     * @param id
     * @return
     */
    @Override
    public Optional<TaskDTO> findById(Long id) {
        try {
            log.info("Obteniendo tarea con id: {}", id);

            return taskRepository.findById(id)
                    .map(taskMapper::toDTO);
        } catch (Exception e) {
            log.error("Error al obtener tarea con id: {}", id, e.getMessage());
            throw new CustomeException("Error al obtener la tarea");
        }
    }
}
