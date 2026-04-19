package br.com.api.pessoa.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.api.pessoa.entidade.Pessoa;

@Repository
public interface PessoaRepositorio extends CrudRepository<Pessoa, Integer>{

    
}
