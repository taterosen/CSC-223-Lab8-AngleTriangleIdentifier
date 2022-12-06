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

class AngleStructureComparatorTest {
	protected PointDatabase _points;
	protected Preprocessor _pp;
	protected Map<Segment, Segment> _segments;
	public static final int STRUCTURALLY_INCOMPARABLE = Integer.MAX_VALUE;
	
	protected void init(String filename)
	{
		FigureNode fig = InputFacade.extractFigure("jsonfiles/crossing_symmetric_triangle.json");

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		_points = pair.getKey();

		_pp = new Preprocessor(_points, pair.getValue());

		_pp.analyze();

		_segments = _pp.getAllSegments();
		
	}
	
	@Test
	void ComparatorCompareTest() throws FactException {
		
		AngleStructureComparator comparator = new AngleStructureComparator();
		//TODO need to test compare method
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
		
		Angle DAC = new Angle(da, ac);
		Angle BAE = new Angle(ba, ae);
		Angle DAE = new Angle(da, ae);
		Angle ABC = new Angle(ab, bc);
		Angle BAC = new Angle(ab, ac);
		Angle ADE = new Angle(da, de);
		Angle AED = new Angle(ae, de);
		Angle DEC = new Angle(de, ce);
		
		//structurally incomparable
		assertEquals(STRUCTURALLY_INCOMPARABLE, comparator.compare(BAC, BDE));
		assertEquals(STRUCTURALLY_INCOMPARABLE, comparator.compare(BDE, BEC));
		assertEquals(STRUCTURALLY_INCOMPARABLE, comparator.compare(BAC, BEC));
		assertEquals(STRUCTURALLY_INCOMPARABLE, comparator.compare(DAC, ABC));
		
		
		//inconclusive result
		//DAC/BAE
		assertEquals(0, comparator.compare(DAC, BAE));
		assertEquals(0, comparator.compare(BAE, DAC));
		
		//1: Greater than
		//DAE > DAC
		assertEquals(1, comparator.compare(DAE, DAC));
		assertEquals(1, comparator.compare(ADE, BDE));
		assertEquals(1, comparator.compare(AED, DEC));
		
		
		//-1: Less than
		//DAE < DAC
		assertEquals(-1, comparator.compare(DAC, DAE));
		assertEquals(-1, comparator.compare(BDE, ADE));
		assertEquals(-1, comparator.compare(DEC, AED));
		
	}

}
