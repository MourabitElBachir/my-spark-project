package com.company

import org.apache.spark.sql.SparkSession

object Main extends App {

    // Start Spark
    implicit val spark = SparkSession.builder
      .appName("My Spark Job")
      .master("local")
      .getOrCreate()

    // Prevent INFO logs
    spark.sparkContext.setLogLevel("ERROR")

    // Run Process Data
    Job.run()

    // Stop Spark
    spark.stop()
}