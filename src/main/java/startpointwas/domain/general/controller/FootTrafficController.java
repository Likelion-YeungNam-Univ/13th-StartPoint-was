package startpointwas.domain.general.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import startpointwas.domain.general.dto.FootTrafficDto;
import startpointwas.domain.general.service.FootTrafficService;

@RestController
@RequestMapping("/api/general/foot-traffic")
public class FootTrafficController {

    private final FootTrafficService service;

    public FootTrafficController(FootTrafficService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<FootTrafficDto> get(
            @RequestParam String analyNo,
            @RequestParam String admiCd,
            @RequestParam String upjongCd
    ) {
        return ResponseEntity.ok(service.fetchFootTrafficInfo(analyNo, admiCd, upjongCd));
    }

}
