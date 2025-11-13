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
import java.util.Optional;

@Controller
public class ItemViewController {
    private final ItemService itemService;

    @Autowired
    public ItemViewController(ItemService itemService){
        this.itemService = itemService;
    }

    // --- MÉTODOS DE VISTA (GET) ---

    @GetMapping("/analista/registros")
    @PreAuthorize("hasRole('ANALISTA')")
    public String verRegistros(Model model){
        List<Item> items = itemService.findAllItems();
        model.addAttribute("items", items);
        return "analista/registros";
    }

    @GetMapping("/analista/registros/nuevo")
    @PreAuthorize("hasRole('ANALISTA')")
    public String mostrarFormularioNuevoItem(Model model){
        model.addAttribute("item", new Item());
        return "analista/nuevo_registro";
    }

    @GetMapping("/analista/registros/edit/{numeroItem}")
    @PreAuthorize("hasRole('ANALISTA')")
    public String showEditForm(@PathVariable("numeroItem") Long numeroItem, Model model) {
        Item item = itemService.findById(numeroItem)
                .orElseThrow(() -> new IllegalArgumentException("ID de Ítem inválido: " + numeroItem));
        model.addAttribute("item", item);
        return "analista/editar_registro";
    }

    @GetMapping("analista/registros/delete/{id}")
    @PreAuthorize("hasRole('ANALISTA')")
    public String deleteItem(@PathVariable("id") Long id){
        itemService.deleteItem(id);
        return "redirect:/analista/registros";
    }

    // --- MÉTODOS DE ACCIÓN (POST) ---

    @PostMapping("/analista/registros/nuevo")
    @PreAuthorize("hasRole('ANALISTA')")
    public String nuevoItem(@ModelAttribute("item") Item item, Model model){

        // --- VALIDACIONES DE CREACIÓN ---

        if (item.getNumeroItem() == null || item.getNumeroItem() <= 0) {
            model.addAttribute("item", item);
            model.addAttribute("error", "El Número de Ítem debe ser un valor positivo.");
            return "analista/nuevo_registro";
        }

        if (itemService.findById(item.getNumeroItem()).isPresent()) {
            model.addAttribute("item", item);
            model.addAttribute("error", "Ya existe un Ítem registrado con el número: " + item.getNumeroItem());
            return "analista/nuevo_registro";
        }

        if (item.getNombre() == null || item.getNombre().trim().isEmpty()) {
            model.addAttribute("item", item);
            model.addAttribute("error", "El campo Nombre no puede estar vacío.");
            return "analista/nuevo_registro";
        }

        if (item.getCantidadPintura() == null || item.getCantidadPintura() <= 0) {
            model.addAttribute("item", item);
            model.addAttribute("error", "La Cantidad de Pintura debe ser mayor a cero.");
            return "analista/nuevo_registro";
        }

        if (item.getLavado() == null || item.getLavado() < 0 ||
                item.getPintura() == null || item.getPintura() < 0 ||
                item.getHorneo() == null || item.getHorneo() < 0) {
            model.addAttribute("item", item);
            model.addAttribute("error", "Los campos de Tiempo deben ser cero o positivos.");
            return "analista/nuevo_registro";
        }

        itemService.saveItem(item);
        return "redirect:/analista/registros";
    }

    @PostMapping("/analista/registros/update")
    @PreAuthorize("hasRole('ANALISTA')")
    public String updateItem(@ModelAttribute("item") Item itemForm, Model model) {

        Item existingItem = itemService.findById(itemForm.getNumeroItem())
                .orElseThrow(() -> new IllegalArgumentException("Ítem no encontrado para actualización."));

        if (itemForm.getNombre() == null || itemForm.getNombre().trim().isEmpty()) {
            model.addAttribute("item", itemForm);
            model.addAttribute("error", "El campo Nombre no puede estar vacío.");
            return "analista/editar_registro";
        }

        if (itemForm.getCantidadPintura() == null || itemForm.getCantidadPintura() <= 0) {
            model.addAttribute("item", itemForm);
            model.addAttribute("error", "La Cantidad de Pintura debe ser mayor a cero.");
            return "analista/editar_registro";
        }

        if (itemForm.getLavado() == null || itemForm.getLavado() < 0 ||
                itemForm.getPintura() == null || itemForm.getPintura() < 0 ||
                itemForm.getHorneo() == null || itemForm.getHorneo() < 0) {
            model.addAttribute("item", itemForm);
            model.addAttribute("error", "Los campos de Tiempo deben ser cero o positivos.");
            return "analista/editar_registro";
        }

        existingItem.setNombre(itemForm.getNombre());
        existingItem.setCantidadPintura(itemForm.getCantidadPintura());
        existingItem.setLavado(itemForm.getLavado());
        existingItem.setPintura(itemForm.getPintura());
        existingItem.setHorneo(itemForm.getHorneo());
        existingItem.setEstado(itemForm.getEstado());

        itemService.saveItem(existingItem);
        return "redirect:/analista/registros";
    }
}