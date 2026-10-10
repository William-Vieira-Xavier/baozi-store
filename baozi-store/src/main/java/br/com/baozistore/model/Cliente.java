package br.com.baozistore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Column(nullable = false, length = 120)
    private String nome;

    @NotNull(message = "A data de cadastro é obrigatória.")
    @Column(name = "cliente_desde", nullable = false)
    private LocalDate clienteDesde;

    public Cliente() {}
    public Cliente(String nome, LocalDate clienteDesde) {
        this.nome = nome;
        this.clienteDesde = clienteDesde;
    }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public LocalDate getClienteDesde() { return clienteDesde; }
    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setClienteDesde(LocalDate clienteDesde) { this.clienteDesde = clienteDesde; }
}
