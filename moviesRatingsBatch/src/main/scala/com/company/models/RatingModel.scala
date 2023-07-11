package com.company.models

import org.apache.spark.sql.types.{IntegerType, StructType}

object RatingModel extends Model {
  val path: String = "C:\\Users\\bachi\\Spark scala\\workspace\\my-spark-project\\moviesRatingsBatch\\src\\main\\resources\\u.data"
  val delimiter: String = "\t"
  val schema: StructType = new StructType()
    .add("userID", IntegerType)
    .add("movieID", IntegerType)
    .add("rating", IntegerType)
    .add("timestamp", IntegerType)
}
