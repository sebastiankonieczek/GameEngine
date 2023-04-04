package graphics.api;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * {@link BaseLayer} represents a {@link GraphicsLayer} container.
 * <br>
 * It's only function is to handle {@link GraphicsLayer} objects. It only
 * paints it's children components, nothing else.
 * <br>
 * For more information see class {@link JLayeredPane}.
 * <br>
 * @author Hammington
 */

public final class BaseLayer extends JPanel implements Layer
{
   private final List< GraphicsLayer > layer_ = new ArrayList< GraphicsLayer >();
   private boolean forceRepaint_ = false;
   private GraphicsObject graphicsObject_;

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public static final BaseLayer create()
   {
      return new BaseLayer();
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private BaseLayer()
   {
      setIgnoreRepaint( true );
      setDoubleBuffered( true );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   /**
    * @return <code>true</code> if one of {@link BaseLayer} objects children needs to be repainted
    *         or child has been added or removed.
    */
   public final boolean needRepaint()
   {
      if( forceRepaint_ ) {
         forceRepaint_ = false;
         return true;
      }
      for( final GraphicsLayer graphicsLayer : layer_ ) {
         if( graphicsLayer.needRepaint() ) {
            return true;
         }
      }
      return false;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   /**
    * asks all children to update their contents
    */
   public final void updateContent()
   {
      for( final GraphicsLayer graphicsLayer : layer_ ) {
         graphicsLayer.updateContent();
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   /**
    * @param newLayer - the {@link GraphicsLayer} to append.
    *
    * adds specific layer to the container (0 - n). The first child appended represents top(index 0).
    * every following child represents bottom(index n).
    *
    * Forces repaint.
    *
    * For more information see {@link JLayeredPane}.
    */
   public final< T extends GraphicsLayer > void appendLayer( final T newLayer )
   {
      layer_.add( newLayer );
      forceRepaint_ = true;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   /**
    * @param layerToRemove - the {@link GraphicsLayer} to remove.
    *
    * Removes specific layer from the container and shifts all following layers to the top.
    *
    * Forces repaint.
    */
   public final< T extends GraphicsLayer > void removeLayer( final T layerToRemove )
   {
      layer_.remove( layerToRemove );
      forceRepaint_ = true;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public final void paint( final Graphics g )
   {
   }

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public final void repaint()
   {
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public final void update( final Graphics g )
   {
   }

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void render()
   {
      // create the buffer if not existant
      if( graphicsObject_ == null ) {
         graphicsObject_ = new GraphicsObject( getSize(), new Point( 0, 0 ) )
         {
            @Override
            public void updateContent()
            {
               drawContent();
            }

            @Override
            public boolean isValid()
            {
               return true;
            }

            @Override
            public void drawContent()
            {
               final Graphics2D createGraphics = getGraphics( true );
               clear( createGraphics );
               for( final GraphicsLayer layer : layer_ ) {
                  layer.render( createGraphics );
               }

                              createGraphics.dispose();
            }
         };
      }


      final Graphics graphics = getGraphics();

      graphicsObject_.updateContent();

      graphics.drawImage( graphicsObject_.content(), 0, 0, null );
      graphics.dispose();
   }
}
