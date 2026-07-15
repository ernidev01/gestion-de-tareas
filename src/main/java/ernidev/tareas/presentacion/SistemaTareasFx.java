package ernidev.tareas.presentacion;

import ernidev.tareas.TareasApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * Clase de presentación que integra el ciclo de vida de JavaFX
 * con el contexto de Spring Boot.
 * <p>
 * Extiende {@link Application} de JavaFX y gestiona la inicialización,
 * arranque y cierre del contexto de Spring, asegurando que los beans
 * de Spring estén disponibles como controladores de JavaFX.
 * </p>
 *
 * @author Erick Gonzalez
 * @version 1.0
 * @see TareasApplication
 */
public class SistemaTareasFx extends Application {

    /**
     * Contexto de aplicación de Spring Boot, utilizado para resolver
     * los beans inyectados en los controladores JavaFX.
     */
    private ConfigurableApplicationContext applicatioContext;

    /**
     * Inicializa el contexto de Spring Boot antes de que JavaFX
     * muestre cualquier ventana.
     * <p>
     * Este método es ejecutado automáticamente por JavaFX antes de
     * {@link #start(Stage)}, garantizando que todos los beans de Spring
     * estén disponibles cuando se cargue la vista FXML.
     * </p>
     */
    @Override
    public void init() {
        // Arranca el contexto de Spring Boot usando TareasApplication como clase principal
        this.applicatioContext = new SpringApplicationBuilder(TareasApplication.class).run();
    }

    /**
     * Punto de entrada gráfico de la aplicación JavaFX.
     * <p>
     * Carga la vista principal desde el archivo FXML, asigna el contexto
     * de Spring como fábrica de controladores para permitir la inyección
     * de dependencias, y muestra la ventana principal.
     * </p>
     *
     * @param stage escenario principal proporcionado por el runtime de JavaFX.
     * @throws Exception si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Carga la vista principal desde el archivo FXML ubicado en /templates/
        FXMLLoader loader = new FXMLLoader(TareasApplication.class.getResource("/templates/index.fxml"));

        // Asigna Spring como fábrica de controladores para soportar @Autowired en los controladores JavaFX
        loader.setControllerFactory(applicatioContext::getBean);

        // Construye la escena con el nodo raíz cargado desde el FXML
        Scene escena = new Scene(loader.load());
        stage.setScene(escena);
        stage.show();
    }

    /**
     * Cierra el contexto de Spring Boot cuando la ventana de JavaFX
     * es cerrada por el usuario.
     * <p>
     * Este método es ejecutado automáticamente por JavaFX al finalizar
     * la aplicación, liberando todos los recursos gestionados por Spring.
     * </p>
     */
    @Override
    public void stop() {
        // Cierra el contexto de Spring para liberar recursos al salir de la aplicación
        applicatioContext.close();
    }
}