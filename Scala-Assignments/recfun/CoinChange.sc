import common._
object CoinChange {
  val a = 0;                                      //> a  : Int = 0
  
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
  
  }                                               //> countChange: (money: Int, coins: List[Int])Int
  
  
  countChange(5,List(2,1,2,1))                    //> res0: Int = 3
  countChange(300,List(5,10,20,50,100,200,500))   //> res1: Int = 1022
  countChange(301,List(5,10,20,50,100,200,500))   //> res2: Int = 0
  countChange(300,List(500,5,50,100,20,200,10))   //> res3: Int = 1022
  
}