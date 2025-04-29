import scala.io.Source
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Harvest extends App {

  val harvestFile = Source.fromFile("/Users/d.gharat/Downloads/harvest.csv.csv")
  val pricesFile = Source.fromFile("/Users/d.gharat/Downloads/prices.csv.csv")

  val pricesMap: mutable.Map[Tuple2[String, String], Float] = mutable.Map().withDefaultValue(0.0f)

  for (line <- pricesFile.getLines) {
    val tuple = (line.split(",")(0), line.split(",")(1))
    pricesMap(tuple) = line.split(",")(2).toFloatOption.getOrElse(0.0f)
  }
  pricesFile.close()

  val map: mutable.Map[String, Float] = mutable.Map().withDefaultValue(0.0f)
  case class Entry(gather: String, date: String, fruit: String, earning: Float)
  val entries = ListBuffer[Entry]()

  for (line <- harvestFile.getLines) {
    val name = line.split(",")(0)
    val date = line.split(",")(1)
    val fruit = line.split(",")(2)
    val amount = line.split(",")(3).toFloatOption.getOrElse(0.0f)
    val price = pricesMap(fruit, date)
    entries += Entry(name, date, fruit, amount * price)
    map(name) += amount
  }
  harvestFile.close()

  println(s"Best gatherer is ${map.maxBy(pair => pair._2)._1}")

}
