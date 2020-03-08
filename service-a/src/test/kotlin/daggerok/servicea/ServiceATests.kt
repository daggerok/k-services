package daggerok.servicea

import org.apache.logging.log4j.LogManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration

// @ExtendWith(SpringExtension::class) // @RunWith(SpringRunner::class)
@SpringBootTest(
    classes = [ServiceA::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [MyBeanInitializer::class])
class ServiceATests {

  val log = LogManager.getLogger()

  @Autowired
  lateinit var ctx: ApplicationContext

  @Test // @org.junit.Test
  fun test() {
    val beans = ctx.beanDefinitionNames
        .filter { it.contains("dag") }
        .onEach { log.info(it) }
    assertThat(beans).hasSizeGreaterThan(1)
  }
}
