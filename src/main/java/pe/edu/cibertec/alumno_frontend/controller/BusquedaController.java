package pe.edu.cibertec.alumno_frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.cibertec.alumno_frontend.client.BusquedaClient;
import pe.edu.cibertec.alumno_frontend.dto.BusquedaRequestDTO;
import pe.edu.cibertec.alumno_frontend.dto.BusquedaResponseDTO;
import pe.edu.cibertec.alumno_frontend.model.AlumnoModel;

@Controller
@RequestMapping("/busqueda")
public class BusquedaController {

    @Autowired
    BusquedaClient busquedaClient;

    @GetMapping("/buscar")
    public String showBusqueda(Model model) {
        AlumnoModel alumnoModel = new AlumnoModel("", "", "", "", "", "");
        model.addAttribute("alumnoModel", alumnoModel);
        return "consulta"; // Muestra la página de búsqueda
    }

    @PostMapping("/buscar-alumno")
    public String buscarAlumno(@RequestParam("codigo") String codigo, Model model) {

        // Validar si el código es vacío o nulo
        if (codigo == null || codigo.trim().isEmpty()) {
            // Mensaje de error si no se ingresa un código
            AlumnoModel alumnoModel = new AlumnoModel("", "", "", "", "", "Error: Debe ingresar un código de alumno.");
            model.addAttribute("alumnoModel", alumnoModel);
            return "consulta"; // Vuelve a la página de búsqueda con mensaje de error
        }

        try {
            BusquedaRequestDTO busquedaRequestDTO = new BusquedaRequestDTO(codigo);
            ResponseEntity<BusquedaResponseDTO> responseEntity = busquedaClient.buscar(busquedaRequestDTO);

            // Si la respuesta del servicio es exitosa
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                BusquedaResponseDTO busquedaResponseDTO = responseEntity.getBody();

                // Verificar si se encontró el alumno
                if (busquedaResponseDTO != null && busquedaResponseDTO.codigo() != null) {
                    // Alumno encontrado, redirige a detalle
                    AlumnoModel alumnoModel = new AlumnoModel(
                            busquedaResponseDTO.codigo(),
                            busquedaResponseDTO.nombres(),
                            busquedaResponseDTO.apellidos(),
                            busquedaResponseDTO.carrera(),
                            busquedaResponseDTO.ciclo(),
                            "");
                    model.addAttribute("alumnoModel", alumnoModel);
                    return "detalle"; // Redirige a la vista detalle si se encuentra el alumno
                } else {
                    // Si no se encontró el alumno
                    AlumnoModel alumnoModel = new AlumnoModel("", "", "", "", "", "Error: No se encontró el alumno con el código ingresado.");
                    model.addAttribute("alumnoModel", alumnoModel);
                    return "consulta"; // Muestra el mensaje de error y se queda en la página de búsqueda
                }
            } else {
                // Si hubo un problema en la respuesta del servicio
                AlumnoModel alumnoModel = new AlumnoModel("", "", "", "", "", "Error: Ocurrió un problema con el servicio.");
                model.addAttribute("alumnoModel", alumnoModel);
                return "consulta"; // Muestra el mensaje de error y se queda en la página de búsqueda
            }
        } catch (Exception e) {
            // Si ocurre un error en la búsqueda
            AlumnoModel alumnoModel = new AlumnoModel("", "", "", "", "", "Error: Ocurrió un problema en la búsqueda.");
            model.addAttribute("alumnoModel", alumnoModel);
            System.out.println(e.getMessage());
            return "consulta"; // Muestra el mensaje de error y se queda en la página de búsqueda
        }
    }
}