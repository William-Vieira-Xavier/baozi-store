package br.com.baozistore.controller;

import br.com.baozistore.exception.RecursoNaoEncontradoException;
import br.com.baozistore.model.Produto;
import br.com.baozistore.repository.ProdutoRepository;
import br.com.baozistore.repository.PedidoRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoRepository produtos;
    private final PedidoRepository pedidos;
    public ProdutoController(ProdutoRepository produtos, PedidoRepository pedidos) {
        this.produtos = produtos; this.pedidos = pedidos;
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto) {
        produto.setId(null);
        Produto salvo = produtos.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public List<Produto> listar() { return produtos.findAll(); }

    @GetMapping("/{id}")
    public Produto buscar(@PathVariable Long id) {
        return produtos.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Produto " + id + " não encontrado."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Produto produto = produtos.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Produto " + id + " não encontrado."));
        if (pedidos.existsByProdutoId(id)) throw new IllegalArgumentException("Exclua os pedidos deste produto antes de excluí-lo.");
        produtos.delete(produto);
        return ResponseEntity.noContent().build();
    }
}
