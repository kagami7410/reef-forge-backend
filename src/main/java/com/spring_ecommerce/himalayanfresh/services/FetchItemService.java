package com.spring_ecommerce.himalayanfresh.services;

import com.spring_ecommerce.himalayanfresh.models.CoffeeItem;
import com.spring_ecommerce.himalayanfresh.models.FragRackItem;
import org.springframework.data.domain.Page;

public interface FetchItemService {
    Page<FragRackItem> getFragRackItemsByPage(int pageNumber, int pageSize);
}
