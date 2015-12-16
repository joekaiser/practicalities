package practicalities.client.render;

import java.util.ArrayList;
import java.util.Random;

import cofh.repack.codechicken.lib.vec.Vector3;




public class Lightning {

	private static Random r = new Random();
	
	public static class Line {
		public Line(Vector3 start, Vector3 end, int level) {
			this.start = start.copy(); this.end = end.copy(); this.level = level;
		}
		public Vector3 start;
		public Vector3 end;
		public int level;
	}
	
	/**
	 * "Detail" of lightning - default: 3
	 */
	public int depth = 3;
	
	/**
	 * Twisty-turney-ness - default: 0.25
	 */
	public double offset = 0.25;
	
	/**
	 * Maximum fork angle (radians) - default: π/3
	 */
	public double forkAngle = Math.PI/3.0;
	
	/**
	 * Fork length multiplier - default: 0.8
	 */
	public double forkLengthMultiplier = 0.8;
	
	/**
	 * Fork Chance - default: 0.25
	 */
	public double forkChance = 0.25;
	
	public ArrayList<Line> lines;
	
	public Vector3 from, to;
	
	public Lightning(Vector3 from, Vector3 to) {
		this.from = from; this.to = to;
	}
	
	public void regenerate() {
		lines = new ArrayList<Line>();
		drawLightning(lines, from.copy(), to.copy(), offset, depth, 0, 0);
	}
	
	private void drawLightning(ArrayList<Line> list, Vector3 start, Vector3 end, double displace, int count, int iterations, int level)
	{
	  if (iterations > count) {
		  list.add(new Line(start, end, level));
	  } else {
		  Vector3 mid = start.copy().add(end).multiply(1/2.0);
		  mid.x += (r.nextDouble()-0.5) * displace;
		  mid.y += (r.nextDouble()-0.5) * displace;
		  mid.z += (r.nextDouble()-0.5) * displace;
		  drawLightning(list, start, mid, displace/2, count, iterations+1, level);
		  
		  if(r.nextDouble() < forkChance) {
			  Vector3 direction = mid.copy().sub(start);
			  Vector3 forkAxis = new Vector3(r.nextDouble(), r.nextDouble(), r.nextDouble()).normalize();
			  double angle = (r.nextDouble()-0.5) * forkAngle;
			  direction.rotate(angle, forkAxis).multiply(forkLengthMultiplier).add(mid);
			  drawLightning(list, mid, direction, displace/2, count, iterations+1, level+1);
			  end.sub(mid).rotate(-angle/3, forkAxis).add(mid);
		  }
		  
		  drawLightning(list, mid,   end, displace/2, count, iterations+1, level);
	  }
	}
	
	
	
}
