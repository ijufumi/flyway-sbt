# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

flyway-sbt is an sbt plugin that integrates [Flyway](https://flywaydb.org) database migrations into sbt builds. It is a fork of the original `flyway/flyway-sbt`, republished under `io.github.ijufumi`. The plugin wraps Flyway Core 9.22.3 and exposes Flyway operations as sbt tasks.

## Build & Test

- **sbt version**: 1.10.1
- **Scala**: Cross-builds for 2.12.20 and 2.13.16
- **Java**: Tested on JDK 11, 17, 21

```bash
# Compile
sbt compile

# Run scripted (integration) tests — this is the primary test suite
sbt scripted

# Publish locally for testing
sbt publishLocal
```

There are no unit tests. All testing uses sbt's [scripted test framework](http://www.scala-sbt.org/1.x/docs/Testing-sbt-plugins.html) located in `src/sbt-test/flyway-sbt/`. Each `test1`–`test4` directory is an independent sbt project with its own `build.sbt`, `plugins.sbt`, and `test` script.

## Architecture

This is a single-file sbt plugin. The entire implementation lives in `src/main/scala/io/github/ijufumi/FlywayPlugin.scala`.

**Key structure within FlywayPlugin:**
- `autoImport` — defines all public sbt settings (`flywayUrl`, `flywayUser`, etc.) and tasks (`flywayMigrate`, `flywayValidate`, `flywayInfo`, `flywayClean`, `flywayBaseline`, `flywayRepair`)
- Private `Config*` case classes — group related settings into typed configuration bundles
- `FluentConfigurationOps` implicit — chains config bundles onto Flyway's `FluentConfiguration`
- `withContextClassLoader` — sets up the classloader from sbt's classpath so Flyway can find JDBC drivers and migrations
- `SbtLogCreator` / `FlywaySbtLog` — bridges Flyway's logging to sbt's logger

The plugin is `noTrigger` — users must explicitly `enablePlugins(FlywayPlugin)`. Settings are provided for both `Runtime` and `Test` configurations.

## Publishing

Published to Maven Central via Sonatype (`s01.oss.sonatype.org`). Configuration is in `publish.sbt`. Uses `sbt-sonatype` and `sbt-pgp` plugins.
