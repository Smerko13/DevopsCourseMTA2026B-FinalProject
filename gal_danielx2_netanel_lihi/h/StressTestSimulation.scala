package mta

/**
 * Stress Test - based on scenario recorded from HAR file (scenario.har)
 * Uses the same HTTP protocol and request as RecordedSimulation.
 * Injection profile: ramp to 200 users to find the breaking point (5 min total).
 */

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class StressTestSimulation extends Simulation {

  // Same protocol as RecordedSimulation (from HAR)
  val httpProtocol = http
    .baseUrl("http://100.26.63.88:8082")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9,he;q=0.8")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36")

  // Same scenario as RecordedSimulation (from HAR)
  val scn = scenario("Stress Test - Course Registration Page")
    .exec(
      http("GET /FinalProject/a/index.jsp")
        .get("/FinalProject/a/index.jsp")
        .check(status.is(200))
        .check(substring("הרשמה לקורס דבאופס"))
    )
    .pause(1, 2)

  // Stress Test: keep ramping up users to find breaking point
  setUp(
    scn.inject(
      rampUsersPerSec(1).to(10).during(30.seconds),   // warm up
      rampUsersPerSec(10).to(50).during(1.minutes),   // ramp to load level
      rampUsersPerSec(50).to(100).during(1.minutes),  // push beyond normal
      rampUsersPerSec(100).to(200).during(1.minutes), // heavy stress
      constantUsersPerSec(200).during(1.minutes),     // hold max stress
      rampUsersPerSec(200).to(10).during(30.seconds)  // cool down
    )
  ).protocols(httpProtocol)
   .assertions(
     global.responseTime.max.lt(10000),              // max response < 10s
     global.successfulRequests.percent.gt(80)        // accept >80% under stress
   )
}
