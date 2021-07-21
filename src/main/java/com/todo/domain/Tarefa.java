package com.todo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todo.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Tarefa.
 */
@Entity
@Table(name = "tarefa")
public class Tarefa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "date_criacao")
    private Instant dateCriacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tarefas", "user" }, allowSetters = true)
    private Categoria categoria;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tarefa id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Tarefa descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDueDate() {
        return this.dueDate;
    }

    public Tarefa dueDate(Instant dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getDateCriacao() {
        return this.dateCriacao;
    }

    public Tarefa dateCriacao(Instant dateCriacao) {
        this.dateCriacao = dateCriacao;
        return this;
    }

    public void setDateCriacao(Instant dateCriacao) {
        this.dateCriacao = dateCriacao;
    }

    public Status getStatus() {
        return this.status;
    }

    public Tarefa status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return this.user;
    }

    public Tarefa user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public Tarefa categoria(Categoria categoria) {
        this.setCategoria(categoria);
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tarefa)) {
            return false;
        }
        return id != null && id.equals(((Tarefa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tarefa{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", dateCriacao='" + getDateCriacao() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
