package polygon;

import java.util.*;
import iofiles.*;
import java.awt.geom.Line2D;

public class main {

	public static void main(String[] args) {
		
		InputFile inputFile = new InputFile();
		inputFile.open("polygon.txt");

		List<Point> polygonA = new ArrayList<Point>();
		List<Point> polygonB = new ArrayList<Point>();
		int numberOfSides=0;
		int xCoordinate=0;
		int yCoordinate=0;
		
		while (!inputFile.eof()) {
			inputFile.readString();
			numberOfSides=Integer.parseInt(inputFile.readString());
			
			for(int j=0; j<numberOfSides; j++) {
				xCoordinate=Integer.parseInt(inputFile.readString());
				yCoordinate=Integer.parseInt(inputFile.readString());
				polygonA.add(new Point(xCoordinate,yCoordinate));
			}
			inputFile.readString();
			numberOfSides=Integer.parseInt(inputFile.readString());
			
			for(int j=0; j<numberOfSides; j++) {
				xCoordinate=Integer.parseInt(inputFile.readString());
				yCoordinate=Integer.parseInt(inputFile.readString());
				polygonB.add(new Point(xCoordinate,yCoordinate));
			}
		}		
		
		TwoDimensionsPolygon myPolyA = new TwoDimensionsPolygon(polygonA);
		TwoDimensionsPolygon myPolyB = new TwoDimensionsPolygon(polygonB);
		
		myPolyA.orderConvexPolygon();	
		myPolyB.orderConvexPolygon();
		/*
		if(Intersection(myPolyA, myPolyB)) {
			System.out.printf("Se intersectan!!!\n");
		} else {
			System.out.printf("No Se intersectan!!!\n");
		}*/
		
		System.out.printf("(%.2f,%.2f)\n",myPolyB.getXPoint(1),myPolyB.getYPoint(1));
		System.out.printf("(%.2f,%.2f)\n",myPolyB.getXPoint(2),myPolyB.getYPoint(2));
		System.out.printf("(%.2f,%.2f)\n",myPolyA.getXPoint(4),myPolyA.getYPoint(4));
		System.out.printf("(%.2f,%.2f)\n",myPolyA.getXPoint(0),myPolyA.getYPoint(0));
		
		if(Line2D.linesIntersect(myPolyB.getXPoint(1),myPolyB.getYPoint(1),myPolyB.getXPoint(2),myPolyB.getYPoint(2)
                				,myPolyA.getXPoint(4),myPolyA.getYPoint(4),myPolyA.getXPoint(0),myPolyA.getYPoint(0))) {
			System.out.printf("Interseccion!!\n");
		}else {
			System.out.printf("Nada man!!\n");
		}
		
		if(Line2D.linesIntersect(-1, 8, 5, 3
								, 0, 0, -1, 10)) {
			System.out.printf("Interseccion!!\n");
		}else {
			System.out.printf("Nada man!!\n");
		}
		
		if(Line2D.linesIntersect(0, 0, 0.5, 0.5, 0, -0.5, -0.5, 0)) {
			System.out.printf("Interseccion!!\n");
		}else {
			System.out.printf("Nada man!!\n");
		}
		
		TwoDimensionsPolygon polyIntersection;
		polyIntersection = convexGetIntersectionPolygonPoints(myPolyA,myPolyB);
		polyIntersection.orderConvexPolygon();
		
		for(int i=0; i<polyIntersection.getSize(); i++) {
			System.out.printf("coordenada %d: (%.2f,%.2f)\n",i+1,polyIntersection.getXPoint(i),polyIntersection.getYPoint(i));
		}
		
		
	}
	
	public static TwoDimensionsPolygon convexGetIntersectionPolygonPoints(TwoDimensionsPolygon polyA, TwoDimensionsPolygon polyB) {
		List<Point> newPolygonPoints = new ArrayList<Point>();		
		
		for(int i=0; i<polyB.getSize(); i++) {
			for(int j=0; j<polyA.getSize(); j++) {
				System.out.printf("punto B a evaluar: %d (%.2f,%.2f)\n",i,polyB.getXPoint(i),polyB.getYPoint(i));
				if(j!=(polyA.getSize()-1)) {
					System.out.printf("entro: j != polyA size\n");
					if(i!=(polyB.getSize()-1)) {
						System.out.printf("entro: i != polyB size\n");
						if(Line2D.linesIntersect(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(i+1),polyB.getYPoint(i+1)
								                ,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(j+1),polyA.getYPoint(j+1))){
							System.out.printf("entro");
							newPolygonPoints.add(getIntersectionPointLine(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(i+1),polyB.getYPoint(i+1)
					                									 ,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(j+1),polyA.getYPoint(j+1)));
						}
					} else {
						System.out.printf("entro: i == polyB size - 1\n");
						if(Line2D.linesIntersect(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(0),polyB.getYPoint(0)
												,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(j+1),polyA.getYPoint(j+1))){
							System.out.printf("entro");
							newPolygonPoints.add(getIntersectionPointLine(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(0),polyB.getYPoint(0)
																		 ,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(j+1),polyA.getYPoint(j+1)));
						}	
					}
				} else {
					System.out.printf("entro: j == polyA size - 1\n");
					if(i!=polyB.getSize()-1) {
						System.out.printf("entro: i != polyB size\n");
						if(Line2D.linesIntersect(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(i+1),polyB.getYPoint(i+1)
								                ,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(0),polyA.getYPoint(0))){
							System.out.printf("entro");
							newPolygonPoints.add(getIntersectionPointLine(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(i+1),polyB.getYPoint(i+1)
					                									 ,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(0),polyA.getYPoint(0)));
						}
					} else {
						System.out.printf("entro: i == polyB size - 1\n");
						if(Line2D.linesIntersect(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(0),polyB.getYPoint(0)
												,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(0),polyA.getYPoint(0))){
							System.out.printf("entro!!\n");
							newPolygonPoints.add(getIntersectionPointLine(polyB.getXPoint(i),polyB.getYPoint(i),polyB.getXPoint(0),polyB.getYPoint(0)
																		 ,polyA.getXPoint(j),polyA.getYPoint(j),polyA.getXPoint(0),polyA.getYPoint(0)));
						}	
					}				
				}
			}
		}
		
		for(int i=0; i<polyB.getSize(); i++) {
			for(int j=0; j<polyA.getSize()-1; j++) {
				System.out.printf("punto B a evaluar: %d (%.2f,%.2f)\n",i,polyB.getXPoint(i),polyB.getYPoint(i));
				if(new RangeFloat(polyA.getXPoint(j),polyA.getXPoint(j+1)).within(polyB.getXPoint(i))) {
					if(new RangeFloat(polyA.getYPoint(j),polyA.getYPoint(j+1)).within(polyB.getYPoint(i))) {
						System.out.printf("el punto que intersecta es: (%.2f,%.2f)\n", polyB.getXPoint(i),polyB.getYPoint(i));
						newPolygonPoints.add(new Point(polyB.getXPoint(i),polyB.getYPoint(i)));
					}
				}
			}
		}
		
		for(int i=0; i<polyA.getSize(); i++) {
			for(int j=0; j<polyB.getSize()-1; j++) {
				System.out.printf("punto A a evaluar: %d (%.2f,%.2f)\n",i,polyA.getXPoint(i),polyA.getYPoint(i));
				if(new RangeFloat(polyB.getXPoint(j),polyB.getXPoint(j+1)).within(polyA.getXPoint(i))) {
					if(new RangeFloat(polyB.getYPoint(j),polyB.getYPoint(j+1)).within(polyA.getYPoint(i))) {
						System.out.printf("el punto que intersecta es: (%.2f,%.2f)\n", polyA.getXPoint(i),polyA.getYPoint(i));
						newPolygonPoints.add(new Point(polyB.getXPoint(i),polyB.getYPoint(i)));
					}
				}
			}
		}
		
		TwoDimensionsPolygon newPolygon = new TwoDimensionsPolygon(newPolygonPoints);
		
		for(int i=0; i<newPolygon.getSize(); i++) {
			System.out.printf("coordenada %d: (%.2f,%.2f)\n",i+1,newPolygon.getXPoint(i),newPolygon.getYPoint(i));
		}
		
		return newPolygon;
	}
	
	public static Point getIntersectionPointLine (float ax1, float ay1, float ax2, float ay2, float bx1, float by1, float bx2, float by2) {
		
		float ma=0, mb=0, ba=0, bb=0, xPoint=0, yPoint=0;
		
		ma=((ay2-ay1)/(ax2-ax1));
		mb=((by2-by1)/(bx2-bx1));
		ba=(ay1-(ax1*ma));
		bb=(by1-(bx1*mb));
		xPoint=-1*(bb-ba)/(mb-ma);
		yPoint=(xPoint*mb)+bb;
		
		Point coordenate = new Point(xPoint,yPoint);
		
		return coordenate;
	}
	
	public static boolean Intersection(TwoDimensionsPolygon polyA, TwoDimensionsPolygon polyB) {
		boolean result = false;
		
		for(int i=0; i<polyB.getSize(); i++) {
			for(int j=0; j<polyA.getSize()-1; j++) {
				System.out.printf("punto B a evaluar: %d (%.2f,%.2f)\n",i,polyB.getXPoint(i),polyB.getYPoint(i));
				if(new RangeFloat(polyA.getXPoint(j),polyA.getXPoint(j+1)).within(polyB.getXPoint(i))) {
					if(new RangeFloat(polyA.getYPoint(j),polyA.getYPoint(j+1)).within(polyB.getYPoint(i))) {
						System.out.printf("el punto que intersecta es: (%.2f,%.2f)\n", polyB.getXPoint(i),polyB.getYPoint(i));
						result = true;
					}
				}
			}
		}
		
		for(int i=0; i<polyA.getSize(); i++) {
			for(int j=0; j<polyB.getSize()-1; j++) {
				System.out.printf("punto A a evaluar: %d (%.2f,%.2f)\n",i,polyA.getXPoint(i),polyA.getYPoint(i));
				if(new RangeFloat(polyB.getXPoint(j),polyB.getXPoint(j+1)).within(polyA.getXPoint(i))) {
					if(new RangeFloat(polyB.getYPoint(j),polyB.getYPoint(j+1)).within(polyA.getYPoint(i))) {
						System.out.printf("el punto que intersecta es: (%.2f,%.2f)\n", polyA.getXPoint(i),polyA.getYPoint(i));
						result = true;
					}
				}
			}
		}
		
		return result;
	}

}


