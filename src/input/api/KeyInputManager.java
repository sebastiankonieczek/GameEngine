package input.api;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyInputManager implements KeyListener
{
   private final Map<Integer, Action>   keyPressedActions_  = new HashMap<Integer, Action>();
   private final Map<Integer, Action>   keyReleasedActions_ = new HashMap<Integer, Action>();
   private final Map<Character, Action> keyTypedActions_    = new HashMap<Character, Action>();

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void registerKeyPressedAction( final int keyCode, final Action actionToHandle )
   {
      keyPressedActions_.put( keyCode, actionToHandle );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void registerKeyReleasedAction( final int keyCode, final Action actionToHandle )
   {
      keyReleasedActions_.put( keyCode, actionToHandle );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void registerKeyTypedAction( final char keyChar, final Action actionToHandle )
   {
      keyTypedActions_.put( keyChar, actionToHandle );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void removeKeyPressedAction( final int keyCode )
   {
      keyPressedActions_.remove( keyCode );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void removeKeyReleasedAction( final int keyCode )
   {
      keyReleasedActions_.remove( keyCode );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void removeKeyTypedAction( final char keyChar )
   {
      keyTypedActions_.remove( keyChar );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void clearKeyPressedActions()
   {
      keyPressedActions_.clear();
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void clearKeyReleasedActions()
   {
      keyReleasedActions_.clear();
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public final void clearKeyTypedActions()
   {
      keyTypedActions_.clear();
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public void keyPressed( final KeyEvent e )
   {
      final int keyCode = e.getKeyCode();
      if ( keyCode == KeyEvent.VK_UNDEFINED ) {
         return;
      }

      if ( keyPressedActions_.containsKey( keyCode ) ) {
         keyPressedActions_.get( keyCode ).handle( e );
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public void keyReleased( final KeyEvent e )
   {
      final int keyCode = e.getKeyCode();
      if ( keyCode == KeyEvent.VK_UNDEFINED ) {
         return;
      }

      if ( keyReleasedActions_.containsKey( keyCode ) ) {
         keyReleasedActions_.get( keyCode ).handle( e );
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public void keyTyped( final KeyEvent e )
   {
      final char keyChar = e.getKeyChar();
      if( keyChar == KeyEvent.CHAR_UNDEFINED ) {
         System.out.println( "Key \"" + e.getKeyChar() + "\" is not a valid character!" );
         return;
      }

      if( keyTypedActions_.containsKey( keyChar ) ) {
         keyTypedActions_.get( keyChar ).handle( e );
      }
   }
}
