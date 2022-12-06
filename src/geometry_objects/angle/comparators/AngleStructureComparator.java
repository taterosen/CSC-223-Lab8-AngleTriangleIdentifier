/**
 * Write a succinct, meaningful description of the class here. You should avoid wordiness    
 * and redundancy. If necessary, additional paragraphs should be preceded by <p>,
 * the html tag for a new paragraph.
 *
 * <p>Bugs: (a list of bugs and / or other problems)
 *
 * @author <your name>
 * @date   <date of completion>
 */

package geometry_objects.angle.comparators;

import java.util.Comparator;

import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.points.Point;
import utilities.math.MathUtilities;
import utilities.math.analytic_geometry.GeometryUtilities;

public class AngleStructureComparator implements Comparator<Angle>
{
	public static final int STRUCTURALLY_INCOMPARABLE = Integer.MAX_VALUE;
	
	/**
	 * Given the figure below:
	 * 
	 *    A-------B----C-----------D
	 *     \
	 *      \
	 *       \
	 *        E
	 *         \
	 *          \
	 *           F
	 * 
	 * What we care about is the fact that angle BAE is the smallest angle (structurally)
	 * and DAF is the largest angle (structurally). 
	 * 
	 * If one angle X has both rays (segments) that are subsegments of an angle Y, then X < Y.
	 * 
	 * If only one segment of an angle is a subsegment, then no conclusion can be made.
	 * 
	 * So:
	 *     BAE < CAE
   	 *     BAE < DAF
   	 *     CAF < DAF

   	 *     CAE inconclusive BAF
	 * 
	 * @param left -- an angle
	 * @param right -- an angle
	 * @return -- according to the algorithm above:
 	 *            Integer.MAX_VALUE will refer to our error result
 	 *            0 indicates an inconclusive result
	 */
	@Override
	public int compare(Angle left, Angle right)
	{
		//inconclusive, not structurally comparable
		if(!left.getVertex().equals(right.getVertex())) return STRUCTURALLY_INCOMPARABLE;
		
		if(!MathUtilities.doubleEquals(left.getMeasure(), right.getMeasure())) return STRUCTURALLY_INCOMPARABLE;
		
		if (!left.overlays(right)) return STRUCTURALLY_INCOMPARABLE; 
		
		//Structurally comparable:
				
		Segment leftRay1 = left.getRay1();
		Segment leftRay2 = left.getRay1();

		Segment overlayingLeftRay1 = right.overlayingRay(left.getRay1());
		Segment overlayingLeftRay2 = right.overlayingRay(left.getRay2());
		
		Point vertex = left.getVertex();
		
		// STRUCTURALLY LARGER
		if(leftRay1.pointLiesBetweenEndpoints(overlayingLeftRay1.other(vertex))) {
			if(leftRay2.pointLiesBetweenEndpoints(overlayingLeftRay2.other(vertex))) return 1;
			if(leftRay2.equals(overlayingLeftRay2)) return 1;
		}
		
		
		if(leftRay2.pointLiesBetweenEndpoints(overlayingLeftRay2.other(vertex))) {
			if(leftRay1.equals(overlayingLeftRay1)) return 1;
		}
		
		if(leftRay1.equals(overlayingLeftRay1) && leftRay2.equals(overlayingLeftRay2)) return 1;
		
		
		//STRUCTURALLY SMALLER
		if(overlayingLeftRay1.pointLiesBetweenEndpoints(leftRay1.other(vertex))) {
			if(overlayingLeftRay2.pointLiesBetweenEndpoints(leftRay2.other(vertex))) return -1;
			if(overlayingLeftRay2.equals(leftRay2)) return -1;
		}
		
		if(overlayingLeftRay2.pointLiesBetweenEndpoints(leftRay2.other(vertex))) {
			if(overlayingLeftRay1.equals(leftRay1)) return -1;
		}
		
		//OTHER
		return 0;
	}
}
