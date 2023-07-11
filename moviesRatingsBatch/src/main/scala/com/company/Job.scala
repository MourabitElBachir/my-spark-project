package com.company

import com.company.models.{MovieModel, RatingModel}
import com.company.process.MostRatedMovies
import org.apache.spark.sql.{DataFrame, SparkSession}
import reader.Reader

import scala.util.{Failure, Success, Try}


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
          val resultTry = MostRatedMovies.process(ratingsDf, moviesDf)
            resultTry match {
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
