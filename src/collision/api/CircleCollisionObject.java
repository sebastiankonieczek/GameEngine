package collision.api;

import java.awt.Point;

public class CircleCollisionObject implements CollidableObject
{
   private final int radius_;
   private final Point center_;

   private Point objectLocationOnMap_ = new Point( -1, -1 );

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   private CircleCollisionObject( final int radius, final Point center )
   {
      radius_ = radius;
      center_ = center;
   }

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   public void setObjectLocationOnMap( final Point location )
   {
      objectLocationOnMap_ = location;
   }

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public boolean collides( final CollidableObject lhs )
   {
      if( lhs instanceof CircleCollisionObject ) {
         final CircleCollisionObject cco = (CircleCollisionObject)lhs;
         return CollisionApi.collides( this, cco );
      }

      if( lhs instanceof RectangleCollisionObject ) {
         final RectangleCollisionObject rco = (RectangleCollisionObject)lhs;
         return CollisionApi.collides( rco, this );
      }

      throw new IllegalArgumentException( "Collidable Object if type \"" + lhs.getClass().getName() + "\" not supported!" );
   }

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   public static CircleCollisionObject create( final int radius, final Point center )
   {
      return new CircleCollisionObject( radius, center );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public Point getLocationOnMap()
   {
      return objectLocationOnMap_;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public Point getCenter()
   {
      return center_;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public int getRadius()
   {
      return radius_;
   }
}
