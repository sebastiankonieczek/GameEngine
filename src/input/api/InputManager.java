package input.api;

import graphics.api.BaseLayer;

import java.awt.Component;

public class InputManager
{
   private MouseInputManager mouseInputManager_;
   private KeyInputManager   keyInputManager_;

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private InputManager( final boolean useMouseListener,
                         final boolean useKeyListener )
   {
      if( useMouseListener ) {
         mouseInputManager_ = new MouseInputManager();
      }
      if( useKeyListener ) {
         keyInputManager_ = new KeyInputManager();
      }
   }

   // /////////////////////////////////////////////////////////////////////////////////////////////////////////

   private void addListeners( final Component layer )
   {
      if( mouseInputManager_ != null ) {
         layer.addMouseListener( mouseInputManager_ );
         layer.addMouseMotionListener( mouseInputManager_ );
         layer.addMouseWheelListener( mouseInputManager_ );
      }

      if( keyInputManager_ != null ) {
         layer.addKeyListener( keyInputManager_ );
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private void removeListeners( final Component layer )
   {
      if( mouseInputManager_ != null ) {
         layer.removeMouseListener( mouseInputManager_ );
         layer.removeMouseMotionListener( mouseInputManager_ );
         layer.removeMouseWheelListener( mouseInputManager_ );
      }

      if( keyInputManager_ != null ) {
         layer.removeKeyListener( keyInputManager_ );
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public static final InputManager create( final boolean useMouseListener,
                                            final boolean useKeyListener )
   {
      return new InputManager( useMouseListener, useKeyListener );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final KeyInputManager keyInputManager()
   {
      return keyInputManager_;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final MouseInputManager mouseInputManager()
   {
      return mouseInputManager_;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public void addInputListeners( final BaseLayer layer )
   {
      addListeners( layer );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public void removeInputListeners( final BaseLayer layer )
   {
      removeListeners( layer );
   }
}
