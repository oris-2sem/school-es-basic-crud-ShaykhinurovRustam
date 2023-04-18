package ru.itis.ediary.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.ediary.dto.parent.NewParentDto;
import ru.itis.ediary.dto.parent.ParentDto;
import ru.itis.ediary.dto.parent.ParentPage;
import ru.itis.ediary.models.Parent;
import ru.itis.ediary.repositories.ParentRepository;
import ru.itis.ediary.services.ParentService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static ru.itis.ediary.dto.parent.ParentDto.from;

@RequiredArgsConstructor
@Service
public class ParentServiceImpl implements ParentService {
    private final ParentRepository parentRepository;

    @Override
    public ParentPage getAllParents() {
        List<Parent> parent = parentRepository.findAll();
        return ParentPage.builder()
                .parentList(from(parent))
                .build();
    }

    @Override
    public ParentDto createParent(NewParentDto newParent) {
        Parent parent = Parent.builder()
                .children(newParent.getChildren())
                .name(newParent.getName())
                .build();

        parentRepository.save(parent);
        return from(parent);
    }

    @Override
    public ParentDto findById(UUID id) {
        Optional<Parent> parent = parentRepository.findById(id);
        return parent.map(ParentDto::from).orElse(null);
    }

    @Override
    public void deleteById(UUID id) {
        parentRepository.deleteById(id);
    }
}
