package polygon;

import java.util.*;

public class PolygonExtended {
	
	public List<Point> myPolygono;
	
	public PolygonExtended (List<Point> poly) {
		myPolygono = poly;
	}
	
	public void orderConvexPolygon() {
		List<Point> tempPoly = new ArrayList<Point>();
		tempPoly.add(0, myPolygono.get(0));
		/*
		System.out.printf("Before = ");
		for (int i=0; i<myPolygono.size(); i++) {
			System.out.printf("(%d,%d) ",myPolygono.get(i).x,myPolygono.get(i).y);
		}
		System.out.printf("\n");*/
		
			for(int j=0; j<myPolygono.size()-1;j++) {
				for (int i=0; i<(myPolygono.size()-j-1); i++) {
					if(myPolygono.get(i).x>myPolygono.get(i+1).x) {
						tempPoly.set(0, myPolygono.get(i));
						myPolygono.set(i,myPolygono.get(i+1));
						myPolygono.set(i+1,tempPoly.get(0));
					}
				}
			}/*
			System.out.printf("On Process = ");
			for (int i=0; i<myPolygono.size(); i++) {
				System.out.printf("(%d,%d) ",myPolygono.get(i).x,myPolygono.get(i).y);
			}
			System.out.printf("\n");*/
		
			for(int j=0; j<myPolygono.size()-1;j++) {
				for (int i=1; i<(myPolygono.size()-j-1); i++) {
					if(myPolygono.get(i).y<myPolygono.get(i+1).y) {
						tempPoly.set(0, myPolygono.get(i));
						myPolygono.set(i,myPolygono.get(i+1));
						myPolygono.set(i+1,tempPoly.get(0));
					}
				}
			}
		System.out.printf("Polygon = ");
		for (int i=0; i<myPolygono.size(); i++) {
			System.out.printf("(%f,%f) ",myPolygono.get(i).x,myPolygono.get(i).y);
		}
		System.out.printf("\n");
	}
	
	public float[] getXPoints() {
		float[] xpoints = new float[myPolygono.size()];
		for(int i=0;i<myPolygono.size();i++) {		
			xpoints[i] = myPolygono.get(i).x;
		}
		return xpoints;
	}
	
	public float getXPoint(int index) {
		float xpoint = 0;
		xpoint = myPolygono.get(index).x;
		return xpoint;
	}
	
	public float[] getYPoints() {
		float[] ypoints = new float[myPolygono.size()];
		for(int i=0;i<myPolygono.size();i++) {		
			ypoints[i] = myPolygono.get(i).y;
		}
		return ypoints;
	}
	
	public float getYPoint(int index) {
		float ypoint = 0;
		ypoint = myPolygono.get(index).y;
		return ypoint;
	}
	
	public int getSize() {
		int size = myPolygono.size();
		return size;
	}	
}
