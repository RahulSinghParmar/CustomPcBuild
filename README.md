# Custom PC Build

[![Java CI](https://github.com/RahulSinghParmar/CustomPcBuild/actions/workflows/ci.yml/badge.svg)](https://github.com/RahulSinghParmar/CustomPcBuild/actions/workflows/ci.yml)
[![Release](https://img.shields.io/github/v/release/RahulSinghParmar/CustomPcBuild)](https://github.com/RahulSinghParmar/CustomPcBuild/releases)
[![MIT license](https://img.shields.io/github/license/RahulSinghParmar/CustomPcBuild)](LICENSE)

A Java console application for exploring compatible PC configurations within a tax-inclusive budget. Built by Rahul Singh Parmar, evolving an introductory Java coursework project into a tested application.

## Download and run

Install Java 17 or newer, download `custompcbuild-1.0.0.jar` from [Releases](https://github.com/RahulSinghParmar/CustomPcBuild/releases/latest), then run:

```sh
java -jar custompcbuild-1.0.0.jar
```

Use `--demo` for an example quote without prompts, or `--version` to print the version. No runtime libraries, account, network connection, Maven or Gradle installation is required.

## Features

- **Express builder:** recommends a configuration for office, gaming or editing use.
- **Custom builder:** choose every component and get compatibility/budget feedback.
- **Random builder:** explores compatible configurations for your purpose and budget.
- **Compatibility checks:** CPU socket, RAM generation, motherboard/case size, integrated graphics availability, and estimated PSU load with 25% headroom.
- **Accurate totals:** decimal INR prices and a maximum budget that includes configurable tax.
- **CSV export:** saves a valid quote to a new file; existing files are never overwritten.
- **Glossary and input recovery:** component explanations, validated prompts and graceful EOF handling.

The catalog contains 17 illustrative entries. Prices are demo data, **not current retailer offers**. Enter tax `0` for tax-inclusive prices. OS licenses, peripherals, shipping and assembly are excluded. Catalog CPUs include stock coolers.

## Build and test

Use a **full JDK 17+**, including the Java compiler and JAR tool:

```sh
git clone https://github.com/RahulSinghParmar/CustomPcBuild.git
cd CustomPcBuild
java Build.java
java -jar build/custompcbuild-1.0.0.jar --demo
```

The same commands work in Windows PowerShell, macOS and Linux. The build compiles for Java 17, runs dependency-free regression tests, and packages application classes plus the license into a runnable JAR. CI checks all three operating systems on JDK 17 and 21.

## Use the builder

1. Choose Express, Custom or Random.
2. Enter a maximum total budget in INR and a tax percentage, each with up to two decimal places.
3. Choose a purpose, or select one component in each category.
4. Review your quote or compatibility feedback. Try another budget/purpose if no recommendation fits.
5. Enter a new CSV file path to export, or press Enter to skip.

Custom selections are checked after all seven choices; invalid selections return to the main menu. Express scores all valid catalog combinations using simple preference rules. Gaming requires a dedicated GPU. Random samples valid candidates using the same purpose constraint.

## Example

```text
Gaming / INR 75,000 maximum / 18% tax
CPU          Ryzen 5 5600 (stock cooler)       11000.00
MOTHERBOARD  AM4 / DDR4 / micro-ATX board       7000.00
RAM          16 GB DDR4 kit                     3500.00
GPU          GeForce RTX 4060 8 GB             30000.00
STORAGE      1 TB SATA SSD                      5000.00
PSU          550 W PSU                          4000.00
CASE         micro-ATX case (fans included)     3000.00
Subtotal: 63500.00
Tax (18%): 11430.00
Total: 74930.00
Budget remaining: 70.00
```

## Design

```text
Console input → Builder + Catalog → PcBuild validation → Quote / CSV
```

| File | Responsibility |
| --- | --- |
| `Part.java` | Immutable components and categories |
| `Catalog.java` | Sample parts and hardware attributes |
| `PcBuild.java` | Compatibility and decimal calculations |
| `Builder.java` | Catalog search and preference scoring |
| `Main.java` | Console interaction and CSV export |
| `BuildTests.java` | Regression suite |
| `Build.java` | Portable compile/test/package command |

Sources live in `src/main/java/io/github/rahulsinghparmar/pcbuild`; tests in `src/test/java/...`. The runnable JAR replaces the old `Build.MainMenu` entry point. The original implementation remains in Git history.

## Limits and roadmap

Validation covers attributes represented by this small catalog. Verify exact motherboard BIOS support, dimensions, connectors and vendor specifications before purchasing. Scores are preference heuristics, not FPS predictions or benchmarks. A larger catalog would need search pruning/indexing.

Future work: an external versioned catalog, saved configurations/import, detailed compatibility rules and an optional desktop interface. See [release notes](CHANGELOG.md), [contributing](CONTRIBUTING.md), and [security](SECURITY.md).

## License and history

[MIT](LICENSE). The 2022 console coursework is the starting point; version 1.0.0 replaces its incomplete implementation. Earlier README content referenced `mr-anonymous-official/PC-Build`; that historical reference is acknowledged here without claiming an independently verified origin for every part of the old coursework. The existing copyright notice is preserved.
