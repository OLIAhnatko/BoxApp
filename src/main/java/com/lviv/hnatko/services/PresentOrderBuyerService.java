package com.lviv.hnatko.services;

import com.lviv.hnatko.entity.AppUser;
import com.lviv.hnatko.entity.PresentBox;
import com.lviv.hnatko.entity.PresentOrder;
import com.lviv.hnatko.entity.enumeration.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lviv.hnatko.exception.ResourceNotFoundException;
import com.lviv.hnatko.repository.AppUserRepository;
import com.lviv.hnatko.repository.PresentBoxRepository;
import com.lviv.hnatko.repository.PresentOrderRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class PresentOrderBuyerService {

    private final AppUserRepository appUserRepository;
    private final PresentBoxRepository presentBoxRepository;
    private final PresentOrderRepository presentOrderRepository;

    @Transactional
    public PresentOrder createPresentOrder(Integer userId, Integer presentBoxId) {
        AppUser user = appUserRepository.findById(userId).orElseThrow(ResourceNotFoundException.userSupplier(userId));
        PresentBox byId = presentBoxRepository.findById(presentBoxId).orElseThrow(ResourceNotFoundException.presentBoxSupplier(presentBoxId));
        PresentOrder presentOrder = new PresentOrder(byId.getName(), byId.getPhotoUrl(), byId.getDescription(), byId.getIsAvailable(), byId.getPriceInUah(), user);
        presentOrderRepository.save(presentOrder);
        return presentOrder;
    }

    public List<PresentOrder> getAllPresentOrder(Integer userId) {
        AppUser user = appUserRepository.findById(userId).orElseThrow(ResourceNotFoundException.userSupplier(userId));
        return presentOrderRepository.findByBuyer(user);
    }

    public PresentOrder getPresentOrder(Integer userId, Integer presentOrderId) {
        AppUser user = appUserRepository.findById(userId).orElseThrow(ResourceNotFoundException.userSupplier(userId));
        return presentOrderRepository.findByIdAndBuyer(presentOrderId, user).orElseThrow(ResourceNotFoundException.presentOrderSupplier(userId));
    }

    public boolean deletePresentOrder(Integer userId, Integer presentBoxId) {
        AppUser user = appUserRepository.findById(userId).orElseThrow(ResourceNotFoundException.userSupplier(userId));
        PresentOrder presentOrder = presentOrderRepository.findById(presentBoxId).orElseThrow(ResourceNotFoundException.presentOrderSupplier(userId));
        if (presentOrder.getBuyer().equals(user)) {
            presentOrderRepository.deleteById(presentBoxId);
            return true;
        } else {
            return false;
        }
    }
    public boolean checkIfBuyer(Integer id) {
        AppUser userById = appUserRepository.findById(id).orElseThrow(ResourceNotFoundException.userSupplier(id));
        return userById.getRole().equals(Role.ROLE_BUYER);
    }

}
