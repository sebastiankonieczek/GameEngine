package graphics.api;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class GraphicsObject
{
   private BufferedImage   content_;
   private Point           location_;
   private final Dimension size_;
   private Graphics2D graphics_;

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public GraphicsObject( final Dimension size )
   {
      initContent( size );
      size_ = size;
      location_ = new Point( 0, 0 );
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public GraphicsObject( final Dimension size,
                          final Point location )
   {
      initContent( size );
      size_ = size;
      location_ = location;
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public GraphicsObject( final Dimension size,
                          final Point location,
                          final int transparency )
   {
      initContent( size, transparency );
      size_ = size;
      location_ = location;
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public GraphicsObject( final Dimension size,
                          final Point location,
                          final int transparency,
                          final GraphicsEnvironment graphicsEnvironment )
   {
      initContent( size, transparency, graphicsEnvironment );
      size_ = size;
      location_ = location;
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public GraphicsObject( final Dimension size,
                          final Point location,
                          final int transparency,
                          final GraphicsEnvironment graphicsEnvironment,
                          final GraphicsDevice graphicsDevice )
   {
      initContent( size, transparency, graphicsEnvironment, graphicsDevice );
      size_ = size;
      location_ = location;
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public GraphicsObject( final Dimension size,
                          final Point location,
                          final int transparency,
                          final GraphicsEnvironment graphicsEnvironment,
                          final GraphicsDevice graphicsDevice,
                          final GraphicsConfiguration graphicsConfiguration )
   {
      initContent( size, transparency, graphicsEnvironment, graphicsDevice, graphicsConfiguration );
      size_ = size;
      location_ = location;
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   private void initContent( final Dimension size )
   {
      initContent( size, BufferedImage.TRANSLUCENT );
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   private void initContent( final Dimension size, final int transparency )
   {
      initContent( size, transparency, GraphicsEnvironment.getLocalGraphicsEnvironment() );
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   private void initContent( final Dimension size,
                             final int transparency,
                             final GraphicsEnvironment graphicsEnvironment )
   {
      initContent( size, transparency, graphicsEnvironment, graphicsEnvironment.getDefaultScreenDevice() );
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   private void initContent( final Dimension size,
                             final int transparency,
                             final GraphicsEnvironment graphicsEnvironment,
                             final GraphicsDevice screenDevice )
   {
      initContent( size,
                   transparency,
                   graphicsEnvironment,
                   screenDevice,
                   screenDevice.getDefaultConfiguration() );
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   private void initContent( final Dimension size,
                             final int transparency,
                             final GraphicsEnvironment graphicsEnvironment,
                             final GraphicsDevice screenDevice,
                             final GraphicsConfiguration graphicsConfiguration )
   {
      content_ = graphicsConfiguration.createCompatibleImage( size.width, size.height, transparency );
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   abstract public void drawContent();

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   abstract public void updateContent();

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   abstract public boolean isValid();

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public Point location()
   {
      return location_;
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public void location( final Point location )
   {
      location_ = location;
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public Dimension size()
   {
      return size_;
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public BufferedImage content()
   {
      return content_;
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public Graphics2D getGraphics( final boolean renew )
   {
      if( graphics_ == null || renew ) {
         graphics_ = content_.createGraphics();
      }

      return graphics_;
   }

   //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public boolean contains( final Point point )
   {
      return location_.x <= point.x
               && location_.y <= point.y
               && ( size_.width + location_.x ) >= point.x
               && ( size_.height + location_.y ) >= point.y;
   }

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void clear( final Graphics2D graphics )
   {
      clear( graphics, location_, size_ );
   }

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void clear( final Graphics2D graphics, final Point location, final Dimension size )
   {
      graphics.setBackground( new Color( .0f, .0f, .0f, .0f ) );
      graphics.clearRect( location.x, location.y, size.width, size.height );
   }
}
