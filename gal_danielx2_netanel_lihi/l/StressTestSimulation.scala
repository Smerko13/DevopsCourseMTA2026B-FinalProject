package mta

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class StressTestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://100.26.63.88:8082")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9,he;q=0.8")

  val scn = scenario("Stress Test - Registration Page")
    .exec(
      http("GET Registration Page")
        .get("/gal_danielx2_netanel_lihi/a/")
        .check(status.in(200, 503))
    )
    .pause(1, 2)

  // 5 minutes total: ramp aggressively past the ~99 req/sec limit
  // Goal: observe degradation and failure behavior under stress
  setUp(
    scn.inject(
      rampUsersPerSec(1).to(10).during(30.seconds),    // warm up
      rampUsersPerSec(10).to(50).during(1.minute),     // normal load
      rampUsersPerSec(50).to(100).during(1.minute),    // at the limit
      rampUsersPerSec(100).to(200).during(1.minute),   // beyond the limit
      constantUsersPerSec(200).during(1.minute),       // hold under stress
      rampUsersPerSec(200).to(1).during(30.seconds)    // cool down
    )
  ).protocols(httpProtocol)
}
