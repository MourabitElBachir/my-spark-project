package com.company.reader

import com.company.models.Model
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.util.Try

class Reader {
  def read(model: Model)(implicit spark: SparkSession): Try[DataFrame] = Try {

    val df = spark
                .read
                .schema(model.schema)
                .option("delimiter", model.delimiter)
                .option("header", "false")
                .csv(model.path)

    df
  }
}
