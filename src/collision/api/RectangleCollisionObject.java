package collision.api;

import java.awt.Point;
import java.awt.Rectangle;

public class RectangleCollisionObject implements CollidableObject
{
   private final Rectangle boundaries_;
   private Point locationOnMap_;

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private RectangleCollisionObject( final Rectangle boundaries )
   {
      boundaries_ = boundaries;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public static RectangleCollisionObject create( final Rectangle boundaries )
   {
      return new RectangleCollisionObject( boundaries );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public < T extends CollidableObject > boolean collides( final T lhs )
   {
      if( lhs instanceof RectangleCollisionObject ) {
         return CollisionApi.collides( this, (RectangleCollisionObject)lhs );
      }

      if( lhs instanceof CircleCollisionObject ) {
         final CircleCollisionObject cco = (CircleCollisionObject)lhs;
         return CollisionApi.collides( this, cco );
      }

      throw new IllegalArgumentException( "Collidable Object if type \"" + lhs.getClass().getName() + "\" not supported!" );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public Point getLocationOnMap()
   {
      return locationOnMap_;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public void setLocationOnMap( final Point locationOnMap )
   {
      locationOnMap_  = locationOnMap;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public Rectangle getBoundaries()
   {
      return boundaries_;
   }

}
