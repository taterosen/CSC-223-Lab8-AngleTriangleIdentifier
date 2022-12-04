package preprocessor;

import java.util.List;
import java.util.Map;

import input.exception.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;
import geometry_objects.points.Point;

public class AngleIdentifier
{
	protected AngleEquivalenceClasses _angles;
	protected Map<Segment, Segment> _segments;

	public AngleIdentifier(Map<Segment, Segment> segments)
	{
		_segments = segments;
	}

	/*
	 * Compute the figure triangles on the fly when requested; memorize results for subsequent calls.
	 */
	public AngleEquivalenceClasses getAngles()
	{
		if (_angles != null) return _angles;

		_angles = new AngleEquivalenceClasses();

		computeAngles();

		return _angles;
	}

	private void computeAngles()
	{
		// TODO Algorithm?
		//intersection of 2 segments will be vertex
		//use minimal segments and nonminimal segments
		//for every vertex, choose a segment and compute the angles between all other segments with that same vertex
		for(Segment seg1: _segments.keySet()) {
			for(Segment seg2: _segments.keySet()) {
				Point vertex = seg1.sharedVertex(seg2);
				if(vertex != null) {
					try {
						Angle newAngle = new Angle(seg1, seg2);
						_angles.add(newAngle);
					} catch (FactException e) {
						//TODO ask what to put in here
						e.printStackTrace();
					}
				}
				
			}
		}
		
		
	}
}
