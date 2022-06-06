package com.lviv.hnatko.services;

import com.lviv.hnatko.entity.PresentOrder;
import com.lviv.hnatko.entity.enumeration.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lviv.hnatko.exception.ResourceNotFoundException;
import com.lviv.hnatko.repository.PresentOrderRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PresentOrderAdminService {

    private final PresentOrderRepository presentOrderRepository;

    public List<PresentOrder> getAllPresentBox() {
        return presentOrderRepository.findAll();
    }

    public PresentOrder getPresentBox(Integer presentOrderId) {
        return presentOrderRepository.findById(presentOrderId)
                .orElseThrow(ResourceNotFoundException.presentBoxSupplier(presentOrderId));
    }

    @Transactional
    public PresentOrder updateOrderStatus(Integer presentOrderId, String orderStatus) {
        PresentOrder presentOrder = presentOrderRepository.findById(presentOrderId)
                .orElseThrow(ResourceNotFoundException.presentBoxSupplier(presentOrderId));
        if(orderStatus.equals(OrderStatus.NOT_CREATED.toString())) {
            presentOrder.setStatus(OrderStatus.NOT_CREATED);
        }
        if(orderStatus.equals(OrderStatus.CREATED.toString())) {
            presentOrder.setStatus(OrderStatus.CREATED);
        }
        if(orderStatus.equals(OrderStatus.APPROVED.toString())) {
            presentOrder.setStatus(OrderStatus.APPROVED);
        }
        if(orderStatus.equals(OrderStatus.CANCELED.toString())) {
            presentOrder.setStatus(OrderStatus.CANCELED);
        }
        if(orderStatus.equals(OrderStatus.DECLINED.toString())) {
            presentOrder.setStatus(OrderStatus.DECLINED);
        }
        if(orderStatus.equals(OrderStatus.DELIVERED.toString())) {
            presentOrder.setStatus(OrderStatus.DELIVERED);
        }
        if(orderStatus.equals(OrderStatus.SENT.toString())) {
            presentOrder.setStatus(OrderStatus.SENT);
        }
        if(orderStatus.equals(OrderStatus.EXPIRED.toString())) {
            presentOrder.setStatus(OrderStatus.EXPIRED);
        }
        return presentOrderRepository.saveAndFlush(presentOrder);
    }
}
