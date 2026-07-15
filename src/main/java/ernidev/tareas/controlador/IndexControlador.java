package ernidev.tareas.controlador;

import ernidev.tareas.modelo.Tarea;
import ernidev.tareas.servicio.TareaServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador JavaFX de la vista principal del sistema de gestión de tareas.
 * <p>
 * Gestiona la interacción entre la interfaz gráfica definida en el archivo FXML
 * y la capa de servicio {@link TareaServicio}, permitiendo listar, agregar,
 * modificar y eliminar tareas desde la vista.
 * </p>
 *
 * @author Erick Gonzalez
 * @version 1.0
 * @see TareaServicio
 * @see Tarea
 */
@Component
public class IndexControlador implements Initializable {

    /** Logger para registrar eventos e información del controlador. */
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    /** Servicio inyectado que gestiona la lógica de negocio de las tareas. */
    @Autowired
    private TareaServicio tareaServicio;

    /** Tabla principal que muestra la lista de tareas. */
    @FXML
    private TableView<Tarea> tareaTabla;

    /** Columna que muestra el identificador único de cada tarea. */
    @FXML
    private TableColumn<Tarea, Long> idTareaColumna;

    /** Columna que muestra el nombre de cada tarea. */
    @FXML
    private TableColumn<Tarea, String> nombreColumnaTarea;

    /** Columna que muestra el responsable asignado a cada tarea. */
    @FXML
    private TableColumn<Tarea, String> resposnsableColumna;

    /** Columna que muestra el estatus actual de cada tarea. */
    @FXML
    private TableColumn<Tarea, String> estatusColumna;

    /** Campo de texto para ingresar o mostrar el responsable de la tarea. */
    @FXML
    private TextField responsableTexto;

    /** Campo de texto para ingresar o mostrar el nombre de la tarea. */
    @FXML
    private TextField nombreTareaTexto;

    /** Campo de texto para ingresar o mostrar el estatus de la tarea. */
    @FXML
    private TextField estatusTexto;

    /** Botón para agregar una nueva tarea. */
    @FXML
    private Button agrgarBtn;

    /** Botón para modificar la tarea seleccionada. */
    @FXML
    private Button modificarBtn;

    /** Botón para eliminar la tarea seleccionada. */
    @FXML
    private Button eliminarBtn;

    /** Botón para limpiar los campos del formulario. */
    @FXML
    private Button limpiarBtn;

    /** Almacena el ID de la tarea seleccionada para operaciones de edición. */
    private Long idInterno;

    /** Lista observable que mantiene sincronizada la tabla con los datos. */
    private final ObservableList<Tarea> tareaList = FXCollections.observableArrayList();

    /**
     * Método de inicialización del controlador, ejecutado automáticamente
     * al cargar la vista FXML.
     * <p>
     * Configura el modo de selección de la tabla, las columnas y carga
     * la lista inicial de tareas.
     * </p>
     *
     * @param location  URL de localización del archivo FXML (puede ser null).
     * @param resources recursos de internacionalización (puede ser null).
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configura la tabla para selección de un solo registro a la vez
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarTareas();
    }

    /**
     * Obtiene las tareas desde el servicio y actualiza la tabla con los datos.
     * <p>
     * Limpia la lista observable antes de cargar los datos para evitar
     * duplicados en la tabla.
     * </p>
     */
    private void listarTareas() {
        // Limpia la lista antes de recargar para evitar registros duplicados
        tareaList.clear();
        tareaList.addAll(tareaServicio.listarTareas());
        tareaTabla.setItems(tareaList);
    }

    /**
     * Configura el mapeo entre las columnas de la tabla y los atributos
     * del modelo {@link Tarea} usando {@link PropertyValueFactory}.
     */
    private void configurarColumnas() {
        idTareaColumna.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumnaTarea.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        resposnsableColumna.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        estatusColumna.setCellValueFactory(new PropertyValueFactory<>("estatus"));
    }

    /**
     * Valida el formulario y agrega una nueva tarea a la base de datos.
     * <p>
     * Si el campo de nombre está vacío, muestra un mensaje de error y
     * enfoca el campo. De lo contrario, crea una nueva {@link Tarea},
     * la guarda y actualiza la tabla.
     * </p>
     */
    public void agregarTarea() {
        // Valida que el nombre de la tarea no esté vacío
        if (nombreTareaTexto.getText().isEmpty()) {
            mostrarMensaje("Error de validacion", "Debe mostar una tarea");
            nombreTareaTexto.requestFocus();
            return;
        } else {
            var tarea = new Tarea();
            recolectarDatosFormulario(tarea);

            // Asegura que el ID sea null para que la BD genere uno nuevo
            tarea.setId(null);
            tareaServicio.guardarTarea(tarea);
            mostrarMensaje("Informacion", "Tarea guardada");
            listarTareas();
            limpiarFormulario();
        }
    }

    /**
     * Recolecta los datos ingresados en el formulario y los asigna
     * al objeto {@link Tarea} recibido.
     * <p>
     * Si existe un {@code idInterno} guardado (tarea en edición),
     * lo asigna al objeto antes de poblar los demás campos.
     * </p>
     *
     * @param tarea objeto {@link Tarea} que será poblado con los datos del formulario.
     */
    private void recolectarDatosFormulario(Tarea tarea) {
        // Si hay un ID interno, se trata de una edición; se asigna el ID existente
        if (idInterno != null) {
            tarea.setId(idInterno);
        }

        tarea.setNombreTarea(nombreTareaTexto.getText());
        tarea.setEstatus(estatusTexto.getText());
        tarea.setResponsable(responsableTexto.getText());
        limpiarFormulario();
        listarTareas();
    }

    /**
     * Limpia todos los campos del formulario y resetea el ID interno.
     * <p>
     * Debe llamarse después de cada operación de agregar, modificar
     * o cancelar para dejar el formulario listo para una nueva entrada.
     * </p>
     */
    private void limpiarFormulario() {
        idInterno = null;
        nombreTareaTexto.clear();
        responsableTexto.clear();
        estatusTexto.clear();
    }

    /**
     * Muestra una ventana emergente de tipo informativo con un título
     * y un mensaje personalizados.
     *
     * @param titulo  texto que aparece en la barra de título de la alerta.
     * @param mensaje contenido del mensaje mostrado al usuario.
     */
    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Carga los datos de la tarea seleccionada en la tabla hacia
     * los campos del formulario para su edición.
     * <p>
     * Si no hay ninguna tarea seleccionada, limpia los campos del formulario.
     * </p>
     */
    public void cargarTarea() {
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();

        if (tarea != null) {
            // Guarda el ID de la tarea seleccionada para usarlo en la modificación
            idInterno = tarea.getId();
            nombreTareaTexto.setText(tarea.getNombreTarea());
            estatusTexto.setText(tarea.getEstatus());
            responsableTexto.setText(tarea.getResponsable());
        } else {
            // Si no hay selección, limpia los campos del formulario
            nombreTareaTexto.setText(null);
            estatusTexto.setText(null);
            responsableTexto.setText(null);
        }
    }

    /**
     * Valida el formulario y aplica los cambios sobre la tarea seleccionada.
     * <p>
     * Verifica que haya una tarea seleccionada y que el nombre no esté vacío
     * antes de ejecutar la actualización en la base de datos.
     * </p>
     */
    public void modificarTarea() {
        // Verifica que haya una tarea seleccionada antes de modificar
        if (idInterno == null) {
            mostrarMensaje("informacion", "Debe seleccionar una tarea");
            return;
        }

        // Valida que el nombre de la tarea no esté vacío
        if (nombreTareaTexto.getText().isEmpty()) {
            mostrarMensaje("Error de validacion", "Debe proporcionar una tarea");
            nombreTareaTexto.requestFocus();
            return;
        }

        var tarea = new Tarea();
        recolectarDatosFormulario(tarea);
        tareaServicio.guardarTarea(tarea);
        mostrarMensaje("Informativo", "Tarea Actualizada");
        limpiarFormulario();
        listarTareas();
    }

    /**
     * Elimina la tarea actualmente seleccionada en la tabla.
     * <p>
     * Registra la operación en el logger antes de eliminar. Si no hay
     * ninguna tarea seleccionada, muestra un mensaje informativo.
     * </p>
     */
    public void eliminarTarea() {
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();

        if (tarea != null) {
            // Registra en el logger la tarea que será eliminada
            logger.info("Tarea a eliminar: " + tarea.toString());
            tareaServicio.eliminarTarea(tarea);
            mostrarMensaje("informativo", "Tarea eliminada");
            limpiarFormulario();
            listarTareas();
        } else {
            mostrarMensaje("informacion", "Debe seleccionar una tarea");
        }
    }
}