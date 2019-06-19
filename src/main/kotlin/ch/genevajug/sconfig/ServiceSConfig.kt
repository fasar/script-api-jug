package ch.genevajug.sconfig

import ch.genevajub.config.model.MyConfig
import ch.genevajug.github.services.GitHubService
import feign.Feign
import feign.Logger
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.slf4j.Slf4jLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceSConfig {

    @Autowired
    lateinit var myConfig: MyConfig

    @Bean
    fun gitHubService(): GitHubService {

        return Feign.builder()
                .encoder(JacksonEncoder())
                .decoder(JacksonDecoder())
                .logger(Slf4jLogger())
                .logLevel(Logger.Level.BASIC)
                .requestInterceptor { template ->
                    val token = myConfig.github.token
                    // println("Detected Authorization token from environment variable")
                    template.header("Accept", "application/vnd.github.v3+json")
                    template.header("Authorization", "token ${token}")

                }
                .target(GitHubService::class.java, "https://api.github.com")
    }
}