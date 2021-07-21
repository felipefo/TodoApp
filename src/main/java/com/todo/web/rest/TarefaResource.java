package com.todo.web.rest;

import com.todo.domain.Tarefa;
import com.todo.repository.TarefaRepository;
import com.todo.service.TarefaQueryService;
import com.todo.service.TarefaService;
import com.todo.service.criteria.TarefaCriteria;
import com.todo.service.criteria.useridvalidation.AddIddTarefaStrategy;
import com.todo.service.criteria.useridvalidation.AddUserId;
import com.todo.service.dto.TarefaDTO;
import com.todo.service.dto.UserDTO;
import com.todo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.todo.domain.Tarefa}.
 */
@RestController
@RequestMapping("/api")
public class TarefaResource {

    private final Logger log = LoggerFactory.getLogger(TarefaResource.class);

    private static final String ENTITY_NAME = "tarefa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarefaService tarefaService;

    private final TarefaRepository tarefaRepository;

    private final TarefaQueryService tarefaQueryService;
    
    private final AddUserId addUserId;


    public TarefaResource(TarefaService tarefaService, TarefaRepository tarefaRepository, TarefaQueryService tarefaQueryService, AddUserId addUserId) {
        this.tarefaService = tarefaService;
        this.tarefaRepository = tarefaRepository;
        this.tarefaQueryService = tarefaQueryService;
        this.addUserId =  addUserId;
    }

    /**
     * {@code POST  /tarefas} : Create a new tarefa.
     *
     * @param tarefaDTO the tarefaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarefaDTO, or with status {@code 400 (Bad Request)} if the tarefa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarefas")
    public ResponseEntity<TarefaDTO> createTarefa(@Valid @RequestBody TarefaDTO tarefaDTO) throws URISyntaxException {
        log.debug("REST request to save Tarefa : {}", tarefaDTO);
        if (tarefaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tarefa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            addUserId.setDTOUserId(new AddIddTarefaStrategy(tarefaDTO , TarefaDTO.class));
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TarefaResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        TarefaDTO result = tarefaService.save(tarefaDTO);
        return ResponseEntity
            .created(new URI("/api/tarefas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarefas/:id} : Updates an existing tarefa.
     *
     * @param id the id of the tarefaDTO to save.
     * @param tarefaDTO the tarefaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaDTO,
     * or with status {@code 400 (Bad Request)} if the tarefaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarefaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarefas/{id}")
    public ResponseEntity<TarefaDTO> updateTarefa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TarefaDTO tarefaDTO
    ) throws URISyntaxException, Exception {
        log.debug("REST request to update Tarefa : {}, {}", id, tarefaDTO);
        if (tarefaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id null");
        }
        if (tarefaDTO.getUser()== null) {
            throw new BadRequestAlertException("Invalid user", ENTITY_NAME, "usuario nao pode ser nulo");
        }
        if (!Objects.equals(id, tarefaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
        }

        if (!tarefaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }
        
        if (!addUserId.checkOwnerDTOId(tarefaDTO.getUser().getId())){
            throw new BadRequestAlertException("Operation not allowed", ENTITY_NAME, "operacao nao permitida"); 
        }

        TarefaDTO result = tarefaService.save(tarefaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tarefas/:id} : Partial updates given fields of an existing tarefa, field will ignore if it is null
     *
     * @param id the id of the tarefaDTO to save.
     * @param tarefaDTO the tarefaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaDTO,
     * or with status {@code 400 (Bad Request)} if the tarefaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tarefaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tarefaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tarefas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TarefaDTO> partialUpdateTarefa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TarefaDTO tarefaDTO
    ) throws URISyntaxException, Exception {
        log.debug("REST request to partial update Tarefa partially : {}, {}", id, tarefaDTO);
        if (tarefaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        
        if (!addUserId.checkOwnerDTOId(tarefaDTO.getUser().getId())){
            throw new BadRequestAlertException("Operation not allowed", ENTITY_NAME, "operacao nao permitida"); 
        }

        Optional<TarefaDTO> result = tarefaService.partialUpdate(tarefaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tarefas} : get all the tarefas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarefas in body.
     */
    @GetMapping("/tarefas")
    public ResponseEntity<List<TarefaDTO>> getAllTarefas(TarefaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Tarefas by criteria: {}", criteria);
        
         try {
            addUserId.setUserOwnerIDFilter(criteria, TarefaCriteria.class);
        } catch (Exception ex) {
           // java.util.logging.Logger.getLogger(TarefaResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Page<TarefaDTO> page = tarefaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tarefas/count} : count all the tarefas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tarefas/count")
    public ResponseEntity<Long> countTarefas(TarefaCriteria criteria) {
        log.debug("REST request to count Tarefas by criteria: {}", criteria);
        
         try {
            addUserId.setUserOwnerIDFilter(criteria, TarefaCriteria.class);
        } catch (Exception ex) {
           // java.util.logging.Logger.getLogger(TarefaResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ResponseEntity.ok().body(tarefaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tarefas/:id} : get the "id" tarefa.
     *
     * @param id the id of the tarefaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarefaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarefas/{id}")
    public ResponseEntity<TarefaDTO> getTarefa(@PathVariable Long id) throws Exception {
        log.debug("REST request to get Tarefa : {}", id);
        
        Optional<TarefaDTO> tarefaDTO = tarefaService.findOne(id);
        UserDTO user = tarefaDTO.get().getUser();
        Long idUser = null;
        if(user != null){
          idUser = user.getId();
        }
         if (!addUserId.checkOwnerDTOId(idUser)){
            throw new BadRequestAlertException("Operation not allowed", ENTITY_NAME, "operacao nao permitida"); 
        }
        
        
        return ResponseUtil.wrapOrNotFound(tarefaDTO);
    }

    /**
     * {@code DELETE  /tarefas/:id} : delete the "id" tarefa.
     *
     * @param id the id of the tarefaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<Void> deleteTarefa(@PathVariable Long id) throws Exception {
        log.debug("REST request to delete Tarefa : {}", id);
        
       
        Tarefa tarefa  = tarefaRepository.getOne(id);
        if(!addUserId.checkOwnerDTOId(tarefa.getUser().getId())){
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }
       
        tarefaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
