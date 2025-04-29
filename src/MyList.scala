abstract class MyList {
  def gather: String
  def tail: MyList
  def isEmpty: Boolean
  def date: String
  def fruit: String
  def earning: Float
  def add(gather: String, date: String, fruit: String, earning: Float): MyList
}

object Empty extends MyList {
  def gather: String = throw new NoSuchElementException()
  def tail: MyList = throw new NoSuchElementException()
  def isEmpty: Boolean = true
  def date: String = throw new NoSuchElementException()
  def fruit: String = throw new NoSuchElementException()
  def earning: Float = throw new NoSuchElementException()
  def add(gather: String, date: String, fruit: String, earning: Float) =
    new Cons(gather, date, fruit, earning, Empty)
}

class Cons(g: String, d: String, f: String, e: Float, t: MyList) extends MyList {
  def gather: String = g
  def tail: MyList = t
  def isEmpty: Boolean = false
  def date: String = d
  def fruit: String = f
  def earning: Float = e
  def add(gather: String, date: String, fruit: String, earning: Float) =
    new Cons(gather, date, fruit, earning, this)
}
