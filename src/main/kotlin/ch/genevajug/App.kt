/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package ch.genevajug

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    val ctx = runApplication<DemoApplication>(*args)

}
