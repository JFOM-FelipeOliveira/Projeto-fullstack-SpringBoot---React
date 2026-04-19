package br.com.api.pessoa.servico;

import br.com.api.pessoa.repositorio.PessoaRepositorio;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.com.api.pessoa.entidade.Pessoa;

@Service
public class PessoaServico {
    
    // Referência do repositório
    private final PessoaRepositorio repositorio;

    public PessoaServico(PessoaRepositorio repositorio){
        this.repositorio = repositorio;
    }

    // Listar todas as pessoas
    public Iterable<Pessoa> selecionar(){
        return repositorio.findAll();
    }

    // Cadastrar pessoas
    public Pessoa cadastrar(String nome, String cidade, MultipartFile imagem) throws IOException {
        // Validar dados
        if(nome == null || nome.isEmpty()){
            throw new IllegalArgumentException("O nome é Obrigatório");
        }
        if(cidade == null || cidade.isEmpty()){
            throw new IllegalArgumentException("A cidade é Obrigatória");
        }
        if(imagem == null || imagem.isEmpty()){
            throw new IllegalArgumentException("A imagem é Obrigatória");
        }

        Pessoa p = new Pessoa();
        p.setNome(nome);
        p.setCidade(cidade);
        p.setImagem(imagem.getBytes());
        p.setExtensao(imagem.getContentType());

        return repositorio.save(p);
    }

    // Alterar pessoas
    public Pessoa alterar(Integer id, Pessoa p){
        // Validar dados
        if(p.getNome() == null || p.getNome().isEmpty()){
            throw new IllegalArgumentException("O nome é Obrigatório");
        }
        if(p.getCidade() == null || p.getCidade().isEmpty()){
            throw new IllegalArgumentException("A cidade é Obrigatória");
        }

        p.setId(id);

        // Obter os dados atuais da pessoa contida na tabela
        Pessoa obj = repositorio.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada com o id: " + id));

        // Extrair a imagem e a extensão para o objeto p
        p.setImagem(obj.getImagem());
        p.setExtensao(obj.getExtensao());
        
        return repositorio.save(p);
    }

    // Remover pessoas
    public void remover(Integer id){
        // Verificar a existência da pessoa com o id informado
        repositorio.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        // Efetuar a remoção
        repositorio.deleteById(id);
    }

    // Obter imagem
    public byte[] obterImagem(Integer id){
        Pessoa p = repositorio.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        return p.getImagem();
    }

    public String obterExtensaoImagem(Integer id){
        Pessoa p = repositorio.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));

        return p.getExtensao();
    }
}
