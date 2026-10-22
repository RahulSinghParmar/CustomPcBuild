package io.github.rahulsinghparmar.pcbuild;

import java.math.BigDecimal;
import java.util.List;
import static io.github.rahulsinghparmar.pcbuild.Part.Category.*;

/** Illustrative INR prices, not retailer offers. All CPUs use the included stock cooler. */
public final class Catalog {
    private Catalog() {}

    public static List<Part> parts() {
        return List.of(
            part("cpu-1", CPU, "Ryzen 5 5600G (stock cooler)", "12000", "AM4", 65, true),
            part("cpu-2", CPU, "Ryzen 5 5600 (stock cooler)", "11000", "AM4", 65, false),
            part("cpu-3", CPU, "Ryzen 5 7600 (stock cooler)", "20000", "AM5", 65, true),
            part("board-1", MOTHERBOARD, "AM4 / DDR4 / micro-ATX board", "7000", "AM4:DDR4:mATX", 35, false),
            part("board-2", MOTHERBOARD, "AM5 / DDR5 / ATX board", "14000", "AM5:DDR5:ATX", 40, false),
            part("ram-1", RAM, "16 GB DDR4 kit", "3500", "DDR4", 8, false),
            part("ram-2", RAM, "32 GB DDR4 kit", "6500", "DDR4", 12, false),
            part("ram-3", RAM, "32 GB DDR5 kit", "9000", "DDR5", 12, false),
            part("gpu-0", GPU, "Use CPU integrated graphics", "0", "integrated", 0, false),
            part("gpu-1", GPU, "Radeon RX 6600 8 GB", "20000", "PCIe", 132, false),
            part("gpu-2", GPU, "GeForce RTX 4060 8 GB", "30000", "PCIe", 115, false),
            part("ssd-1", STORAGE, "500 GB SATA SSD", "2500", "SATA", 5, false),
            part("ssd-2", STORAGE, "1 TB SATA SSD", "5000", "SATA", 5, false),
            part("psu-1", PSU, "550 W PSU", "4000", "ATX", 550, false),
            part("psu-2", PSU, "750 W PSU", "6500", "ATX", 750, false),
            part("case-1", CASE, "micro-ATX case (fans included)", "3000", "mATX", 10, false),
            part("case-2", CASE, "ATX case (fans included)", "5000", "ATX", 10, false));
    }

    private static Part part(String id, Part.Category category, String name, String price,
                             String platform, int watts, boolean graphics) {
        return new Part(id, category, name, new BigDecimal(price), platform, watts, graphics);
    }
}
