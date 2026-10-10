package br.com.baozistore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Column(nullable = false, length = 120)
    private String nome;

    @NotNull(message = "O preço é obrigatório.")
    @PositiveOrZero(message = "O preço não pode ser negativo.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @NotNull(message = "Informe se há estoque.")
    @Column(nullable = false)
    private Boolean estoque;

    public Produto() {}
    public Produto(String nome, BigDecimal preco, Boolean estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }
    public Boolean getEstoque() { return estoque; }
    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public void setEstoque(Boolean estoque) { this.estoque = estoque; }
}
