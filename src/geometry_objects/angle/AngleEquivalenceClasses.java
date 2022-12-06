package geometry_objects.angle;

import java.util.ArrayList;
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
	protected List<AngleLinkedEquivalenceClass> _classes;

	public AngleEquivalenceClasses() {
		super(new AngleStructureComparator());
		this._classes = new ArrayList<AngleLinkedEquivalenceClass>();
	}
	
	public AngleEquivalenceClasses(Comparator<Angle> comparator) {
		super(comparator);
		this._classes = new ArrayList<AngleLinkedEquivalenceClass>();
	}
	
	@Override
	public boolean add(Angle element) {
		return super.add(element);
	}
	

	// TODO what methods need to be overridden?? 
	
	
}