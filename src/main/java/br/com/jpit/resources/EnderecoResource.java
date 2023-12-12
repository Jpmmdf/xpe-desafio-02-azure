package br.com.jpit.resources;

import br.com.jpit.entidades.Endereco;
import br.com.jpit.services.EnderecoService;
import br.com.jpit.uteis.validators.ValidCep;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/endereco")
@ApplicationScoped
public class EnderecoResource {

    @Inject
    EnderecoService enderecoService;

    @GET
    @Path("/{cep}")
    public Response buscarEndereco(@PathParam("cep") @ValidCep String cep) {
        Endereco endereco = enderecoService.buscarEndereco(cep);
        return endereco != null ? Response.ok(endereco).build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}

