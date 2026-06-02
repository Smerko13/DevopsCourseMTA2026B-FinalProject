package mta

/**
 * Load Test - based on scenario recorded from HAR file (scenario.har)
 * Uses the same HTTP protocol and request as RecordedSimulation.
 * Injection profile: ramp to 50 users over 2 min, hold for 3 min (5 min total).
 */

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTestSimulation extends Simulation {

  // Same protocol as RecordedSimulation (from HAR)
  val httpProtocol = http
    .baseUrl("http://100.26.63.88:8082")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9,he;q=0.8")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36")

  // Same scenario as RecordedSimulation (from HAR)
  val scn = scenario("Load Test - Course Registration Page")
    .exec(
      http("GET /FinalProject/a/index.jsp")
        .get("/FinalProject/a/index.jsp")
        .check(status.is(200))
        .check(substring("הרשמה לקורס דבאופס"))
    )
    .pause(1, 3)

  // Load Test: ramp to max stable users over 5 minutes
  setUp(
    scn.inject(
      rampUsersPerSec(1).to(50).during(2.minutes),   // ramp up
      constantUsersPerSec(50).during(3.minutes)       // hold load
    )
  ).protocols(httpProtocol)
   .assertions(
     global.responseTime.max.lt(5000),               // max response < 5s
     global.successfulRequests.percent.gt(95)        // >95% success rate
   )
}
