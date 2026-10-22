package io.github.rahulsinghparmar.pcbuild;

import java.math.BigDecimal;

/** Catalog entry. Attributes are deliberately small and explicit for this demo. */
public record Part(String id, Category category, String name, BigDecimal price,
                   String platform, int watts, boolean integratedGraphics) {
    public enum Category { CPU, MOTHERBOARD, RAM, GPU, STORAGE, PSU, CASE }

    public Part {
        if (id == null || id.isBlank() || category == null || name == null || name.isBlank()
                || price == null || price.signum() < 0 || platform == null || watts < 0) {
            throw new IllegalArgumentException("Invalid component entry");
        }
    }
}
