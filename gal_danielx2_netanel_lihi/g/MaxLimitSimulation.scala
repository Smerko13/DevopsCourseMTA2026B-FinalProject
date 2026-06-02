package mta

/**
 * Max Limit Test - based on scenario recorded from HAR file (scenario.har)
 * Uses the same HTTP protocol and request as RecordedSimulation.
 * Strategy: aggressively ramp to 300 users/sec to find the breaking point.
 * NO assertions - we let the full test run so the HTML report captures
 * exactly where response times explode and errors begin.
 *
 * Target: http://100.26.63.88:8082
 * Result: breaking point found at ~113 successful req/sec (63% failure at 300 req/sec)
 */

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class MaxLimitSimulation extends Simulation {

  // Same protocol as RecordedSimulation (from HAR)
  val httpProtocol = http
    .baseUrl("http://100.26.63.88:8082")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9,he;q=0.8")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36")

  // Same scenario as RecordedSimulation (from HAR)
  val scn = scenario("Max Limit Test - Course Registration Page")
    .exec(
      http("GET /FinalProject/a/index.jsp")
        .get("/FinalProject/a/index.jsp")
        .check(status.in(200, 503)) // accept all - we want to observe, not fail early
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
