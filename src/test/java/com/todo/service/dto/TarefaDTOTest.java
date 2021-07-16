package com.todo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.todo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TarefaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarefaDTO.class);
        TarefaDTO tarefaDTO1 = new TarefaDTO();
        tarefaDTO1.setId(1L);
        TarefaDTO tarefaDTO2 = new TarefaDTO();
        assertThat(tarefaDTO1).isNotEqualTo(tarefaDTO2);
        tarefaDTO2.setId(tarefaDTO1.getId());
        assertThat(tarefaDTO1).isEqualTo(tarefaDTO2);
        tarefaDTO2.setId(2L);
        assertThat(tarefaDTO1).isNotEqualTo(tarefaDTO2);
        tarefaDTO1.setId(null);
        assertThat(tarefaDTO1).isNotEqualTo(tarefaDTO2);
    }
}
