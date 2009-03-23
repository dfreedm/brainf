/** Brainfuck interpreter
 *  Written just to do it
 */
import BrainFToken._
import scala.collection.mutable.{Queue,Stack}
class BrainF {
	/*Parse a brainfuck string in and decode it to the output string.
	 *Not sure how to handle bad input, but will figure it out later
	 */
	//All brainfuck programs are to operate within a 30,000 byte array.
	private var data = new Array[Byte](30000);
	//Instruction counter
	private var PC = 0;
	//Token queue has a Token, and an Optional goto if Token == LB
	private var tokenQueue = new Queue[(BrainFToken,Option[Int])];
	private def goto(x:Int) = { PC = x }
	/* Tokenize a string
	 * Upon seeing a '[', push PC onto oldPC stack
	 * Upon seeing a ']', pop oldPC stack into the optional Int portion of the tuple
	 * This will allow for loops on parsing
	 */
	private def tokenize(x:String):Unit = {
		tokenQueue.clear
		var oldPC = new Stack[Int];
		for (i <- new Range(0,x.length,1)) {
			x(i) match {
				case '<' => tokenQueue.enqueue((DP,None))
				case '>' => tokenQueue.enqueue((IP,None))
				case '+' => tokenQueue.enqueue((PP,None))
				case '-' => tokenQueue.enqueue((MP,None))
				case '.' => tokenQueue.enqueue((OUT,None))
				case ',' => tokenQueue.enqueue((IN,None))
				case '[' => {
					oldPC.push(i)
					tokenQueue.enqueue((LB,None))
				}
				case ']' => tokenQueue.enqueue((RB,Some(oldPC.pop())))
				case _ => { /*?*/ }
			}
		}
	}
	def evalTokens(x:String):String = {
		tokenize(x);
		tokenQueue.mkString(",");
	}
}
