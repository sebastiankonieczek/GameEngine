package loop;

import state.api.StateRegistry;

/**
 * Abstract base class for GameLoops
 * <br>
 * updates all states in existing in {@link StateRegistry}
 * <br>
 * a single game loop will last at least < minimumTickDuration > milliseconds
 * <br>
 * gives chance to handle events and do some clean up after all states have been updated
 * <br>
 * the handling of events will be performed before updating states
 *
 * @author Hammington
 */

public abstract class GameLoop extends Thread
{
   private long                minimumTickDuration_;

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   public GameLoop( final long tickDuration )
   {
      minimumTickDuration_ = tickDuration;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   @Override
   public void run()
   {
      long duration = 0;
      while ( true ) {
         if ( duration < minimumTickDuration_ ) {
            try {
               sleep( minimumTickDuration_ - duration );
            } catch ( final InterruptedException e ) {
               e.printStackTrace();
            }
            duration = minimumTickDuration_;
         }
         final long start = System.currentTimeMillis();
         updateStates( duration );
         duration = System.currentTimeMillis() - start;
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   private synchronized void updateStates( final long duration )
   {
      long innerDuration = duration;
      long start = System.currentTimeMillis();
      handleEventHook( duration );
      innerDuration += System.currentTimeMillis() - start;

      final StateRegistry registry = StateRegistry.getInstance();
      for ( final state.api.State state : registry.getValues() ) {
         start = System.currentTimeMillis();
         state.updateState( innerDuration );
         innerDuration += System.currentTimeMillis() - start;
      }
      updateStatesHook( innerDuration );
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   /**
    * called after update of states in general already proceeded, to give chance to do some clean up etc.
    * 
    * @param duration - duration the last loop took
    */
   abstract protected void updateStatesHook( final long duration);

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   /**
    * called before update of states is done to give chance to influence states during current loop
    * 
    * @param duration - duration the last loop took
    */
   abstract protected void handleEventHook( final long duration );

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   /**
    * @return the minimum duration of a single game loop in milliseconds
    */
   public synchronized long minimumTickDuration()
   {
      return minimumTickDuration_;
   }

   ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   /**
    * @param minimumTickDuration - the minimum duration of a single game loop in milliseconds
    */
   public synchronized void minimumTickDuration( final long minimumTickDuration )
   {
      minimumTickDuration_ = minimumTickDuration;
   }
}
