package com.todo.service;

import com.todo.domain.Tarefa;
import com.todo.repository.TarefaRepository;
import com.todo.service.dto.TarefaDTO;
import com.todo.service.mapper.TarefaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tarefa}.
 */
@Service
@Transactional
public class TarefaService {

    private final Logger log = LoggerFactory.getLogger(TarefaService.class);

    private final TarefaRepository tarefaRepository;

    private final TarefaMapper tarefaMapper;

    public TarefaService(TarefaRepository tarefaRepository, TarefaMapper tarefaMapper) {
        this.tarefaRepository = tarefaRepository;
        this.tarefaMapper = tarefaMapper;
    }

    /**
     * Save a tarefa.
     *
     * @param tarefaDTO the entity to save.
     * @return the persisted entity.
     */
    public TarefaDTO save(TarefaDTO tarefaDTO) {
        log.debug("Request to save Tarefa : {}", tarefaDTO);
        Tarefa tarefa = tarefaMapper.toEntity(tarefaDTO);
        tarefa = tarefaRepository.save(tarefa);
        return tarefaMapper.toDto(tarefa);
    }

    /**
     * Partially update a tarefa.
     *
     * @param tarefaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TarefaDTO> partialUpdate(TarefaDTO tarefaDTO) {
        log.debug("Request to partially update Tarefa : {}", tarefaDTO);

        return tarefaRepository
            .findById(tarefaDTO.getId())
            .map(
                existingTarefa -> {
                    tarefaMapper.partialUpdate(existingTarefa, tarefaDTO);
                    return existingTarefa;
                }
            )
            .map(tarefaRepository::save)
            .map(tarefaMapper::toDto);
    }

    /**
     * Get all the tarefas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TarefaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tarefas");
        return tarefaRepository.findAll(pageable).map(tarefaMapper::toDto);
    }

    /**
     * Get one tarefa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TarefaDTO> findOne(Long id) {
        log.debug("Request to get Tarefa : {}", id);
        return tarefaRepository.findById(id).map(tarefaMapper::toDto);
    }

    /**
     * Delete the tarefa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tarefa : {}", id);
        tarefaRepository.deleteById(id);
    }
}
