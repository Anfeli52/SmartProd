package com.andres.smartprod.Controller;

import com.andres.smartprod.Model.Item;
import com.andres.smartprod.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ItemViewController {
    private final ItemService itemService;

    @Autowired
    public ItemViewController(ItemService itemService){
        this.itemService=itemService;
    }

    @GetMapping("/analista/registros")
    @PreAuthorize("hasRole('ANALISTA')")
    public String verRegistros(Model model){
        List<Item> items = itemService.findAllItems();
        model.addAttribute("items",items);

        return "analista/registros";
    }
}
