/** Brainfuck interpreter
 *  Written just to do it
 */
import scala.collection.mutable.ArrayStack
import scala.collection.jcl.ArrayList
class BrainF {
	/*Parse a brainfuck string in and decode it to the output string.
	 *Not sure how to handle bad input, but will figure it out later
	 */
	object BrainFToken extends Enumeration {
		type BrainFToken = Value
		val IP,DP,PP,MP,OUT,IN,LB,RB,NOP = Value
		/* IP == >
		 * DP == <
		 * PP == +
		 * MP == -
		 * OUT == .
		 * IN == ,
		 * LB == [
		 * RB == ]
		 * NOP == a no op value to preserve space
		 */
	}
	import BrainFToken._
	//Token holder, array list for backtracking purposes
	private var tokenList = new ArrayList[(BrainFToken,Option[Int])];
	/* Tokenize a string
	 * Upon seeing a '[', push PC onto oldPC stack
	 * Upon seeing a ']', pop oldPC stack into the optional Int portion of the tuple
	 * This will allow for loops on parsing
	 */
	private def tokenize(x:String):Unit = {
		tokenList.clear
		var oldPC = new ArrayStack[Int]
		for (i <- 0 until x.length) {
			x(i) match {
				case '<' => tokenList.add((DP,None))
				case '>' => tokenList.add((IP,None))
				case '+' => tokenList.add((PP,None))
				case '-' => tokenList.add((MP,None))
				case '.' => tokenList.add((OUT,None))
				case ',' => tokenList.add((IN,None))
				case '[' => {
					oldPC.push(i)
					tokenList.add((LB,None))
				}
				case ']' => {
					//Rewrite '[' to point to the corresponding ']'
					tokenList.add((RB,Some(oldPC.peek)))
					tokenList.update(oldPC.pop,(LB,Some(i)))
				}
				case _ => tokenList.add((NOP,None))
			}
		}
	}
	/* Prints out token queue to check for accuracy */
	def evalTokens(x:String):String = {
		tokenize(x);
		tokenList.mkString(",");
	}
	/* Parse the string, backtrack as necessary */
	def parse(x:String):String = {
		//Instruction counter
		var PC = 0;
		//Pointer to current data cell
		var dataPtr = 0
		//All brainfuck programs are to operate within a 30,000 byte array.
		var data = new Array[Byte](30000);
		tokenize(x)
		var outString = new StringBuffer
		while(PC < tokenList.size)
		{
			var curInst = tokenList(PC)
			curInst._1 match {
				case IP => dataPtr+=1
				case DP => dataPtr-=1
				case PP => data.update(dataPtr,(data(dataPtr)+1).toByte)
				case MP => data.update(dataPtr,(data(dataPtr)-1).toByte)
				//Basically gotos, might be a better way, but this is so easy
				case LB => {
					if (data(dataPtr) == 0)
					{
						curInst._2 match {
							case Some(x) => PC = x
							case None => /*?*/
						}
					}
				}
				case RB => {
					if (data(dataPtr) != 0)
					{
						curInst._2 match {
							case Some(x) => PC = x
							case None => /*?*/
						}
					}
				}
				case IN => data(dataPtr)=(readChar).toByte
				case OUT => outString.append(data(dataPtr).toChar)
				case _ => {}
			}
			PC+=1
		}
		return outString.toString
	}
}
object BrainFParser{
	def main(args:Array[String]) = {
		val a = new BrainF
		args.foreach(arg => println(a.parse(arg)))
	}
}
