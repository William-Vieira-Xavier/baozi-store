package br.com.baozistore.controller;

import br.com.baozistore.exception.RecursoNaoEncontradoException;
import br.com.baozistore.model.Cliente;
import br.com.baozistore.repository.ClienteRepository;
import br.com.baozistore.repository.PedidoRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteRepository clientes;
    private final PedidoRepository pedidos;
    public ClienteController(ClienteRepository clientes, PedidoRepository pedidos) {
        this.clientes = clientes; this.pedidos = pedidos;
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente) {
        cliente.setId(null);
        Cliente salvo = clientes.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public List<Cliente> listar() { return clientes.findAll(); }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable Long id) {
        return clientes.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente " + id + " não encontrado."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Cliente cliente = clientes.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente " + id + " não encontrado."));
        if (pedidos.existsByClienteId(id)) throw new IllegalArgumentException("Exclua os pedidos deste cliente antes de excluí-lo.");
        clientes.delete(cliente);
        return ResponseEntity.noContent().build();
    }
}
