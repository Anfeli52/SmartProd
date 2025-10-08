package com.andres.smartprod.Controller;

import com.andres.smartprod.Model.Item;
import com.andres.smartprod.Model.CalculationResult;
import com.andres.smartprod.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/analista/items")
public class ItemCalculationController {

    private final ItemService itemService;

    @Autowired
    public ItemCalculationController(ItemService itemService){
        this.itemService = itemService;
    }

    @PostMapping("/calculate")
    @PreAuthorize("hasRole('ANALISTA')")
    public CalculationResult calculateTotal(@RequestParam("itemId") Long itemId,
                                            @RequestParam("pieces") int pieces) {

        Item item = itemService.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ítem con ID " + itemId + " no encontrado."));

        double totalPintura = item.getCantidadPintura() * pieces;
        double totalLavado = item.getLavado() * pieces;
        double totalPinturaMin = item.getPintura() * pieces;
        double totalHorneo = item.getHorneo() * pieces;

        // 3. Devolver el resultado (Spring lo convierte a JSON)
        return new CalculationResult(totalPintura, totalLavado, totalPinturaMin, totalHorneo, pieces);
    }
}
