package com.company.process

import org.apache.spark.sql.functions.{count, desc}
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.util.Try

object MostRatedMovies {

  val outputPath = "C:\\output\\mostRatedMovies"

  def process(ratingsDf: DataFrame, moviesDf: DataFrame)(implicit spark: SparkSession): Try[DataFrame] = Try {

    val countRatingsDf = ratingsDf
                            .groupBy("movieID")
                            .agg(count("rating").alias("views"))
      
    val topCountRatingsMoviesDf =  countRatingsDf
                                              .join(
                                                      moviesDf, 
                                                      countRatingsDf("movieID") === moviesDf("movieID"), 
                                                      "inner"
                                                    )
                                              .orderBy(desc("views"))
                                              .select("title", "views")

    topCountRatingsMoviesDf
  }
}