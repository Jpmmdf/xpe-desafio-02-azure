package br.com.jpit.services;
import br.com.jpit.entidades.Endereco;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/ws")
@RegisterRestClient(configKey="viacep-api")
public interface ViaCepService {

    @GET
    @Path("/{cep}/json/")
    @Produces(MediaType.APPLICATION_JSON)
    Endereco buscarCep(@PathParam("cep") String cep);
}