package com.spring_ecommerce.himalayanfresh.repository;

import com.spring_ecommerce.himalayanfresh.models.CoffeeItem;
import com.spring_ecommerce.himalayanfresh.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
