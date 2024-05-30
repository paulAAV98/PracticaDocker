package distribuidos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.opentracing.Traced;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
@OpenAPIDefinition(info = @Info(
		title = "Esto son los microservicios de la practica - Dentro de la clase",
		description = "Creacion y listado de productos", version = "3.0.1"))
@Path("/service")
public class Service {
	@EJB
	private ProductoFacade productoFacade;

	@GET
	@Path("/listar")
	@Traced(operationName = "listado-trace")
	@Counted(description = "Contador de listar", absolute = true)
	@Timed(name = "listar-time", description = "Tiempo de procesamiento de listar", unit = MetricUnits.MILLISECONDS, absolute = true)
	@Produces("application/json")
	@Operation(description = "Captura de lista de productos en formato JSON", summary = "echo jaxrs")
	@APIResponse(responseCode = "200", description = "Echo respuesta",
		  content = @Content(mediaType = MediaType.APPLICATION_JSON,
		  schema = @Schema(implementation = String.class)))
	public String listarProductos() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(productoFacade.findAll());
	}

	@POST
	@Path("/crear")
	@Traced(operationName = "crear-traced")
	@Counted(description = "Contador CrearProducto", absolute = true)
	@Timed(name = "crear-producto-time", description = "Tiempo de procesamiento de crear producto", unit = MetricUnits.MILLISECONDS, absolute = true)
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public boolean crearProducto(@FormParam("codigo") String codigo, @FormParam("nombre") String nombre,
							 @FormParam("precio") Double precio){
		ProductoRepository productoRepository = new ProductoRepository();
		productoRepository.setCodigo(codigo);
		productoRepository.setNombre(nombre);
		productoRepository.setPrecio(precio);
		return productoFacade.create(productoRepository);
	}
	@PUT
	@Path("/update")
	@Traced(operationName = "update-traced")
	@Counted(description = "Contador ActualizarProducto", absolute = true)
	@Timed(name = "actualizar-producto-time", description = "Tiempo de procesamiento de actualizar producto", unit = MetricUnits.MILLISECONDS, absolute = true)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean actualizar(ProductoRepository productoDTO) {
		ProductoRepository productoRepository = new ProductoRepository();
		productoRepository.setCodigo(productoDTO.getCodigo());
		productoRepository.setNombre(productoDTO.getNombre());
		productoRepository.setPrecio(productoDTO.getPrecio());
		return productoFacade.edit(productoRepository);
	}


/*
	@PUT
	@Path("/update")
	@Traced(operationName = "update-traced")
	@Counted(description = "Contador ActualizarProducto", absolute = true)
	@Timed(name = "actualizar-producto-time", description = "Tiempo de procesamiento de actualizar producto", unit = MetricUnits.MILLISECONDS, absolute = true)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean actualizar(@FormParam("codigo") String codigo, @FormParam("nombre") String nombre,
							  @FormParam("precio") Double precio){
		ProductoRepository productoRepository = new ProductoRepository();
		productoRepository.setCodigo(codigo);
		productoRepository.setNombre(nombre);
		productoRepository.setPrecio(precio);
		return productoFacade.edit(productoRepository);
	}
/*
	@DELETE
	@Path("/delete")
	@Traced(operationName = "delete-traced")
	@Counted(description = "Contador BorraProducto", absolute = true)
	@Timed(name = "borrar-producto-time", description = "Tiempo de procesamiento de borrar producto", unit = MetricUnits.MILLISECONDS, absolute = true)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean borrar(@FormParam("codigo") String codigo) {
		ProductoRepository productoRepository = new ProductoRepository();
		productoRepository.setCodigo(codigo);
		return productoFacade.remove(productoRepository);
	}

*/
@DELETE
@Path("/delete")
@Traced(operationName = "delete-traced")
@Counted(description = "Contador BorraProducto", absolute = true)
@Timed(name = "borrar-producto-time", description = "Tiempo de procesamiento de borrar producto", unit = MetricUnits.MILLISECONDS, absolute = true)
@Produces(MediaType.APPLICATION_JSON)
public boolean borrar(@QueryParam("codigo") String codigo) {
	ProductoRepository productoRepository = new ProductoRepository();
	productoRepository.setCodigo(codigo);
	return productoFacade.remove(productoRepository);
}


}
