package mta

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

/**
 * Max Limit Test - finds the application's breaking point.
 * Strategy: continuously ramp up concurrent users until response times
 * degrade and error rate rises. The Gatling report will show exactly
 * where the system starts to fail (the "knee" of the curve).
 *
 * Target: http://100.26.63.88:8082
 * Expected max limit: somewhere between 100-300 users/sec based on stress test results.
 */
class MaxLimitSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://100.26.63.88:8082")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Gatling MaxLimitTest")

  val scn = scenario("Max Limit Test - Course Registration Page")
    .exec(
      http("Open Index Page")
        .get("/FinalProject/a/index.jsp")
        .check(status.is(200))
    )
    .pause(1)
    .exec(
      http("Submit Registration")
        .post("/FinalProject/a/index.jsp")
        .formParam("username", "Moshe Mamia")
        .check(status.in(200, 302, 500, 503)) // accept all - we want to observe, not fail early
    )
    .pause(1)

  // Max Limit: aggressively ramp up to 300 users/sec to find the breaking point.
  // Intentionally NO assertions - we let the test run fully so the HTML report
  // captures the complete picture: where response times explode and errors begin.
  setUp(
    scn.inject(
      nothingFor(5.seconds),                              // give server a moment
      rampUsersPerSec(1).to(50).during(1.minute),        // gentle warm-up
      rampUsersPerSec(50).to(150).during(1.minute),      // approaching limit
      rampUsersPerSec(150).to(300).during(1.minute),     // past expected limit
      constantUsersPerSec(300).during(1.minute),         // hold at max to confirm break
      rampUsersPerSec(300).to(1).during(30.seconds)      // cool down
    )
  ).protocols(httpProtocol)
}
