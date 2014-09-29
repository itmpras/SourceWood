package recfun
import common._
import scala.annotation.tailrec

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {

    def isRoot(col: Int, row: Int): Boolean = {

      if (row == 0 && col == 0)
        true

      false
    }

    def computeColAndRoWValue(col: Int, row: Int): Int = {

      compuateColAndRowValueRec(col, row, 0)
    }

    def compuateColAndRowValueRec(col: Int, row: Int, value: Int): Int = {

      //println("col " + col + "  Row " + row + " Value : "+ value  )
      if (isRoot(col, row)) value + 1;

      else if (col <= 0) value + 1;
      else if (row <= 0) value;
      else {
        val temp = compuateColAndRowValueRec(col - 1, row - 1, value);

        compuateColAndRowValueRec(col, row - 1, temp);
      }

    }

    computeColAndRoWValue(c, r);

  } //> pascalTriangle: (col: Int, row: Int)Int

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    @tailrec
    def recBalance(chars: List[Char], len: Int, balanced: Int): Int = {

      if (balanced == -1) -1

      else if (chars.length == len) balanced

      else {
        val currentChar = chars.apply(len)

        val retBalance = computeBalance(currentChar, balanced);
        recBalance(chars, len + 1, retBalance);
      }
    }

    def computeBalance(char: Char, balanced: Int): Int = {
      if (char == '(') balanced + 1
      else if (char == ')') balanced - 1
      else balanced
    }

    if (chars.length == 1) false
    else (recBalance(chars, 0, 0) == 0) 
    

  } //> balance: (chars: List[Char])Boolean

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {

    val dupCoins = coins.toSet.toList

    def countChangeRec(money: Int, coins: List[Int]): Int = {

      if (money == 0) 1;
      else if (money < 0) 0;
      else if (coins.isEmpty) 0;
      else {
        val a = countChangeRec(money, coins.tail);
        val b = countChangeRec(money - coins.head, coins)

        a + b;
      }
    }

    countChangeRec(money, dupCoins);

  } //> countChange: (money: Int, coins: List[Int])Int

}
