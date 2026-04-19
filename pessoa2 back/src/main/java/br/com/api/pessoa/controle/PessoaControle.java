package br.com.api.pessoa.controle;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.api.pessoa.servico.PessoaServico;
import br.com.api.pessoa.entidade.Pessoa;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;




@CrossOrigin(value = "*")

@RestController
public class PessoaControle {
    
    // Referenciar PessoaServico
    private final PessoaServico servico;

    public PessoaControle(PessoaServico servico){
        this.servico = servico;
    }

    // Rota para listar as pessoas
    @GetMapping("/selecionar")
    public ResponseEntity<Iterable<Pessoa>> selecionar() {
        return ResponseEntity.ok(servico.selecionar());
    }

    // Rota para cadastrar pessoas
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(
        @RequestParam("nome") String nome,
        @RequestParam("cidade") String cidade,
        @RequestParam("imagem") MultipartFile imagem
    ) {
        try{
            Pessoa p = servico.cadastrar(nome, cidade, imagem);
            return ResponseEntity.status(201).body(p);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Rota para alterar PUT request
    @PutMapping("alterar/{id}")
    public ResponseEntity<?> alterar(@PathVariable Integer id, @RequestBody Pessoa p) {
        try{
            Pessoa obj = servico.alterar(id, p);
            return ResponseEntity.status(201).body(obj);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Rota para remover pessoas
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> remover(@PathVariable Integer id){
        try{
            servico.remover(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    // Exibir a imagem da pessoa atrvés do id
    @GetMapping("/imagem/{id}")
    public ResponseEntity<byte[]> imagem(@PathVariable Integer id) {
        try{
            byte[] imagem = servico.obterImagem(id);
            String extensao = servico.obterExtensaoImagem(id);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(extensao))
                    .body(imagem);        
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    

}
