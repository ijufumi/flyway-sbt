# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

flyway-sbt is an sbt plugin that integrates [Flyway](https://flywaydb.org) database migrations into sbt builds. It is a fork of the original `flyway/flyway-sbt`, republished under `io.github.ijufumi`. The plugin wraps Flyway Core 9.22.3 and exposes Flyway operations as sbt tasks.

## Build & Test

- **sbt version**: 1.12.8
- **Scala**: Cross-builds for 2.12.20 (sbt 1.x) and 3.8.2 (sbt 2.x)
- **Java**: Tested on JDK 11, 17, 21

```bash
# Compile (cross-build)
sbt +compile

# Compile for specific Scala version
sbt "++2.12.20 compile"
sbt "++3.8.2 compile"

# Run scripted (integration) tests — this is the primary test suite
sbt "++2.12.20 scripted"

# Publish locally for testing
sbt publishLocal
```

There are no unit tests. All testing uses sbt's [scripted test framework](http://www.scala-sbt.org/1.x/docs/Testing-sbt-plugins.html) located in `src/sbt-test/flyway-sbt/`. Each `test1`–`test4` directory is an independent sbt project with its own `build.sbt`, `plugins.sbt`, and `test` script.

## Architecture

The plugin implementation is split across shared and version-specific source directories:

- `src/main/scala/` — shared code (FlywayPlugin.scala)
- `src/main/scala-2.12/` — Scala 2.12 / sbt 1.x specific code
- `src/main/scala-3/` — Scala 3 / sbt 2.x specific code

**Key structure within FlywayPlugin:**
- `autoImport` — defines all public sbt settings (`flywayUrl`, `flywayUser`, etc.) and tasks (`flywayMigrate`, `flywayValidate`, `flywayInfo`, `flywayClean`, `flywayBaseline`, `flywayRepair`)
- `Config*` case classes — group related settings into typed configuration bundles
- `FluentConfigurationOps` implicit — chains config bundles onto Flyway's `FluentConfiguration`
- `withContextClassLoader` — sets up the classloader from sbt's classpath so Flyway can find JDBC drivers and migrations
- `SbtLogCreator` / `FlywaySbtLog` — bridges Flyway's logging to sbt's logger

**Version-specific code:**
- `compat.scala` — provides `CollectionConverters` (JavaConverters for 2.12, jdk.CollectionConverters for 3) and `classpathUrls` (handles File vs VirtualFileRef classpath differences)
- `FlywayTaskDefs.scala` — task definitions (sbt 2.x requires `Def.uncached` for tasks without `HashWriter`)

The plugin is `noTrigger` — users must explicitly `enablePlugins(FlywayPlugin)`. Settings are provided for both `Runtime` and `Test` configurations.

## Publishing

Published to Maven Central via Sonatype (`s01.oss.sonatype.org`). Configuration is in `publish.sbt`. Uses `sbt-sonatype` and `sbt-pgp` plugins.
