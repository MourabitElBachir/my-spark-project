package com.company.process

import org.apache.spark.sql.functions.{avg, count, desc}
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.util.Try

object BadRatedMovies {
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
      .orderBy("avgRating")
      .select("title", "avgRating")

    topCountRatingsMoviesDf
  }
}
