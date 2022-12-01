package preprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import input.exception.FactException;
import geometry_objects.Segment;
import geometry_objects.Triangle;

public class TriangleIdentifier
{
	protected Set<Triangle>         _triangles;
	protected Map<Segment, Segment> _segments;

	public TriangleIdentifier(Map<Segment, Segment> segments)
	{
		_segments = segments;
	}

	/*
	 * Compute the figure triangles on the fly when requested;
	 * memoize results for subsequent calls.
	 */
	public Set<Triangle> getTriangles()
	{
		if (_triangles != null) return _triangles;

		_triangles = new HashSet<Triangle>();

		computeTriangles();

		return _triangles;
	}

	private void computeTriangles()
	{
		//Gross Algorithm
		
		//check all segments
		for (Segment currSegment:_segments.keySet()) 
		{
			//check current segment's neighbors
			for (Segment segment2:_segments.keySet()) 
			{
				for (Segment segment3:_segments.keySet()) 
				{
					//create list of segments and add current segment
					List<Segment> segmentList = new ArrayList<Segment>();
					segmentList.add(currSegment);
					segmentList.add(segment2);
					segmentList.add(segment3);
					
					try 
					{
						//TODO TEST
						//try to create triangle
						Triangle potentialTriangle = new Triangle(segmentList);
						_triangles.add(potentialTriangle);
					} 
					catch (FactException e) 
					{
						//shhhhh
					}
					
				}
			}
			
			
			
			
			
		}
		//check every segment 
		//check if 3 points are collinear (if so not a triangle)
		//check that endpoints point to the same point (if so its a triangle)
		//check that triangle doesnt already exist
		
	}
	

	
	
}
