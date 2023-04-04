package collision.api;

import java.awt.Point;

public interface CollidableObject
{
   public < T extends CollidableObject > boolean collides( final T collidableObject );

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public Point getLocationOnMap();
}
