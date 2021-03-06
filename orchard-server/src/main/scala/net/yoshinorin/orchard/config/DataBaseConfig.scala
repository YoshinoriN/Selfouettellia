package net.yoshinorin.orchard.config

object DataBaseConfig extends ConfigProvider {
  val url: String = configuration.getString("db.ctx.dataSource.url")
  val user: String = configuration.getString("db.ctx.dataSource.user")
  val password: String = configuration.getString("db.ctx.dataSource.password")

  val migration: Boolean = configuration.getBoolean("db.migration")
}
