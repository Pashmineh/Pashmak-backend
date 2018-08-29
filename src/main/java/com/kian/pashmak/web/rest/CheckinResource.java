package com.kian.pashmak.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kian.pashmak.domain.enumeration.PaymentType;
import com.kian.pashmak.security.SecurityUtils;
import com.kian.pashmak.service.CheckinService;
import com.kian.pashmak.service.DebtService;
import com.kian.pashmak.service.dto.DebtDTO;
import com.kian.pashmak.web.rest.errors.BadRequestAlertException;
import com.kian.pashmak.web.rest.util.HeaderUtil;
import com.kian.pashmak.web.rest.util.PaginationUtil;
import com.kian.pashmak.service.dto.CheckinDTO;
import com.kian.pashmak.web.rest.vm.CheckinType;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

/**
 * REST controller for managing Checkin.
 */
@RestController
@RequestMapping("/api")
public class CheckinResource {

    private final Logger log = LoggerFactory.getLogger(CheckinResource.class);

    private static final String ENTITY_NAME = "checkin";

    private final CheckinService checkinService;

    private final DebtService debtService;

    public CheckinResource(CheckinService checkinService, DebtService debtService) {
        this.checkinService = checkinService;
        this.debtService = debtService;
    }

    /**
     * POST  /checkins : Create a new checkin.
     *
     * @param checkinDTO the checkinDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new checkinDTO, or with status 400 (Bad Request) if the checkin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/checkins")
    @Timed
    public ResponseEntity<CheckinDTO> createCheckin(@RequestBody CheckinDTO checkinDTO) throws URISyntaxException {
        log.debug("REST request to save Checkin : {}", checkinDTO);
        if (checkinDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckinDTO result = checkinService.save(checkinDTO);
        return ResponseEntity.created(new URI("/api/checkins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/checkin")
    @Timed
    public ResponseEntity<CheckinDTO> checkIn(@RequestParam("checkinType") CheckinType checkinType) throws URISyntaxException, ParseException {
        CheckinDTO checkinDTO= new CheckinDTO();
        ZonedDateTime current = ZonedDateTime.now(TimeZone.getTimeZone("Asia/Tehran").toZoneId());

        checkinDTO.setCheckinTime(current);


        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Date ten = parser.parse("10:00");
        Date userTime;
        if(current.getMinute()<10)
         userTime = parser.parse(current.getHour()+":0"+current.getMinute());
        else
             userTime = parser.parse(current.getHour()+":"+current.getMinute());
        checkinDTO.setUserLogin(SecurityUtils.getCurrentUserLogin().get());
        CheckinDTO result = checkinService.save(checkinDTO);
        result.setCheckinType(checkinType);
        if (userTime.after(ten)) {
            DebtDTO debt= new DebtDTO();
            debt.setAmount(BigDecimal.valueOf(5000));
            debt.setPaymentTime(current);
            debt.setReason(PaymentType.TAKHIR);
            debt.setUserLogin(SecurityUtils.getCurrentUserLogin().get());
            debt.setUserId(checkinDTO.getUserId());
            debtService.save(debt);
        }

        return ResponseEntity.created(new URI("/api/checkins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /checkins : Updates an existing checkin.
     *
     * @param checkinDTO the checkinDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated checkinDTO,
     * or with status 400 (Bad Request) if the checkinDTO is not valid,
     * or with status 500 (Internal Server Error) if the checkinDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/checkins")
    @Timed
    public ResponseEntity<CheckinDTO> updateCheckin(@RequestBody CheckinDTO checkinDTO) throws URISyntaxException {
        log.debug("REST request to update Checkin : {}", checkinDTO);
        if (checkinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CheckinDTO result = checkinService.save(checkinDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, checkinDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /checkins : get all the checkins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of checkins in body
     */
    @GetMapping("/checkins")
    @Timed
    public ResponseEntity<List<CheckinDTO>> getAllCheckins(Pageable pageable) {
        log.debug("REST request to get a page of Checkins");
        Page<CheckinDTO> page = checkinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/checkins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /checkins/:id : get the "id" checkin.
     *
     * @param id the id of the checkinDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the checkinDTO, or with status 404 (Not Found)
     */
    @GetMapping("/checkins/{id}")
    @Timed
    public ResponseEntity<CheckinDTO> getCheckin(@PathVariable Long id) {
        log.debug("REST request to get Checkin : {}", id);
        Optional<CheckinDTO> checkinDTO = checkinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkinDTO);
    }

    /**
     * DELETE  /checkins/:id : delete the "id" checkin.
     *
     * @param id the id of the checkinDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/checkins/{id}")
    @Timed
    public ResponseEntity<Void> deleteCheckin(@PathVariable Long id) {
        log.debug("REST request to delete Checkin : {}", id);
        checkinService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
