package daggerok.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class Gateway

private val myBeans = beans {
  bean {
    router {
      "/".nest {
        GET("/") {
          ok().bodyValue(mapOf("message" to "from gateway"))
        }
        path("/api/**") {
          val baseUrl = it.uri().run { "$scheme://$authority" }
          ok().bodyValue(listOf(
              "$baseUrl/service-a/{segment}",
              "$baseUrl/service-a/",
              "$baseUrl/service-a",
              "$baseUrl/a/{segment}",
              "$baseUrl/a/",
              "$baseUrl/a",
              "$baseUrl/service-b/{segment}",
              "$baseUrl/service-b/",
              "$baseUrl/service-b",
              "$baseUrl/b/{segment}",
              "$baseUrl/b/",
              "$baseUrl/b",
              "$baseUrl/{segment}",
              "$baseUrl/"
          ))
        }
      }
    }
  }
}

class MyBeanInitializer : ApplicationContextInitializer<GenericApplicationContext> {
  override fun initialize(applicationContext: GenericApplicationContext) {
    myBeans.initialize(applicationContext)
  }
}

fun main(args: Array<String>) {
  SpringApplicationBuilder(Gateway::class.java)
      .properties("context.initializer.classes=${MyBeanInitializer::class.java.name}")
      .run(*args)
}
