package daggerok.fua

import org.springframework.boot.WebApplicationType
import org.springframework.fu.kofu.application
import org.springframework.fu.kofu.webflux.webFlux
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import kotlin.random.Random

class Handlers {
  fun root(request: ServerRequest) = ok().bodyValue(mapOf("spring" to "fu"))
  fun fallback(request: ServerRequest) = request.uri().run {
    val baseUrl = "$scheme://$authority"
    ok().bodyValue(mapOf(
        "root" to "$baseUrl/",
        "_self" to this
    ))
  }
}

val app = application(WebApplicationType.REACTIVE) {
  beans {
    bean<Handlers>()
  }
  webFlux {
    engine = netty() // optional
    port = if (profiles.contains("test")) Random.nextInt(30000, Short.MAX_VALUE.toInt()) else 8003
    router {
      val handlers = ref<Handlers>()
      GET("/", handlers::root)
      path("/**", handlers::fallback)
    }
    codecs {
      string()
      jackson()
    }
  }
}

fun main() {
  app.run()
}
