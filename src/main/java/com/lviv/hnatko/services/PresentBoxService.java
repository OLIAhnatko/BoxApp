package com.lviv.hnatko.services;

import com.lviv.hnatko.entity.PresentBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lviv.hnatko.exception.ResourceNotFoundException;
import com.lviv.hnatko.repository.AppUserRepository;
import com.lviv.hnatko.repository.PresentBoxRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class PresentBoxService {

    private final PresentBoxRepository presentBoxRepository;
    private final AppUserRepository appUserRepository;

    public List<PresentBox> getAllPresentBox() {
        return presentBoxRepository.findAll();
    }

    @Transactional
    public PresentBox createPresentBox(PresentBox presentBox) {
        return presentBoxRepository.save(presentBox);
    }

    public PresentBox getPresentBox(Integer presentBoxId) {
        return presentBoxRepository.findById(presentBoxId)
                .orElseThrow(ResourceNotFoundException.presentBoxSupplier(presentBoxId));
    }

    public boolean deletePresentBox(Integer presentBoxId) {
        if (presentBoxRepository.existsById(presentBoxId)) {
            presentBoxRepository.deleteById(presentBoxId);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public PresentBox updatePresentBox(Integer id, PresentBox updatedPresentBox) {
        return presentBoxRepository.findById(id)
                .map(presentBox -> updatePresentBoxFields(presentBox, updatedPresentBox))
                .map(presentBoxRepository::saveAndFlush)
                .orElseThrow(ResourceNotFoundException.presentBoxSupplier(id));
    }

    private PresentBox updatePresentBoxFields(PresentBox presentBox, PresentBox updatedPresentBox) {
        presentBox.setDescription(Objects.requireNonNullElse(updatedPresentBox.getDescription(),presentBox.getDescription()));
        presentBox.setPriceInUah(Objects.requireNonNullElse(updatedPresentBox.getPriceInUah(), presentBox.getPriceInUah()));
        presentBox.setPhotoUrl(Objects.requireNonNullElse(updatedPresentBox.getPhotoUrl(), presentBox.getPhotoUrl()));
        presentBox.setIsAvailable(Objects.requireNonNullElse(updatedPresentBox.getIsAvailable(), presentBox.getIsAvailable()));
        presentBox.setName(Objects.requireNonNullElse(updatedPresentBox.getName(), presentBox.getName()));
        return presentBox;
    }

}
