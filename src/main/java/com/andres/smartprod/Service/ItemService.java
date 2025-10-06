package com.andres.smartprod.Service;

import com.andres.smartprod.Model.Item;
import com.andres.smartprod.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }
}
