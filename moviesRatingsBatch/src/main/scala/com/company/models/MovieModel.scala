package com.company.models

import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

import java.nio.file.Paths

object MovieModel extends Model {
  val path: String = Paths.get(getClass.getClassLoader.getResource("u.item").toURI).toString
  val delimiter: String = "|"
  val schema: StructType = new StructType()
    .add("movieID", IntegerType)
    .add("title", StringType)
    .add("date", StringType)
}
