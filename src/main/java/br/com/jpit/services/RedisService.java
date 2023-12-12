package br.com.jpit.services;

import br.com.jpit.entidades.Endereco;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.hash.HashCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.jboss.logging.Logger;

@ApplicationScoped
public class RedisService {
    private static final String MY_KEY = "my-key";

    private static final Logger LOG = Logger.getLogger(RedisService.class.getName());

    RedisDataSource redisDataSource;

    private final HashCommands<String, String, Endereco> commands;

    RedisService(RedisDataSource redisDataSource) {
        this.redisDataSource = redisDataSource;
        this.commands = redisDataSource.hash(String.class, String.class, Endereco.class);
    }

    public void salvarCep(Endereco endereco) {
        try {
            commands.hset(MY_KEY, endereco.getCep(), endereco);
            LOG.infof("Valor salvo no Redis para a chave: %s", endereco.getCep());
        } catch (Exception e) {
            LOG.errorf(e, "Erro ao salvar no Redis para a chave: %s", endereco.getCep());
        }
    }

    public Endereco buscarCep(String chave) {
        try {
            return commands.hget(MY_KEY, chave);
        } catch (Exception e) {
            LOG.errorf(e, "Erro ao buscar no Redis para a chave: %s", chave);
            return null;
        }
    }
}