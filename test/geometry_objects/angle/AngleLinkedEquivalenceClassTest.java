package geometry_objects.angle;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import geometry_objects.Segment;
import geometry_objects.angle.comparators.AngleStructureComparator;
import geometry_objects.points.PointDatabase;
import input.InputFacade;
import input.components.FigureNode;
import input.exception.FactException;
import preprocessor.Preprocessor;

class AngleLinkedEquivalenceClassTest {

	public static final int STRUCTURALLY_INCOMPARABLE = Integer.MAX_VALUE;
	protected PointDatabase _points;
	protected Preprocessor _pp;
	protected Map<Segment, Segment> _segments;
	protected AngleStructureComparator comparator = new AngleStructureComparator();
	protected AngleLinkedEquivalenceClass _AngleEC1 = new AngleLinkedEquivalenceClass(comparator);
	
	protected void init(String filename)
	{
		FigureNode fig = InputFacade.extractFigure("jsonfiles/crossing_symmetric_triangle.json");

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		_points = pair.getKey();

		_pp = new Preprocessor(_points, pair.getValue());

		_pp.analyze();

		_segments = _pp.getAllSegments();
		
	}
		
		//test whether belongs or not
	
	@Test
	void ALECTest() throws FactException {
		init("crossing_symmetric_triangle.json");
		//      A                                 
		//     / \                                
		//    B___C                               
		//   / \ / \                              
		//  /   X   \  X is not a specified point (it is implied) 
		// D_________E
		//
		// This figure contains 44 angles
		//
		
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment ae = new Segment(_points.getPoint("A"), _points.getPoint("E"));
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));

		Segment bd = new Segment(_points.getPoint("B"), _points.getPoint("D"));
		Segment ba = new Segment(_points.getPoint("B"), _points.getPoint("A"));
		Segment ce = new Segment(_points.getPoint("C"), _points.getPoint("E"));
		Segment da = new Segment(_points.getPoint("D"), _points.getPoint("A"));
		Segment de = new Segment(_points.getPoint("D"), _points.getPoint("E"));

		Segment be = new Segment(_points.getPoint("B"), _points.getPoint("E"));
		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));
		
		Angle BDE = new Angle(bd, de);
		Angle BEC = new Angle(be, ce);
		Angle ABC = new Angle(ab, bc);
		Angle ADE = new Angle(da, de);
		Angle AED = new Angle(ae, de);
		Angle DEC = new Angle(de, ce);
		
		Angle BAC = new Angle(ba, ac);
		Angle DAC = new Angle(da, ac);
		Angle BAE = new Angle(ba, ae);
		Angle DAE = new Angle(da, ae);
		
		//BAC == DAE == DAC == BAE//
		////////////////////////////
		assertTrue(_AngleEC1.add(BAC));
		//add null
		assertFalse(_AngleEC1.add(null));
		//add existing
		assertTrue(_AngleEC1.belongs(BAC));
		assertFalse(_AngleEC1.add(BAC));
		assertEquals(1, _AngleEC1.size());
		assertEquals(BAC, _AngleEC1.canonical());
		//check DAC
		assertTrue(_AngleEC1.belongs(DAC));
		assertTrue(_AngleEC1.add(DAC));
		assertEquals(2, _AngleEC1.size());
		assertEquals(BAC, _AngleEC1.canonical());
		//add BAE and DAE
		assertTrue(_AngleEC1.belongs(BAE));
		assertTrue(_AngleEC1.belongs(DAE));
		assertTrue(_AngleEC1.add(BAE));
		assertTrue(_AngleEC1.add(DAE));
		assertEquals(4, _AngleEC1.size());
		assertEquals(BAC, _AngleEC1.canonical());
		
		//add one that doesnt belong
		assertFalse(_AngleEC1.belongs(AED));
		assertFalse(_AngleEC1.add(AED)); 
		
		//remove DAC, BAC, BAE
		assertTrue(_AngleEC1.remove(DAC));
		assertEquals(3, _AngleEC1.size());
		assertEquals(BAC, _AngleEC1.canonical());
	
		assertTrue(_AngleEC1.remove(BAC));
		assertEquals(2, _AngleEC1.size());
		assertEquals(BAE, _AngleEC1.canonical());
		
		assertTrue(_AngleEC1.removeCanonical());
		assertEquals(1, _AngleEC1.size());
		assertEquals(DAE, _AngleEC1.canonical());
		
		//new canonical: DAE 
		//size: 1;
	}
	

}
