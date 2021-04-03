package controllers

import org.scalatest.OptionValues
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class IndexControllerSpec extends AnyWordSpec with Matchers with OptionValues with Injecting with GuiceOneAppPerTest {

  "IndexController GET" should {

    "return \"Hello, Play!!!\"" in {
      val request = FakeRequest(GET, "/")
      val index = route(app, request).get

      status(index) mustBe OK
      contentType(index) mustBe Some("text/plain")
      contentAsString(index) mustBe "Hello, Play!!!"
    }

    "return \"Hello, Play!!!\" from the application" in {
      val controller = inject[IndexController]
      val index = controller.index().apply(FakeRequest(GET, "/"))

      status(index) mustBe OK
      contentType(index) mustBe Some("text/plain")
      contentAsString(index) mustBe "Hello, Play!!!"
    }
  }
}
