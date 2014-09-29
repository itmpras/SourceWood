import common._

object PascalTriangle {
 
val a = 0                                         //> a  : Int = 0

def balance(chars: List[Char]): Boolean= {

def recBalance(chars:List[Char],len:Int,balanced:Int) :Int = {

if(balanced == -1 )  return -1
 
if( chars.length == len)  return balanced
 
 val currentChar =  chars.apply(len)
 
val retBalance = computeBalance( currentChar,balanced);
return recBalance(chars,len+1,retBalance) ;
}

def computeBalance(char:Char,balanced:Int) :Int = {
 if(char == '(' ) {
   return balanced+1
 } else if ( char == ')' ) {
 return balanced-1
 }

return balanced
}



if(chars.length <= 1)  return false

val ret= recBalance(chars,0,0)

if(ret == 0 ) return true

return false

}                                                 //> balance: (chars: List[Char])Boolean



val b = balance("(if (zero? x) max (/ 1 x))".toList)
                                                  //> b  : Boolean = true
val c = balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList)
                                                  //> c  : Boolean = true
val d = balance(":-)".toList)                     //> d  : Boolean = false

val e = balance("())(".toList)                    //> e  : Boolean = false

val f = balance("))(".toList)                     //> f  : Boolean = false
val g = balance("(3(4))".toList)                  //> g  : Boolean = true
val h = balance("()".toList)                      //> h  : Boolean = true
                                                  //
}