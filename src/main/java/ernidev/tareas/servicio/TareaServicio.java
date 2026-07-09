package ernidev.tareas.servicio;

import ernidev.tareas.modelo.Tarea;
import ernidev.tareas.repositorio.TareaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la lógica de negocio para la gestión de tareas.
 * Delega las operaciones de persistencia al TareaRepositorio (Spring Data JPA).
 */
@Service
public class TareaServicio implements InterfaceTareaServicio {

    @Autowired
    private TareaRepositorio tareaRepositorio;

    /**
     * Retorna todas las tareas almacenadas en la base de datos.
     */
    @Override
    public List<Tarea> listarTareas() {
        return tareaRepositorio.findAll();
    }

    /**
     * Busca una tarea por su id.
     * Si no existe, retorna null (orElse(null)).
     */
    @Override
    public Tarea buscarTarea(Long id) {
        return tareaRepositorio.findById(id).orElse(null);
    }

    /**
     * Guarda una tarea nueva o actualiza una existente si ya tiene id.
     */
    @Override
    public void guardarTarea(Tarea tarea) {
        tareaRepositorio.save(tarea);
    }

    /**
     * Elimina una tarea de la base de datos.
     */
    @Override
    public void eliminarTarea(Tarea tarea) {
        tareaRepositorio.delete(tarea);
    }
}
