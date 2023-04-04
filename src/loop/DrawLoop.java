package loop;

import graphics.api.BaseLayer;

import java.awt.GraphicsEnvironment;
import java.util.List;

/**
 * This class represents a draw loop.
 * <br>
 * It repaints a list of base layers at framerate according to monitors refresh rate
 * <br>
 * repaint is only performed if at least one base layer needs repaint
 * <br>
 * @author Hammington
 */

public final class DrawLoop extends Thread
{
   private final List< ? extends BaseLayer> baseLayers_;
   private final boolean debug_ = false;
   private final long minimumFrameDuration_;

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public static final DrawLoop create( final List< ? extends BaseLayer> baseLayers )
   {
      return new DrawLoop( baseLayers );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private DrawLoop( final List< ? extends BaseLayer> baseLayers )
   {
      baseLayers_ = baseLayers;
      final GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
      final int refreshRate = localGraphicsEnvironment.getDefaultScreenDevice().getDisplayMode().getRefreshRate();

      minimumFrameDuration_ = Math.round( 1d /( ( (double)refreshRate) / 1000) );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public void run()
   {
      boolean needFinalRepaint = false;
      long duration = minimumFrameDuration_;
      while( true ) {
         final long start = System.currentTimeMillis();

         if( duration < minimumFrameDuration_ ) {
            try {
               if( debug_ ) {
                  System.out.println( "sleep: " + ( minimumFrameDuration_ - duration ) );
               }
               sleep( minimumFrameDuration_ - duration );
            } catch( final InterruptedException ex ) {
               ex.printStackTrace();
            }
         }
         duration = System.currentTimeMillis() - start;

         if( debug_ && duration > 0 ) {

            if( ( 1000 / duration ) > 100 ) {
               System.out.println( "duration" + duration );
            }

            System.out.println( "fps:" + ( 1000 / duration ) );
         }

         if( updateContent() ) {
            needFinalRepaint = true;
            doRepaint();
         }
         else if( needFinalRepaint ) {
            needFinalRepaint = false;
            // do last repaint to clean view
            doRepaint();
         }

         duration = System.currentTimeMillis() - start;
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private void doRepaint()
   {
      for( final BaseLayer layer : baseLayers_ ) {
         layer.render();
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private boolean updateContent()
   {
      // NEEDS FIX C: probably store all dirty layers to not repaint all clean layers as well
      boolean needRepaint = false;
      for( final BaseLayer layer : baseLayers_ ) {
         layer.updateContent();
         needRepaint = needRepaint | layer.needRepaint();
      }
      return needRepaint;
   }
}
