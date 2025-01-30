package com.spring_ecommerce.himalayanfresh.services;

import com.spring_ecommerce.himalayanfresh.models.FragRackItem;
import com.spring_ecommerce.himalayanfresh.repository.FragRacksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FetchItemServiceImpl implements FetchItemService {

    @Autowired
    FragRacksRepository fragRacksRepository;



    @Override
    public Page<FragRackItem> getFragRackItemsByPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return  fragRacksRepository.findAll(pageRequest);
    }
}
