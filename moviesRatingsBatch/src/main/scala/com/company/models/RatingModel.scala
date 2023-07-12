package com.company.models

import com.company.models.MovieModel.getClass
import org.apache.spark.sql.types.{IntegerType, StructType}

import java.nio.file.Paths

object RatingModel extends Model {
  val path: String = Paths.get(getClass.getClassLoader.getResource("u.data").toURI).toString
  val delimiter: String = "\t"
  val schema: StructType = new StructType()
    .add("userID", IntegerType)
    .add("movieID", IntegerType)
    .add("rating", IntegerType)
    .add("timestamp", IntegerType)
}
