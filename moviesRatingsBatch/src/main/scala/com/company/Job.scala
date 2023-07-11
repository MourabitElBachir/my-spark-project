package com.company

import com.company.models.{MovieModel, RatingModel}
import com.company.process.{BadRatedMovies, MostRatedMovies, TopRatedMovies}
import org.apache.spark.sql.{DataFrame, SparkSession}
import reader.Reader

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}

import scala.concurrent.ExecutionContext.Implicits.global

object Job {

  def run()(implicit spark: SparkSession): Unit = {

    // Reading
    val ratingsDfT = new Reader().read(RatingModel)
    val moviesDfT = new Reader().read(MovieModel)

    // Process
    ratingsDfT match {
      case Success(ratingsDf) =>
        moviesDfT match {
          case Success(moviesDf) =>

            // Processing 1
            val mostRatedMovies = Future {MostRatedMovies.process(ratingsDf, moviesDf)}
            // Processing 2
            val topRatedMovies =  Future {TopRatedMovies.process(ratingsDf, moviesDf)}
            // Processing 3
            val badRatedMovies =  Future {BadRatedMovies.process(ratingsDf, moviesDf)}

            val allFutures = Future.sequence(Seq(mostRatedMovies, topRatedMovies, badRatedMovies))

            val result = Await.result(allFutures, Duration.Inf)

            result.foreach{
              case Success(resultDf) => resultDf.show(10)
              case Failure(exception) => println(s"Error in result Df : ${exception.getMessage}")
            }
          case Failure(exception) => println(s"Error in result Df : ${exception.getMessage}")
        }
      case Failure(exception) => println(s"Error in result Df : ${exception.getMessage}")
    }

    // Writing (Persist)

  }

}
