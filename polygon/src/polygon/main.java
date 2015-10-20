package polygon;

import java.util.*;
import iofiles.*;
import java.awt.geom.Line2D;

public class main {

	public static void main(String[] args) {
		
		//In order that this code works all the polygons must be convex and must intersect with each other

		//First, we read the TXT file
		//So, we create the object that will hold the TXT file
		InputFile inputFile = new InputFile();
		inputFile.open("polygon.txt");
		
		//Now, we define some variables to read the data from the TXT file
		// I assume that only we have two convex polygons as input
		List<List<Point>> polygon = new ArrayList<List<Point>>(); //Here all the polygons are stored
		int numberOfPolygons=0; //Number of polygons to intersect
		int numberOfSides=0; //I'll save temporarily the number of sides here  
		float xCoordinate=0.0f; //I'll save temporarily the x coordinate from the TXT file here
		float yCoordinate=0.0f; //I'll save temporarily the y coordinate from the TXT file here
		
		//Begin of the reading. It will stop when the end of the whole file is reached
		while (!inputFile.eof()) {
			numberOfPolygons=Integer.parseInt(inputFile.readString());//Get the number of polygons
			for(int i=0;i<numberOfPolygons; i++) { //Start to read the coordinates of polygon i
				inputFile.readString(); //This is the "P", I don't need to store it
				numberOfSides=Integer.parseInt(inputFile.readString()); //We read the number of sides of the polygon and store it to use later
				//Now, we use the value of "numberOfSides" to read the coordinates of the polygon
				polygon.add(i,new ArrayList<Point>()); //Create the space for polygon i
				for(int j=0; j<numberOfSides; j++) {
					//We fulfill the "j" point by reading the "x" and "y" coordinate from the TXT for the first polygon
					xCoordinate=Float.valueOf(inputFile.readString()); 
					yCoordinate=Float.valueOf(inputFile.readString());
					polygon.get(i).add(new Point(xCoordinate,yCoordinate));//Now the coordinate is loaded into the polygon
				}				
			}
		}		
		for(int j=0; j<polygon.size();j++) {
			System.out.printf("Polygono: %d\n",j);
			for(int i=0; i<polygon.get(j).size();i++) {
				System.out.printf("(%.2f,%.2f)", polygon.get(j).get(i).x,polygon.get(j).get(i).y);	
			}
			System.out.printf("\n\n");
		}
		
		//== We can omit this part???? ==
		
		//Create a List of ExtendedPolygons
		List<PolygonExtended> polygonExtended = new ArrayList<PolygonExtended>(); //Here all the polygons are saved
		
		for (int i=0; i<polygon.size(); i++) {
			polygonExtended.add(new PolygonExtended(polygon.get(i))); //Create the space for polygon extended i
			polygonExtended.get(i).orderConvexPolygon(); //order the points clockwise
		}
		
		//== Until here???? ==
		
		//I create a PolygonExtended to define the new polygon defined by the intersection of the 2 input polygons
		PolygonExtended polyIntersection = new PolygonExtended(new ArrayList<Point>());
		
		//Get the points of the new polygon
		if(polygon.size()>2) {
			for(int i=0; i<polygon.size();i++) {
				if(polyIntersection.isEmpty()) {
					polyIntersection = convexGetIntersectionPolygonPoints(polygonExtended.get(i),polygonExtended.get(i+1));
				} else {
					polyIntersection = convexGetIntersectionPolygonPoints(polygonExtended.get(i),polyIntersection);
				}
			}
		} else {
			polyIntersection = convexGetIntersectionPolygonPoints(polygonExtended.get(0),polygonExtended.get(1));
		}
		//Order the points of the new polygon clockwise
		polyIntersection.orderConvexPolygon();
		//Print the coordinates of the new polygon
		try {
			System.out.printf("Polygon resulting of intersection:");
			for(int i=0; i<polyIntersection.getSize(); i++) {
				System.out.printf("\ncoordinate %d: (%.2f,%.2f)",i+1,polyIntersection.getXPoint(i),polyIntersection.getYPoint(i));
			}
		} catch (IndexOutOfBoundsException e) {
		    System.err.println("IndexOutOfBoundsException: " + e.getMessage());
		} 
	}
	
	//This method will get the points of the polygon made by intersecting two convex polygons
	public static PolygonExtended convexGetIntersectionPolygonPoints(PolygonExtended polyA, PolygonExtended polyB) {
		//This Polygon will store the points of the new polygon
		List<Point> newPolygonPoints = new ArrayList<Point>();		
		
		//First, the intersection points created by lines of the polygons crossing each other are going to be find
		//A line from polygon B is evaluated against the lines of polygon A and so on
		//There are as many lines as sides of the polygon so the length of the loop will be defined by the dimension of the polygon
		for(int i=0; i<polyB.getSize(); i++) {
			for(int j=0; j<polyA.getSize(); j++) {				
				//If we are not evaluating the last coordinate of Polygon A we can proceed. This is important because if that is the case we have to change the way we evaluate it.
				if(j!=(polyA.getSize()-1)) {
					//If not evaluating the last coordinate of Polygon B we can proceed
					if(i!=(polyB.getSize()-1)) {
						//any last point of any polygon is being evaluated so we can go on
						//Now, using the method "linesIntersect" of the class "Line2D" is possible to know if there is an intersection
						if(Line2D.linesIntersect(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(i+1),polyB.getYPoint(i+1)
								                ,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(j+1),polyA.getYPoint(j+1))){
							//Now add the intersection point to the polygon. I get the intersection points using the method "getIntersectionPointLine" which will make line equations with the input points.
							newPolygonPoints.add(getIntersectionPointLine(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(i+1),polyB.getYPoint(i+1)
					                									 ,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(j+1),polyA.getYPoint(j+1)));
						}
					} else {
						//As the last point of the polygon B is being evaluated we need to change the order of the points that are being evaluated
						if(Line2D.linesIntersect(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(0),polyB.getYPoint(0)
												,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(j+1),polyA.getYPoint(j+1))){
							//Now add the intersections points to the polygon
							newPolygonPoints.add(getIntersectionPointLine(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(0),polyB.getYPoint(0)
																		 ,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(j+1),polyA.getYPoint(j+1)));
						}	
					}
				} else {
					//As the last point of the polygon A is evaluated, the order of point evaluation has to change
					if(i!=polyB.getSize()-1) {
						//If not evaluating the last point of B then we can proceed with this order
						if(Line2D.linesIntersect(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(i+1),polyB.getYPoint(i+1)
								                ,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(0),polyA.getYPoint(0))){
							//Now add the intersections points to the polygon
							newPolygonPoints.add(getIntersectionPointLine(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(i+1),polyB.getYPoint(i+1)
					                									 ,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(0),polyA.getYPoint(0)));
						}
					} else {
						//As the last point of both polygons are being evaluated we have to come here
						if(Line2D.linesIntersect(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(0),polyB.getYPoint(0)
												,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(0),polyA.getYPoint(0))){
							//Now add the intersections points to the polygon
							newPolygonPoints.add(getIntersectionPointLine(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(0),polyB.getYPoint(0)
																		 ,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(0),polyA.getYPoint(0)));
						}	
					}				
				}
			}
		}
		
		//Now, look for some points that are part of the new polygon but not created by the intersection of two lines
		
		//The i point of polygon B will be evaluated against points of polygon A
		for(int i=0; i<polyB.getSize(); i++) {
			for(int j=0; j<polyA.getSize()-1; j++) {
				//If the X coordinate of the i point of polygon B is inside the range of two proximate points of polygon A we proceed to evaluate the Y points on the same way  
				if(new RangeFloat(polyA.getXPoint(j),polyA.getXPoint(j+1)).within(polyB.getXPoint(i))) {
					if(new RangeFloat(polyA.getYPoint(j),polyA.getYPoint(j+1)).within(polyB.getYPoint(i))) {
						//Now add the point to the polygon
						newPolygonPoints.add(new Point(polyB.getXPoint(i),polyB.getYPoint(i)));
					}
				}
			}
		}
		//The i point of polygon A will be evaluated against points of polygon B
		for(int i=0; i<polyA.getSize(); i++) {
			for(int j=0; j<polyB.getSize()-1; j++) {
				//If the X coordinate of the i point of polygon A is inside the range of two proximate points of polygon B we proceed to evaluate the Y points on the same way
				if(new RangeFloat(polyB.getXPoint(j),polyB.getXPoint(j+1)).within(polyA.getXPoint(i))) {
					if(new RangeFloat(polyB.getYPoint(j),polyB.getYPoint(j+1)).within(polyA.getYPoint(i))) {
						//Now add the point to the polygon
						newPolygonPoints.add(new Point(polyB.getXPoint(i),polyB.getYPoint(i)));
					}
				}
			}
		}
		
		//The Points are passed to the polygon that will be returned.
		PolygonExtended newPolygon = new PolygonExtended(newPolygonPoints);
		//The new polygon is ready to be delivered!
		return newPolygon;
	}
	
	//This method has 4 coordinates as input so it can create two lines and get the coordinate of the intersection of those 2 lines.
	public static Point getIntersectionPointLine (float ax1, float ay1, float ax2, float ay2, float bx1, float by1, float bx2, float by2) {
		
		float ma=0, mb=0, ba=0, bb=0, xPoint=0, yPoint=0;
		
		ma=((ay2-ay1)/(ax2-ax1));
		mb=((by2-by1)/(bx2-bx1));
		ba=(ay1-(ax1*ma));
		bb=(by1-(bx1*mb));
		xPoint=-1*(bb-ba)/(mb-ma);
		yPoint=(xPoint*mb)+bb;
		
		Point coordinate = new Point(xPoint,yPoint);
		
		return coordinate;
	}
	
	
	//This method is not used. It only evaluates if two polygons intersect on some point, it does not returns points, only a boolean value
	public static boolean Intersection(PolygonExtended polyA, PolygonExtended polyB) {
		boolean result = false;
		
		for(int i=0; i<polyB.getSize(); i++) {
			for(int j=0; j<polyA.getSize()-1; j++) {
				//System.out.printf("punto B a evaluar: %d (%.2f,%.2f)\n",i,polyB.getXPoint(i),polyB.getYPoint(i));
				if(new RangeFloat(polyA.getXPoint(j),polyA.getXPoint(j+1)).within(polyB.getXPoint(i))) {
					if(new RangeFloat(polyA.getYPoint(j),polyA.getYPoint(j+1)).within(polyB.getYPoint(i))) {
						//System.out.printf("el punto que intersecta es: (%.2f,%.2f)\n", polyB.getXPoint(i),polyB.getYPoint(i));
						result = true;
					}
				}
			}
		}
		
		for(int i=0; i<polyA.getSize(); i++) {
			for(int j=0; j<polyB.getSize()-1; j++) {
				//System.out.printf("punto A a evaluar: %d (%.2f,%.2f)\n",i,polyA.getXPoint(i),polyA.getYPoint(i));
				if(new RangeFloat(polyB.getXPoint(j),polyB.getXPoint(j+1)).within(polyA.getXPoint(i))) {
					if(new RangeFloat(polyB.getYPoint(j),polyB.getYPoint(j+1)).within(polyA.getYPoint(i))) {
						//System.out.printf("el punto que intersecta es: (%.2f,%.2f)\n", polyA.getXPoint(i),polyA.getYPoint(i));
						result = true;
					}
				}
			}
		}
		
		return result;
	}

}


