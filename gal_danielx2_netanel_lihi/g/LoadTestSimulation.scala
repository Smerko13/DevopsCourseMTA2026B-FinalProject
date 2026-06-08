package mta

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://100.26.63.88:8082")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.9,he;q=0.8")

  val scn = scenario("Load Test - Registration Page")
    .exec(
      http("GET Registration Page")
        .get("/gal_danielx2_netanel_lihi/a/")
        .check(status.is(200))
    )
    .pause(1, 3)

  // 5 minutes total: ramp to 50 req/sec over 2 min, hold for 3 min
  // 50 req/sec is well below the ~99 req/sec practical max limit
  setUp(
    scn.inject(
      rampUsersPerSec(1).to(50).during(2.minutes),
      constantUsersPerSec(50).during(3.minutes)
    )
  ).protocols(httpProtocol)
   .assertions(
     global.responseTime.max.lt(5000),
     global.successfulRequests.percent.gt(95)
   )
}
