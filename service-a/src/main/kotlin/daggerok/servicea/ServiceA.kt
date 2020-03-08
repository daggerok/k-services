package daggerok.servicea

import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class ServiceA

class MyBeanInitializer : ApplicationContextInitializer<GenericApplicationContext> {
  override fun initialize(applicationContext: GenericApplicationContext) {
    myInitializers.initialize(applicationContext)
  }
}

private val log = LogManager.getLogger()

private val myInitializers = beans {
  bean("dag1") {
    log.info("map bean initialization...")
    mapOf("ololo" to "trololo")
  }
  bean("dag2") {
    ApplicationRunner {
      log.info("application runner $it")
    }
  }
  bean("dag3") {
    InitializingBean {
      log.info("initializing bean...")
    }
  }
  bean {
    router {
      log.info("logger on start...")
      "/".nest {
        GET("/") {
          log.info("GET logger...")
          ok().bodyValue(mapOf(
              "message" to "Hello!",
              "_self" to it.uri()
          ))
        }
        path("/**") {
          log.info("path logger...")
          /*
          it.uri().run {
            val baseUrl = "$scheme://$authority"
            ok().body(
                mapOf("api" to "$baseUrl/")
                    .toMono()
            )
          }
          */
          val uri = it.uri()
          val baseUrl = uri.run { "$scheme://$authority" }
          /*
          ok().body(mapOf("api" to "$baseUrl/")
              .toMono())
          */
          ok().bodyValue(mapOf(
              "api" to "$baseUrl/",
              "_self" to uri
          ))
        }
      }
    }
  }
}

fun main(args: Array<String>) {

  // boot 2.3.x receiver
  // val context = runApplication<ServiceA>(*args) {
  //   setAllowBeanDefinitionOverriding(true)
  //   setBannerMode(Banner.Mode.OFF)
  //   addInitializers(myInitializers)
  // }
  // myInitializers.initialize(context as GenericApplicationContext)

  // boot 2.3.x builder
  val context = SpringApplicationBuilder(ServiceA::class.java)
      // .properties(mapOf("context.initializer.classes" to MyBeanInitializer::class.java.name))
      // .properties("context.initializer.classes=${MyBeanInitializer::class.qualifiedName}")
      .properties("context.initializer.classes=${MyBeanInitializer::class.java.name}")
      // .initializers(myInitializers) // manual, cannot be tested...
      .bannerMode(Banner.Mode.OFF)
      .run(*args)
  // myInitializers.initialize(context as GenericApplicationContext)

  val dag1 = context.getBean("dag1")
  log.info("I have found bean dag1: {}", dag1)
}

/*
fun main(args: Array<String>) {

  // boot 2.2.5.RELEASE
  val context = runApplication<ServiceA>(*args) {
  	addInitializers(beans {
  		bean {
  			log.info("map bean initialization...")
  			mapOf("ololo" to "trololo")
  		}
  		bean {
  			ApplicationRunner {
  				log.info("application runner $it")
  			}
  		}
  		bean {
  			InitializingBean {
  				log.info("initializing bean...")
  			}
  		}
  	})
  }

  // // or with builder
  // SpringApplicationBuilder()
  // 		.sources(ServiceA::class.java)
  // 		.initializers(beans {
  // 			// ...
  // 		})
  // 		.run(*args)

  // myInitializers.initialize(context as GenericApplicationContext)

  val dag1 = context.getBean("dag1")
  log.info("I have found bean dag1: {}", dag1)
}
*/
