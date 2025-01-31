package com.spring_ecommerce.reefForge.services;

import com.spring_ecommerce.reefForge.models.FragRackItem;
import org.springframework.data.domain.Page;

public interface FetchItemService {
    Page<FragRackItem> getFragRackItemsByPage(int pageNumber, int pageSize);
}
