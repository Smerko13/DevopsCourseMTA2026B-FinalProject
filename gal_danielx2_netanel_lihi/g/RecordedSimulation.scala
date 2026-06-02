package mta

/**
 * AUTO-GENERATED from HAR file (scenario.har) recorded on 2026-06-02
 * Tool: Gatling Recorder (HAR Converter mode)
 * Source: Chrome DevTools -> Network -> Save as HAR
 *
 * Recorded scenario:
 *   1. Navigate to http://100.26.63.88:8082/FinalProject/a/index.jsp (GET -> 200)
 *   2. Page loads with 396ms response time (including 200ms connect + 179ms wait)
 *
 * External requests (fonts.googleapis.com) were filtered out as they are
 * third-party resources not under test.
 */

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class RecordedSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://100.26.63.88:8082")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9,he;q=0.8")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36")
    .upgradeInsecureRequestsHeader("1")

  // Scenario recorded from HAR: single page load of the registration page
  val recordedScenario = scenario("Recorded - Registration Page Load")
    .exec(
      http("GET /FinalProject/a/index.jsp")
        .get("/FinalProject/a/index.jsp")
        .check(status.is(200))
        .check(substring("הרשמה לקורס דבאופס"))
    )
    .pause(1) // simulates user reading the page

  // Default run: 1 user, just to verify the recording works
  setUp(
    recordedScenario.inject(atOnceUsers(1))
  ).protocols(httpProtocol)
}
