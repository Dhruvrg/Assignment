import scala.io.Source
import scala.jdk.Accumulator

object Core_Digit extends App {

  val lines = Source.fromFile("/Users/d.gharat/Downloads/CoreData.txt").getLines.toList
  val digit = lines.head.split(' ')(1)
  val multiple = lines.head.split(' ')(1).toInt

  def summation(num: String, accumulator: Int = 0): String = {
    if (num.isEmpty) accumulator.toString
    else summation(num.tail, accumulator + num.head - 48)
  }

  def function(num: String): String = {
    if (num.toInt < 10) num
    else function(summation(num))
  }

  val temp = function(digit).toInt * multiple
  println(function(temp.toString))
}
