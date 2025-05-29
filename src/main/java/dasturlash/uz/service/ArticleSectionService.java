package dasturlash.uz.service;

import dasturlash.uz.dto.SectionDTO;
import dasturlash.uz.entity.ArticleSectionEntity;
import dasturlash.uz.repository.ArticleSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleSectionService {
    @Autowired
    private ArticleSectionRepository articleSectionRepository;

    public void create(UUID articleId, List<SectionDTO> sectionList) {
        for (SectionDTO sectionDTO : sectionList) {
            ArticleSectionEntity entity = new ArticleSectionEntity();
            entity.setArticleId(articleId);
            entity.setSectionId(sectionDTO.getId());
            articleSectionRepository.save(entity);
        }
    }

    public void merge(UUID articleId, List<SectionDTO> newList) {
        List<SectionDTO> oldList = articleSectionRepository.findSectionsByArticleId(articleId);
        newList.stream().filter(n -> !oldList.contains(n)).forEach(sd -> create(articleId, sd));
        oldList.stream().filter(old -> !newList.contains(old)).forEach(sd -> articleSectionRepository.deleteByArticleIdAndSectionId(articleId, sd.getId()));
    }

    public void create(UUID articleId, SectionDTO sectionDTO) {
        ArticleSectionEntity entity = new ArticleSectionEntity();
        entity.setArticleId(articleId);
        entity.setSectionId(sectionDTO.getId());
        articleSectionRepository.save(entity);
    }
}
