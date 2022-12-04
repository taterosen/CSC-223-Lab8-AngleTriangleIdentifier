package geometry_objects.angle;

import java.util.Comparator;
import java.util.List;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.eq_classes.EquivalenceClasses;
import utilities.eq_classes.LinkedEquivalenceClass;

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
 * Equivalence classes structure we want:
 * 
 *   canonical = BAE
 *   rest = BAF, CAE, DAE, CAF, DAF
 */
public class AngleEquivalenceClasses extends EquivalenceClasses<Angle>
{
	
	//protected Comparator<Angle> _comparator;
	//protected List<LinkedEquivalenceClass<Angle>> _classes;

	public AngleEquivalenceClasses() {
		super(new AngleStructureComparator());
	}
	
	public AngleEquivalenceClasses(Comparator<Angle> comparator) {
		super(comparator);
	}
	

	// TODO what methods need to be overridden?? 
	
	
}