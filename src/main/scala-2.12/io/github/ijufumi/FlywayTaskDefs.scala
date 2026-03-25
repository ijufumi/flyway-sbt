package io.github.ijufumi

import org.flywaydb.core.Flyway
import org.flywaydb.core.internal.info.MigrationInfoDumper
import sbt.Keys._
import sbt._

import FlywayPlugin._
import FlywayPlugin.autoImport._

object FlywayTaskDefs {

  def flywayTaskSettings(conf: Configuration): Seq[Setting[_]] = {
    Seq[Setting[_]](
      flywayConfigDataSource := ConfigDataSource(
        flywayDriver.value,
        flywayUrl.value,
        flywayUser.value,
        flywayPassword.value,
      ),
      flywayConfigBase := ConfigBase(
        flywaySchemas.value,
        flywayTable.value,
        flywayBaselineVersion.value,
        flywayBaselineDescription.value,
      ),
      flywayConfigMigrationLoading := ConfigMigrationLoading(
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
      ),
      flywayConfigSqlMigration := ConfigSqlMigration(
        flywaySqlMigrationPrefix.value,
        flywayRepeatableSqlMigrationPrefix.value,
        flywaySqlMigrationSeparator.value,
        flywaySqlMigrationSuffixes.value,
      ),
      flywayConfigMigrate := ConfigMigrate(
        flywayIgnoreMissingMigrations.value,
        flywayIgnoreFutureMigrations.value,
        flywayIgnoreFailedFutureMigration.value,
        flywayBaselineOnMigrate.value,
        flywayValidateOnMigrate.value,
        flywayMixed.value,
        flywayGroup.value,
        flywayInstalledBy.value,
      ),
      flywayConfigPlaceholder := ConfigPlaceholder(
        flywayPlaceholderReplacement.value,
        flywayPlaceholders.value,
        flywayPlaceholderPrefix.value,
        flywayPlaceholderSuffix.value,
      ),
      flywayConfig := Config(
        flywayConfigDataSource.value,
        flywayConfigBase.value,
        flywayConfigMigrationLoading.value,
        flywayConfigSqlMigration.value,
        flywayConfigMigrate.value,
        flywayConfigPlaceholder.value,
      ),
      flywayClasspath := Def.taskDyn {
        if (flywayLocations.value.forall(_.startsWith("filesystem:"))) {
          conf / externalDependencyClasspath
        } else {
          conf / fullClasspath
        }
      }.value,
      flywayDefaults := withPrepared(flywayClasspath.value, streams.value)(
        Flyway.configure(),
      ),
      flywayMigrate := flywayDefaults.value
        .configure(flywayConfig.value)
        .migrate(),
      flywayValidate := flywayDefaults.value
        .configure(flywayConfig.value)
        .validate(),
      flywayInfo := {
        val info = flywayDefaults.value.configure(flywayConfig.value).info()
        streams.value.log.info(MigrationInfoDumper.dumpToAsciiTable(info.all()))
      },
      flywayRepair := flywayDefaults.value
        .configure(flywayConfig.value)
        .repair(),
      flywayClean := flywayDefaults.value.configure(flywayConfig.value).clean(),
      flywayBaseline := flywayDefaults.value
        .configure(flywayConfig.value)
        .baseline(),
    )
  }
}
