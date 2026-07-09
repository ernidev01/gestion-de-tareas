package ernidev.tareas.repositorio;

import ernidev.tareas.modelo.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepositorio extends JpaRepository<Tarea, Long> {
}
