package io.github.rahulsinghparmar.pcbuild;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import static io.github.rahulsinghparmar.pcbuild.Part.Category.*;

/** Dependency-free regression suite; throws on failure regardless of JVM assertion settings. */
public final class BuildTests {
    private static int checks;
    private static void check(boolean ok, String message) {
        checks++;
        if (!ok) throw new AssertionError(message);
    }
    private static BigDecimal money(String value) { return new BigDecimal(value); }

    public static void main(String[] args) throws Exception {
        for (Builder.Purpose purpose : Builder.Purpose.values()) {
            for (String amount : List.of("30000", "50000", "100000", "500000")) {
                for (String rate : List.of("0", "18", "100")) {
                    for (boolean randomized : List.of(false, true)) {
                        var result = Builder.recommend(money(amount), money(rate), purpose, randomized ? new Random(42) : null);
                        if (result.isPresent()) {
                            PcBuild build = result.orElseThrow();
                            check(build.problems().isEmpty(), "Recommendation must be valid");
                            check(build.total().compareTo(money(amount)) <= 0, "Tax-inclusive budget exceeded");
                            if (purpose == Builder.Purpose.GAMING) check(!build.part(GPU).id().equals("gpu-0"), "Gaming needs dedicated GPU");
                        }
                    }
                }
            }
        }
        check(Builder.recommend(money("1"), money("0"), Builder.Purpose.OFFICE, null).isEmpty(), "Impossible budget");
        PcBuild base = Builder.recommend(money("100000"), money("18"), Builder.Purpose.GAMING, null).orElseThrow();
        check(base.total().equals(base.subtotal().add(base.tax())), "Tax calculation");
        check(base.tax().equals(base.subtotal().multiply(money("0.18")).setScale(2)), "18% tax");
        PcBuild atLimit = new PcBuild(base.parts(), base.total(), base.taxPercent());
        check(atLimit.problems().isEmpty(), "Exact budget accepted");
        check(!new PcBuild(base.parts(), base.total().subtract(money("0.01")), base.taxPercent()).problems().isEmpty(), "One paisa over budget rejected");
        check(new PcBuild(List.of(), money("100"), money("0")).problems().size() == 7, "Missing categories");
        List<Part> duplicates = new ArrayList<>(base.parts()); duplicates.add(base.part(CPU));
        check(!new PcBuild(duplicates, money("999999"), money("0")).problems().isEmpty(), "Duplicates rejected");
        PcBuild am4 = new PcBuild(List.of(find("cpu-1"), find("board-1"), find("ram-1"), find("gpu-0"), find("ssd-1"), find("psu-1"), find("case-1")), money("100000"), money("0"));
        check(am4.problems().isEmpty(), "Valid integrated office build");
        invalidReplacement(am4, CPU, find("cpu-3"), "sockets");
        invalidReplacement(am4, RAM, find("ram-3"), "RAM generation");
        invalidReplacement(am4, CPU, find("cpu-2"), "dedicated graphics");
        invalidReplacement(am4, PSU, new Part("weak", PSU, "Weak PSU", money("1"), "ATX", 50, false), "headroom");
        PcBuild am5 = new PcBuild(List.of(find("cpu-3"), find("board-2"), find("ram-3"), find("gpu-0"), find("ssd-1"), find("psu-1"), find("case-2")), money("100000"), money("0"));
        invalidReplacement(am5, CASE, find("case-1"), "fit case");
        for (String rate : List.of("-1", "101")) {
            boolean rejected = false;
            try { new PcBuild(List.of(), money("100"), money(rate)); } catch (IllegalArgumentException e) { rejected = true; }
            check(rejected, "Invalid tax rejected");
        }
        String invalidInput = session("hello\n0\n6\n1\nNaN\n-1\n1e5\n100000\n101\n0\n2\n\n5\n");
        check(invalidInput.contains("Enter a whole number"), "Invalid menu recovery");
        check(invalidInput.contains("Enter a decimal"), "Invalid decimal recovery");
        check(invalidInput.contains("Custom PC Build quote"), "Recovered session completes");
        check(session("1\n").contains("Input closed"), "EOF handled");
        check(session("4\n5\n").contains("DDR4 and DDR5"), "Glossary populated");
        check(session("2\n100000\n0\n1\n1\n1\n1\n1\n1\n1\n\n5\n").contains("Custom PC Build quote"), "Custom mode works");
        check(session("2\n100000\n0\n3\n1\n1\n1\n1\n1\n1\n5\n").contains("sockets differ"), "Invalid custom selection explained");
        check(session("3\n100000\n0\n1\n\n5\n").contains("Custom PC Build quote"), "Random office mode works");
        var directory = Files.createTempDirectory("pcbuild-test-");
        var path = directory.resolve("quote.csv");
        try {
            Main.export(base, path);
            String original = Files.readString(path);
            check(original.contains("Total,," + base.total()), "CSV includes total");
            boolean refused = false;
            try { Main.export(base, path); } catch (java.nio.file.FileAlreadyExistsException e) { refused = true; }
            check(refused && Files.readString(path).equals(original), "Export never overwrites existing file");
        } finally { Files.deleteIfExists(path); Files.deleteIfExists(directory); }
        check(Builder.recommend(money("100000"), money("0"), Builder.Purpose.OFFICE, new Random(8))
                .equals(Builder.recommend(money("100000"), money("0"), Builder.Purpose.OFFICE, new Random(8))), "Seeded random reproducibility");
        System.out.println("Passed " + checks + " regression checks");
    }

    private static Part find(String id) { return Catalog.parts().stream().filter(p -> p.id().equals(id)).findFirst().orElseThrow(); }
    private static void invalidReplacement(PcBuild base, Part.Category category, Part replacement, String expected) {
        List<Part> parts = base.parts().stream().map(p -> p.category() == category ? replacement : p).toList();
        check(new PcBuild(parts, base.budget(), base.taxPercent()).problems().stream().anyMatch(p -> p.contains(expected)), expected);
    }
    private static String session(String text) {
        var bytes = new ByteArrayOutputStream();
        new Main(new Scanner(text), new PrintStream(bytes, true, StandardCharsets.UTF_8)).run();
        return bytes.toString(StandardCharsets.UTF_8);
    }
}
