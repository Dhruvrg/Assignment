import scala.io.Source

object Exam_data_problem extends App {
  val lines = Source.fromFile("/Users/d.gharat/Downloads/exam_data.txt").getLines.toList


  def function(lines: List[String]): Boolean = {
    if (lines.isEmpty) true
    else {
      val arr = lines.head.split(", ")
      function(lines.tail) && (arr(0).toInt * arr(1).toInt >= arr(2).toInt)
    }
  }
  println(function(lines))
}
