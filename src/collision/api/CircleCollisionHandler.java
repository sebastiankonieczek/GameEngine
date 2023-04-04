package collision.api;

import java.awt.Point;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class CircleCollisionHandler implements CollisionHandler
{
   private final TreeMap< String, ArrayList< CircleCollisionObject > > collisionObjectLevels_;
   private Point locationOnMap_;

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   private CircleCollisionHandler( final TreeMap< String, ArrayList< CircleCollisionObject > > collisionObjectLevels )
   {
      collisionObjectLevels_ = collisionObjectLevels;
   }

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   public static final CircleCollisionHandler create( final TreeMap< String, ArrayList< CircleCollisionObject > > collisionObjectLevels )
   {
      return new CircleCollisionHandler( collisionObjectLevels );
   }

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   public void setLocationOnMap( final Point locationOnMap )
   {
      locationOnMap_ = locationOnMap;

      for( final ArrayList< CircleCollisionObject > list : collisionObjectLevels_.values() ) {
         for( final CircleCollisionObject object : list ) {
            object.setObjectLocationOnMap( locationOnMap_ );
         }
      }
   }

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public boolean collides( final CollisionHandler value )
   {
      if( value instanceof CircleCollisionHandler ) {
         final CircleCollisionHandler lhs = (CircleCollisionHandler)value;

         final TreeMap< String, ArrayList< CircleCollisionObject > > root;
         final TreeMap< String, ArrayList< CircleCollisionObject > > child;

         if( collisionObjectLevels_.size() > lhs.collisionObjectLevels_.size() ) {
            root = collisionObjectLevels_;
            child = lhs.collisionObjectLevels_;
         }
         else {
            root = lhs.collisionObjectLevels_;
            child = collisionObjectLevels_;
         }

         for( final String key : root.keySet() ) {
            final ArrayList< CircleCollisionObject > collisionObjectsRoot = root.get( key );
            ArrayList< CircleCollisionObject > collisionObjectsChild = child.get( key );

            if( collisionObjectsChild == null ) {
               collisionObjectsChild = child.lastEntry().getValue();
            }

            boolean collisionFound = false;
            for( final CircleCollisionObject collisionObjectRoot : collisionObjectsRoot ) {
               for( final CircleCollisionObject collisionObjectChild : collisionObjectsChild ) {
                  if( collisionObjectRoot.collides( collisionObjectChild ) ) {
                     // collision found on this detail level, check next detail level for collision
                     collisionFound = true;
                     break;
                  }
               }
            }

            // no collision found on the current detail level, no "real" collision
            if( !collisionFound ) {
               return false;
            }
         }

         // collision detected through all detail levels
         return true;
      }

      if( value instanceof RectangleCollisionHandler ) {
         final RectangleCollisionObject rectangleCollisionObject = ( (RectangleCollisionHandler)value ).getCollisionObject();
         for( final String key : collisionObjectLevels_.keySet() ) {
            final List< CircleCollisionObject > v = collisionObjectLevels_.get( key );
            boolean collisionFound = false;
            for( final CircleCollisionObject collisionObject : v ) {
               if( collisionObject.collides( rectangleCollisionObject ) ) {
                  // collision found on this detail level, check next detail level for collision
                  collisionFound = true;
                  break;
               }
            }

            // no collision found on the current detail level, no "real" collision
            if( !collisionFound ) {
               System.out.println( "no collision on detail level " + key );
               return false;
            }
         }

         // collision detected through all detail levels
         return true;
      }

      throw new InvalidParameterException( "By now only CircleCollisionHanlder is allowed" );
   }
}
