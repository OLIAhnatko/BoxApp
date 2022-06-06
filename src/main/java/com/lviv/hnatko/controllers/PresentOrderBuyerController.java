package com.lviv.hnatko.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.lviv.hnatko.dto.PresentOrderDto;
import com.lviv.hnatko.entity.PresentOrder;
import com.lviv.hnatko.mapper.PresentOrderMapper;
import com.lviv.hnatko.security.UserPrincipal;
import com.lviv.hnatko.services.PresentOrderBuyerService;
import com.lviv.hnatko.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/present/order/buyer")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PresentOrderBuyerController {

    private final PresentOrderBuyerService presentOrderBuyerService;
    private final UserService userService;
    private final PresentOrderMapper presentOrderMapper;

    @PostMapping
    public ResponseEntity<PresentOrderDto> createPresentBox(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                            @RequestParam Integer presentBoxId) {
        userService.checkIfBuyer(userPrincipal.getId());
        PresentOrder presentOrder = presentOrderBuyerService.createPresentOrder(userPrincipal.getId(), presentBoxId);
        PresentOrderDto boxDto = presentOrderMapper.toDto(presentOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(boxDto);
    }

    @GetMapping
    public ResponseEntity<List<PresentOrderDto>> getAllPresentOrder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                    @RequestParam Integer userId) {
        userService.checkIfBuyer(userPrincipal.getId());
        List<PresentOrder> allPresentOrder = presentOrderBuyerService.getAllPresentOrder(userPrincipal.getId());
        List<PresentOrderDto> allPresentOrderDto = allPresentOrder.stream()
                .map(presentOrderMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allPresentOrderDto);
    }

    @GetMapping(path = {"/getOne/{id}"})
    public ResponseEntity<PresentOrderDto> getPresentOrder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                           @PathVariable("id") Integer presentOrderId) {
        userService.checkIfBuyer(userPrincipal.getId());
        PresentOrder presentOrder = presentOrderBuyerService.getPresentOrder(userPrincipal.getId(), presentOrderId);
        PresentOrderDto presentOrderDto = presentOrderMapper.toDto(presentOrder);
        return ResponseEntity.ok(presentOrderDto);
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<PresentOrderDto> deletePresentBox(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                            @PathVariable("id") Integer presentOrderId) {
        userService.checkIfBuyer(userPrincipal.getId());
        HttpStatus status = presentOrderBuyerService.deletePresentOrder(userPrincipal.getId(), presentOrderId) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).build();
    }

}
