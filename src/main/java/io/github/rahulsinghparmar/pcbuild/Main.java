package io.github.rahulsinghparmar.pcbuild;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public final class Main {
    public static final String VERSION = "1.0.0";
    private final Scanner input;
    private final PrintStream out;

    public Main(Scanner input, PrintStream out) { this.input = input; this.out = out; }

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("--version")) { System.out.println(VERSION); return; }
        if (args.length == 1 && args[0].equals("--demo")) {
            print(Builder.recommend(new BigDecimal("75000"), new BigDecimal("18"),
                    Builder.Purpose.GAMING, null).orElseThrow(), System.out);
            return;
        }
        if (args.length != 0) {
            System.out.println("Usage: java -jar custompcbuild-1.0.0.jar [--demo|--version]");
            return;
        }
        new Main(new Scanner(System.in, StandardCharsets.UTF_8), System.out).run();
    }

    public void run() {
        out.println("Custom PC Build " + VERSION + " | Demo catalog | Prices in INR");
        try {
            while (true) {
                out.println("\n1 Express builder\n2 Custom builder\n3 Random builder\n4 Glossary\n5 Exit");
                int mode = integer("Choose: ", 1, 5);
                if (mode == 5) return;
                if (mode == 4) { glossary(); continue; }
                BigDecimal budget = decimal("Maximum total budget including tax (INR): ", new BigDecimal("1"), new BigDecimal("10000000"));
                BigDecimal tax = decimal("Tax percent (0-100; use 0 if prices already include tax): ", BigDecimal.ZERO, new BigDecimal("100"));
                PcBuild build;
                if (mode == 2) {
                    build = custom(budget, tax);
                } else {
                    int purpose = integer("Purpose: 1 Office, 2 Gaming, 3 Editing: ", 1, 3);
                    build = Builder.recommend(budget, tax, Builder.Purpose.values()[purpose - 1],
                            mode == 3 ? new Random() : null).orElse(null);
                }
                if (build == null) {
                    out.println("No compatible build fits this budget in the demo catalog. Try a larger budget or another purpose.");
                    continue;
                }
                List<String> problems = build.problems();
                if (!problems.isEmpty()) {
                    out.println("Selection needs changes:");
                    problems.forEach(p -> out.println("- " + p));
                    continue;
                }
                print(build, out);
                String destination = line("Export CSV to a new file (blank to skip): ");
                if (!destination.isBlank()) {
                    try {
                        export(build, Path.of(destination));
                        out.println("Quote exported to " + destination);
                    } catch (IOException | IllegalArgumentException | SecurityException e) {
                        out.println("Could not export quote: " + e.getMessage());
                    }
                }
            }
        } catch (EndOfInput e) { out.println("\nInput closed. Goodbye."); }
    }

    private PcBuild custom(BigDecimal budget, BigDecimal tax) {
        List<Part> parts = new ArrayList<>();
        for (Part.Category category : Part.Category.values()) {
            List<Part> choices = Catalog.parts().stream().filter(p -> p.category() == category).toList();
            out.println("\n" + category);
            for (int i = 0; i < choices.size(); i++) {
                Part p = choices.get(i);
                out.printf("%d %s | %s | INR %s%n", i + 1, p.name(), p.platform(), p.price());
            }
            parts.add(choices.get(integer("Choose: ", 1, choices.size()) - 1));
        }
        return new PcBuild(parts, budget, tax);
    }

    private void glossary() {
        out.println("""
                CPU: Executes programs; its socket must match the motherboard.
                Motherboard: Connects components and determines socket, RAM and case requirements.
                RAM: Working memory; DDR4 and DDR5 are not interchangeable.
                GPU: Renders graphics; a dedicated GPU is required when the CPU has no integrated graphics.
                Storage: Keeps files and the OS; this catalog uses SATA SSDs.
                PSU: Supplies power. This tool reserves 25% headroom over estimated component load.
                Case: Houses components; an ATX board needs an ATX case in this catalog.
                Prices are illustrative. Check exact BIOS support, dimensions, connectors and retailer details.
                OS, peripherals, shipping and assembly are excluded from quotes.
                """);
    }

    private String line(String prompt) {
        out.print(prompt);
        if (!input.hasNextLine()) throw new EndOfInput();
        return input.nextLine().trim();
    }

    private int integer(String prompt, int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(line(prompt));
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { }
            out.printf("Enter a whole number from %d to %d.%n", min, max);
        }
    }

    private BigDecimal decimal(String prompt, BigDecimal min, BigDecimal max) {
        while (true) {
            String value = line(prompt);
            if (value.matches("[0-9]{1,8}(\\.[0-9]{1,2})?")) {
                BigDecimal number = new BigDecimal(value);
                if (number.compareTo(min) >= 0 && number.compareTo(max) <= 0) return number;
            }
            out.printf("Enter a decimal from %s to %s with at most two decimal places.%n", min, max);
        }
    }

    public static void print(PcBuild build, PrintStream out) {
        out.println("\nCustom PC Build quote | INR | ILLUSTRATIVE PRICES");
        for (Part p : build.parts()) out.printf("%-12s %-44s %10.2f%n", p.category(), p.name(), p.price());
        out.printf("Subtotal: %s%nTax (%s%%): %s%nTotal: %s%nBudget remaining: %s%n",
                build.subtotal(), build.taxPercent(), build.tax(), build.total(), build.budget().subtract(build.total()));
        out.println("OS, peripherals, shipping and assembly excluded. Verify exact hardware before purchasing.");
    }

    public static void export(PcBuild build, Path path) throws IOException {
        if (!build.problems().isEmpty()) throw new IllegalArgumentException("Cannot export an invalid build");
        StringBuilder csv = new StringBuilder("Category,Component,Price_INR\n");
        for (Part p : build.parts()) csv.append(p.category()).append(',').append(quote(p.name())).append(',').append(p.price()).append('\n');
        csv.append("Subtotal,,").append(build.subtotal()).append("\nTax,,").append(build.tax())
                .append("\nTotal,,").append(build.total()).append("\nBudget,,")
                .append(build.budget().setScale(2, RoundingMode.HALF_UP))
                .append("\nNote,\"Illustrative prices; OS/peripherals/shipping/assembly excluded\",\n");
        Files.writeString(path, csv, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
    }

    private static String quote(String value) { return "\"" + value.replace("\"", "\"\"") + "\""; }
    private static final class EndOfInput extends RuntimeException { private static final long serialVersionUID = 1L; }
}
