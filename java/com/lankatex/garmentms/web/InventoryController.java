package com.lankatex.garmentms.web;

import com.lankatex.garmentms.dto.CreateInventoryItemRequest;
import com.lankatex.garmentms.dto.UpdateInventoryItemRequest;
import com.lankatex.garmentms.model.InventoryItem;
import com.lankatex.garmentms.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("createInventoryItemRequest", new CreateInventoryItemRequest());
        return "inventory/create";
    }

    @PostMapping("/create")
    public String createInventoryItem(
            @Valid @ModelAttribute CreateInventoryItemRequest createInventoryItemRequest,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "inventory/create";
        }

        try {
            inventoryService.createInventoryItem(
                    createInventoryItemRequest.getItemName(),
                    createInventoryItemRequest.getItemCode(),
                    createInventoryItemRequest.getQuantity(),
                    createInventoryItemRequest.getUnitPrice(),
                    createInventoryItemRequest.getDescription()
            );
            redirectAttributes.addFlashAttribute("successMessage", "Item created successfully!");
            return "redirect:/admin/inventory/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "inventory/create";
        }
    }

    @GetMapping("/list")
    public String listInventoryItems(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {

        Page<InventoryItem> inventoryPage =
                inventoryService.listAllInventoryItems(
                        PageRequest.of(page, size, Sort.by("id").descending())
                );

        model.addAttribute("inventoryPage", inventoryPage);
        return "inventory/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            InventoryItem item = inventoryService.findInventoryItemById(id);

            UpdateInventoryItemRequest updateRequest = new UpdateInventoryItemRequest();
            updateRequest.setId(item.getId());
            updateRequest.setItemName(item.getItemName());
            updateRequest.setItemCode(item.getItemCode());
            updateRequest.setQuantity(item.getQuantity());
            updateRequest.setUnitPrice(item.getUnitPrice());
            updateRequest.setDescription(item.getDescription());

            model.addAttribute("updateInventoryItemRequest", updateRequest);
            return "inventory/edit";
        } catch (IllegalArgumentException e) {
            return "redirect:/admin/inventory/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateInventoryItem(
            @PathVariable Long id,
            @Valid @ModelAttribute UpdateInventoryItemRequest updateRequest,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "inventory/edit";
        }

        try {
            inventoryService.updateInventoryItem(
                    id,
                    updateRequest.getItemName(),
                    updateRequest.getItemCode(),
                    updateRequest.getQuantity(),
                    updateRequest.getUnitPrice(),
                    updateRequest.getDescription()
            );
            redirectAttributes.addFlashAttribute("successMessage", "Item updated successfully!");
            return "redirect:/admin/inventory/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "inventory/edit";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteInventoryItem(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        try {
            inventoryService.deleteInventoryItem(id);
            redirectAttributes.addFlashAttribute("successMessage", "Item deleted successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/inventory/list";
    }
}