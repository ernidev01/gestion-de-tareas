package ernidev.tareas.repositorio;

import ernidev.tareas.modelo.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la gestión de la entidad {@link Tarea} en la base de datos.
 * <p>
 * Extiende {@link JpaRepository} para heredar automáticamente las operaciones
 * CRUD básicas sin necesidad de implementarlas manualmente:
 * </p>
 * <ul>
 *   <li>{@code findAll()} — obtener todas las tareas</li>
 *   <li>{@code findById(Long id)} — buscar tarea por ID</li>
 *   <li>{@code save(Tarea tarea)} — guardar o actualizar una tarea</li>
 *   <li>{@code delete(Tarea tarea)} — eliminar una tarea</li>
 * </ul>
 *
 * @author Erick Gonzalez
 * @version 1.0
 * @see Tarea
 * @see JpaRepository
 */
public interface TareaRepositorio extends JpaRepository<Tarea, Long> {
}