package ernidev.tareas.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad que representa una tarea dentro del sistema de gestión de tareas.
 * Cada instancia corresponde a una fila en la tabla correspondiente de la base de datos.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor   // Constructor vacío requerido por JPA/Hibernate
@AllArgsConstructor  // Constructor con todos los campos (útil para tests o builders manuales)
@ToString
@EqualsAndHashCode
public class Tarea {

    /**
     * Identificador único de la tarea.
     * Se usa Long (no int) para que, antes de persistir, el valor sea null
     * y así Hibernate pueda distinguir una entidad nueva de una existente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El valor lo genera la BD (AUTO_INCREMENT en MySQL)
    private Long id;

    /**
     * Nombre o título descriptivo de la tarea.
     */
    private String nombreTarea;

    /**
     * Persona encargada de realizar la tarea.
     */
    private String responsable;

    /**
     * Estado actual de la tarea (ej. "pendiente", "en progreso", "completada").
     */
    private String estatus;
}