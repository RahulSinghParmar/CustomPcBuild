# Contributing

Use a full JDK 17+ and run `java Build.java` before proposing a change. Submit focused pull requests explaining the behavior changed and checks performed.

Use four-space indentation and descriptive Java names. Keep console interaction out of compatibility/calculation code. Add regression cases for new rules and bug fixes. Compiler warnings fail the build.

Label sample prices as illustrative unless they have dated, verifiable sources. Keep the catalog small while recommendations use exhaustive enumeration.

For releases, update `Main.VERSION`, artifact names in `Build.java`, CI and README, and `CHANGELOG.md` together. Test the merged commit and wait for CI before publishing the matching tag, JAR and SHA-256 checksum.
