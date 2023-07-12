package com.company.process

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.{avg, desc}

import scala.util.Try

object TopRatedMovies {

  val outputPath = "C:\\output\\topRatedMovies"

  def process(ratingsDf: DataFrame, moviesDf: DataFrame)(implicit spark: SparkSession): Try[DataFrame] = Try {

    val avgRatingsDf = ratingsDf
      .groupBy("movieID")
      .agg(avg("rating").alias("avgRating"))

    val topCountRatingsMoviesDf = avgRatingsDf
      .join(
        moviesDf,
        avgRatingsDf("movieID") === moviesDf("movieID"),
        "inner"
      )
      .orderBy(desc("avgRating"))
      .select("title", "avgRating")

    topCountRatingsMoviesDf
  }
}
