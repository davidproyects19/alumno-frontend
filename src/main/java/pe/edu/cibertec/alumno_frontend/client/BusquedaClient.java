package pe.edu.cibertec.alumno_frontend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.edu.cibertec.alumno_frontend.config.FeignClientConfig;
import pe.edu.cibertec.alumno_frontend.dto.BusquedaRequestDTO;
import pe.edu.cibertec.alumno_frontend.dto.BusquedaResponseDTO;

//depende de el backend el nombre en el controller
@FeignClient(name = "busqueda", url = "http://localhost:8082/busqueda", configuration = FeignClientConfig.class)
public interface BusquedaClient {

    @PostMapping("/buscar")
    ResponseEntity<BusquedaResponseDTO> buscar(@RequestBody BusquedaRequestDTO busquedaRequestDTO);

}
