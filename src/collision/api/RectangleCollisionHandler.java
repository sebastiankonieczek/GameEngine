package collision.api;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class RectangleCollisionHandler implements CollisionHandler
{
   private final Dimension size_;
   private Point location_;

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private RectangleCollisionHandler( final Dimension size )
   {
      size_ = size;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public static RectangleCollisionHandler create( final Dimension size )
   {
      return new RectangleCollisionHandler( size );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public void setLocationOnMap( final Point location )
   {
      location_ = location;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public RectangleCollisionObject getCollisionObject()
   {
      final RectangleCollisionObject collisionObject = RectangleCollisionObject.create( new Rectangle( location_, size_ ) );
      collisionObject.setLocationOnMap( location_ );
      return collisionObject;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public < T extends CollisionHandler > boolean collides( final T collisionHandler )
   {
      if( collisionHandler instanceof RectangleCollisionHandler ) {
         final RectangleCollisionObject lhs = getCollisionObject();
         final RectangleCollisionObject rhs = ( (RectangleCollisionHandler) collisionHandler ).getCollisionObject();
         return lhs.collides( rhs );
      }

      if( collisionHandler instanceof CircleCollisionHandler ) {
         return ((CircleCollisionHandler)collisionHandler).collides( this );
      }

      throw new IllegalArgumentException( "Invalid CollisionHandler" );
   }
}
