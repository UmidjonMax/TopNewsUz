package dasturlash.uz.controller;

import dasturlash.uz.dto.RegionDTO;
import dasturlash.uz.entity.RegionEntity;
import dasturlash.uz.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @GetMapping("")
    public ResponseEntity<List<RegionDTO>> getAllRegions() {
        List<RegionDTO> dtoList = regionService.getAllRegions();
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("")
    public ResponseEntity<RegionDTO> createRegion(@RequestBody RegionDTO dto) {
        RegionDTO region = regionService.createRegion(dto);
        return ResponseEntity.ok(region);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRegion(@PathVariable("id") Integer id) {
        boolean result = regionService.deleteRegion(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionDTO> update(@PathVariable("id") Integer id, @RequestBody RegionDTO dto) {
        RegionDTO updated = regionService.updateRegion(id, dto);
        return ResponseEntity.ok(updated);
    }
}
