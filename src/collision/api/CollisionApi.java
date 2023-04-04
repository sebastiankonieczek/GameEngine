package collision.api;

import java.awt.Point;
import java.awt.Rectangle;

import javafx.geometry.Dimension2D;

public class CollisionApi
{
   public static < T extends CircleCollisionObject > Boolean collides( final T rhs, final T lhs )
   {
      final Point realCenter = new Point( rhs.getLocationOnMap().x + rhs.getCenter().x,
                                          rhs.getLocationOnMap().y + rhs.getCenter().y );
      final Point centerLhs = new Point( lhs.getLocationOnMap().x + lhs.getCenter().x,
                                         lhs.getLocationOnMap().y + lhs.getCenter().y );

      final int minimalDistance = rhs.getRadius() + lhs.getRadius();

      final Point distance = new Point( realCenter.x - centerLhs.x, realCenter.y - centerLhs.y );
      return ( ( distance.x * distance.x ) + (  distance.y * distance.y ) ) < ( minimalDistance * minimalDistance );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public static < T extends RectangleCollisionObject > boolean collides( final T rhs, final T lhs )
   {
      final Point rhsLocation = rhs.getLocationOnMap();
      final Point lhsLocation = lhs.getLocationOnMap();
      final Rectangle lhsBoundaries = lhs.getBoundaries();
      final Rectangle rhsBoundaries = rhs.getBoundaries();

      return rhsLocation.x <= lhsLocation.x + lhsBoundaries.width
             && rhsLocation.x + rhsBoundaries.width >= lhsLocation.x
             && rhsLocation.y <= lhsLocation.y + lhsBoundaries.height
             && rhsLocation.y + rhsBoundaries.height >= lhsLocation.y;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public static < T extends RectangleCollisionObject, U extends CircleCollisionObject > boolean collides( final T rhs, final U lhs )
   {
      final Point rhsLocation = rhs.getLocationOnMap();
      final Point centerLhs = new Point( lhs.getLocationOnMap().x + lhs.getCenter().x,
                                         lhs.getLocationOnMap().y + lhs.getCenter().y );
      final Rectangle rhsBoundaries = rhs.getBoundaries();
//
//      final boolean centerOfCircleWithinRectangle = rhsLocation.x <= centerLhs.x
//      && rhsLocation.x + rhsBoundaries.width >= centerLhs.x
//      && rhsLocation.y <= centerLhs.y
//      && rhsLocation.y + rhsBoundaries.height >= centerLhs.y;
//
//      if( centerOfCircleWithinRectangle ) {
//         return true;
//      }
//
      final int squareRadius = (int)Math.pow( lhs.getRadius(), 2 );
//      final boolean rectangleCornersWithinCircle =
//
//      if( rectangleCornersWithinCircle ) {
//         return true;
//      }

      // this is special for rectangles without rotation, needs to be refactored to match all rectangles in a 2d area
      final double rectCenterX = rhsLocation.x + ( .5 * rhsBoundaries.width );
      final double rectCenterY = rhsLocation.y + ( .5 * rhsBoundaries.height );

      final Dimension2D distanceVector = new Dimension2D( Math.abs( centerLhs.x - rectCenterX ), Math.abs( centerLhs.y - rectCenterY ) );
      final Dimension2D rectQuadrantSizeVector = new Dimension2D( rhsBoundaries.width * .5, rhsBoundaries.height * .5 );

      // the circle is too far away from the rectangle, no intersection possible
      if( distanceVector.getWidth() > rectQuadrantSizeVector.getWidth() + lhs.getRadius()
          || distanceVector.getHeight() > rectQuadrantSizeVector.getHeight() + lhs.getRadius() ) {
         return false;
      }

      // the circle intersects the inner circle, intersection guaranteed
      if( distanceVector.getWidth() <= rectQuadrantSizeVector.getWidth()
          || distanceVector.getHeight() <= rectQuadrantSizeVector.getHeight() ) {
         return true;
      }

      return checkRectangleCorners( rhsLocation, rhsBoundaries, centerLhs, squareRadius );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private static boolean checkRectangleCorners( final Point rhsLocation,
                                                 final Rectangle rhsBoundaries,
                                                 final Point centerLhs,
                                                 final int squareRadius )
   {
      final Point leftUpperCorner = rhsLocation;
      final int leftUpperCornerValue = circleEquation( leftUpperCorner, centerLhs );

      if( leftUpperCornerValue <= squareRadius ) {
         return true;
      }

      final Point rightUpperCorner = new Point( rhsLocation.x + rhsBoundaries.width, rhsLocation.y );
      final int rightUpperCornerValue = circleEquation( rightUpperCorner, centerLhs );

      if( rightUpperCornerValue <= squareRadius ) {
         return true;
      }

      final Point leftLowerCorner = new Point( rhsLocation.x, rhsLocation.y + rhsBoundaries.height );
      final int leftLowerCornerValue = circleEquation( leftLowerCorner, centerLhs );

      if( leftLowerCornerValue <= squareRadius ) {
         return true;
      }

      final Point rightLowerCorner = new Point( rhsLocation.x + rhsBoundaries.width, rhsLocation.y + rhsBoundaries.height );
      final int rightLowerCornerValue = circleEquation( rightLowerCorner, centerLhs );

      if( rightLowerCornerValue <= squareRadius ) {
         return true;
      }

      return false;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private static int circleEquation( final Point rhsLocation, final Point centerLhs )
   {
      return square( rhsLocation.x - centerLhs.x ) + square( rhsLocation.y - centerLhs.y );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private static int square( final double base )
   {
      return (int) Math.pow( base, 2 );
   }

}
