package mta

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class StressTestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://100.26.63.88:8082")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Gatling StressTest")

  val scn = scenario("Stress Test - Course Registration Page")
    .exec(
      http("Open Index Page")
        .get("/FinalProject/a/index.jsp")
        .check(status.is(200))
        .check(substring("הרשמה לקורס דבאופס"))
    )
    .pause(1, 2)
    .exec(
      http("Submit Registration")
        .post("/FinalProject/a/index.jsp")
        .formParam("username", "Moshe Mamia")
        .check(status.in(200, 302))
    )
    .pause(1)

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
