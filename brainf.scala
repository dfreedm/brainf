/** Brainfuck interpreter
 *  Written just to do it
 */
import BrainFToken._
import scala.collection.mutable.ArrayStack
import scala.collection.jcl.ArrayList
class BrainF {
	/*Parse a brainfuck string in and decode it to the output string.
	 *Not sure how to handle bad input, but will figure it out later
	 */
	//All brainfuck programs are to operate within a 30,000 byte array.
	private var data = new Array[Byte](30000);
	//Pointer to current data cell
	private var dataPtr = 0
	//Instruction counter
	private var PC = 0;
	//Token holder, array list for backtracking purposes
	private var tokenList = new ArrayList[(BrainFToken,Option[Int])];
	private def goto(x:Int) = { PC = x }
	/* Tokenize a string
	 * Upon seeing a '[', push PC onto oldPC stack
	 * Upon seeing a ']', pop oldPC stack into the optional Int portion of the tuple
	 * This will allow for loops on parsing
	 */
	private def tokenize(x:String):Unit = {
		tokenList.clear
		var oldPC = new ArrayStack[Int]
		var level = 0
		for (i <- new Range(0,x.length,1)) {
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
					tokenList.add((RB,Some(oldPC.peek)))
					tokenList.update(oldPC.pop,(LB,Some(i)))
				}
				case _ => { /*?*/ }
			}
		}
	}
	/* Prints out token queue to check for accuracy */
	def evalTokens(x:String):String = {
		tokenize(x);
		tokenList.mkString(",");
	}
	private def parse():String = {return null}
}
