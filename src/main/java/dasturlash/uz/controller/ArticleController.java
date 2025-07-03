package dasturlash.uz.controller;

import dasturlash.uz.dto.SectionListDTO;
import dasturlash.uz.dto.article.*;
import dasturlash.uz.enums.AppLanguageEnum;
import dasturlash.uz.enums.ArticleStatusEnum;
import dasturlash.uz.service.ArticleService;
import dasturlash.uz.util.PageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/moderator")
    public ResponseEntity<ArticleDTO> create(@Valid @RequestBody ArticleCreateDTO articleDTO) {
        return ResponseEntity.ok(articleService.createArticle(articleDTO));
    }
    @DeleteMapping("/moderator/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return ResponseEntity.ok(articleService.delete(id));
    }
    @PutMapping("/moderator/{id}")
    public ResponseEntity<ArticleDTO> update(@PathVariable("id") String id, @Valid @RequestBody ArticleCreateDTO articleDTO) {
        return ResponseEntity.ok(articleService.update(id,articleDTO));
    }
    @PutMapping("/moderator/{articleId}/status")
    public ResponseEntity<String> changeStatus(@PathVariable("articleId") String articleId,
                                               @RequestBody @Valid ArticleChangeStatusDTO statusDTO) {
        return ResponseEntity.ok(articleService.changeStatus(articleId, statusDTO.getStatus()));
    }

    @GetMapping("/last-n-by-section/{sectionId}")
    public ResponseEntity<List<ArticleDTO>> getLastNBySection(@RequestParam(value = "sectionId") Integer sectionId,
                                                                  @RequestParam(value = "quantity", defaultValue = "5") Integer n) {
        return ResponseEntity.ok(articleService.getLastNArticlesBySection(n, sectionId));
    }

    @GetMapping("/by-category/{categoryId}/{limit}")
    public ResponseEntity<List<ArticleDTO>> lastNArticleByCategoryId(@PathVariable("categoryId") Integer categoryId,
                                                                     @PathVariable("limit") Integer limit) {
        return ResponseEntity.ok(articleService.getLastNArticlesByCategory(categoryId, limit));
    }

    @GetMapping("/last-twelwe")
    public ResponseEntity<List<ArticleDTO>> getLastTwelwe(@RequestBody List<String> list){
        return ResponseEntity.ok(articleService.getLast12Articles(list));
    }

    @GetMapping("/by-region/{regionId}/{limit}")
    public ResponseEntity<List<ArticleDTO>> lastNArticleByRegionId(@PathVariable("regionId") Integer regionId,
                                                                   @PathVariable("limit") Integer limit) {
        return ResponseEntity.ok(articleService.getLastNArticlesByRegion(regionId, limit));
    }

    //  9. Get Article By Id And Lang
    @GetMapping("/detail/{articleId}")
    public ResponseEntity<ArticleDTO> getDetail(@PathVariable("articleId") String articleId,
                                                @RequestHeader(name = "Accept-Language", defaultValue = "uz") AppLanguageEnum language) {
        return ResponseEntity.ok(articleService.getById(articleId, language));
    }

    // 11. Get Last 4 Article By sectionId, except given article id.
    @GetMapping("/section-small/{sectionId}")
    public ResponseEntity<List<ArticleDTO>> last4ArticleBySectionId(@PathVariable("sectionId") Integer sectionId,
                                                                    @RequestParam(value = "limit", defaultValue = "4") int limit) {
        return ResponseEntity.ok(articleService.getLastNArticlesBySection(4, sectionId));
    }

    //   12. Get 4 most read articles, except given article id . (Ko'p oqilgan oxirgi 4-ta yangilikni return qiladi, berilgan maqoldagan tashqari)
    @GetMapping("/most-read/{exceptArticleId}")
    public ResponseEntity<List<ArticleDTO>> mostReadArticles(@PathVariable("exceptArticleId") String sectionId) {
        return ResponseEntity.ok(articleService.getMostReadArticles(sectionId));
    }

    // 13. Increase Article View Count by Article Id
    @GetMapping("/view-count/{articleId}")
    public ResponseEntity<Boolean> increaseViewCount(@PathVariable("articleId") String articleId) {
        return ResponseEntity.ok(articleService.increaseViewCount(articleId));
    }

    // 14. Increase Share Count by Article Id
    @GetMapping("/shared-count/{articleId}")
    public ResponseEntity<Integer> increaseSharedCount(@PathVariable("articleId") String articleId) {
        return ResponseEntity.ok(articleService.increaseSharedCount(articleId));
    }

    //  15. Filter Article
    @PostMapping("/filter")
    public ResponseEntity<Page<ArticleDTO>> filter(@RequestBody ArticleFilterDTO filter,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(articleService.filter(filter, PageUtil.page(page), size));
    }

}