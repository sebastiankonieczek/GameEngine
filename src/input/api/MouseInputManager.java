package input.api;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseInputManager implements MouseListener, MouseMotionListener, MouseWheelListener
{
   private static Action mouseClickedAction_;

   private static Action mouseEnteredAction_;
   private static Action mouseExitedAction_;

   private static Action mouseMovedAction_;
   private static Action mouseDraggedAction_;

   private static Action mouseWheelMovedAction_;

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void addMouseClickedAction( final Action mouseClickedAction )
   {
      mouseClickedAction_ = mouseClickedAction;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void addMouseEnteredAction( final Action mouseEnteredAction )
   {
      mouseEnteredAction_ = mouseEnteredAction;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void addMouseExitedAction( final Action mouseExitedAction )
   {
      mouseExitedAction_ = mouseExitedAction;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void addMouseMovedAction( final Action mouseMovedAction )
   {
      mouseMovedAction_ = mouseMovedAction;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void addMouseDraggedAction( final Action mouseDraggedAction )
   {
      mouseDraggedAction_ = mouseDraggedAction;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void addMouseWheelMovedAction( final Action mouseWheelMovedAction )
   {
      mouseWheelMovedAction_ = mouseWheelMovedAction;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void removeMouseClickedAction()
   {
      mouseClickedAction_ = null;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void removeMouseEnteredAction()
   {
      mouseEnteredAction_ = null;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void removeMouseExitedAction()
   {
      mouseExitedAction_ = null;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void removeMouseMovedAction()
   {
      mouseMovedAction_ = null;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void removeMouseDraggedAction()
   {
      mouseDraggedAction_ = null;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void removeMouseWheelMovedAction()
   {
      mouseWheelMovedAction_ = null;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public void mouseClicked( final MouseEvent e )
   {
      if( mouseClickedAction_ != null ) {
         mouseClickedAction_.handle( e );
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public void mouseEntered( final MouseEvent e )
   {
      if( mouseEnteredAction_ != null ) {
         mouseEnteredAction_.handle( e );
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public void mouseExited( final MouseEvent e )
   {
      if( mouseExitedAction_ != null ) {
         mouseExitedAction_.handle( e );
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public void mousePressed( final MouseEvent e )
   {
      // ignore, implement on need
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public void mouseReleased( final MouseEvent e )
   {
      // ignore, implement on need
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public void mouseDragged( final MouseEvent e )
   {
      if( mouseDraggedAction_ != null ) {
         mouseDraggedAction_.handle( e );
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public void mouseMoved( final MouseEvent e )
   {
      if( mouseMovedAction_ != null ) {
         mouseMovedAction_.handle( e );
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public void mouseWheelMoved( final MouseWheelEvent e )
   {
      if( mouseWheelMovedAction_ != null ) {
         mouseWheelMovedAction_.handle( e );
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////
}
