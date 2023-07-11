package com.company.models

import org.apache.spark.sql.types.StructType

trait Model {

  val path: String
  val delimiter: String
  val schema: StructType

}
