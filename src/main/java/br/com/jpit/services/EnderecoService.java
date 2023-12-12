package br.com.jpit.services;


import br.com.jpit.entidades.Endereco;
import br.com.jpit.repositories.EnderecoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class EnderecoService {

    private static final Logger LOG = Logger.getLogger(EnderecoService.class.getName());

    @Inject
    RedisService redisService;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    @RestClient
    ViaCepService viaCepService;

    public Endereco buscarEndereco(String cep) {
        LOG.infof("Buscando endereço para o CEP: %s", cep);

        // Tenta buscar no Redis
        Endereco endereco = redisService.buscarCep(cep);
        if (endereco != null) {
            LOG.infof("Endereço encontrado no Redis para o CEP: %s", cep);
            return endereco;
        }


        // Se não encontrado no Redis, tenta buscar no repositório
        endereco = enderecoRepository.findByCep(cep);
        if (endereco != null) {
            LOG.infof("Endereço encontrado no repositório para o CEP: %s", cep);
            return endereco;
        }

        // Se não encontrado, buscar no ViaCEP
        try {
            LOG.infof("Buscando no ViaCEP para o CEP: %s", cep);
            Response response = viaCepService.buscarCep(cep);
            if (response.getStatus() == Response.Status.OK.getStatusCode() && response.hasEntity()) {
                endereco = response.readEntity(Endereco.class);
                redisService.salvarCep(endereco);
                salvarEnderecoNoRepositorio(endereco);
            }
        } catch (Exception e) {
            LOG.errorf(e, "Erro ao buscar endereço no ViaCEP para o CEP: %s", cep);
            // Pode-se lançar uma exceção customizada ou retornar null
        }

        return endereco;
    }


    private void salvarEnderecoNoRepositorio(Endereco endereco) {
        LOG.infof("Salvando endereço no repositório: %s", endereco);
        enderecoRepository.persist(endereco);
    }
}
