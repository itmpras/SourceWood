import common._

object PascalTriangle {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(52); 
 
val a = 0;System.out.println("""a  : Int = """ + $show(a ));$skip(606); 

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

};System.out.println("""balance: (chars: List[Char])Boolean""");$skip(56); 



val b = balance("(if (zero? x) max (/ 1 x))".toList);System.out.println("""b  : Boolean = """ + $show(b ));$skip(92); 
val c = balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList);System.out.println("""c  : Boolean = """ + $show(c ));$skip(30); 
val d = balance(":-)".toList);System.out.println("""d  : Boolean = """ + $show(d ));$skip(32); 

val e = balance("())(".toList);System.out.println("""e  : Boolean = """ + $show(e ));$skip(31); 

val f = balance("))(".toList);System.out.println("""f  : Boolean = """ + $show(f ));$skip(33); 
val g = balance("(3(4))".toList);System.out.println("""g  : Boolean = """ + $show(g ));$skip(29); 
val h = balance("()".toList);System.out.println("""h  : Boolean = """ + $show(h ))}
                                                  //
}
