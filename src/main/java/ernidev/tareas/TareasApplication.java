package ernidev.tareas;

import ernidev.tareas.presentacion.SistemaTareasFx;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Zona Fit - Gestión de Tareas.
 * <p>
 * Punto de entrada de la aplicación que integra <b>Spring Boot</b> con
 * <b>JavaFX</b>. En lugar de iniciar el contexto de Spring directamente,
 * delega el arranque a {@link SistemaTareasFx}, que se encarga de
 * inicializar JavaFX y el contexto de Spring de forma integrada.
 * </p>
 *
 * @author Erick Gonzalez
 * @version 1.0
 * @see SistemaTareasFx
 */
@SpringBootApplication
public class TareasApplication {

	/**
	 * Método principal de entrada de la aplicación.
	 * <p>
	 * Lanza la aplicación JavaFX a través de {@link SistemaTareasFx},
	 * que internamente inicializa el contexto de Spring Boot antes de
	 * mostrar la interfaz gráfica.
	 * </p>
	 *
	 * @param args argumentos de línea de comandos pasados al launcher de JavaFX.
	 */
	public static void main(String[] args) {
		// SpringApplication.run(TareasApplication.class, args);
		// Se usa JavaFX como punto de entrada para integrar Spring Boot con JavaFX
		Application.launch(SistemaTareasFx.class, args);
	}
}