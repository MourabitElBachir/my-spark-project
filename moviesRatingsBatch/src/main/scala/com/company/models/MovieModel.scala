package com.company.models

import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

object MovieModel extends Model {
  val path: String = "C:\\Users\\bachi\\Spark scala\\workspace\\my-spark-project\\moviesRatingsBatch\\src\\main\\resources\\u.item"
  val delimiter: String = "|"
  val schema: StructType = new StructType()
    .add("movieID", IntegerType)
    .add("title", StringType)
    .add("date", StringType)
}
