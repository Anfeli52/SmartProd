package com.andres.smartprod.Service;

import com.andres.smartprod.Model.Item;
import com.andres.smartprod.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Item> findById(Long id){return itemRepository.findById(id);}

    public Item saveItem(Item item){
        return itemRepository.save(item);
    }

    public void deleteItem(Long itemId){
        itemRepository.deleteById(itemId);
    }

    public boolean existsItem(Long itemId){
        return itemRepository.existsById(itemId);
    }

}
