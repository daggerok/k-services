package daggerok.serviceb

import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.HandlerFunction
import org.springframework.web.servlet.function.RequestPredicates.path
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.router
import kotlin.math.log

@SpringBootApplication
class ServiceB

@Component
class MyBeanInitializer : ApplicationContextInitializer<GenericApplicationContext> {
  override fun initialize(applicationContext: GenericApplicationContext) {
    myBeans.initialize(applicationContext)
  }
}

private val log = LogManager.getLogger()

private val myBeans = beans {
  bean {
    router {
      "/".nest {
        GET("/") {
          ok().body(mapOf(
              "ololo" to "trololo",
              "_self" to it.uri()
          ))
        }
        val fallback = { request: ServerRequest ->
          mapOf(
              "who" to "cares",
              "_self" to request.uri(),
              "_segment" to request.path()
          )
        }
        path("/**") {
          val request = it
          log.warn("inner path segment ignored...")
          ok().body(fallback(request))
        }
      }
    }
  }
}

fun main(args: Array<String>) {
  // runApplication<ServiceB>(*args)
  SpringApplicationBuilder(ServiceB::class.java)
      .properties("context.initializer.classes=${MyBeanInitializer::class.java.name}")
      .run(*args)
}
