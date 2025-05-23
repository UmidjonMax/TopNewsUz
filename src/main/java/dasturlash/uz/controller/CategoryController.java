package dasturlash.uz.controller;

import dasturlash.uz.dto.CategoryDTO;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable("id") Integer id, @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<CategoryDTO>> getAllByLang(@RequestHeader(name = "Accept-Language", defaultValue = "UZ")AppLanguageEnum language) {
        return ResponseEntity.ok(categoryService.getAllByLang(language));
    }

}
