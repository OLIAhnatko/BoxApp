package com.lviv.hnatko.services;

import com.lviv.hnatko.entity.AppUser;
import com.lviv.hnatko.entity.enumeration.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lviv.hnatko.exception.ForbiddenToDoException;
import com.lviv.hnatko.exception.ResourceNotFoundException;
import com.lviv.hnatko.repository.AppUserRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class UserService {

    private final AppUserRepository appUserRepository;

    public List<AppUser> getAllUser(){
        return appUserRepository.findAll();
    }

    public List<AppUser> getAllBuyer(){
        return appUserRepository.findByRole(Role.ROLE_BUYER);
    }

    public List<AppUser> getAllAdmin(){
        return appUserRepository.findByRole(Role.ROLE_ADMIN);
    }
    public AppUser getUserInfo(Integer userId){
        return appUserRepository.findById(userId)
                .orElseThrow(ResourceNotFoundException.userSupplier(userId));
    }

    @Transactional
    public AppUser updateUser(Integer userId, AppUser updateAppUser) {
        return appUserRepository.findById(userId)
                .map(appUser -> updateUserFields(appUser, updateAppUser))
                .map(appUserRepository::saveAndFlush)
                .orElseThrow(ResourceNotFoundException.userSupplier(userId));
    }
    @Transactional
    public AppUser updateUserRole(Integer userId, String role) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(ResourceNotFoundException.userSupplier(userId));
        if(role.equals(Role.ROLE_ADMIN.toString())){
            user.setRole(Role.ROLE_ADMIN);
        }
        if(role.equals(Role.ROLE_BUYER.toString())){
            user.setRole(Role.ROLE_BUYER);
        }
        appUserRepository.saveAndFlush(user);
        return user;
    }

    public boolean deleteAccount(Integer authId, Integer userId) {
        if(!authId.equals(userId)){
            throw new ForbiddenToDoException();
        }
        if (appUserRepository.existsById(userId) ) {
            appUserRepository.deleteById(userId);
            return true;
        } else {
            return false;
        }
    }

    private AppUser updateUserFields(AppUser appUser, AppUser updateAppUser) {
        appUser.setEmail(Objects.requireNonNullElse(updateAppUser.getEmail(),appUser.getEmail()));
        appUser.setPhoneNumber(Objects.requireNonNullElse(updateAppUser.getPhoneNumber(), appUser.getPhoneNumber()));
        appUser.setFirstName(Objects.requireNonNullElse(updateAppUser.getFirstName(), appUser.getFirstName()));
        appUser.setLastName(Objects.requireNonNullElse(updateAppUser.getLastName(), appUser.getLastName()));
        appUser.setId(appUser.getId());
        return appUser;
    }
    public boolean checkIfAdmin(Integer id) {
        AppUser userById = appUserRepository.findById(id).orElseThrow(ResourceNotFoundException.userSupplier(id));
        boolean isAdmin = userById.getRole().equals(Role.ROLE_ADMIN);
        if (isAdmin) {
            return true;
        } else {
            throw new ForbiddenToDoException();
        }
    }

    public boolean checkIfBuyer(Integer id) {
        AppUser userById = appUserRepository.findById(id).orElseThrow(ResourceNotFoundException.userSupplier(id));
        boolean isBuyer = userById.getRole().equals(Role.ROLE_BUYER);
        if (isBuyer) {
            return true;
        } else {
            throw new ForbiddenToDoException();
        }
    }
}
