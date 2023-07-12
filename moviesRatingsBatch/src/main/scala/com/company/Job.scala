package com.company

import com.company.models.{MovieModel, RatingModel}
import com.company.process.{BadRatedMovies, MostRatedMovies, TopRatedMovies}
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
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

            val jobs = Map(
                MostRatedMovies.outputPath -> Future {MostRatedMovies.process(ratingsDf, moviesDf)},
                TopRatedMovies.outputPath -> Future {TopRatedMovies.process(ratingsDf, moviesDf)},
                BadRatedMovies.outputPath -> Future {TopRatedMovies.process(ratingsDf, moviesDf)}
            )

            val jobsResult = jobs.mapValues(Await.result(_, Duration.Inf))

            jobsResult.foreach{
              // Write in Hadoop
              case (outputPath, resultDfT) =>
                resultDfT match {
                  case Success(resultDf) =>
                                            resultDf
                                              .write
                                              .mode(SaveMode.Overwrite)
                                              .csv(outputPath)
                  case Failure(exception) =>
                                        println(s"Error in result Df : ${exception.getMessage}")
                }
            }
          case Failure(exception) => println(s"Error in result Df : ${exception.getMessage}")
        }
      case Failure(exception) => println(s"Error in result Df : ${exception.getMessage}")
    }
  }
}
