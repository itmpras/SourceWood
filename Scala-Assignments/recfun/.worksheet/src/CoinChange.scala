import common._
object CoinChange {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(48); 
  val a = 0;System.out.println("""a  : Int = """ + $show(a ));$skip(473); ;
  
  def countChange(money: Int, coins: List[Int]): Int = {
 
  val dupCoins = coins.toSet.toList
  
  
  def countChangeRec (money: Int, coins: List[Int]): Int = {
  
  if(money == 0)
        return 1;
  if(money < 0 )
         return 0;
        
  if(coins.isEmpty)
           return 0;
  
   val a = countChangeRec(money,coins.tail) ;
   val b = countChangeRec(money - coins.head , coins)
    
  
  return a+b;
  }
 
 
  
  return countChangeRec(money,dupCoins);
  
  };System.out.println("""countChange: (money: Int, coins: List[Int])Int""");$skip(37); val res$0 = 
  
  
  countChange(5,List(2,1,2,1));System.out.println("""res0: Int = """ + $show(res$0));$skip(48); val res$1 = 
  countChange(300,List(5,10,20,50,100,200,500));System.out.println("""res1: Int = """ + $show(res$1));$skip(48); val res$2 = 
  countChange(301,List(5,10,20,50,100,200,500));System.out.println("""res2: Int = """ + $show(res$2));$skip(48); val res$3 = 
  countChange(300,List(500,5,50,100,20,200,10));System.out.println("""res3: Int = """ + $show(res$3))}
  
}
