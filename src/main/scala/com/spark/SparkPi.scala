package com.spark

import scala.math.random
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

/**
  * pi
  */
object SparkPi {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("spark test").setMaster("yarn")
        val sc = new SparkContext(conf)
        val textFile = sc.textFile("spark-env.sh")
        textFile.flatMap(_.split("\\s+|\\.")).map(a => (a,1)).reduceByKey(_+_)
                  .map{case (a,b) => (b,a)}.sortByKey(false).take(5).foreach(print)
        println("-------------------------------")
        var count = 0
        for (_ <- 1 to 100000) {
            val x = random * 2 - 1
            val y = random * 2 - 1
            if (x*x + y*y <= 1) count += 1
        }
        println("Pi is roughly " + 4 * count / 100000.0)
    }
}
