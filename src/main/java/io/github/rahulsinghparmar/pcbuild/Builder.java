package io.github.rahulsinghparmar.pcbuild;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import static io.github.rahulsinghparmar.pcbuild.Part.Category.*;

/** Searches the small catalog completely, retaining only valid, affordable combinations. */
public final class Builder {
    public enum Purpose { OFFICE, GAMING, EDITING }
    private Builder() {}

    public static Optional<PcBuild> recommend(BigDecimal budget, BigDecimal tax, Purpose purpose, Random random) {
        new PcBuild(List.of(), budget, tax); // Validate even if no affordable candidate exists.
        List<PcBuild> candidates = new ArrayList<>();
        search(0, new ArrayList<>(), budget, tax, purpose, candidates);
        if (candidates.isEmpty()) return Optional.empty();
        if (random != null) return Optional.of(candidates.get(random.nextInt(candidates.size())));
        return candidates.stream().max(Comparator.comparingInt((PcBuild b) -> score(b, purpose))
                .thenComparing(PcBuild::total, Comparator.reverseOrder()));
    }

    private static void search(int index, List<Part> selection, BigDecimal budget, BigDecimal tax,
                               Purpose purpose, List<PcBuild> candidates) {
        if (index == Part.Category.values().length) {
            PcBuild build = new PcBuild(selection, budget, tax);
            if (purpose == Purpose.GAMING && build.part(GPU).platform().equals("integrated")) return;
            if (build.problems().isEmpty()) candidates.add(build);
            return;
        }
        Part.Category category = Part.Category.values()[index];
        for (Part part : Catalog.parts()) {
            if (part.category() == category) {
                selection.add(part);
                search(index + 1, selection, budget, tax, purpose, candidates);
                selection.remove(selection.size() - 1);
            }
        }
    }

    // Transparent preference heuristic, not a benchmark or a gaming FPS prediction.
    private static int score(PcBuild b, Purpose p) {
        int cpu = b.part(CPU).id().equals("cpu-3") ? 3 : 1;
        int ram = b.part(RAM).id().equals("ram-1") ? 1 : 2;
        int gpu = switch (b.part(GPU).id()) { case "gpu-2" -> 3; case "gpu-1" -> 2; default -> 0; };
        int disk = b.part(STORAGE).id().equals("ssd-2") ? 2 : 1;
        return switch (p) {
            case OFFICE -> cpu * 10 + ram * 5 + disk * 5;
            case GAMING -> gpu * 100 + cpu * 10 + ram * 5 + disk;
            case EDITING -> cpu * 30 + ram * 20 + gpu * 10 + disk * 5;
        };
    }
}
