package com.lviv.hnatko.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.lviv.hnatko.dto.PresentBoxDto;
import com.lviv.hnatko.entity.PresentBox;
import com.lviv.hnatko.mapper.PresentBoxMapper;
import com.lviv.hnatko.security.UserPrincipal;
import com.lviv.hnatko.services.PresentBoxService;
import com.lviv.hnatko.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/present/box")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PresentBoxController {

    private final UserService userService;
    private final PresentBoxMapper presentBoxMapper;
    private final PresentBoxService presentBoxService;

    @PostMapping
    public ResponseEntity<PresentBoxDto> createPresentBox(@RequestParam Integer userId,
                                                          @RequestBody PresentBoxDto presentBoxDto) {
        userService.checkIfAdmin(userId);
        PresentBox presentBox = presentBoxService.createPresentBox(presentBoxMapper.toEntity(presentBoxDto));
        PresentBoxDto boxDto = presentBoxMapper.toDto(presentBox);
        return ResponseEntity.status(HttpStatus.CREATED).body(boxDto);
    }

    @GetMapping(path = {"/listAll"})
    public ResponseEntity<List<PresentBoxDto>> getAllPresentBox() {
        List<PresentBox> allPresentBox = presentBoxService.getAllPresentBox();
        List<PresentBoxDto> allPresentBoxDto = allPresentBox.stream()
                .map(presentBoxMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allPresentBoxDto);
    }

    @GetMapping(path = {"/getOne/{id}"})
    public ResponseEntity<PresentBoxDto> getPresentBox(@PathVariable("id") Integer presentBoxId) {
        PresentBox presentBox = presentBoxService.getPresentBox(presentBoxId);
        PresentBoxDto boxDto = presentBoxMapper.toDto(presentBox);
        return new ResponseEntity<>(boxDto, HttpStatus.OK);
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<PresentBoxDto> updatePresentBox(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                          @PathVariable("id") Integer presentBoxId,
                                                          @RequestBody PresentBoxDto presentBoxDto) {
        userService.checkIfAdmin(userPrincipal.getId());
        PresentBox updatedPresentBox = presentBoxService.updatePresentBox(presentBoxId,
                presentBoxMapper.toEntity(presentBoxDto));
        return (presentBoxId == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(presentBoxMapper.toDto(updatedPresentBox), HttpStatus.OK));
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<PresentBoxDto> deletePresentBox(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                          @PathVariable("id") Integer presentBoxId) {
        userService.checkIfAdmin(userPrincipal.getId());
        HttpStatus status = presentBoxService.deletePresentBox(presentBoxId) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).build();
    }
}
