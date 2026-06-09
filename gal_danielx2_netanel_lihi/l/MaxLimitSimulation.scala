package mta

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class MaxLimitSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://100.26.63.88:8082")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9,he;q=0.8")

  val scn = scenario("Max Limit - Registration Page")
    .exec(
      http("GET Registration Page")
        .get("/gal_danielx2_netanel_lihi/a/")
        .check(status.in(200, 503))
    )

  setUp(
    scn.inject(
      nothingFor(5.seconds),
      rampUsersPerSec(1).to(50).during(1.minute),
      rampUsersPerSec(50).to(150).during(1.minute),
      rampUsersPerSec(150).to(300).during(1.minute),
      constantUsersPerSec(300).during(1.minute),
      rampUsersPerSec(300).to(1).during(30.seconds)
    )
  ).protocols(httpProtocol)
}
