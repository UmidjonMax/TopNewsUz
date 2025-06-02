package dasturlash.uz.controller;

import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.dto.profile.ProfileUpdateDTO;
import dasturlash.uz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<ProfileDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody ProfileUpdateDTO dto) {
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProfileDTO>> getPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "2") int size) {
        return ResponseEntity.ok(profileService.getAll(page-1, size));
    }
}
