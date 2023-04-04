package graphics.api;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

public abstract class GraphicsLayer extends GraphicsObject implements Layer
{
   public GraphicsLayer( final Dimension size, final Point location )
   {
      super( size, location );
   }

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   public abstract void render( final Graphics2D graphics);
}
