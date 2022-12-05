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
		
		
		Angle a1 = new Angle(ab, ac);
		Angle a2 = new Angle(bd, de);
		Angle a3 = new Angle(be, ce);
		
		Angle a4 = new Angle(da, ac);
		Angle a5 = new Angle(ba, ae);
		
		//structurally incomparable
		assertEquals(STRUCTURALLY_INCOMPARABLE, comparator.compare(a1, a2));
		assertEquals(STRUCTURALLY_INCOMPARABLE, comparator.compare(a2, a3));
		assertEquals(STRUCTURALLY_INCOMPARABLE, comparator.compare(a1, a3));
		
		//inconclusive result
		//DAC/BAE
		assertEquals(0, a4.compareTo(a5));
		assertEquals(0, a5.compareTo(a4));
		
		//1: Greater than
		
		
		//-1: Less than
		
	}

}
