package dasturlash.uz.service;

import dasturlash.uz.dto.FilterResultDTO;
import dasturlash.uz.dto.profile.ProfileDTO;
import dasturlash.uz.dto.profile.ProfileFilterDTO;
import dasturlash.uz.dto.profile.ProfileUpdateDTO;
import dasturlash.uz.entity.ProfileEntity;
import dasturlash.uz.enums.ProfileStatusEnum;
import dasturlash.uz.exceptions.AppBadException;
import dasturlash.uz.repository.ProfileCustomRepository;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleService profileRoleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ProfileCustomRepository profileCustomRepository;

    public ProfileDTO create(ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if (optional.isPresent()) {
            throw new AppBadException("User already exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setStatus(ProfileStatusEnum.ACTIVE);
        entity.setVisible(Boolean.TRUE);
        profileRepository.save(entity);
        profileRoleService.merge(entity.getId(), dto.getRoleList());
        ProfileDTO response = toDTO(entity);
        response.setRoleList(dto.getRoleList());
        return response;
    }

    public ProfileDTO update(Integer id, ProfileUpdateDTO dto) {
        ProfileEntity entity = get(id);
        // check username exists
        Optional<ProfileEntity> usernameOptional = profileRepository.findByUsernameAndVisibleIsTrue(dto.getUsername());
        if (usernameOptional.isPresent() && !usernameOptional.get().getId().equals(id)) {
            throw new AppBadException("Username exists");
        }
        //
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        profileRepository.save(entity); // update
        // role_save
        profileRoleService.merge(entity.getId(), dto.getRoleList());
        // result
        ProfileDTO response = toDTO(entity);
        response.setRoleList(dto.getRoleList());
        return response;
    }

    public Boolean delete(Integer id) {
        return profileRepository.updateVisible(id) == 1;
    }

    public PageImpl<ProfileDTO> getAll(int size, int page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProfileEntity> entities = profileRepository.findAll(pageRequest);

        List<ProfileEntity> entityList = entities.getContent();
        long total = entities.getTotalElements();

        List<ProfileDTO> response = new LinkedList<>();
        entityList.forEach(entity -> response.add(toDTO(entity)));

        return new PageImpl<ProfileDTO>(response, pageRequest, total);
    }

    public PageImpl<ProfileDTO> filter(ProfileFilterDTO filterDTO, int page, int size) {
        FilterResultDTO<ProfileEntity> result = profileCustomRepository.filter(filterDTO, page, size);
        List<ProfileDTO> profileDTOList = new LinkedList<>();
        result.getContent().forEach(entity -> profileDTOList.add(toDTO(entity)));
        return new PageImpl<>(profileDTOList, PageRequest.of(page, size), result.getTotal());
    }

    public ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setUsername(entity.getUsername());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }



    public ProfileEntity get(Integer id) {
        return profileRepository.findByIdAndVisibleIsTrue(id).orElseThrow(() -> new AppBadException("Profile not found"));
    }
}