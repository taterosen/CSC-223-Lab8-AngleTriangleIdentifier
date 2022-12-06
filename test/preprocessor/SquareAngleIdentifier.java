package preprocessor;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.InputFacade;
import input.components.FigureNode;
import input.exception.FactException;
import preprocessor.delegates.ImplicitPointPreprocessor;

public class SquareAngleIdentifier {
	protected PointDatabase _points;
	protected Preprocessor _pp;
	protected Map<Segment, Segment> _segments;
	protected Set<Segment> _nonImplicitSegments;

	protected void init(String filename)
	{
		FigureNode fig = InputFacade.extractFigure("jsonfiles/square.json");

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		_nonImplicitSegments = pair.getValue();
		_points = pair.getKey();

		_pp = new Preprocessor(_points, pair.getValue());

		_pp.analyze();

		_segments = _pp.getAllSegments();
	}



	//  B        E____F
	//  |\      /|\   |
	//  | \    / | \  |
	//  |  \  /  |  \ |
	//  |___\/___|   \|
	//  A    C   D    G


	@Test
	void test_crossing_symmetric_triangle()
	{
		init("square.json");

		AngleIdentifier angleIdentifier = new AngleIdentifier(_segments);

		AngleEquivalenceClasses computedAngles = angleIdentifier.getAngles();

		// The number of classes should equate to the number of 'minimal' angles
		assertEquals("Number of Angle Equivalence classes", 4, computedAngles.numClasses());

		//
		// ALL original segments: 13 in this figure.
		//
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment bd = new Segment(_points.getPoint("B"), _points.getPoint("D"));
		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));
		
		

		
		// Angles we expect to find
		//
		List<Angle> expectedAngles = new ArrayList<Angle>();
		try {
			
			expectedAngles.add(new Angle(ab, bd));
			expectedAngles.add(new Angle(ab, ac));
			expectedAngles.add(new Angle(ac, cd));
			expectedAngles.add(new Angle(bd, cd));
			
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }
		
		assertEquals(expectedAngles.size(), computedAngles.size());

		//
		// Equality
		//
		for (Angle expected : expectedAngles)
		{
			assertTrue(computedAngles.contains(expected));
		}
	}
}
