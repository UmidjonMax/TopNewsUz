package dasturlash.uz.controller;

import dasturlash.uz.dto.ArticleDTO;
import dasturlash.uz.dto.ArticleShortInfoDTO;
import dasturlash.uz.enums.ArticleStatusEnum;
import dasturlash.uz.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<ArticleShortInfoDTO> create(@Valid @RequestBody ArticleDTO articleDTO) {
        return ResponseEntity.ok(articleService.create(articleDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(articleService.delete(id));
    }
    @PutMapping("{id}")
    public ResponseEntity<ArticleShortInfoDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody ArticleDTO articleDTO) {
        return ResponseEntity.ok(articleService.update(id,articleDTO));
    }
    @PutMapping("/status")
    public ResponseEntity<String> changeStatus(@PathVariable("id") UUID id, @PathVariable("status") ArticleStatusEnum status) {
        return ResponseEntity.ok(articleService.changeStatus(id,status));
    }
}
