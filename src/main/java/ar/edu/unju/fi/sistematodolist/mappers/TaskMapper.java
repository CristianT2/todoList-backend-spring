package ar.edu.unju.fi.sistematodolist.mappers;

import ar.edu.unju.fi.sistematodolist.dto.TaskDTO;
import ar.edu.unju.fi.sistematodolist.entities.Task;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class TaskMapper {

    private ModelMapper modelMapper;

    public TaskMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public TaskDTO toDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

    public Task toEntity(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }
}
