package geometry_objects.angle;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometry_objects.Segment;
import geometry_objects.points.Point;
import input.exception.FactException;

class AngleTest {

	@Test
	void EqualsTest() throws FactException {
		//ints//
		Point pt1 = new Point("D", 0, 0);
		Point pt2 = new Point("E", 6, 0);
		Point pt3 = new Point("B", 2, 4);
		Point pt4 = new Point("C", 4, 4);
		Point pt5 = new Point("A", 3, 6); 
		
		Segment s1 = new Segment(pt5, pt3); 
		Segment s2 = new Segment(pt5, pt4); 
		Segment s3 = new Segment(pt3, pt4); 
		Segment s4 = new Segment(pt3, pt1);
		Segment s7 = new Segment(pt4, pt2); 
		Segment s8 = new Segment(pt1, pt2); 
	
		Angle a1 = new Angle(s1, s2);
		Angle a2 = new Angle(s8, s7);
		Angle a3 = new Angle(s4, s3);
		Angle a4 = new Angle(s8, s4);
		Angle a5 = new Angle(s8, s4);
		Angle a6 = new Angle(s1, s2);
		Angle a7 = new Angle(s3, s4);
		
		
		
		//null angle
		assertFalse(a1.equals(null));
		
		//different vertices
		assertFalse(a1.equals(a2));
		assertFalse(a3.equals(a1));
		assertFalse(a2.equals(a5));
		
		//same vertices
		assertTrue(a4.equals(a4));
		assertTrue(a1.equals(a6));
		assertTrue(a4.equals(a5));
		assertTrue(a7.equals(a3));

		
		
		
		
		
		
		//doubles//
		Point pt1d = new Point("D", 0.7, 0.7);
		Point pt2d = new Point("E", 6.8, 0.7);
		Point pt3d = new Point("B", 2.6, 4.1);
		Point pt4d = new Point("C", 4.1, 4.1);
		Point pt5d = new Point("A", 3.8, 6.8); 
		
		Segment s1d = new Segment(pt5d, pt3d); 
		Segment s2d = new Segment(pt5d, pt4d); 
		Segment s3d = new Segment(pt3d, pt4d); 
		Segment s4d = new Segment(pt3d, pt1d);
		Segment s7d = new Segment(pt4d, pt2d); 
		Segment s8d = new Segment(pt1d, pt2d); 
		
		Angle a1d = new Angle(s1d, s2d);
		Angle a2d = new Angle(s8d, s7d);
		Angle a3d = new Angle(s1d, s3d);
		Angle a4d = new Angle(s8d, s4d);
		Angle a5d = new Angle(s8d, s4d);
		Angle a6d = new Angle(s1d, s2d);
		Angle a7d = new Angle(s3d, s4d);
		
		
		
		
		
		//null angle
		assertFalse(a1d.equals(null));

		//different vertices
		assertFalse(a1d.equals(a2d));
		assertFalse(a3d.equals(a1d));
		assertFalse(a2d.equals(a5d));

		//same vertices
		assertTrue(a4d.equals(a4d));
		assertTrue(a1d.equals(a6d));
		assertTrue(a4d.equals(a5d));
		assertTrue(a7d.equals(a3d));
	}

}
