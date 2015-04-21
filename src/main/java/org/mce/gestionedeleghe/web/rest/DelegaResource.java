package org.mce.gestionedeleghe.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mce.gestionedeleghe.domain.Delega;
import org.mce.gestionedeleghe.repository.DelegaRepository;
import org.mce.gestionedeleghe.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Delega.
 */
@RestController
@RequestMapping("/api")
public class DelegaResource {

    private final Logger log = LoggerFactory.getLogger(DelegaResource.class);

    @Inject
    private DelegaRepository delegaRepository;

    /**
     * POST  /delegas -> Create a new delega.
     */
    @RequestMapping(value = "/delegas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Delega delega) throws URISyntaxException {
        log.debug("REST request to save Delega : {}", delega);
        if (delega.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new delega cannot already have an ID").build();
        }
        delegaRepository.save(delega);
        return ResponseEntity.created(new URI("/api/delegas/" + delega.getId())).build();
    }

    /**
     * PUT  /delegas -> Updates an existing delega.
     */
    @RequestMapping(value = "/delegas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Delega delega) throws URISyntaxException {
        log.debug("REST request to update Delega : {}", delega);
        if (delega.getId() == null) {
            return create(delega);
        }
        delegaRepository.save(delega);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /delegas -> get all the delegas.
     */
    @RequestMapping(value = "/delegas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Delega>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Delega> page = delegaRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/delegas", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /delegas/:id -> get the "id" delega.
     */
    @RequestMapping(value = "/delegas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Delega> get(@PathVariable String id) {
        log.debug("REST request to get Delega : {}", id);
        return Optional.ofNullable(delegaRepository.findOne(id))
            .map(delega -> new ResponseEntity<>(
                delega,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /delegas/:id -> delete the "id" delega.
     */
    @RequestMapping(value = "/delegas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Delega : {}", id);
        delegaRepository.delete(id);
    }
}
