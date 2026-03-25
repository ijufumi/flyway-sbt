package io.github.ijufumi

import org.flywaydb.core.Flyway
import org.flywaydb.core.internal.info.MigrationInfoDumper
import sbt.Keys.*
import sbt.*

import FlywayPlugin.*
import FlywayPlugin.autoImport.*

object FlywayTaskDefs {

  def flywayTaskSettings(conf: Configuration): Seq[Setting[?]] = {
    Seq[Setting[?]](
      flywayConfigDataSource := Def.uncached {
        ConfigDataSource(
          flywayDriver.value,
          flywayUrl.value,
          flywayUser.value,
          flywayPassword.value,
        )
      },
      flywayConfigBase := Def.uncached {
        ConfigBase(
          flywaySchemas.value,
          flywayTable.value,
          flywayBaselineVersion.value,
          flywayBaselineDescription.value,
        )
      },
      flywayConfigMigrationLoading := Def.uncached {
        ConfigMigrationLoading(
          flywayLocations.value,
          flywayResolvers.value,
          flywaySkipDefaultResolvers.value,
          flywayEncoding.value,
          flywayCleanOnValidationError.value,
          flywayCleanDisabled.value,
          flywayTarget.value,
          flywayOutOfOrder.value,
          flywayCallbacks.value,
          flywaySkipDefaultCallbacks.value,
        )
      },
      flywayConfigSqlMigration := Def.uncached {
        ConfigSqlMigration(
          flywaySqlMigrationPrefix.value,
          flywayRepeatableSqlMigrationPrefix.value,
          flywaySqlMigrationSeparator.value,
          flywaySqlMigrationSuffixes.value,
        )
      },
      flywayConfigMigrate := Def.uncached {
        ConfigMigrate(
          flywayIgnoreMissingMigrations.value,
          flywayIgnoreFutureMigrations.value,
          flywayIgnoreFailedFutureMigration.value,
          flywayBaselineOnMigrate.value,
          flywayValidateOnMigrate.value,
          flywayMixed.value,
          flywayGroup.value,
          flywayInstalledBy.value,
        )
      },
      flywayConfigPlaceholder := Def.uncached {
        ConfigPlaceholder(
          flywayPlaceholderReplacement.value,
          flywayPlaceholders.value,
          flywayPlaceholderPrefix.value,
          flywayPlaceholderSuffix.value,
        )
      },
      flywayConfig := Def.uncached {
        Config(
          flywayConfigDataSource.value,
          flywayConfigBase.value,
          flywayConfigMigrationLoading.value,
          flywayConfigSqlMigration.value,
          flywayConfigMigrate.value,
          flywayConfigPlaceholder.value,
        )
      },
      flywayClasspath := Def.uncached {
        Def.taskDyn {
          if (flywayLocations.value.forall(_.startsWith("filesystem:"))) {
            conf / externalDependencyClasspath
          } else {
            conf / fullClasspath
          }
        }.value
      },
      flywayDefaults := Def.uncached {
        withPrepared(flywayClasspath.value, streams.value)(
          Flyway.configure(),
        )
      },
      flywayMigrate := Def.uncached {
        flywayDefaults.value
          .configure(flywayConfig.value)
          .migrate()
      },
      flywayValidate := Def.uncached {
        flywayDefaults.value
          .configure(flywayConfig.value)
          .validate()
      },
      flywayInfo := Def.uncached {
        val info = flywayDefaults.value.configure(flywayConfig.value).info()
        streams.value.log.info(MigrationInfoDumper.dumpToAsciiTable(info.all()))
      },
      flywayRepair := Def.uncached {
        flywayDefaults.value
          .configure(flywayConfig.value)
          .repair()
      },
      flywayClean := Def.uncached {
        flywayDefaults.value.configure(flywayConfig.value).clean()
      },
      flywayBaseline := Def.uncached {
        flywayDefaults.value
          .configure(flywayConfig.value)
          .baseline()
      },
    )
  }
}
