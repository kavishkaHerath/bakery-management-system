package com.erp.bakery.service;

import com.erp.bakery.model.Item;
import com.erp.bakery.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

//    public Item addItem(Item item){
// // Check for duplicate email and phone
//    }
}
