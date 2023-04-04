package collision.api;

public interface CollisionHandler
{
   public < T extends CollisionHandler > boolean collides( final T collisionHandler );
}
