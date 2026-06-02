package mta

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://100.26.63.88:8082")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Gatling LoadTest")

  val scn = scenario("Load Test - Course Registration Page")
    .exec(
      http("Open Index Page")
        .get("/FinalProject/a/index.jsp")
        .check(status.is(200))
        .check(substring("הרשמה לקורס דבאופס"))
    )
    .pause(1, 3)
    .exec(
      http("Submit Registration")
        .post("/FinalProject/a/index.jsp")
        .formParam("username", "Moshe Mamia")
        .check(status.in(200, 302))
    )
    .pause(1, 2)

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
