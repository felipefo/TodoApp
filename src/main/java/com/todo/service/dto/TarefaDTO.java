package com.todo.service.dto;

import com.todo.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.todo.domain.Tarefa} entity.
 */
public class TarefaDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    private Instant dueDate;

    private Instant dateCriacao;

    @NotNull
    private Status status;

    private UserDTO user;

    private CategoriaDTO categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getDateCriacao() {
        return dateCriacao;
    }

    public void setDateCriacao(Instant dateCriacao) {
        this.dateCriacao = dateCriacao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarefaDTO)) {
            return false;
        }

        TarefaDTO tarefaDTO = (TarefaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tarefaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarefaDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", dateCriacao='" + getDateCriacao() + "'" +
            ", status='" + getStatus() + "'" +
            ", user=" + getUser() +
            ", categoria=" + getCategoria() +
            "}";
    }
}
