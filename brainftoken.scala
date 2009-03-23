/** Brain Fuck Token Enum
 * Just to make life easier
 */
object BrainFToken extends Enumeration {
	type BrainFToken = Value
	val IP,DP,PP,MP,OUT,IN,LB,RB = Value
	/* IP == >
	 * DP == <
	 * PP == +
	 * MP == -
	 * OUT == .
	 * IN == ,
	 * LB == [
	 * RB == ]
	 */
}
