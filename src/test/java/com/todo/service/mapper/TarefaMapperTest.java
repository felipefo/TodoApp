package com.todo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TarefaMapperTest {

    private TarefaMapper tarefaMapper;

    @BeforeEach
    public void setUp() {
        tarefaMapper = new TarefaMapperImpl();
    }
}
