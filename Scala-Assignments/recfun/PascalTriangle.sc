import common._

object PascalTriangle {
 
 
  def pascalTriangle(col:Int,row:Int) :Int = {
 
  
   def isRoot(col:Int,row:Int) :Boolean = {
 
 if(row == 0 && col == 0 )
     return true
 
  return  false
 }
 
 
 def computeColAndRoWValue(col:Int,row:Int) :Int = {
 
      compuateColAndRowValueAggregate ( col,row , 0)
 }
 
 
 def compuateColAndRowValueAggregate(col:Int,row:Int,value:Int) : Int = {
 
 //println("col " + col + "  Row " + row + " Value : "+ value  )
    if( isRoot(col,row) )
          return value+1;
 
    if( col <= 0 )
            return  value+1;
     if( row <= 0 )
             return  value;
             
    
    val temp = compuateColAndRowValueAggregate(col-1,row-1,value);
    
   //  println("col " + col + "  Row " + (row-1) + " Value : "+ (temp)   )
   
   return  compuateColAndRowValueAggregate(col,row-1,temp);
  
 }
 
 
 return computeColAndRoWValue(col,row);
  
 }                                                //> pascalTriangle: (col: Int, row: Int)Int
 
 
  
 pascalTriangle(5,30)                             //> res0: Int = 142506
 
}