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

  case class Entry(gather: String, date: String, fruit: String, amount: Float, earning: Float)
  val entries = ListBuffer[Entry]()

  for (line <- harvestFile.getLines) {
    val name = line.split(",")(0)
    val date = line.split(",")(1)
    val fruit = line.split(",")(2)
    val amount = line.split(",")(3).toFloatOption.getOrElse(0.0f)
    val price = pricesMap(fruit, date)
    entries += Entry(name, date, fruit, amount, amount * price)
  }
  harvestFile.close()

  val list = entries.tail

  def sum(list: ListBuffer[Entry]): Float = {
    if (list.isEmpty) 0
    else list.head.earning + sum(list.tail)
  }

  def sumAmount(list: ListBuffer[Entry]): Float = {
    if (list.isEmpty) 0
    else list.head.amount + sumAmount(list.tail)
  }

  def bestFruitOfMonth(groupData: ListBuffer[Entry]): String = {
    val data = groupData.groupBy(pair => pair.fruit)
    data.maxBy(fruit => sum(fruit._2))._1
  }

  def leastFruitOfMonth(groupData: ListBuffer[Entry]): String = {
    val data = groupData.groupBy(pair => pair.fruit)
    data.minBy(fruit => sum(fruit._2))._1
  }

  def mostContributedIncome(groupData: ListBuffer[Entry]): String = {
    val data = groupData.groupBy(pair => pair.gather)
    data.maxBy(gather => sum(gather._2))._1
  }

  def bestGatherOfMonth(groupData: ListBuffer[Entry]): String = {
    val data = groupData.groupBy(pair => pair.gather)
    data.maxBy(gather => sumAmount(gather._2))._1
  }

  def bestGatheringFruit(groupData: ListBuffer[Entry]): String = {
    val data = groupData.groupBy(pair => pair.fruit)
    data.maxBy(fruit => sumAmount(fruit._2))._1
  }

  val groupData = list.groupBy(entry => entry.date.split("-")(1))

  groupData.map(pair => println(s"The best fruit in month ${pair._1} is ${bestFruitOfMonth(pair._2)}"))
  println("\n")
  groupData.map(pair => println(s"The least fruit in month ${pair._1} is ${leastFruitOfMonth(pair._2)}"))

  println("\n")
  val fruitData = list.groupBy(entry => entry.fruit)
  println(s"The best fruit is ${fruitData.maxBy(entry => sum(entry._2))._1}")
  println(s"The least fruit is ${fruitData.minBy(entry => sum(entry._2))._1}")

  println("\n")
  groupData.map(pair => println(s"Most Contributed Income in month ${pair._1} is done by ${mostContributedIncome(pair._2)}"))

  println("\n")
  val gatherList = list.groupBy(entry => entry.gather)
  println(s"Most Contributed Income is done by ${gatherList.maxBy(entry => sum(entry._2))._1}")

  println("\n")
  groupData.map(pair => println(s"The best gather in month ${pair._1} is ${bestGatherOfMonth(pair._2)}"))

  println("\n")
  gatherList.map(pair => println(s"${pair._1} is good at gathering ${bestGatheringFruit(pair._2)}"))
}

