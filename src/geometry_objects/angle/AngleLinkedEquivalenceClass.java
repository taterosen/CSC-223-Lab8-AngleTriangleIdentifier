package geometry_objects.angle;

import java.util.Comparator;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.eq_classes.LinkedEquivalenceClass;
import utilities.eq_classes.LinkedList;

/**
 * This implementation requires greater knowledge of the implementing Comparator.
 * 
 * According to our specifications for the AngleStructureComparator, we have
 * the following cases:
 *
 *    Consider Angles A and B
 *    * Integer.MAX_VALUE -- indicates that A and B are completely incomparable
                             STRUCTURALLY (have different measure, don't share sides, etc. )
 *    * 0 -- The result is indeterminate:
 *           A and B are structurally the same, but it is not clear one is structurally
 *           smaller (or larger) than another
 *    * 1 -- A > B structurally
 *    * -1 -- A < B structurally
 *    
 *    We want the 'smallest' angle structurally to be the canonical element of an
 *    equivalence class.
 * 
 * @author George (Mr. Employed), Tate, and Jace
 */
public class AngleLinkedEquivalenceClass extends LinkedEquivalenceClass<Angle>
{
	
	//protected Angle _canonical;
	//protected Comparator<Angle> _comparator;
	//protected LinkedList<Angle> _rest;
	public static final int STRUCTURALLY_INCOMPARABLE = Integer.MAX_VALUE;

	public AngleLinkedEquivalenceClass(AngleStructureComparator comparator) {
		super(comparator);
	}
	
	//TODO test all methods
	@Override
	public boolean belongs(Angle target) {
		int compareVal = _comparator.compare(_canonical, target);

		if (compareVal == STRUCTURALLY_INCOMPARABLE) return false;
		return true;
	}

	/**
	 * if input is structurally smaller than current canonical,adds previous 
	 * canonical to Linked Equivalence Class and sets input to new canonical
	 * @param element
	 * @return True if element is different than canonical
	 */
	@Override
	public boolean demoteAndSetCanonical(Angle element) {
		//TODO canonical should always be the smallest angle
		//if 1st time setting canonical//
		if (_canonical == null ) {
			_canonical = element;
			return true;
		}
		//resetting canonical//
		//check if canonical and element are identical or if element is null
		if (_canonical.equals(element) || element == null) return false;
		//check if element belongs in list
		if (!(belongs(element))) return false;
		//check if element is already contained , if so remove element
		if (contains(element)) _rest.remove(element);

		//check if element is smaller than current canonical
		if (_comparator.compare(_canonical, element) == 1) {
			_rest.addToFront(_canonical);
			_canonical = element;
			return true;
		}
		return false;
	}

	/**
	 * If input is structurally smaller than canonical, make input
	 * new canonical and put canonical in LEC
	 * Else adds input to the Linked Equivalence Class
	 * @param element
	 * @return True if successfully added
	 */
	@Override
	public boolean add(Angle element) {
		//TODO need to check if need to swap canonical
		//if(!this.demoteAndSetCanonical(element)) return false;
		//_rest.addToBack(element);
		//return true;
		
		return this.demoteAndSetCanonical(element);
	}
	
	@Override
	public boolean removeCanonical() {
		
		if ((_canonical == null) || (_rest.isEmpty())) return false;
		
		//find smallest angle in _rest
		Angle smallest = _rest.getIndex(0);
		for (int i = 0; i < _rest.size(); i++) {
			Angle currAngle = _rest.getIndex(i);
			if (_comparator.compare(smallest, currAngle) == 1) smallest = currAngle;
		}
		
		//remove from rest and set as canonical 
		_rest.remove(smallest);
		_canonical = smallest;
		return true;
	}



}