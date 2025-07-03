package dasturlash.uz.controller;

import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.service.RegionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @GetMapping("/admin")
    public ResponseEntity<List<RegionDTO>> getAllRegions() {
        return ResponseEntity.ok(regionService.getAllRegions());
    }

    @PostMapping("/admin")
    public ResponseEntity<RegionDTO> createRegion(@Valid @RequestBody RegionDTO dto) {
        return ResponseEntity.ok(regionService.create(dto));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Boolean> deleteRegion(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(regionService.delete(id));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<RegionDTO> update(@PathVariable("id") Integer id, @RequestBody RegionDTO dto) {
        return ResponseEntity.ok(regionService.update(id, dto));
    }

    @GetMapping("/lang")
    public ResponseEntity<List<RegionDTO>> getAllRegionsByLanguage(@RequestHeader(name = "Accept-Language", defaultValue = "UZ") AppLanguageEnum language) {
        return ResponseEntity.ok(regionService.getAllByLang(language));
    }
}
