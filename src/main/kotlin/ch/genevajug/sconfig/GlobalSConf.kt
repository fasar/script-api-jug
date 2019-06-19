package ch.genevajug.sconfig

import ch.genevajub.config.model.MyConfig
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(value = [MyConfig::class])
@Configuration()
@EnableConfigurationProperties
@EnableCaching
class GlobalSConf