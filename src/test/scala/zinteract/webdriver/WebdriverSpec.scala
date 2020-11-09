package zinteract.test

import zio.test._
import zio.test.Assertion._

import zinteract.session
import zinteract.webdriver.BuilderOps.{chrome, firefox}
import zinteract.webdriver.{ChromeBlueprintOps, FirefoxBlueprintOps}

object WebdriverSpec extends DefaultRunnableSpec {
  def suiteWebdriverLayer =
    suite("Webdriver Layer Spec")(
      testM("We can use chromedriver") {
        val blueprint = ChromeBlueprintOps.headless
        val builder   = chrome at s"${System.getenv("HOME")}/Webdriver/chromedriver" using blueprint

        val effect = session.link("https://www.google.com/") *> session.url

        assertM(effect.provideCustomLayer(builder.buildLayer))(equalTo("https://www.google.com/"))
      },
      testM("We can use geckodriver") {
        val blueprint = FirefoxBlueprintOps.headless
        val builder   = firefox at s"${System.getenv("HOME")}/Webdriver/geckodriver" using blueprint

        val effect = session.link("https://www.google.com/") *> session.url

        assertM(effect.provideCustomLayer(builder.buildLayer))(equalTo("https://www.google.com/"))
      }
    )

  def spec = suite("Webdriver Spec")(suiteWebdriverLayer)
}
