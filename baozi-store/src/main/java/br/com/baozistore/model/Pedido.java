package br.com.baozistore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O clienteId é obrigatório.")
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @NotNull(message = "O produtoId é obrigatório.")
    @Column(name = "produto_id", nullable = false)
    private Long produtoId;

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    @Column(nullable = false)
    private Integer quantidade;

    public Pedido() {}
    public Pedido(Long clienteId, Long produtoId, Integer quantidade) {
        this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }
    public Long getId() { return id; }
    public Long getClienteId() { return clienteId; }
    public Long getProdutoId() { return produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setId(Long id) { this.id = id; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}
