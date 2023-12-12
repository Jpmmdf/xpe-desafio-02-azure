package br.com.jpit.repositories;

import br.com.jpit.entidades.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EnderecoRepository implements PanacheMongoRepository<Endereco> {

    public Endereco findByCep(String cep) {
        return find("cep", cep).firstResult();
    }


}
