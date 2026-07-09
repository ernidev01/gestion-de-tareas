package ernidev.tareas.servicio;

import ernidev.tareas.modelo.Tarea;

import java.util.List;

/**
 * Define las operaciones de negocio disponibles para gestionar tareas.
 * La implementación concreta (ej. TareaServicioImpl) contendrá la lógica real,
 * delegando la persistencia al repositorio correspondiente.
 */
public interface InterfaceTareaServicio {

    /**
     * Obtiene la lista completa de tareas registradas.
     * @return lista de todas las tareas
     */
    List<Tarea> listarTareas();

    /**
     * Busca una tarea por su identificador.
     * @param id identificador de la tarea
     * @return la tarea encontrada, o null/Optional según se implemente
     */
    Tarea buscarTarea(Long id);

    /**
     * Guarda una tarea nueva o actualiza una existente.
     * @param tarea la tarea a guardar
     */
    void guardarTarea(Tarea tarea);

    /**
     * Elimina una tarea existente.
     * @param tarea la tarea a eliminar
     */
    void eliminarTarea(Tarea tarea);
}