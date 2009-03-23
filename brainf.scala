/** Brainfuck interpreter
 *  Written just to do it
 */
class BrainF {
	/*Parse a brainfuck string in and decode it to the output string.
	 *Not sure how to handle bad input, but will figure it out later
	 */
	//All brainfuck programs are to operate within a 30,000 byte array.
	var data = Array[Byte](30000)
	//Instruction counter
	var PC = 0;
	//Token stack has a Token, and an Optional goto if Token == LB
	var tokenStack:List[(BrainFToken,Option[Int])] = null
