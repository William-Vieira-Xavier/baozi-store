package br.com.baozistore.controller;

import br.com.baozistore.exception.RecursoNaoEncontradoException;
import br.com.baozistore.model.Pedido;
import br.com.baozistore.repository.ClienteRepository;
import br.com.baozistore.repository.ProdutoRepository;
import br.com.baozistore.repository.PedidoRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoRepository pedidos;
    private final ClienteRepository clientes;
    private final ProdutoRepository produtos;

    public PedidoController(PedidoRepository pedidos, ClienteRepository clientes, ProdutoRepository produtos) {
        this.pedidos = pedidos; this.clientes = clientes; this.produtos = produtos;
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@Valid @RequestBody Pedido pedido) {
        pedido.setId(null);
        if (!clientes.existsById(pedido.getClienteId()))
            throw new RecursoNaoEncontradoException("Cliente " + pedido.getClienteId() + " não encontrado.");
        var produto = produtos.findById(pedido.getProdutoId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Produto " + pedido.getProdutoId() + " não encontrado."));
        if (!Boolean.TRUE.equals(produto.getEstoque()))
            throw new IllegalArgumentException("O produto não está disponível em estoque.");
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidos.save(pedido));
    }

    @GetMapping
    public List<Pedido> listar() { return pedidos.findAll(); }

    @GetMapping("/{id}")
    public Pedido buscar(@PathVariable Long id) {
        return pedidos.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Pedido " + id + " não encontrado."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Pedido pedido = pedidos.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Pedido " + id + " não encontrado."));
        pedidos.delete(pedido);
        return ResponseEntity.noContent().build();
    }
}
