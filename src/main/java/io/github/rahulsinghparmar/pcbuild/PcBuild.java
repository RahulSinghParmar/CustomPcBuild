package io.github.rahulsinghparmar.pcbuild;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import static io.github.rahulsinghparmar.pcbuild.Part.Category.*;

/** A quote includes tax in the user's maximum budget. */
public record PcBuild(List<Part> parts, BigDecimal budget, BigDecimal taxPercent) {
    public PcBuild {
        parts = List.copyOf(parts);
        if (budget == null || budget.signum() <= 0 || taxPercent == null
                || taxPercent.signum() < 0 || taxPercent.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Budget must be positive and tax must be between 0 and 100");
        }
    }

    public BigDecimal subtotal() {
        return parts.stream().map(Part::price).reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal tax() {
        return subtotal().multiply(taxPercent).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal total() { return subtotal().add(tax()); }

    public Part part(Part.Category category) {
        return parts.stream().filter(p -> p.category() == category).findFirst().orElseThrow();
    }

    public List<String> problems() {
        List<String> errors = new ArrayList<>();
        for (Part.Category category : Part.Category.values()) {
            if (parts.stream().filter(p -> p.category() == category).count() != 1) {
                errors.add("Choose exactly one " + category);
            }
        }
        if (!errors.isEmpty()) return List.copyOf(errors);
        String[] board = part(MOTHERBOARD).platform().split(":");
        if (board.length != 3) {
            errors.add("Unrecognized motherboard specification");
            return List.copyOf(errors);
        }
        if (!part(CPU).platform().equals(board[0])) errors.add("CPU and motherboard sockets differ");
        if (!part(RAM).platform().equals(board[1])) errors.add("RAM generation does not match motherboard");
        if (!part(CASE).platform().equals("ATX") && !part(CASE).platform().equals(board[2])) {
            errors.add("Motherboard does not fit case");
        }
        if (part(GPU).platform().equals("integrated") && !part(CPU).integratedGraphics()) {
            errors.add("This CPU needs a dedicated graphics card");
        }
        int load = parts.stream().filter(p -> p.category() != PSU).mapToInt(Part::watts).sum();
        if ((long) part(PSU).watts() * 100 < (long) load * 125) {
            errors.add("PSU needs at least 25% estimated power headroom");
        }
        if (total().compareTo(budget) > 0) errors.add("Total including tax exceeds budget by INR " + total().subtract(budget));
        return List.copyOf(errors);
    }
}
