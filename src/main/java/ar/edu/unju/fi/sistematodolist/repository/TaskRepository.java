package ar.edu.unju.fi.sistematodolist.repository;

import ar.edu.unju.fi.sistematodolist.entities.Task;
import ar.edu.unju.fi.sistematodolist.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    //Lista por estado
    List<Task> findByStatus(TaskStatus status);

    //Lista tareas con fecha de vencimiento próxima
    List<Task> findByDueDateBefore(LocalDate date);
}
