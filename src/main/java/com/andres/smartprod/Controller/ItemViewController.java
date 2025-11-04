package com.andres.smartprod.Controller;

import com.andres.smartprod.Model.Item;
import com.andres.smartprod.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/analista/registros/nuevo")
    @PreAuthorize("hasRole('ANALISTA')")
    public String mostrarFormularioNuevoItem(Model model){
        model.addAttribute("item", new Item());
        return "analista/nuevo_registro";
    }

    @PostMapping("/analista/registros/nuevo")
    @PreAuthorize("hasRole('ANALISTA')")
    public String nuevoItem(@ModelAttribute("item") Item item){
        itemService.saveItem(item);
        return "redirect:/analista/registros";
    }

    @GetMapping("/analista/registros/edit/{id}")
    @PreAuthorize("hasRole('ANALISTA')")
    public String mostrarFormularioEditarItem(@ModelAttribute("item") Item item, Model model){
        model.addAttribute("item", item);
        return "analista/editar_registro";
    }

    @GetMapping("analista/registros/delete/{id}")
    @PreAuthorize("hasRole('ANALISTA')")
    public String deleteItem(@PathVariable("id") Long id){
        itemService.deleteItem(id);
        return "redirect:/analista/registros";
    }
}
