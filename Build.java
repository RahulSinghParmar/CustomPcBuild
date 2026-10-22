import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.tools.ToolProvider;

/** Cross-platform build using only the JDK: java Build.java */
public class Build {
    public static void main(String[] args) throws Exception {
        for (String directory : List.of("build/classes", "build/jar")) {
            Path path = Path.of(directory);
            if (Files.exists(path)) {
                try (var entries = Files.walk(path)) {
                    for (Path entry : entries.sorted(java.util.Comparator.reverseOrder()).toList()) Files.delete(entry);
                }
            }
        }
        Path output = Path.of("build/classes");
        Files.createDirectories(output);
        List<String> options = new ArrayList<>(List.of("--release", "17", "-encoding", "UTF-8", "-Xlint:all", "-Werror", "-d", output.toString()));
        try (var sources = Files.walk(Path.of("src/main/java"))) {
            options.addAll(sources.filter(p -> p.toString().endsWith(".java")).sorted().map(Path::toString).toList());
        }
        try (var sources = Files.walk(Path.of("src/test/java"))) {
            options.addAll(sources.filter(p -> p.toString().endsWith(".java")).sorted().map(Path::toString).toList());
        }
        var compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) throw new IllegalStateException("A full JDK 17+ is required");
        if (compiler.run(null, System.out, System.err, options.toArray(String[]::new)) != 0) System.exit(1);
        String javaExecutable = Path.of(System.getProperty("java.home"), "bin", "java").toString();
        if (new ProcessBuilder(javaExecutable, "-cp", output.toString(), "io.github.rahulsinghparmar.pcbuild.BuildTests")
                .inheritIO().start().waitFor() != 0) System.exit(1);
        // Package application classes only; tests are compiled to the same folder but excluded.
        Path staging = Path.of("build/jar/io/github/rahulsinghparmar/pcbuild");
        Files.createDirectories(staging);
        try (var classes = Files.list(output.resolve("io/github/rahulsinghparmar/pcbuild"))) {
            for (Path file : classes.filter(p -> !p.getFileName().toString().startsWith("BuildTests")).toList()) {
                Files.copy(file, staging.resolve(file.getFileName()), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        }
        Files.copy(Path.of("LICENSE"), Path.of("build/jar/LICENSE"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        var jar = java.util.spi.ToolProvider.findFirst("jar").orElseThrow();
        if (jar.run(System.out, System.err, "--create", "--file", "build/custompcbuild-1.0.0.jar",
                "--main-class", "io.github.rahulsinghparmar.pcbuild.Main", "-C", "build/jar", ".") != 0) System.exit(1);
        System.out.println("Built build/custompcbuild-1.0.0.jar");
    }
}
