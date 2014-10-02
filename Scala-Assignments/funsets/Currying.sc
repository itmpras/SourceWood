object Currying {
  
def sum(f:Int=>Int) (a:Int,b:Int):Int =
if(a>b) 0 else f(a)+sum(f)(a+1,b)                 //> sum: (f: Int => Int)(a: Int, b: Int)Int

def combine(f:Int=>Int) :(Int,Int)=>Int = {

def sumF(a :Int,b:Int):Int =
f(a)+f(b)

sumF

}                                                 //> combine: (f: Int => Int)(Int, Int) => Int

def combine2(f:Int=>Int) (a:Int,b:Int) : (Int,Int)=>Int = {

(a,b)=>f(a)+f(b)

}                                                 //> combine2: (f: Int => Int)(a: Int, b: Int)(Int, Int) => Int
 

sum(x=>x)(1,2)                                    //> res0: Int = 3

combine(x=>x+x)(2,3)                              //> res1: Int = 10
combine2(x=>x+x)(2,3)(5,6)                        //> res2: Int = 22

  type Set = Int => Boolean
  
  def contains(s: Set, elem: Int): Boolean = s(elem)
                                                  //> contains: (s: Int => Boolean, elem: Int)Boolean
    def singletonSet(elem: Int): Set = (check:Int) => elem == check
                                                  //> singletonSet: (elem: Int)Int => Boolean
                                                  
    def union(s: Set, t: Set): Set = (check :Int) => s(check) || t(check)
                                                  //> union: (s: Int => Boolean, t: Int => Boolean)Int => Boolean
    def intersect(s: Set, t: Set): Set = (check :Int) => s(check) && t(check)
                                                  //> intersect: (s: Int => Boolean, t: Int => Boolean)Int => Boolean
    
    def diff(s: Set, t: Set): Set = (check :Int) => s(check) &&  !t(check)
                                                  //> diff: (s: Int => Boolean, t: Int => Boolean)Int => Boolean
                                                  
  def filter(s: Set, p: Int => Boolean): Set = (check:Int) => p(check) && s(check)
                                                  //> filter: (s: Int => Boolean, p: Int => Boolean)Int => Boolean
    
        
  def s = singletonSet(4)                         //> s: => Int => Boolean
  def p = singletonSet(5)                         //> p: => Int => Boolean
  
  def s1 = singletonSet(4)                        //> s1: => Int => Boolean
  def s2 = singletonSet(5)                        //> s2: => Int => Boolean
  
  contains(s,4)                                   //> res3: Boolean = true
  contains(p,5)                                   //> res4: Boolean = true
  
  
  contains(union(s,p),5)                          //> res5: Boolean = true
  contains(union(s,p),5)                          //> res6: Boolean = true
  contains(intersect(s,s),4)                      //> res7: Boolean = true
  
  contains(diff(s,s1),4)                          //> res8: Boolean = false
  
  def one = singletonSet(1)                       //> one: => Int => Boolean
  def two = singletonSet(2)                       //> two: => Int => Boolean
  def three = singletonSet(3)                     //> three: => Int => Boolean
  def four = singletonSet(4)                      //> four: => Int => Boolean
  def neg = singletonSet(-1)                      //> neg: => Int => Boolean
  def all = union(union(union( union(one,two),three),four),neg)
                                                  //> all: => Int => Boolean
  
   def forall(s: Set, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean = {
      if (a> 200) false
      else if ( s(a) ) p(a)
      else iter(a+1)
    }
    iter(1)
  }                                               //> forall: (s: Int => Boolean, p: Int => Boolean)Boolean
  
  
 

  
contains(all ,-1)                                 //> res9: Boolean = true
  
def filterTest  = filter(all,x=>x>0)              //> filterTest: => Int => Boolean
def filterTest2 = filter(all,x=>x<0)              //> filterTest2: => Int => Boolean
contains(filterTest,1)                            //> res10: Boolean = true
contains(filterTest2,-1)                          //> res11: Boolean = true



}