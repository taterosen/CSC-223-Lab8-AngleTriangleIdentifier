package preprocessor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import geometry_objects.Segment;
import geometry_objects.Triangle;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.InputFacade;
import input.components.FigureNode;
import input.exception.FactException;
import preprocessor.delegates.ImplicitPointPreprocessor;

class Tri_QuadTriangleTest {

	protected PointDatabase _points;
	protected Preprocessor _pp;
	protected Map<Segment, Segment> _segments;
	protected Set<Segment> _nonImplicitSegments;
	
	protected void init(String filename)
	{
		FigureNode fig = InputFacade.extractFigure("jsonfiles/Tri_Quad.json");

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		_nonImplicitSegments = pair.getValue();
		_points = pair.getKey();

		_pp = new Preprocessor(_points, pair.getValue());

		_pp.analyze();

		_segments = _pp.getAllSegments();
	}
	
		//  G   H
	//     / \ / \
	//    /   X   \           X is not a point it is a crossing of 2 lines
	//   E\__/_\__/F        
	//      C---D
	//      | X |
	//      A---B

	//Shape with 8 C 2 Segments

	//      G   H
	//     / \ / \
	//    /  A*   \           X is not a point it is a crossing of 2 lines
	//  E\_B*/_\C*_/F        
	//      C---D
	//      | D*|
	//      A---B

	@Test
	void test_Tri_QuadTriangle()
	{
		init("Tri_Quad.json");

		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);

		Set<Triangle> computedTriangles = triIdentifier.getTriangles();

		System.out.println(computedTriangles);

		assertEquals(15, computedTriangles.size());

		//
		// ALL original segments: 13 in this figure.
		//
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment ad = new Segment(_points.getPoint("A"), _points.getPoint("D"));
		
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));
		Segment bd = new Segment(_points.getPoint("B"), _points.getPoint("D"));

		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));
		Segment ce = new Segment(_points.getPoint("C"), _points.getPoint("E"));
		Segment ch = new Segment(_points.getPoint("C"), _points.getPoint("H"));
		
		Segment df = new Segment(_points.getPoint("D"), _points.getPoint("F"));
		Segment dg = new Segment(_points.getPoint("D"), _points.getPoint("G"));
		
		Segment ef = new Segment(_points.getPoint("E"), _points.getPoint("F"));
		Segment eg = new Segment(_points.getPoint("E"), _points.getPoint("G"));
		
		Segment fh = new Segment(_points.getPoint("F"), _points.getPoint("H"));
		
		Set<Point> iPoints = ImplicitPointPreprocessor.compute(_points, new ArrayList<Segment>(_nonImplicitSegments));
		assertEquals(4, iPoints.size());
		Object[] arr = iPoints.toArray();
		//
		// Implied minimal segments: 4 in this figure.
		//
		Point a_star = new Point("A*", 6.0, 8.5);
		Point b_star = (Point) arr[3];
		Point c_star = (Point) arr[2];
		Point d_star = new Point("D*", 6.0, 2.5);

		Segment d_star_a = new Segment(_points.getPoint("A"), d_star);
		Segment d_star_b = new Segment(_points.getPoint("B"), d_star);
		Segment d_star_c = new Segment(_points.getPoint("C"), d_star);
		Segment d_star_d = new Segment(_points.getPoint("D"), d_star);
		
		
		Segment b_star_d = new Segment(_points.getPoint("D"), b_star);

		Segment c_star_c = new Segment(_points.getPoint("C"), c_star);

		Segment b_star_e = new Segment(_points.getPoint("E"), b_star);

		Segment c_star_f = new Segment(_points.getPoint("F"), c_star);
		
		Segment a_star_g = new Segment(_points.getPoint("G"), a_star);

		Segment a_star_h = new Segment(_points.getPoint("H"), a_star);
		
		Segment b_star_g = new Segment(_points.getPoint("G"), b_star);
		
		Segment c_star_h = new Segment(_points.getPoint("H"), b_star);
		
		Segment a_star_c = new Segment(_points.getPoint("C"), a_star);
		Segment a_star_d = new Segment(_points.getPoint("D"), a_star);
		

		Segment b_star_c_star = new Segment(b_star, c_star);
		Segment a_star_b_star = new Segment(a_star, b_star);
		Segment a_star_c_star = new Segment(a_star, c_star);


		
		

		//
		// Non-minimal, computed segments: 2 in this figure.
		//
		
		Segment af = new Segment(_points.getPoint("A"), _points.getPoint("F"));
		
		Segment be = new Segment(_points.getPoint("B"), _points.getPoint("E"));
		
		Segment c_star_e = new Segment(_points.getPoint("E"), c_star);
	
		Segment d_star_e = new Segment(_points.getPoint("E"), d_star);
		
		Segment b_star_f = new Segment(_points.getPoint("F"), b_star);
		
		Segment d_star_f = new Segment(_points.getPoint("F"), d_star);
		
		Segment c_star_g = new Segment(_points.getPoint("G"), c_star);
		
		Segment b_star_h = new Segment(_points.getPoint("H"), b_star);
		

		//
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		try {
			expectedTriangles.add(new Triangle(Arrays.asList(ad, ab, bd)));
			expectedTriangles.add(new Triangle(Arrays.asList(d_star_a, ab, d_star_b)));
			expectedTriangles.add(new Triangle(Arrays.asList(d_star_a, ac, d_star_c)));
			expectedTriangles.add(new Triangle(Arrays.asList(ac, bc, ab)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(d_star_b, bd, d_star_d)));
			expectedTriangles.add(new Triangle(Arrays.asList(bc, cd, bd)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(ad, cd, ac)));
			expectedTriangles.add(new Triangle(Arrays.asList(d_star_c, cd, d_star_d)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(b_star_g, eg, b_star_e)));
			expectedTriangles.add(new Triangle(Arrays.asList(b_star_g, a_star_b_star, a_star_c_star)));
			expectedTriangles.add(new Triangle(Arrays.asList(c_star_h, c_star_f, fh)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(a_star_c, cd, a_star_d)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(d_star_e, d_star_f, ef)));
			
			expectedTriangles.add(new Triangle(Arrays.asList(ce, c_star_c, c_star_e)));
			expectedTriangles.add(new Triangle(Arrays.asList(df, b_star_d, d_star_f)));
			
			
		}
		catch (FactException te) { System.err.println("Invalid triangles in triangle test."); }

		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
}
