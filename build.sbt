// Define root project
lazy val root = (project in file("."))
    .aggregate(moviesRatingsBatch, moviesRatingsStreaming)
    .settings(
        name := "my-spark-project",
        version := "0.1",
        scalaVersion := "2.12.10"
    ).dependsOn(moviesRatingsBatch)

// Define Module 1

lazy val moviesRatingsBatch = (project in file("moviesRatingsBatch"))
    .settings(
        name := "moviesRatingsBatch",
        version := "0.1",
        scalaVersion := "2.12.10",
        libraryDependencies += "org.apache.spark" %% "spark-core" % "3.0.1",
        libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.0.1"
    )


// Define Module 2

lazy val moviesRatingsStreaming = (project in file("moviesRatingsStreaming"))
    .settings(
        name := "moviesRatingsStreaming",
        version := "0.1",
        scalaVersion := "2.12.10",
        libraryDependencies += "org.apache.spark" %% "spark-core" % "3.0.1",
        libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.0.1"
    )