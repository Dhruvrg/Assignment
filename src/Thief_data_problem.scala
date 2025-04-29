import scala.io.Source

object Thief_data_problem extends App{

  val lines = Source.fromFile("/Users/d.gharat/Downloads/thief_data.txt").getLines.toList

  def result(str: String, ch: Char = '1', accumulator: Int = 0): Int = {
    if (str.isEmpty) accumulator
    else if (str.head == ch) result(str.tail, ch, accumulator)
    else if (ch == '1') result(str.tail, '0', accumulator + 1)
    else result(str.tail, '1', accumulator + 1)
  }

  def function(lines: List[String]): Unit = {
    if (lines.isEmpty) ()
    else {
      println(result(lines.head))
      function(lines.tail)
    }
  }
  function(lines)
}

